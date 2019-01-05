package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.Plan;
import com.itsight.domain.UsuarioFitness;
import com.itsight.domain.dto.UsuarioFitnessDto;
import com.itsight.service.*;
import com.itsight.util.Parseador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/gestion/usuario-fitness")
public class UsuarioFitnessController {

    private UsuarioFitnessService usuarioFitnessService;

    private ObjetivoService objetivosService;

    private MusculoService musculoService;

    private TipoDocumentoService tipoDocumentoService;

    private TrainerService trainerService;

    @Autowired
    public UsuarioFitnessController(UsuarioFitnessService usuarioFitnessService, ObjetivoService objetivosService, MusculoService musculoService, TipoDocumentoService tipoDocumentoService,  TrainerService trainerService) {
        this.usuarioFitnessService = usuarioFitnessService;
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
        return new ModelAndView(ViewConstant.MAIN_FICHA_INSCRIPCION);
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    UsuarioFitness obtenerPorId(@RequestParam(value = "id") int usuarioId) {
        return usuarioFitnessService.findOne(usuarioId);
    }

    //Edicion de rutinas
    @GetMapping(value = "/obtener/secundario/{runnerId}")
    public @ResponseBody
    UsuarioFitness obtenerPorUsuarioId(@PathVariable(name = "runnerId") String runnerId, HttpSession session) {
        int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);
        return usuarioFitnessService.findByUsuarioId(runneId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@RequestBody UsuarioFitnessDto usuarioFitDto) {
        return usuarioFitnessService.registrar(usuarioFitDto);
    }

    @GetMapping(value = "/planes")
    public ModelAndView principalplanes(Model model) {
        return new ModelAndView(ViewConstant.MAIN_PLANES);
    }

}
