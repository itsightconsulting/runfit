package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Objetivo;
import com.itsight.service.ObjetivoService;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/gestion/objetivo")
public class ObjetivoController {

    private ObjetivoService objetivoService;

    @Autowired
    public ObjetivoController(ObjetivoService objetivoService) {
        this.objetivoService = objetivoService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal() {
        return new ModelAndView(ViewConstant.MAIN_OBJETIVO);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}")
    public @ResponseBody
    List<Objetivo> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado) {
        return objetivoService.listarPorFiltro(comodin, estado, null);
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    Objetivo obtenerPorId(@RequestParam(value = "id") int objetivoId) {
        return objetivoService.findOne(objetivoId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute Objetivo objetivo) {
        if (objetivo.getId() == 0) {
            objetivoService.save(objetivo);
            return Enums.ResponseCode.REGISTRO.get();
        }
        objetivoService.update(objetivo);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            objetivoService.actualizarFlagActivoById(id, flagActivo);
            return Enums.ResponseCode.EXITO_GENERICA.get();
        } catch (Exception e) {
            return Enums.ResponseCode.EX_GENERIC.get();
        }
    }
}
