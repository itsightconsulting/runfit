package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.dto.QueryParamsDTO;
import com.itsight.domain.dto.RedFitCliDTO;
import com.itsight.domain.dto.ResPaginationDTO;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.service.RedFitnessProcedureInvoker;
import com.itsight.service.RedFitnessService;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.itsight.util.Enums.ResponseCode.EXITO_GENERICA;

@Controller
@RequestMapping("/gestion/trainer/red")
public class RedFitnessController {

    private RedFitnessService redFitnessService;

    private RedFitnessProcedureInvoker redFitnessProcedureInvoker;

    @Autowired
    public RedFitnessController(RedFitnessService redFitnessService, RedFitnessProcedureInvoker redFitnessProcedureInvoker) {
        this.redFitnessService = redFitnessService;
        this.redFitnessProcedureInvoker = redFitnessProcedureInvoker;
    }

    public RedFitnessController(RedFitnessService redFitnessService){
        this.redFitnessService = redFitnessService;
    }

    @GetMapping("")
    public ModelAndView principal(){
        return new ModelAndView(ViewConstant.MAIN_TRAINER_RED);
    }

    @GetMapping(value = "/obtenerListado")
    public @ResponseBody
    ResPaginationDTO listarConFiltro(@RequestParam String nombres,
                                     @ModelAttribute QueryParamsDTO queryParams, HttpSession session) {
        Integer trainerId = (Integer) session.getAttribute("id");
        List<RedFitCliDTO> lstRed = redFitnessProcedureInvoker.findAllByNombreDynamic(trainerId, nombres, queryParams);
        return new ResPaginationDTO(lstRed, lstRed.isEmpty() ? 0 : lstRed.get(0).getRows());

    }

    @PutMapping(value = "/anadir-nota")
    public @ResponseBody
    String actualizarNotaAIntegrante(@RequestParam String id, @RequestParam String nota) {
        redFitnessService.actualizarNotaACliente(Integer.parseInt(id), nota);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @PostMapping(value = "/enviar/correo/personal")
    public @ResponseBody
    String enviarCorreoARunnerEspecifico(
            @RequestParam String cliId,
            @RequestParam String cliCorreo,
            @RequestParam String cuerpo,
            HttpSession session) throws JsonProcessingException {
        Integer trainerId = (Integer) session.getAttribute("id");
        return Utilitarios.jsonResponse(redFitnessService.enviarNotificacionPersonal(Integer.parseInt(cliId), cliCorreo, trainerId, cuerpo));
    }

    @PostMapping(value = "/enviar/correo/general")
    public @ResponseBody
    String enviarCorreoATodosLosRunners(
            @RequestParam String asunto,
            @RequestParam String cuerpo,
            HttpSession session) {
        Integer trainerId = (Integer) session.getAttribute("id");
        return Utilitarios.jsonResponse(redFitnessService.enviarNotificacionGeneral(trainerId, asunto, cuerpo));
    }

    @PutMapping(value = "/actualizar/flag")
    public @ResponseBody
    String actualizarFlagActivoSegunId(
            @RequestParam String id,
            HttpSession session) {
        Integer trainerId = (Integer) session.getAttribute("id");
        redFitnessService.actualizarFlagActivoByIdAndTrainerId(Integer.parseInt(id), trainerId, false);
        return Utilitarios.jsonResponse(EXITO_GENERICA.get());
    }

}
