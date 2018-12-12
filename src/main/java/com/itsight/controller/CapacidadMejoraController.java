package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.CapacidadMejora;
import com.itsight.service.CapacidadMejoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.itsight.util.Enums.ResponseCode.*;
import static com.itsight.util.Utilitarios.customResponse;

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
            return capacidadMejoraService.registrar(capacidadMejora, null);
        }
        return capacidadMejoraService.actualizar(capacidadMejora, null);
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            capacidadMejoraService.actualizarFlagActivoById(id, flagActivo);
            return EXITO_GENERICA.get();
        } catch (Exception e) {
            return EX_GENERIC.get();
        }
    }
}
