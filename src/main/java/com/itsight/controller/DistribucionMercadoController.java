package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.Servicio;
import com.itsight.domain.Trainer;
import com.itsight.domain.dto.ClienteDTO;
import com.itsight.domain.pojo.ClienteFitnessPOJO;
import com.itsight.domain.pojo.ServicioPOJO;
import com.itsight.service.*;
import com.itsight.service.impl.TrainerProcedureInvokerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/gestion/distribucion-mercado")
public class DistribucionMercadoController {

 private ClienteFitnessProcedureInvoker clienteFitnessProcedureInvoker;
 private ServicioProcedureInvoker servicioProcedureInvoker;
 private ServicioService servicioService;
 private TrainerService trainerService;

@Autowired
 public DistribucionMercadoController(ClienteFitnessProcedureInvoker clienteFitnessProcedureInvoker,
                                      ServicioProcedureInvoker servicioProcedureInvoker,
                                      ServicioService servicioService,TrainerService trainerService) {
        this.clienteFitnessProcedureInvoker = clienteFitnessProcedureInvoker;
        this.servicioProcedureInvoker = servicioProcedureInvoker;
        this.servicioService = servicioService;
        this.trainerService = trainerService;
}

 @GetMapping(value = "")
 public ModelAndView getDistribucionClientes(Model model){

  return new ModelAndView(ViewConstant.MAIN_DISTRIBUCION_MERCADO_RED);
 }



 @GetMapping(value = "/obtener")
 public @ResponseBody
 List<ClienteFitnessPOJO> obtenerInfoCompletaClientes() {

  Integer trainerId = null;
  List<ClienteFitnessPOJO> fichaClienteFitness = clienteFitnessProcedureInvoker.getDistribucionMercado(null);

  return fichaClienteFitness;
 }


 @GetMapping(value = "/trainer/obtener")
 public @ResponseBody
 List<ClienteFitnessPOJO> obtenerInfoCompletaClientesXTrainerId(@RequestParam Integer id) {

  Trainer trainer = trainerService.findOne(id);
  List<ClienteFitnessPOJO> fichaClienteFitness = null;

  if(trainer!=null){
  //TRAINER
    if(trainer.getTipoTrainer().getId() != 2){
      fichaClienteFitness = clienteFitnessProcedureInvoker.getDistribucionMercado(id);
    }else{ //EMPRESA
      fichaClienteFitness = clienteFitnessProcedureInvoker.getDistribucionMercadoEmpresa(id);
    }
  }

  return fichaClienteFitness;

 }

 @GetMapping(value = "/servicio/obtener")
 public @ResponseBody
 List<ServicioPOJO> obtenerInfoTopServicios() {
  List<ServicioPOJO> lstServicios = servicioProcedureInvoker.getTopServicioTrainerPlataforma();
  return lstServicios;
 }


 @GetMapping(value = "/servicio/total/obtener")
 public @ResponseBody int getTotalClientesServicios(HttpSession session){

  Integer total = servicioService.getTotalClientesServicio();

  return total;
 }




}
