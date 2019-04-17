package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.PostulanteTrainer;
import com.itsight.domain.dto.PreTrainerDTO;
import com.itsight.service.CondicionMejoraService;
import com.itsight.service.DisciplinaService;
import com.itsight.service.PostulanteTrainerService;
import com.itsight.util.Parseador;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/p")
public class PublicoController {

    private CondicionMejoraService condicionMejoraService;

    private DisciplinaService disciplinaService;

    private PostulanteTrainerService postulanteTrainerService;

    @Autowired
    public PublicoController(CondicionMejoraService condicionMejoraService,
                             DisciplinaService disciplinaService,
                             PostulanteTrainerService postulanteTrainerService) {
        this.condicionMejoraService = condicionMejoraService;
        this.disciplinaService = disciplinaService;
        this.postulanteTrainerService = postulanteTrainerService;
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
    public @ResponseBody String registrarSolicitudTrainer(@ModelAttribute @Valid PreTrainerDTO preTrainerDTO){
        PostulanteTrainer preTrainer = new PostulanteTrainer();
        BeanUtils.copyProperties(preTrainerDTO, preTrainer);
        return postulanteTrainerService.registrar(preTrainer, null);
    }

    @GetMapping("/registro/trainer/{hashPreTrainerId}")
    public ModelAndView registroTrainer(Model model, @PathVariable(name = "hashPreTrainerId") String hashPreTrainerId){
        Integer hashId = Parseador.getDecodeHash32Id("rf-request", hashPreTrainerId);
        PostulanteTrainer obj = postulanteTrainerService.findOne(hashId);
        if(obj == null){
            return new ModelAndView(ViewConstant.ERROR404);
        }
        if(obj.isFlagRechazado() || obj.isFlagRegistrado() || !obj.isFlagAceptado()){
            return new ModelAndView(ViewConstant.ERROR404);
        }
        model.addAttribute("disciplinas", disciplinaService.findAll());
        model.addAttribute("postulante", obj);
        return new ModelAndView(ViewConstant.MAIN_REGISTRO_TRAINER);
    }

    @GetMapping("/busqueda/trainer/{codTrainer}")
    public ModelAndView perfilTrainer(@PathVariable(value = "codTrainer") String codTrainer){
        return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER);
    }

    //END TRAINER PROCESS
}
