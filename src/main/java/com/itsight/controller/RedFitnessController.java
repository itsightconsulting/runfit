package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.RedFitness;
import com.itsight.domain.dto.RedFitCliDTO;
import com.itsight.service.RedFitnessService;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
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

    public RedFitnessController(RedFitnessService redFitnessService){
        this.redFitnessService = redFitnessService;
    }

    @GetMapping("")
    public ModelAndView principal(){
        return new ModelAndView(ViewConstant.MAIN_TRAINER_RED);
    }

    @GetMapping(value = "/obtenerListado")
    public @ResponseBody
    List<RedFitCliDTO> listarConFiltro(@RequestParam String nombres, HttpSession session) {
        if (session.getAttribute("id") != null) {
            Integer trainerId = (Integer) session.getAttribute("id");
            return redFitnessService.listarSegunRedTrainerAndCliNom(trainerId, nombres);
        }
        return new ArrayList<>();
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
            @RequestParam String asunto,
            @RequestParam String cuerpo,
            HttpSession session) {
        Integer trainerId = (Integer) session.getAttribute("id");
        return Utilitarios.jsonResponse(redFitnessService.enviarNotificacionPersonal(Integer.parseInt(cliId), cliCorreo, trainerId, asunto, cuerpo));
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

}
