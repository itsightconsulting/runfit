package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.Usuario;
import com.itsight.domain.UsuarioFitness;
import com.itsight.domain.dto.UsuarioFitnessDto;
import com.itsight.domain.jsonb.CompetenciaRunner;
import com.itsight.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/gestion/usuario-fitness")
public class UsuarioFitnessController {

    private UsuarioFitnessService usuarioFitnessService;

    private ObjetivoService objetivosService;

    private MusculoService musculoService;

    private TipoDocumentoService tipoDocumentoService;

    private UsuarioService usuarioService;

    @Autowired
    public UsuarioFitnessController(UsuarioFitnessService usuarioFitnessService, ObjetivoService objetivosService, MusculoService musculoService, TipoDocumentoService tipoDocumentoService, UsuarioService usuarioService) {
        this.usuarioFitnessService = usuarioFitnessService;
        this.objetivosService = objetivosService;
        this.musculoService = musculoService;
        this.tipoDocumentoService = tipoDocumentoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping(value = "")
    public ModelAndView principal(Model model) {
        model.addAttribute("lstObjetivos", objetivosService.findAll());
        model.addAttribute("lstMusculos", musculoService.findAll());
        model.addAttribute("lstTd", tipoDocumentoService.findAll());
        model.addAttribute("lstEntrenadores", usuarioService.listarEntrenadores());
        return new ModelAndView(ViewConstant.MAIN_FICHA_INSCRIPCION);
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    UsuarioFitness obtenerPorId(@RequestParam(value = "id") int usuarioId) {
        return usuarioFitnessService.findOne(usuarioId);
    }

    //Edicion de rutinas
    @GetMapping(value = "/obtener/secundario")
    public @ResponseBody
    UsuarioFitness obtenerPorUsuarioId(HttpSession session) {
        int usuarioId = (int) session.getAttribute("edicionUsuarioId");
        return usuarioFitnessService.findByUsuarioId(usuarioId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@RequestBody UsuarioFitnessDto usuarioFitDto) {
        return usuarioFitnessService.registrar(usuarioFitDto);
    }

    //edicionUsuarioId
}
