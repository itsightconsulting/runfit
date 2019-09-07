package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.CategoriaPlantilla;
import com.itsight.domain.Rutina;
import com.itsight.domain.Trainer;
import com.itsight.domain.dto.QueryParamsDTO;
import com.itsight.domain.dto.RedFitCliDTO;
import com.itsight.domain.dto.ResPaginationDTO;
import com.itsight.domain.pojo.ClienteFitnessPOJO;
import com.itsight.domain.pojo.RutinaPOJO;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.itsight.util.Enums.ResponseCode.EXITO_GENERICA;

@Controller
@RequestMapping("/gestion/trainer/red")
public class RedFitnessController {

    private RedFitnessService redFitnessService;

    private RutinaService rutinaService;

    private RedFitnessProcedureInvoker redFitnessProcedureInvoker;

    private ClienteFitnessProcedureInvoker clienteFitnessProcedureInvoker;

    private TrainerService trainerService;



    @Autowired
    public RedFitnessController(RedFitnessService redFitnessService, RedFitnessProcedureInvoker redFitnessProcedureInvoker, ClienteFitnessProcedureInvoker clienteFitnessProcedureInvoker, RutinaService rutinaService, TrainerService trainerService) {
        this.redFitnessService = redFitnessService;
        this.redFitnessProcedureInvoker = redFitnessProcedureInvoker;
        this.clienteFitnessProcedureInvoker = clienteFitnessProcedureInvoker;
        this.rutinaService = rutinaService;
        this.trainerService = trainerService;
    }

    public RedFitnessController(RedFitnessService redFitnessService){
        this.redFitnessService = redFitnessService;
    }

    @GetMapping("")
    public ModelAndView principal(){
        return new ModelAndView(ViewConstant.MAIN_TRAINER_RED);
    }


    @GetMapping(value = {"/suspendidos"})
    public ModelAndView suspendido() {
        return new ModelAndView(ViewConstant.MAIN_SUSPENDIDO);
    }

    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @GetMapping(value = {"/historial_rutina"})
    public ModelAndView historialRutina(@RequestParam(name = "key") String redFitnessId, @RequestParam(name = "rn") String runnerId, @RequestParam(name = "nm") String nombreCompleto ,
                                         Model model, HttpSession session) throws JsonProcessingException {


        int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
        int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);

        session.setAttribute("historialRedFitId",redFitId );
        session.setAttribute("historialRunnerId",runneId );

        String cliNombre = Parseador.getDecodeBase64(nombreCompleto);

        if(redFitId > 0 && runneId > 0) {
            Integer trainerId = (Integer) session.getAttribute("id");
            Integer qTrainerId = redFitnessService.findTrainerIdByIdAndRunnerId(redFitId, runneId);
            if (trainerId.equals(qTrainerId)) {
                List<Rutina> lstRutina = rutinaService.findAllByRedFitnessOrderByIdDesc(redFitId);

                boolean isValid = true;

                List<RutinaPOJO> lstRutinaPojo = new ArrayList<RutinaPOJO>();


                for (Rutina r : lstRutina) {

                   RutinaPOJO rpj =  new RutinaPOJO(r);
                   lstRutinaPojo.add(rpj);
                }

                if (lstRutinaPojo.size() > 0) {
                    model.addAttribute("rutinas", lstRutinaPojo);
                    model.addAttribute("nombreCliente", cliNombre);
                    return new ModelAndView(ViewConstant.MAIN_HISTORIAL_RUTINA_ELEGIDA);
                }
            }
        }
        return new ModelAndView(ViewConstant.ERROR404);

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
            HttpSession session) throws CustomValidationException {
        if(cuerpo.length()>2000){
            throw new CustomValidationException(
                    Enums.Msg.VALIDACION_FALLIDA+", se permiten máximo 2000 caracteres",
                    Enums.ResponseCode.EX_VALIDATION_FAILED.get());
        }
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

    @PutMapping(value = "/suspendidos/activar")
    public @ResponseBody
    String activarClienteSuspendido(
            @RequestParam String id,
            HttpSession session) {
        Integer trainerId = (Integer) session.getAttribute("id");
        redFitnessService.actualizarFlagActivoByIdAndTrainerId(Integer.parseInt(id), trainerId, true);

        return Utilitarios.jsonResponse(EXITO_GENERICA.get());
    }

    @GetMapping(value="/suspendidos/obtener")
    public @ResponseBody
    List<RedFitCliDTO> listarClientesSuspendidos(HttpSession session, @RequestParam String mes){

        Integer trainerId = (Integer) session.getAttribute("id");
        List<RedFitCliDTO> lstSuspendidos = redFitnessService.findSuspendidosbyTrainerId(trainerId,mes);

        return lstSuspendidos;
    }



    @GetMapping(value="/suspendidos/obtener/periodos")
    public @ResponseBody
    String getMesesClientesSuspendidos(HttpSession session){

        Integer trainerId = (Integer) session.getAttribute("id");
        String infoPeriodoSuspendidos = redFitnessService.getMesesCliSuspendidos(trainerId);

        return infoPeriodoSuspendidos;

    }


    @GetMapping(value = "/consultar/cliente")
    public ModelAndView obtenerInfoCompletaByClienteId(Model model,HttpSession session, @RequestParam(value = "id") Integer clienteId,@RequestParam(value = "rfId") Integer redFitnessId) throws JsonProcessingException {

       Integer trainerId = (Integer) session.getAttribute("id");
       Integer validTrainer = redFitnessService.findTrainerIdByIdAndRunnerId(redFitnessId,clienteId);

       if(validTrainer.intValue() == trainerId.intValue()){
           ClienteFitnessPOJO fichaClienteFitness = clienteFitnessProcedureInvoker.getById(clienteId);
           model.addAttribute("clientData", new ObjectMapper().writeValueAsString(fichaClienteFitness));
           return new ModelAndView(ViewConstant.CLIENTE_PERFIL);
       }else{
           return new ModelAndView(ViewConstant.P_ERROR404);
       }

    }

}
