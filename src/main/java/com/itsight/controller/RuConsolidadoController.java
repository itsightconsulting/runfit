package com.itsight.controller;

import com.itsight.advice.SecCustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.RuConsolidado;
import com.itsight.domain.Rutina;
import com.itsight.domain.Semana;
import com.itsight.domain.pojo.RuCliPOJO;
import com.itsight.repository.RutinaRepository;
import com.itsight.service.RuConsolidadoService;
import com.itsight.service.RutinaProcedureInvoker;
import com.itsight.service.RutinaService;
import com.itsight.service.SemanaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("cliente/rutina/consolidado")
public class RuConsolidadoController extends BaseController {

    private  RuConsolidadoService ruConsolidadoService;
    private RutinaService rutinaService;
    private SemanaService semanaService;
    private RutinaRepository rutinaRepository;
    private RutinaProcedureInvoker rutinaProcedureInvoker;

    @Autowired
    public RuConsolidadoController(RuConsolidadoService ruConsolidadoService, RutinaService rutinaService, SemanaService semanaService, RutinaRepository rutinaRepository, RutinaProcedureInvoker rutinaProcedureInvoker) {
          this.ruConsolidadoService = ruConsolidadoService;
          this.rutinaService = rutinaService;
          this.semanaService = semanaService;
          this.rutinaRepository = rutinaRepository;
          this.rutinaProcedureInvoker = rutinaProcedureInvoker;
    }

    @PreAuthorize("hasRole('ROLE_RUNNER')")
    @GetMapping("")
    public ModelAndView vistaConsolidadoParaCliente(){
     return new ModelAndView(ViewConstant.CLIENTE_RUTINA_CONSOLIDADO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRAINER')")
    @GetMapping("/ver")
    public ModelAndView vistaConsolidadoParaTrainer(
            Model model,
            @RequestParam(name = "key") String key) throws SecCustomValidationException {
        model.addAttribute("clienteId", getDecodeHashIdSecCustom("rf-rutina", key));
        return new ModelAndView(ViewConstant.CLIENTE_RUTINA_CONSOLIDADO);
    }


    @PreAuthorize("hasRole('ROLE_RUNNER')")
    @GetMapping(value="/obtener")
    public @ResponseBody
    RuConsolidado obtenerRuConsolidado(HttpSession session, Integer rutinaId){
        Integer clienteId = (Integer) session.getAttribute("id");
        Integer filteredRutinaId = rutinaId == 0 ?   rutinaService.getMaxRutinaIdByClienteId(clienteId) : rutinaId ;
        RuConsolidado ruConsolidado = ruConsolidadoService.findOne(filteredRutinaId);
        return ruConsolidado;
    }

    @PreAuthorize("hasRole('ROLE_RUNNER')")
    @GetMapping(value="/metricas-semanal/obtener")
    public @ResponseBody
    ResponseEntity<RuCliPOJO> obtenerDataSemanal(HttpSession session, Integer rutinaId){
        Integer clienteId = (Integer) session.getAttribute("id");
        Integer filteredRutinaId = rutinaId == 0 ?   rutinaService.getMaxRutinaIdByClienteId(clienteId) : rutinaId ;

        RuCliPOJO ruCliente = rutinaProcedureInvoker.getDatosAvanceSemanalbyRutinaId(filteredRutinaId);

        return new ResponseEntity<>(ruCliente, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_RUNNER')")
    @GetMapping(value="/metricas-semanal/obtener/by")
    public @ResponseBody
    RuConsolidado obtenerDataSemanalById(HttpSession session, Integer rutinaId){
        Integer clienteId = (Integer) session.getAttribute("id");
        Integer filteredRutinaId = rutinaId == 0 ?   rutinaService.getMaxRutinaIdByClienteId(clienteId) : rutinaId ;
        RuConsolidado ruConsolidado = ruConsolidadoService.findOne(filteredRutinaId);
        return ruConsolidado;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRAINER')")
    @GetMapping(value="/obtener/by")
    public @ResponseBody
     RuConsolidado obtenerRuConsolidadoById(Integer rutinaId, @RequestParam Integer clienteId){
       Integer filteredRutinaId = rutinaId == 0 ?   rutinaService.getMaxRutinaIdByClienteId(clienteId) : rutinaId ;
       RuConsolidado ruConsolidado = ruConsolidadoService.findOne(filteredRutinaId);
       return ruConsolidado;
    }

    @GetMapping(value="/intensidad")
    public @ResponseBody
    Semana obtenerIntensidadConsolidado(@RequestParam Integer rutinaId, @RequestParam int semanaIx){
        Integer semanaId =  rutinaRepository.findSemanaIdByIndex(rutinaId, semanaIx);
        Semana semana = semanaService.findOne(semanaId);

        return semana;
    }




}
