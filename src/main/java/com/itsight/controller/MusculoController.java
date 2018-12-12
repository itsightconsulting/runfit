package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.Musculo;
import com.itsight.service.MusculoService;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.itsight.util.Enums.ResponseCode.EXITO_GENERICA;
import static com.itsight.util.Enums.ResponseCode.EX_GENERIC;

@Controller
@RequestMapping("/gestion/musculo")
public class MusculoController {

    private MusculoService musculoService;

    @Autowired
    public MusculoController(MusculoService musculoService) {
        // TODO Auto-generated constructor stub
        this.musculoService = musculoService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principalMusculo() {
        return new ModelAndView(ViewConstant.MAIN_MUSCULO);
    }

    @GetMapping(value = "/listarTodos")
    public @ResponseBody
    List<Musculo> listarConFiltrosWithoutFilters(){
        return musculoService.findAll();
    }

    @GetMapping(value = "/obtenerListado/{comodin}")
    public @ResponseBody
    List<Musculo> listarConFiltro(
            @PathVariable("comodin") String comodin){
        return musculoService.listarPorFiltro (comodin, "", "");
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    Musculo obtenerEspecifico(@RequestParam(value = "id") int musculoId) {
        return musculoService.findOne(musculoId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute Musculo musculo) {
        if (musculo.getId() == 0) {
            return musculoService.registrar(musculo, null);
        }
        return musculoService.actualizar(musculo, null);
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            musculoService.actualizarFlagActivoById(id, flagActivo);
            return EXITO_GENERICA.get();
        } catch (Exception e) {
            return EX_GENERIC.get();
        }
    }
}
