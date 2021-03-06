package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.advice.SecCustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.UsuarioRecover;
import com.itsight.domain.dto.PasswordDTO;
import com.itsight.repository.IdiomaRepository;
import com.itsight.repository.UsuarioRecoverRepository;
import com.itsight.service.SecurityUserService;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

import static com.itsight.util.Enums.Msg.CAMBIO_PASSWORD_PASADO;
import static com.itsight.util.Enums.Msg.ENLACE_CADUCADO;

@Controller
public class AuthController extends BaseController {

    private UsuarioRecoverRepository usuarioRecoverRepository;

    private SecurityUserService securityUserService;

    private IdiomaRepository idiomaRepository;

    @Autowired
    public AuthController(
                          UsuarioRecoverRepository usuarioRecoverRepository,
                          SecurityUserService securityUserService,
                          IdiomaRepository idiomaRepository) {
        this.usuarioRecoverRepository = usuarioRecoverRepository;
        this.securityUserService = securityUserService;
        this.idiomaRepository = idiomaRepository;
    }

    @GetMapping(value = "/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        if (error != null) {
            if (error.equals("true")) {
                model.addAttribute("error", "error");
            }
            else if(error.equals("session-expired")){
                model.addAttribute("error", "expired");
            }

            else if(error.equals("disabled")){
                model.addAttribute("error", "disabled");
            }

            else if(error.equals("checkout")){
                model.addAttribute("error", "checkout");
            }

            else if(error.equals("loggedin")){
                model.addAttribute("error", "loggedin");
            }
        }
        return ViewConstant.LOGIN;
    }

    //	@PreAuthorize("hasAnyRole({'ADMIN','USER'}) or hasAuthority('READ_PRIVILEGE')")
    @GetMapping(value = {"/bienvenido"})
    public String welcome(Model model){
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority: authorities){
            if(authority.getAuthority().equals("ROLE_ADMIN")){
                return "redirect:/"+ViewConstant.MAIN_USUARIO;
            }
            if(authority.getAuthority().equals("ROLE_TRAINER"))
                return ViewConstant.MAIN_TRAINER_RED;
            if(authority.getAuthority().equals("ROLE_RUNNER") || authority.getAuthority().equals("ROLE_STORE"))
                return ViewConstant.CLIENTE_INDEX;
            if(authority.getAuthority().equals("ROLE_GUEST")){
                model.addAttribute("idiomas", idiomaRepository.findAll());
                return ViewConstant.CLIENTE_VISITANTE;
            }
        }
        return ViewConstant.PRINCIPAL;
    }

    @GetMapping(value = "/")
    public String index() { return ViewConstant.MAIN_INICIO;}

    @GetMapping(value = "/accesoDenegado", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String permisosInsuficientes() {
        return ViewConstant.ERROR403;
    }

    @GetMapping(value = "/p/recuperar/password")
    public String recuperarPassword(){
        return ViewConstant.RECUPERAR_PASSWORD;
    }

    @GetMapping(value = "/pagos/resumen-anual")
    public String resumenAnual(){
        return ViewConstant.MAIN_PAGOS_RESUMEN_ANUAL;
    }

    @PostMapping(value = "/p/recuperacion/password/check/username")
    public @ResponseBody String recuperarPasswordCheckUsernameExiste(@RequestParam String username) throws CustomValidationException, InterruptedException {
        if(username == null || username.trim().equals("")){
            throw new CustomValidationException(Utilitarios.jsonResponse(Enums.Msg.VALIDACION_FALLIDA.get()));
        }
        return Utilitarios.jsonResponse(securityUserService.recuperarPassword(username.toLowerCase()));
    }

    @GetMapping(value = "/p/cambiar/password/{hshId}")
    public String vistaCambiarPassword(@PathVariable String hshId, @RequestParam String sc, Model model) throws SecCustomValidationException {
        Integer id = getDecodeHashIdSecCustom(Parseador.getDecodeBase64(sc), hshId);
        UsuarioRecover usurec = usuarioRecoverRepository.findById(id).orElse(null);
        if(usurec == null){
            return ViewConstant.P_ERROR404;
        }

        if(!usurec.isFlagRecover()){
            model.addAttribute("msg", CAMBIO_PASSWORD_PASADO.get());
            return ViewConstant.MAIN_INF_N;
        }

        if(usurec.getFechaLimite().before(new Date())){
            model.addAttribute("msg", ENLACE_CADUCADO.get());
            return ViewConstant.MAIN_INF_N;
        }
        return ViewConstant.CAMBIAR_PASSWORD;
    }

    @PostMapping(value = "/p/recuperacion/password/cambiar")
    public @ResponseBody String cambiarPassword(@ModelAttribute PasswordDTO passwordDTO) throws CustomValidationException {
        Integer id = getDecodeHashId(Parseador.getDecodeBase64(passwordDTO.getSchema()), passwordDTO.getUserId());
        return securityUserService.cambiarPassword(passwordDTO, id);
    }
}
