package com.itsight.controller;


import com.itsight.constants.ViewConstant;
import com.itsight.domain.pojo.ClienteFitnessPOJO;
import com.itsight.service.ClienteFitnessProcedureInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/gestion/distribucion-mercado")
public class DistribuciónMercadoController {

 private ClienteFitnessProcedureInvoker clienteFitnessProcedureInvoker;

@Autowired
 public DistribuciónMercadoController(ClienteFitnessProcedureInvoker clienteFitnessProcedureInvoker) {
        this.clienteFitnessProcedureInvoker = clienteFitnessProcedureInvoker;
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

}
