package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.RuConsolidado;
import com.itsight.domain.Rutina;
import com.itsight.service.RuConsolidadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("cliente/rutina/consolidado")
public class RuConsolidadoController {

    private  RuConsolidadoService ruConsolidadoService;

    @Autowired
    public RuConsolidadoController(RuConsolidadoService ruConsolidadoService) {
          this.ruConsolidadoService = ruConsolidadoService;
    }

    @GetMapping("")
    public ModelAndView vistaConsolidado(){

     return new ModelAndView(ViewConstant.CLIENTE_RUTINA_CONSOLIDADO);
    }

    @GetMapping(value="/obtener")
    public @ResponseBody
     RuConsolidado obtenerRuConsolidado(HttpSession session){
       Integer clienteId = (Integer) session.getAttribute("id");
       RuConsolidado ruConsolidado = ruConsolidadoService.findOne(1);

       return ruConsolidado;
 }




}
