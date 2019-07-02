package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.ClienteFitness;
import com.itsight.domain.dto.ClienteFitnessDTO;
import com.itsight.service.*;
import com.itsight.util.Parseador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/gestion/cliente-fitness")
public class ClienteFitnessController {

    private ClienteFitnessService clienteFitnessService;

    private ObjetivoService objetivosService;

    private MusculoService musculoService;

    private TipoDocumentoService tipoDocumentoService;

    private TrainerService trainerService;

    @Autowired
    public ClienteFitnessController(ClienteFitnessService clienteFitnessService, ObjetivoService objetivosService, MusculoService musculoService, TipoDocumentoService tipoDocumentoService, TrainerService trainerService) {
        this.clienteFitnessService = clienteFitnessService;
        this.objetivosService = objetivosService;
        this.musculoService = musculoService;
        this.tipoDocumentoService = tipoDocumentoService;
        this.trainerService = trainerService;
    }

    @GetMapping(value = "")
    public ModelAndView principal(Model model) {
        model.addAttribute("lstObjetivos", objetivosService.findAll());
        model.addAttribute("lstMusculos", musculoService.findAll());
        model.addAttribute("lstTd", tipoDocumentoService.findAll());
        model.addAttribute("lstEntrenadores", trainerService.findAll());
        return new ModelAndView(ViewConstant.MAIN_FICHA_INSCRIPCION_RUNNING);
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    ClienteFitness obtenerPorId(@RequestParam(value = "id") Integer clienteId) {
        return clienteFitnessService.findOne(clienteId);
    }

    //Edicion de rutinas
    @GetMapping(value = "/obtener/secundario/{clienteId}")
    public @ResponseBody
    ClienteFitness obtenerPorUsuarioId(@PathVariable(name = "clienteId") String clienteId) {
        Integer redFitId = Parseador.getDecodeHash16Id("rf-rutina", clienteId);
        return clienteFitnessService.findByClienteId(redFitId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@RequestBody ClienteFitnessDTO clienteFitDto, BindingResult bindingResult) throws CustomValidationException {
        if(!bindingResult.hasErrors()){
            return clienteFitnessService.registrar(clienteFitDto);
        }else{
            return "-1";
        }
    }

    @GetMapping(value = "/planes")
    public ModelAndView principalplanes() {
        return new ModelAndView(ViewConstant.MAIN_PLANES);
    }


    @GetMapping(value = "/perfil")
    public ModelAndView perfilClienteFitness() {
        return new ModelAndView(ViewConstant.CLIENTE_PERFIL);
    }


    @GetMapping(value = "/obtener/completo")
    public @ResponseBody ClienteFitness obtenerInfoCompletaByClienteId(HttpSession session){

        Integer clienteId = (Integer) session.getAttribute("id");

        return clienteFitnessService.findClientDataByClienteId(clienteId);
    }

}
