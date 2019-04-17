package com.itsight.controller;

import com.itsight.service.PostulanteTrainerService;
import com.itsight.util.Parseador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import static com.itsight.util.Enums.Decision.APROBADO;
import static com.itsight.util.Enums.Decision.DESAPROBADO;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;
import static com.itsight.util.Enums.ResponseCode.NOT_FOUND_MATCHES;


@Controller
@RequestMapping("/postulacion")
public class PostulanteTrainerController {

    private PostulanteTrainerService postulanteTrainerService;

    @Autowired
    public PostulanteTrainerController(PostulanteTrainerService postulanteTrainerService) {
        this.postulanteTrainerService = postulanteTrainerService;
    }

    @GetMapping("/revisar/{codPreTrainer}")
    public ModelAndView revisarMiniCvPostulante(
            @PathVariable(value = "codPreTrainer") String codPreTrainer,
            Model model)
    {
        model.addAttribute("trainer", postulanteTrainerService.findOne(Parseador.getDecodeHash32Id("rf-request", codPreTrainer)));
        return new ModelAndView("portal/eleccion_pre_trainer");
    }

    @GetMapping("/decision/{preTrainerIdHash}/{eleccionId}")
    public @ResponseBody String decidirPostulacion(
            @PathVariable(value = "preTrainerIdHash") String preTrainerIdHash,
            @PathVariable(value = "eleccionId") String eleccionId){
        Integer preTrainerId = Parseador.getDecodeHash32Id("rf-request", preTrainerIdHash);
        Integer eleccId = Integer.parseInt(eleccionId);
        if(preTrainerId == 0){
            return NOT_FOUND_MATCHES.get();
        }
        if (eleccId == APROBADO.get() || eleccId == DESAPROBADO.get()) {
            return postulanteTrainerService.decidir(preTrainerId, eleccId);
        }
        return EX_VALIDATION_FAILED.get();
    }
}
