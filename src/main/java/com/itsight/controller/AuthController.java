package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.service.RutinaPlantillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
public class AuthController {

    @Autowired
    private RutinaPlantillaService rutinaService;

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
        return "login";
    }

    //	@PreAuthorize("hasAnyRole({'ADMIN','USER'}) or hasAuthority('READ_PRIVILEGE')")
    @GetMapping(value = {"/bienvenido", "/"})
    public String welcome() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority: authorities){
            if(authority.getAuthority().equals("ROLE_TRAINER") || authority.getAuthority().equals("ROLE_ADMIN"))
                return ViewConstant.MAIN_TRAINER_RED;
            if(authority.getAuthority().equals("ROLE_RUNNER") || authority.getAuthority().equals("ROLE_STORE"))
                return ViewConstant.PRINCIPAL;
        }
        return ViewConstant.PRINCIPAL;
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
    public String expiredBySessionMultiple(Model model) {
        return "lock";
    }

    /*@GetMapping(value = "/jsonb/rutina/{id}")
    public @ResponseBody
    RutinaPlantilla jsonBResponseRutina(@PathVariable int id){
        RutinaPlantilla obj = new RutinaPlantilla();
        return rutinaService.findOne(new Integer(id));
    }*/
}
