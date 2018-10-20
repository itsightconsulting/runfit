package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.*;
import com.itsight.service.RutinaService;
import com.itsight.service.SemanaService;
import com.itsight.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cliente")

public class ClienteController {

    private SemanaService semanaService;
    private RutinaService rutinaService;
    private UsuarioService usuarioService;

    @Autowired
    public ClienteController(SemanaService semanaService, RutinaService rutinaService,UsuarioService usuarioService ){
        this.semanaService = semanaService;
        this.rutinaService = rutinaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping(value = "")
    public ModelAndView principal(Model model) {
        return new ModelAndView(ViewConstant.CLIENTE_PRINCIPAL);
    }


    @GetMapping(value = "/get/obtenerSemanasPorRutina")
    public @ResponseBody List<Semana> obtenerPrimeraSemanaRutina(@RequestParam int idrutina) {
        return semanaService.findByRutinaIdOrderByIdDesc(idrutina);
    }

    @GetMapping(value = "/get/rutinas")
    public @ResponseBody List<Rutina> obtenerRutinas(HttpSession session) {
        int id = (int)session.getAttribute("id");
        return rutinaService.getAllRutinasByUser(id);
    }

}
