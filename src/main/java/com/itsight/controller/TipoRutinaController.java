package com.itsight.controller;


import com.itsight.domain.TipoRutina;
import com.itsight.domain.dto.TipoRutinaDTO;
import com.itsight.domain.dto.TrainerDTO;
import com.itsight.service.TipoRutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value="/gestion/tipo-rutina")
public class TipoRutinaController {

    private TipoRutinaService tipoRutinaService;

    @Autowired
    public TipoRutinaController(TipoRutinaService tipoRutinaService) {
        this.tipoRutinaService = tipoRutinaService;
    }

    @GetMapping(value="")
    public @ResponseBody
    TipoRutina consultarTipoRutina()
    {
        return tipoRutinaService.consultarTipoRutina(1);
    }

    @PostMapping(value="/agregar")
    public @ResponseBody
    TipoRutina nuevoTipoRutina(@RequestBody TipoRutinaDTO tipoRutina)
    {
        return tipoRutinaService.ingresarTipoRutina(tipoRutina);
    }


    @PutMapping(value="/actualizar")
    public @ResponseBody
    void actualizarTipoRutina(@RequestBody TipoRutinaDTO tipoRutina)
    {
        tipoRutinaService.actualizarTipoRutina(tipoRutina);
    }


    @PostMapping(value="/eliminar/{id}")
    public @ResponseBody void eliminarTipoRutina(@PathVariable("id") Integer tipoRutinaId)
    {
        tipoRutinaService.eliminarTipoRutina(tipoRutinaId);
    }




}
