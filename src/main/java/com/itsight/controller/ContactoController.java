package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.ContactoTrainer;
import com.itsight.domain.Contacto;
import com.itsight.domain.dto.ContactoTrainerDTO;
import com.itsight.service.ContactoTrainerService;
import com.itsight.domain.dto.ContactoDTO;
import com.itsight.service.ContactoService;
import com.itsight.service.TrainerFichaService;
import com.itsight.service.TrainerService;
import com.itsight.util.Enums;
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
public class ContactoController extends BaseController{

    private ContactoTrainerService contactoTrainerService;

    private ContactoService contactoService;

    private TrainerService trainerService;

    private TrainerFichaService trainerFichaService;

    @Autowired
    public ContactoController(
            ContactoTrainerService contactoTrainerService,
            ContactoService contactoService,
            TrainerService trainerService,
            TrainerFichaService trainerFichaService) {
        this.contactoTrainerService = contactoTrainerService;
        this.contactoService = contactoService;
        this.trainerService = trainerService;
        this.trainerFichaService = trainerFichaService;
    }

    @GetMapping("")
    public ModelAndView verFormularioContacto(){
        return new ModelAndView(ViewConstant.MAIN_VER_CONTACTO_RUNFIT);
    }

    @PostMapping("/registrar")
    public @ResponseBody String nuevoContactoGeneral(
            @ModelAttribute @Valid ContactoDTO contactoDto) throws CustomValidationException {
        Contacto contacto = new Contacto();
        BeanUtils.copyProperties(contactoDto, contacto);
        return Utilitarios.jsonResponse(contactoService.registrar(contacto, null));
    }

    @PostMapping("/trainer/registrar/{trainerId}")
    public @ResponseBody String nuevoContacto(
            @ModelAttribute @Valid ContactoTrainerDTO contactoTrainer,
            @PathVariable(name = "trainerId") String trainerId,
            @RequestParam String ttId) throws CustomValidationException {
        Integer tipoTrainerId = Integer.parseInt(ttId);
        ContactoTrainer contacto = new ContactoTrainer();
        BeanUtils.copyProperties(contactoTrainer, contacto);
        if(tipoTrainerId != Enums.TipoTrainer.PARA_EMPRESA.get()){
            return Utilitarios.jsonResponse(contactoTrainerService.registrar(contacto, trainerId));
        }
        Integer empTrId = trainerFichaService.getTrEmpIdById(Integer.parseInt(trainerId));
        contacto.setCorreoTrainer(trainerService.getCorreoById(empTrId));
        return Utilitarios.jsonResponse(contactoTrainerService.registrar(contacto, String.valueOf(empTrId)));
    }

    @GetMapping("/trainer/{hshConTraId}")
    public ModelAndView verDatosClienteInteresado(
            @PathVariable(name = "hshConTraId") String hshConTraId,
            Model model) throws CustomValidationException {
        Integer conTraId = getDecodeHashId("rf-contacto", hshConTraId);
        ContactoTrainer conTrainer = contactoTrainerService.findOne(conTraId);
        if(conTrainer == null) {
            return new ModelAndView(ViewConstant.P_ERROR404);

        }
        if(conTrainer.isFlagLeido() || conTrainer.isFlagLeidoFueraFecha()){
            model.addAttribute("contacto", conTrainer);
            return new ModelAndView(ViewConstant.MAIN_VER_CONTACTO_TRAINER);
        }

        Date now = new Date();
        conTrainer.setFechaVisualizacion(now);

        if(now.before(conTrainer.getFechaExpiracion())){
            contactoTrainerService.updateFlagLeido(true, conTrainer.getId());
        } else {
            contactoTrainerService.updateFlagLeidoFueraFecha(true, conTrainer.getId());
        }

        model.addAttribute("contacto", conTrainer);
        return new ModelAndView(ViewConstant.MAIN_VER_CONTACTO_TRAINER);
    }
}
