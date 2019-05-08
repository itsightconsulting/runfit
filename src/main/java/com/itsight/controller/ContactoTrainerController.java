package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.ContactoTrainer;
import com.itsight.domain.dto.ContactoTrainerDTO;
import com.itsight.service.ContactoTrainerService;
import com.itsight.util.Utilitarios;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/p/contacto")
public class ContactoTrainerController extends BaseController{

    private ContactoTrainerService contactoTrainerService;

    @Autowired
    public ContactoTrainerController(ContactoTrainerService contactoTrainerService) {
        this.contactoTrainerService = contactoTrainerService;
    }

    @PostMapping("/trainer/registrar/{hshTrainerId}")
    public @ResponseBody String nuevoContacto(
            @ModelAttribute @Valid ContactoTrainerDTO contactoTrainer,
            @PathVariable(name = "hshTrainerId") String hshTrainerId) throws CustomValidationException {
        Integer trainerId = getDecodeHashId("rf-cont-tra", hshTrainerId);
        ContactoTrainer contacto = new ContactoTrainer();
        BeanUtils.copyProperties(contactoTrainer, contacto);
        return Utilitarios.jsonResponse(contactoTrainerService.registrar(contacto, trainerId.toString()));
    }

    @GetMapping("/trainer/{hshConTraId}")
    public ModelAndView verDatosClienteInteresado(
            @PathVariable(name = "hshConTraId") String hshConTraId,
            Model model) throws CustomValidationException {
        Integer conTraId = getDecodeHashId("rf-cont-tra", hshConTraId);
        ContactoTrainer conTrainer = contactoTrainerService.findOne(conTraId);
        if(conTrainer == null) {
            return new ModelAndView(ViewConstant.P_ERROR404);

        }
        if(conTrainer.isFlagLeido() || conTrainer.isFlagLeidoFueraFecha()){
            model.addAttribute("contacto", conTrainer);
            return new ModelAndView();
        }

        Date now = new Date();
        conTrainer.setFechaVisualizacion(now);

        if(conTrainer.getFechaExpiracion().before(now)){
            conTrainer.setFlagLeido(true);
        }else{
            conTrainer.setFlagLeidoFueraFecha(true);
        }
        model.addAttribute("contacto", conTrainer);
        return new ModelAndView(ViewConstant.MAIN_VER_CONTACTO_TRAINER);
    }
}
