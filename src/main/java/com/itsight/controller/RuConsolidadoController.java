package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.RuConsolidado;
import com.itsight.domain.Rutina;
import com.itsight.domain.Semana;
import com.itsight.repository.RutinaRepository;
import com.itsight.service.RuConsolidadoService;
import com.itsight.service.RutinaService;
import com.itsight.service.SemanaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("cliente/rutina/consolidado")
public class RuConsolidadoController {

    private  RuConsolidadoService ruConsolidadoService;
    private RutinaService rutinaService;
    private SemanaService semanaService;
    private RutinaRepository rutinaRepository;

    @Autowired
    public RuConsolidadoController(RuConsolidadoService ruConsolidadoService, RutinaService rutinaService, SemanaService semanaService, RutinaRepository rutinaRepository) {
          this.ruConsolidadoService = ruConsolidadoService;
          this.rutinaService = rutinaService;
          this.semanaService = semanaService;
          this.rutinaRepository = rutinaRepository;
    }

    @GetMapping("")
    public ModelAndView vistaConsolidado(){

     return new ModelAndView(ViewConstant.CLIENTE_RUTINA_CONSOLIDADO);
    }

    @GetMapping(value="/obtener")
    public @ResponseBody
     RuConsolidado obtenerRuConsolidado(HttpSession session, Integer rutinaId){
       Integer clienteId = (Integer) session.getAttribute("id");
       Integer filteredRutinaId = rutinaId == 0 ?   rutinaService.getMaxRutinaIdByClienteId(clienteId) : rutinaId ;
       RuConsolidado ruConsolidado = ruConsolidadoService.findOne(filteredRutinaId);

       return ruConsolidado;
 }

    @GetMapping(value="/intensidad")
    public @ResponseBody
    Semana obtenerIntensidadConsolidado(HttpSession session, @RequestParam Integer rutinaId, @RequestParam int semanaIx){
        Integer semanaId =  rutinaRepository.findSemanaIdByIndex(rutinaId, semanaIx);
        Semana semana = semanaService.findOne(semanaId);

        return semana;
    }




}
