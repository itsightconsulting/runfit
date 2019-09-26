package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.advice.CustomValidationException;
import com.itsight.domain.Chat;
import com.itsight.domain.dto.ChatDTO;
import com.itsight.domain.dto.ConfiguracionClienteDTO;
import com.itsight.domain.pojo.ChatPOJO;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.itsight.util.Enums.CfsCliente.*;
import static com.itsight.util.Enums.Galletas.*;
import static com.itsight.util.Enums.ResponseCode.ACTUALIZACION;
import static com.itsight.util.Enums.TipoUsuario.CLIENTE;
import static com.itsight.util.Utilitarios.createCookie;

@Controller
@RequestMapping("/gestion/chat")
public class ChatController {

    private ChatService chatService;

    private ConfiguracionClienteService configuracionClienteService;

    private ChatProcedureInvoker chatProcedureInvoker;

    private RedFitnessService redFitnessService;

    @Autowired
    private ConfiguracionClienteProcedureInvoker configuracionClienteProcedureInvoker;

    @Autowired
    public ChatController(ChatService chatService,
                          ConfiguracionClienteService configuracionClienteService,
                          ChatProcedureInvoker chatProcedureInvoker,
                          RedFitnessService redFitnessService,
                          ConfiguracionClienteProcedureInvoker configuracionClienteProcedureInvoker) {
        this.chatService = chatService;
        this.configuracionClienteService = configuracionClienteService;
        this.chatProcedureInvoker = chatProcedureInvoker;
        this.redFitnessService = redFitnessService;
        this.configuracionClienteProcedureInvoker = configuracionClienteProcedureInvoker;
    }

    @GetMapping("/get/{id}")
    public @ResponseBody ResponseEntity<Chat> obtenerChatSegunId(@PathVariable String id){
        Chat chat = chatService.findOne(Integer.parseInt(id));
        if(chat != null){
            return new ResponseEntity<>(chat, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get/cliente")
    public @ResponseBody ResponseEntity<List<ChatPOJO>> obtenerChatBySessionId(HttpSession session){
        Integer clienteId = (Integer) session.getAttribute("id");
        List<ChatPOJO> lstChat = chatProcedureInvoker.getAllByClienteId(clienteId);
        if(!lstChat.isEmpty()){
            return new ResponseEntity<>(lstChat, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody String actualizarChatSegundId(@RequestParam String msg,
                                                       @RequestParam(required = false) String cliId,
                                                       @PathVariable String id) throws CustomValidationException, JsonProcessingException {
        if(msg.length()== 0 || msg.length()>255){
            throw new CustomValidationException(Enums.Msg.VALIDACION_FALLIDA.get(), Enums.ResponseCode.EX_VALIDATION_FAILED.get());
        }
        Integer chatId = Integer.parseInt(id);
        Boolean flagLeido = chatService.checkFlagLeidoById(chatId);
        if(flagLeido){
            if(cliId != null){
                configuracionClienteService.actualizarNotificacionChatById(Integer.parseInt(cliId));
            }
        }
        Chat chat = chatService.findOne(chatId);

        boolean isLastMessageFromTrainer = !chat.getUltimo().isEsSalida();
        boolean actualUserTrainer = false;

        chat.getUltimo().setMsg(msg);
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority: authorities){
            if(authority.getAuthority().equals("ROLE_TRAINER")){
                chat.getUltimo().setEsSalida(false);
                actualUserTrainer = true;
                break;
            }
            if(authority.getAuthority().equals("ROLE_RUNNER") || authority.getAuthority().equals("ROLE_STORE")){
                chat.getUltimo().setEsSalida(true);
                break;
            }
        }

        //Checking last message's date
        Date timestamp = new Date();

        if((isLastMessageFromTrainer && actualUserTrainer) || (!isLastMessageFromTrainer && !actualUserTrainer)){
            long lastMessageUnixDate = chat.getUltimo().getFecha().getTime();
            long actuMessageUnixDate = timestamp.getTime();
            boolean esContinuo = actuMessageUnixDate - lastMessageUnixDate < (15*1000);
            chat.getUltimo().setEsContinuo(esContinuo);
        }else{
            chat.getUltimo().setEsContinuo(false);
        }

        chat.getUltimo().setFecha(timestamp);
        chat.getMensajes().add(chat.getUltimo());
        chat.setFlagLeido(!actualUserTrainer);
        chat.setFechaModificacion(new Date());
        chatService.save(chat);
        return new ObjectMapper().writeValueAsString(chat.getMensajes());
//        return Utilitarios.jsonResponse(ACTUALIZACION.get());
    }

    @PutMapping("/update/flag/{id}")
    public @ResponseBody String updateFlag(@PathVariable String id, HttpSession session){
        Integer chatId = Integer.parseInt(id);
        Integer cliId = (Integer) session.getAttribute("id");
        return chatService.updateFlagById(chatId, cliId);
    }

    @PostMapping(value = "/primer/mensaje")
    public @ResponseBody
    String enviarCorreoATrainerByPrimerChat(
            ChatDTO chat,
            HttpSession session) throws JsonProcessingException {
        Integer clienteId = (Integer) session.getAttribute("id");
        return redFitnessService.enviarNotificacionPersonal(chat, clienteId, CLIENTE.ordinal());
    }

    @GetMapping("/refresh/notificacion")
    public @ResponseBody ResponseEntity<String> refrescarCookieNotificacionChatCliente(HttpSession session){
        Integer id = (Integer) session.getAttribute("id");
        if(id != null){
            List<ConfiguracionClienteDTO> lstConfCli = configuracionClienteProcedureInvoker.getAllById(id);
            lstConfCli.forEach(e->{
                if(e.getNombre().equals(NOTIFICACION_CHAT.name())){
                    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
                    response.addCookie(createCookie(GLL_NOTIFICACION_CHAT.name(), e.getValor()));
                }
            });
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
