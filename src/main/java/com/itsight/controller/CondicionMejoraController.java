package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.CondicionMejora;
import com.itsight.domain.dto.CondicionMejoraDTO;
import com.itsight.service.CondicionMejoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.itsight.util.Enums.ResponseCode.EXITO_GENERICA;
import static com.itsight.util.Enums.ResponseCode.EX_GENERIC;

@Controller
@RequestMapping("/gestion/condicion-mejora")
public class CondicionMejoraController {

    private CondicionMejoraService condicionMejoraService;

    @Autowired
    public CondicionMejoraController(CondicionMejoraService condicionMejoraService) {
        // TODO Auto-generated constructor stub
        this.condicionMejoraService = condicionMejoraService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principalCondicionMejora() {
        return new ModelAndView(ViewConstant.MAIN_CAPACIDAD_MEJORA);
    }

    @GetMapping(value = "/listarTodos")
    public @ResponseBody
    List<CondicionMejoraDTO> listarWithoutFilters(){
        return condicionMejoraService.getAll();
    }

    @GetMapping(value = "/obtenerListado/{comodin}")
    public @ResponseBody
    List<CondicionMejora> listarConFiltro(
            @PathVariable("comodin") String comodin){
        return condicionMejoraService.listarPorFiltro (comodin, "", "");
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    CondicionMejora obtenerEspecifico(@RequestParam(value = "id") int capacidadMejoraId) {
        return condicionMejoraService.findOne(capacidadMejoraId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute CondicionMejora capacidadMejora) throws CustomValidationException {
        if (capacidadMejora.getId() == 0) {
            return condicionMejoraService.registrar(capacidadMejora, null);
        }
        return condicionMejoraService.actualizar(capacidadMejora, null);
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            condicionMejoraService.actualizarFlagActivoById(id, flagActivo);
            return EXITO_GENERICA.get();
        } catch (Exception e) {
            return EX_GENERIC.get();
        }
    }
}
