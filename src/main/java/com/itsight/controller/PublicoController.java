package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.service.CondicionMejoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/p")
public class PublicoController {

    private CondicionMejoraService condicionMejoraService;

    @Autowired
    public PublicoController(CondicionMejoraService condicionMejoraService) {
        this.condicionMejoraService = condicionMejoraService;
    }

    @GetMapping("/fi/2")
    public ModelAndView fiDos(){
        return new ModelAndView("portal/ficha_inscripcion");
    }

    @GetMapping("/inicio")
    public ModelAndView index(){
        return new ModelAndView(ViewConstant.MAIN_INICIO);
    }

    @GetMapping("/contigo")
    public ModelAndView contigo(){
        return new ModelAndView(ViewConstant.MAIN_CONTIGO);
    }

    @GetMapping("/quienes-somos")
    public ModelAndView quienesSomos(){
        return new ModelAndView(ViewConstant.MAIN_QUIENES_SOMOS);
    }

    @GetMapping("/busqueda/trainers")
    public ModelAndView busquedaTrainer(){
        return new ModelAndView(ViewConstant.MAIN_BUSQUEDA_TRAINER);
    }

    @GetMapping("/ficha-inscripcion")
    public ModelAndView fichaInscripcion(Model model){
        model.addAttribute("lstCondMejoras", condicionMejoraService.getAll());
        return new ModelAndView(ViewConstant.MAIN_FICHA_INSCRIPCION);
    }

    @GetMapping("/registro/trainer")
    public ModelAndView registroTrainer(){
        return new ModelAndView(ViewConstant.MAIN_REGISTRO_TRAINER);
    }

    @GetMapping("/busqueda/trainer/{codTrainer}")
    public ModelAndView perfilTrainer(@PathVariable(value = "codTrainer") String codTrainer){
        return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER);
    }
}
