package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.CapacidadMejora;
import com.itsight.service.CapacidadMejoraService;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/gestion/capacidad-mejora")
public class CapacidadMejoraController {

    private CapacidadMejoraService capacidadMejoraService;

    @Autowired
    public CapacidadMejoraController(CapacidadMejoraService capacidadMejoraService) {
        // TODO Auto-generated constructor stub
        this.capacidadMejoraService = capacidadMejoraService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principalCapacidadMejora() {
        return new ModelAndView(ViewConstant.MAIN_CAPACIDAD_MEJORA);
    }

    @GetMapping(value = "/listarTodos")
    public @ResponseBody
    List<CapacidadMejora> listarConFiltrosWithoutFilters(){
        return capacidadMejoraService.findAll();
    }

    @GetMapping(value = "/obtenerListado/{comodin}")
    public @ResponseBody
    List<CapacidadMejora> listarConFiltro(
            @PathVariable("comodin") String comodin){
        return capacidadMejoraService.listarPorFiltro (comodin, "", "");
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    CapacidadMejora obtenerEspecifico(@RequestParam(value = "id") int capacidadMejoraId) {
        return capacidadMejoraService.findOne(capacidadMejoraId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute CapacidadMejora capacidadMejora) {
        if (capacidadMejora.getId() == 0) {
            capacidadMejoraService.save(capacidadMejora);
            return Enums.ResponseCode.REGISTRO.get()+","+String.valueOf(capacidadMejora.getId());
        } else {
            capacidadMejoraService.update(capacidadMejora);
            return Enums.ResponseCode.ACTUALIZACION.get()+","+String.valueOf(capacidadMejora.getId());
        }
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            capacidadMejoraService.actualizarFlagActivoById(id, flagActivo);
            return "1";
        } catch (Exception e) {
            return "-9";
        }
    }
}
