package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.BandejaTemporal;
import com.itsight.domain.PostulanteTrainer;
import com.itsight.domain.dto.PostulanteTrainerDTO;
import com.itsight.domain.dto.TrainerFichaDTO;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.repository.BandejaTemporalRepository;
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
import java.util.List;

import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;
import static com.itsight.util.Enums.ResponseCode.REGISTRO;
import static com.itsight.util.Utilitarios.jsonResponse;

@Controller
@RequestMapping("/p")
public class PublicoController {

    private CondicionMejoraService condicionMejoraService;

    private DisciplinaService disciplinaService;

    private PostulanteTrainerService postulanteTrainerService;

    private UbPeruService ubPeruService;

    private TrainerService trainerService;

    private TrainerFichaService trainerFichaService;

    @Autowired
    private BandejaTemporalRepository bandejaTemporalRepository;

    @Autowired
    public PublicoController(CondicionMejoraService condicionMejoraService,
                             DisciplinaService disciplinaService,
                             PostulanteTrainerService postulanteTrainerService,
                             UbPeruService ubPeruService,
                             TrainerService trainerService,
                             TrainerFichaService trainerFichaService) {
        this.condicionMejoraService = condicionMejoraService;
        this.disciplinaService = disciplinaService;
        this.postulanteTrainerService = postulanteTrainerService;
        this.ubPeruService = ubPeruService;
        this.trainerService = trainerService;
        this.trainerFichaService = trainerFichaService;
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

    @GetMapping({"/ficha-inscripcion", "/ficha-inscripcion"})
    public ModelAndView fichaInscripcion(
        @RequestParam(name="key", required=false) String hshTrainerId,
        @RequestParam(name="ml", required=false) String trainerMailDecode,
        Model model) {
        if(hshTrainerId != null){
            Integer trainerId = Parseador.getDecodeHash32Id("rf-public", hshTrainerId);
            if(trainerId == 0){
                return new ModelAndView(ViewConstant.ERROR404);
            }else{
                model.addAttribute("trainerId", trainerId);
                model.addAttribute("correoTrainer", trainerMailDecode);
            }
        }
        model.addAttribute("lstCondMejoras", condicionMejoraService.findAll());
        return new ModelAndView(ViewConstant.MAIN_FICHA_INSCRIPCION);
    }

    //TRAINER PROCESS

    @GetMapping("/postulacion/trainer")
    public ModelAndView preRegistroTrainer(){
        return new ModelAndView(ViewConstant.MAIN_EMPRENDE_CON_NOSOTROS);
    }

    @PostMapping("/postulacion/trainer/registrar")
    public @ResponseBody String registrarSolicitudTrainer(
            @ModelAttribute @Valid PostulanteTrainerDTO postulanteTrainerDTO){
        PostulanteTrainer preTrainer = new PostulanteTrainer();
        BeanUtils.copyProperties(postulanteTrainerDTO, preTrainer);
        return jsonResponse(postulanteTrainerService.registrar(preTrainer, null));
    }

    @GetMapping("/formulario/trainer/{hashPreTrainerId}")
    public ModelAndView formularioRegistroTrainer(Model model,
                @PathVariable(name = "hashPreTrainerId") String hashPreTrainerId){
        Integer preTraId = 0;

        if(hashPreTrainerId.length() == 32){
            preTraId = Parseador.getDecodeHash32Id("rf-request", hashPreTrainerId);
        }

        if(preTraId == 0){
            return new ModelAndView(ViewConstant.ERROR404);
        }

        PostulanteTrainer post = postulanteTrainerService.findOne(preTraId);
        if(post == null){
            return new ModelAndView(ViewConstant.ERROR404);
        }
        if(post.isFlagRechazado() || post.isFlagRegistrado() || !post.isFlagAceptado()){
            return new ModelAndView(ViewConstant.ERROR404);
        }

        Date now = new Date();
        if(now.after(post.getFechaLimiteAccion())){
            model.addAttribute("msg", "El vínculo ha expirado");
            return new ModelAndView(ViewConstant.ERROR403);
        }
        model.addAttribute("disciplinas", disciplinaService.findAll());
        model.addAttribute("postulante", post);
        model.addAttribute("distritos", ubPeruService.findPeDistByDepAndProv("15", "01"));
        return new ModelAndView(ViewConstant.MAIN_REGISTRO_TRAINER);
    }

    @PostMapping("/registro/trainer/{hashPreTrainerId}")
    public @ResponseBody String registroTrainer(
                @PathVariable(name = "hashPreTrainerId") String hashPreTrainerId,
                @RequestBody @Valid TrainerFichaDTO trainerFicha) throws CustomValidationException {
        Integer postTraId = 0;

        if(hashPreTrainerId.length() == 32){
            postTraId = Parseador.getDecodeHash32Id("rf-request", hashPreTrainerId);
        }

        if(postTraId == 0){
            throw new CustomValidationException("La validación ha fallado", EX_VALIDATION_FAILED.get());
        }

        PostulanteTrainer postulante = postulanteTrainerService.findOne(postTraId);
        if(postulante == null){
            throw new CustomValidationException("La validación ha fallado", EX_VALIDATION_FAILED.get());
        }

        if(!postulante.isFlagAceptado()){
            return Enums.ResponseCode.EX_VALIDATION_FAILED.get();
        }

        if(postulante.isFlagAceptado() && !postulante.isFlagRegistrado()){
            trainerFicha.setCorreo(postulante.getCorreo());
            trainerFicha.setPostulanteTrainerId(postTraId);
            return trainerService.registrarPostulante(trainerFicha);
        }

        return jsonResponse(REGISTRO.get());
    }

    @GetMapping("/busqueda/trainer/{codTrainer}")
    public ModelAndView perfilTrainer(@PathVariable(value = "codTrainer") String codTrainer){
        return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER);
    }

    //END TRAINER PROCESS

    //TestQueries
    @GetMapping("/get/all/trainer-ficha")
    public @ResponseBody List<TrainerFichaPOJO> findAllTrainerResponseBody(){
        return trainerFichaService.findAllWithFgEnt();
    }

    @GetMapping("/bandeja")
    public @ResponseBody List<BandejaTemporal> getBandejaGeneral(){
        return bandejaTemporalRepository.findAllByOrderByIdDesc();
    }
}
