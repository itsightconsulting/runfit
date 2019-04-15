package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.PreTrainer;
import com.itsight.domain.dto.PreTrainerDTO;
import com.itsight.service.CondicionMejoraService;
import com.itsight.service.DisciplinaService;
import com.itsight.service.PreTrainerService;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/p")
public class PublicoController {

    private CondicionMejoraService condicionMejoraService;

    private DisciplinaService disciplinaService;

    private PreTrainerService preTrainerService;

    @Autowired
    public PublicoController(CondicionMejoraService condicionMejoraService,
                             DisciplinaService disciplinaService,
                             PreTrainerService preTrainerService) {
        this.condicionMejoraService = condicionMejoraService;
        this.disciplinaService = disciplinaService;
        this.preTrainerService = preTrainerService;
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

    @GetMapping("/solicitud/trainer")
    public ModelAndView preRegistroTrainer(){
        return new ModelAndView(ViewConstant.MAIN_EMPRENDE_CON_NOSOTROS);
    }

    @PostMapping("/solicitud/trainer")
    public @ResponseBody String registrarSolicitudTrainer(@ModelAttribute @Valid PreTrainerDTO preTrainerDTO){
        PreTrainer preTrainer = new PreTrainer();
        BeanUtils.copyProperties(preTrainerDTO, preTrainer);
        return preTrainerService.registrar(preTrainer, null);
    }

    @GetMapping("/registro/trainer")
    public ModelAndView registroTrainer(Model model){
        model.addAttribute("disciplinas", disciplinaService.findAll());
        return new ModelAndView(ViewConstant.MAIN_REGISTRO_TRAINER);
    }

    @GetMapping("/busqueda/trainer/{codTrainer}")
    public ModelAndView perfilTrainer(@PathVariable(value = "codTrainer") String codTrainer){
        return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER);
    }

    @GetMapping("/q/pre-trainer/{codPreTrainer}")
    public ModelAndView miniCvPreTrainer(@PathVariable(value = "codPreTrainer") String codPreTrainer,
                                                                                Model model){
        model.addAttribute("trainer", preTrainerService.findOne(Parseador.getDecodeHash32Id("rf-request", codPreTrainer)));
        return new ModelAndView("portal/eleccion_pre_trainer");
    }

    @GetMapping("/aprobacion-desaprobacion/trainer/{preTrainerIdHash}/{eleccion}")
    public @ResponseBody String aprobacionPreTrainer(
            @PathVariable(value = "preTrainerIdHash") String preTrainerIdHash,
            @PathVariable(value = "eleccion") String eleccion){
        Integer preTrainerId = Parseador.getDecodeHash32Id("rf-request", preTrainerIdHash);
        Integer decision = Integer.parseInt(eleccion);
        if(preTrainerId == 0 && decision > -1 && decision < 2){
            return Enums.ResponseCode.NOT_FOUND_MATCHES.get();
        }
        return preTrainerService.aprobarDesaprobar(preTrainerId, decision);
    }
}
