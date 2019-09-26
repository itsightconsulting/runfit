package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.Servicio;
import com.itsight.domain.Trainer;
import com.itsight.domain.dto.ClienteDTO;
import com.itsight.domain.pojo.ClienteFitnessPOJO;
import com.itsight.domain.pojo.ServicioPOJO;
import com.itsight.service.ClienteFitnessProcedureInvoker;
import com.itsight.service.ServicioProcedureInvoker;
import com.itsight.service.ServicioService;
import com.itsight.service.TrainerProcedureInvoker;
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
public class DistribuciónMercadoController {

 private ClienteFitnessProcedureInvoker clienteFitnessProcedureInvoker;
 private ServicioProcedureInvoker servicioProcedureInvoker;
 private ServicioService servicioService;

@Autowired
 public DistribuciónMercadoController(ClienteFitnessProcedureInvoker clienteFitnessProcedureInvoker,
                                      ServicioProcedureInvoker servicioProcedureInvoker,
                                      ServicioService servicioService) {
        this.clienteFitnessProcedureInvoker = clienteFitnessProcedureInvoker;
        this.servicioProcedureInvoker = servicioProcedureInvoker;
        this.servicioService = servicioService;
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

  List<ClienteFitnessPOJO> fichaClienteFitness = clienteFitnessProcedureInvoker.getDistribucionMercado(id);

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
