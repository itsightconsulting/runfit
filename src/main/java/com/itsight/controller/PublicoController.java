package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.PostulanteTrainer;
import com.itsight.domain.dto.PostulanteTrainerDTO;
import com.itsight.domain.dto.TrainerFichaDTO;
import com.itsight.service.*;
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

    private PostulanteTrainerService postulanteTrainerService;

    private UbPeruService ubPeruService;

    private TrainerService trainerService;

    @Autowired
    public PublicoController(CondicionMejoraService condicionMejoraService,
                             DisciplinaService disciplinaService,
                             PostulanteTrainerService postulanteTrainerService,
                             UbPeruService ubPeruService,
                             TrainerService trainerService) {
        this.condicionMejoraService = condicionMejoraService;
        this.disciplinaService = disciplinaService;
        this.postulanteTrainerService = postulanteTrainerService;
        this.ubPeruService = ubPeruService;
        this.trainerService = trainerService;
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
    public ModelAndView fichaInscripcion(Model model) {
        model.addAttribute("lstCondMejoras", condicionMejoraService.getAll());
        return new ModelAndView(ViewConstant.MAIN_FICHA_INSCRIPCION);
    }

    //TRAINER PROCESS

    @GetMapping("/postulacion/trainer")
    public ModelAndView preRegistroTrainer(){
        return new ModelAndView(ViewConstant.MAIN_EMPRENDE_CON_NOSOTROS);
    }

    @PostMapping("/postulacion/trainer/registrar")
    public @ResponseBody String registrarSolicitudTrainer(@ModelAttribute @Valid PostulanteTrainerDTO postulanteTrainerDTO){
        PostulanteTrainer preTrainer = new PostulanteTrainer();
        BeanUtils.copyProperties(postulanteTrainerDTO, preTrainer);
        return postulanteTrainerService.registrar(preTrainer, null);
    }

    @GetMapping("/formulario/trainer/{hashPreTrainerId}")
    public ModelAndView formularioRegistroTrainer(Model model, @PathVariable(name = "hashPreTrainerId") String hashPreTrainerId){
        Integer hashId = Parseador.getDecodeHash32Id("rf-request", hashPreTrainerId);
        PostulanteTrainer obj = postulanteTrainerService.findOne(hashId);
        if(obj == null){
            return new ModelAndView(ViewConstant.ERROR404);
        }
        if(obj.isFlagRechazado() || obj.isFlagRegistrado() || !obj.isFlagAceptado()){
            return new ModelAndView(ViewConstant.ERROR404);
        }
        Date now = new Date();
        if(now.after(obj.getFechaLimiteAccion())){
            model.addAttribute("msg", "El vínculo ha expirado");
            return new ModelAndView(ViewConstant.ERROR403);
        }
        model.addAttribute("disciplinas", disciplinaService.findAll());
        model.addAttribute("postulante", obj);
        model.addAttribute("distritos", ubPeruService.findPeDistByDepAndProv("15", "01"));
        return new ModelAndView(ViewConstant.MAIN_REGISTRO_TRAINER);
    }

    @PostMapping("/registro/trainer/{hashPreTrainerId}")
    public @ResponseBody String registroTrainer(@PathVariable(name = "hashPreTrainerId") String hashPreTrainerId, @RequestBody @Valid TrainerFichaDTO trainerFicha){
        Integer postTraId = Parseador.getDecodeHash32Id("rf-request", hashPreTrainerId);
        PostulanteTrainer postulante = postulanteTrainerService.findOne(postTraId);
        if(postulante == null){
            return Enums.ResponseCode.NOT_FOUND_MATCHES.get();
        }

        if(!postulante.isFlagAceptado()){
            return Enums.ResponseCode.EX_VALIDATION_FAILED.get();
        }

        if(postulante.isFlagAceptado() && !postulante.isFlagRegistrado()){
            trainerFicha.setCorreo(postulante.getCorreo());
            trainerFicha.setPostulanteTrainerId(postTraId);
            return trainerService.registrarPostulante(trainerFicha);
        }

        return "-1";
    }

    @GetMapping("/busqueda/trainer/{codTrainer}")
    public ModelAndView perfilTrainer(@PathVariable(value = "codTrainer") String codTrainer){
        return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER);
    }

    //END TRAINER PROCESS
}
