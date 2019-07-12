package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.advice.CustomValidationException;
import com.itsight.domain.PostulanteTrainer;
import com.itsight.service.PostulanteTrainerService;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static com.itsight.util.Enums.Decision.APROBADO;
import static com.itsight.util.Enums.Decision.DESAPROBADO;
import static com.itsight.util.Enums.Msg.VALIDACION_FALLIDA;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;
import static com.itsight.util.Enums.ResponseCode.NOT_FOUND_MATCHES;
import static com.itsight.util.Utilitarios.jsonResponse;


@Controller
@RequestMapping("/postulacion")
public class PostulanteTrainerController extends BaseController{

    private PostulanteTrainerService postulanteTrainerService;

    @Autowired
    public PostulanteTrainerController(PostulanteTrainerService postulanteTrainerService) {
        this.postulanteTrainerService = postulanteTrainerService;
    }

    @GetMapping("/obtenerListado")
    public @ResponseBody List<PostulanteTrainer> obtenerTodos(){
        return postulanteTrainerService.findAll();
    }

    @GetMapping("/revisar/{codPreTrainer}")
    public ModelAndView revisarMiniCvPostulante(@PathVariable(value = "codPreTrainer") String codPreTrainer, Model model)
    {
        if(codPreTrainer.length() == 32){
            PostulanteTrainer post = postulanteTrainerService.findOne(Parseador.getDecodeHash32Id("rf-request", codPreTrainer));
            if(post == null){
                return new ModelAndView(ViewConstant.P_ERROR404);
            }

            if(post.isFlagAceptado()){
                model.addAttribute("msg", Enums.Msg.POSTULANTE_ACEPTADO_ANT.get());
                return new ModelAndView(ViewConstant.MAIN_INF_P);
            }

            if(post.isFlagRechazado() && new Date().before(post.getFechaLimiteAccion())){
                model.addAttribute("msg", Enums.Msg.POSTULANTE_RECHAZADO_ANT.get());
                return new ModelAndView(ViewConstant.MAIN_INF_N);
            }

            if(!post.isFlagAceptado()){
                model.addAttribute("post", post);
                return new ModelAndView(ViewConstant.MAIN_VER_POSTULANE);
            }
        }
        model.addAttribute("msg", Enums.Msg.RESOURCE_NOT_FOUND.get());
        return new ModelAndView(ViewConstant.MAIN_INF_N);
    }

    @PutMapping("/decision")
    public @ResponseBody String decidirPostulacion(
            @RequestParam(value = "key") String preTrainerIdHash,
            @RequestParam(value = "o") String eleccionId) throws CustomValidationException{

        if(preTrainerIdHash.length() != 32 || eleccionId.length() != 1){
            throw new CustomValidationException(VALIDACION_FALLIDA.get(), EX_VALIDATION_FAILED.get());
        }

        Integer parseEleccionId = Parseador.fromStringToInt(eleccionId);
        if(parseEleccionId == -1) {
            throw new CustomValidationException(VALIDACION_FALLIDA.get(), EX_VALIDATION_FAILED.get());
        }

        Integer preTrainerId = Parseador.getDecodeHash32Id("rf-request", preTrainerIdHash);
        if (preTrainerId > 0 && parseEleccionId == APROBADO.get() || parseEleccionId == DESAPROBADO.get()) {
            return jsonResponse(postulanteTrainerService.decidir(preTrainerId, parseEleccionId, ""));
        }
        throw new CustomValidationException(Enums.Msg.ELECCION_INVALIDA.get(), EX_VALIDATION_FAILED.get());
    }

    @GetMapping("/decision/ml")
    public ModelAndView decidirPostulacionByMail(
            @RequestParam(value = "key") String preTrainerIdHash,
            @RequestParam(value = "sc") String secret,
            @RequestParam(value = "o") String eleccionId) throws CustomValidationException{

        if(eleccionId.length() != 1){
            throw new CustomValidationException(VALIDACION_FALLIDA.get(), EX_VALIDATION_FAILED.get());
        }

        Integer parseEleccionId = Parseador.fromStringToInt(eleccionId);
        if(parseEleccionId == -1) {
            throw new CustomValidationException(VALIDACION_FALLIDA.get(), EX_VALIDATION_FAILED.get());
        }

        Integer preTrainerId = Parseador.getDecodeHash32Id("rf-request", preTrainerIdHash);
        if (preTrainerId > 0 && parseEleccionId == APROBADO.get() || parseEleccionId == DESAPROBADO.get()) {
            return new ModelAndView(ViewConstant.MAIN_INF_P,
                "msg",
                jsonResponse(postulanteTrainerService.decidir(preTrainerId, parseEleccionId, secret)));
        }
        throw new CustomValidationException(Enums.Msg.ELECCION_INVALIDA.get(), EX_VALIDATION_FAILED.get());
    }
}
