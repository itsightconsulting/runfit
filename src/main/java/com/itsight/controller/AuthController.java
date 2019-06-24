package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.advice.SecCustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.SecurityUser;
import com.itsight.domain.UsuarioRecover;
import com.itsight.domain.dto.PasswordDTO;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.repository.UsuarioRecoverRepository;
import com.itsight.service.EmailService;
import com.itsight.service.SecUserProcedureInvoker;
import com.itsight.service.SecurityUserService;
import com.itsight.service.impl.SecurityServiceImpl;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Controller
public class AuthController extends BaseController {

    private SecurityUserRepository securityUserRepository;

    private EmailService emailService;

    private UsuarioRecoverRepository usuarioRecoverRepository;

    private SecurityUserService securityUserService;

    @Autowired
    public AuthController(SecurityUserRepository securityUserRepository,
                          EmailService emailService,
                          UsuarioRecoverRepository usuarioRecoverRepository,
                          SecurityUserService securityUserService) {
        this.securityUserRepository = securityUserRepository;
        this.emailService = emailService;
        this.usuarioRecoverRepository = usuarioRecoverRepository;
        this.securityUserService = securityUserService;
    }

    @GetMapping(value = "/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            Model model
    ) {
        if (error != null) {
            if (error.equals("session-expired")) {
                model.addAttribute("expired", "expired");
            } else {
                model.addAttribute("error", "error");
            }
        }
        return ViewConstant.LOGIN;
    }

    //	@PreAuthorize("hasAnyRole({'ADMIN','USER'}) or hasAuthority('READ_PRIVILEGE')")
    @GetMapping(value = {"/bienvenido", "/"})
    public String welcome(){
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority: authorities){
            if(authority.getAuthority().equals("ROLE_TRAINER") || authority.getAuthority().equals("ROLE_ADMIN"))
                return ViewConstant.MAIN_TRAINER_RED;
            if(authority.getAuthority().equals("ROLE_RUNNER") || authority.getAuthority().equals("ROLE_STORE"))
                return ViewConstant.CLIENTE_INDEX;
        }
        return ViewConstant.PRINCIPAL;
    }

    @GetMapping(value = {"/inicio"})
    public String indexCliente() {
        return ViewConstant.CLIENTE_INDEX;
    }

    @GetMapping(value = "/accesoDenegado", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String permisosInsuficientes() {
        return ViewConstant.ERROR403;
    }

    @GetMapping(value = "/session-expirada", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String expiredSession() {
        return "{\"mensaje\": \"Su sessión ha expirado, por favor dirigirse a la pagina /login\"}";
    }

    @GetMapping(value = "/session-multiple")
    public String expiredBySessionMultiple() {
        return "lock";
    }

    @GetMapping(value = "/p/recuperar/password")
    public String recuperarPassword(){
        return ViewConstant.RECUPERAR_PASSWORD;
    }

    @PostMapping(value = "/p/recuperacion/password/check/username")
    public @ResponseBody ResponseEntity<String> recuperarPasswordCheckUsernameExiste(@RequestParam String username) throws CustomValidationException {
        if(username == null || username.trim().equals("")){
            return new ResponseEntity<>(Utilitarios.jsonResponse(Enums.Msg.VALIDACION_FALLIDA.get()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(Utilitarios.jsonResponse(securityUserService.recuperarPassword(username)), HttpStatus.OK);
    }

    @GetMapping(value = "/p/cambiar/password/{hshId}")
    public String vistaCambiarPassword(@PathVariable String hshId, @RequestParam String sc, Model model) throws SecCustomValidationException {
        Integer id = getDecodeHashIdSecCustom(new String(Base64.getDecoder().decode(sc.getBytes())), hshId);
        UsuarioRecover usurec = usuarioRecoverRepository.findById(id).orElse(null);
        if(usurec == null){
            return ViewConstant.P_ERROR404;
        }
        if(!usurec.isFlagRecover()){
            model.addAttribute("msg", "El enlace ha caducado");
            return ViewConstant.MAIN_INF_N;
        }
        if(usurec.getFechaLimite().before(new Date())){
            model.addAttribute("msg", "El enlace ha caducado");
            return ViewConstant.MAIN_INF_N;
        }
        return ViewConstant.CAMBIAR_PASSWORD;
    }

    @PostMapping(value = "/p/recuperacion/password/cambiar")
    public ResponseEntity<String> cambiarPassword(@ModelAttribute PasswordDTO passwordDTO) throws CustomValidationException {
        Integer id = getDecodeHashId(new String(Base64.getDecoder().decode(passwordDTO.getSchema().getBytes())), passwordDTO.getUserId());

        if(!passwordDTO.getNuevaPassword().equals(passwordDTO.getNuevaPasswordRe())){
            return new ResponseEntity<>(Utilitarios.jsonResponse(Enums.Msg.VALIDACION_FALLIDA.get()), HttpStatus.UNAUTHORIZED);
        }

        UsuarioRecover usuRec = usuarioRecoverRepository.findById(id).orElse(null);
        if(usuRec == null || !usuRec.isFlagRecover()){
            return new ResponseEntity<>(Utilitarios.jsonResponse(Enums.Msg.VALIDACION_FALLIDA.get()), HttpStatus.NOT_FOUND);
        }
        if(usuRec.getFechaLimite().before(new Date())){
            return new ResponseEntity<>(Utilitarios.jsonResponse(Enums.Msg.VALIDACION_FALLIDA.get()), HttpStatus.NOT_FOUND);
        }
        usuRec.setFlagRecover(false);
        usuarioRecoverRepository.saveAndFlush(usuRec);
        securityUserRepository.actualizarPassword(Utilitarios.encoderPassword(passwordDTO.getNuevaPassword()), id);
        String correo = securityUserRepository.getCorreoById(id);
        emailService.enviarCorreoInformativo("Contraseña actualizada", correo, "Su contraseña se ha actualizado con éxito");
        return new ResponseEntity<>(Utilitarios.jsonResponse(Enums.Msg.REGISTRO_EXITOSO.get()), HttpStatus.OK);
    }
}
