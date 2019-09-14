package com.itsight.controller;

import com.itsight.domain.Chat;
import com.itsight.domain.pojo.ChatPOJO;
import com.itsight.service.ChatProcedureInvoker;
import com.itsight.service.ChatService;
import com.itsight.service.ConfiguracionClienteService;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

import static com.itsight.util.Enums.ResponseCode.ACTUALIZACION;

@Controller
@RequestMapping("/gestion/chat")
public class ChatController {

    private ChatService chatService;

    private ConfiguracionClienteService configuracionClienteService;

    private ChatProcedureInvoker chatProcedureInvoker;

    @Autowired
    public ChatController(ChatService chatService,
                          ConfiguracionClienteService configuracionClienteService,
                          ChatProcedureInvoker chatProcedureInvoker) {
        this.chatService = chatService;
        this.configuracionClienteService = configuracionClienteService;
        this.chatProcedureInvoker = chatProcedureInvoker;
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
                                                       @RequestParam String cliId,
                                                       @PathVariable String id){
        Integer chatId = Integer.parseInt(id);
        Boolean flagLeido = chatService.checkFlagLeidoById(chatId);
        if(flagLeido){
            configuracionClienteService.actualizarNotificacionChatById(Integer.parseInt(cliId));
        }
        Chat chat = chatService.findOne(chatId);
        chat.getUltimo().setMsg(msg);
        chat.getUltimo().setFecha(new Date());
        chat.getMensajes().add(chat.getUltimo());
        chat.setFlagLeido(false);
        chatService.save(chat);
        return Utilitarios.jsonResponse(ACTUALIZACION.get());
    }

    @PutMapping("/update/flag/{id}")
    public @ResponseBody String updateFlag(@PathVariable String id){
        Integer chatId = Integer.parseInt(id);
        return chatService.updateFlagById(chatId);
    }
}
