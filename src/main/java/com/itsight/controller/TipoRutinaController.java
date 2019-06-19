package com.itsight.controller;


import com.itsight.constants.ViewConstant;
import com.itsight.domain.TipoRutina;
import com.itsight.domain.dto.TipoRutinaDTO;
import com.itsight.domain.dto.TrainerDTO;
import com.itsight.service.TipoRutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

import static com.itsight.util.Enums.ResponseCode.EXITO_GENERICA;
import static com.itsight.util.Enums.ResponseCode.EX_GENERIC;

@Controller
@RequestMapping(value="/gestion/tipo-rutina")
public class TipoRutinaController {

    private TipoRutinaService tipoRutinaService;

    @Autowired
    public TipoRutinaController(TipoRutinaService tipoRutinaService) {
        this.tipoRutinaService = tipoRutinaService;
    }

    @GetMapping(value = "")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principalTipoRutina() {
        return new ModelAndView(ViewConstant.MAIN_TIPO_RUTINA);
    }

    @GetMapping(value="/obtener/{txtFiltro}/{estado}")
    public @ResponseBody
    List<TipoRutina> obtenerTipoRutina(@PathVariable("txtFiltro") String txtFiltro, @PathVariable("estado") String estado)
    {

        return tipoRutinaService.obtenerTipoRutina(txtFiltro,estado);
    }

    @GetMapping(value="/consultar")
    public @ResponseBody
    TipoRutina obtenerTipoRutinaporId(@RequestParam Integer id)
    {

        return tipoRutinaService.obtenerTipoRutinaporId(id);
    }



    @PostMapping(value="/agregar")
    public @ResponseBody
    TipoRutina nuevoTipoRutina(@ModelAttribute @Valid TipoRutinaDTO tipoRutina)
    {
        return tipoRutinaService.ingresarTipoRutina(tipoRutina);
    }



    @PostMapping(value="/actualizar")
    public @ResponseBody
    TipoRutina actualizarTipoRutina(@ModelAttribute @Valid TipoRutinaDTO tipoRutina)
    {
        return tipoRutinaService.actualizarTipoRutina(tipoRutina);
    }

    @PutMapping(value="/actualizar-estado")
    public @ResponseBody
    String actualizarFlagActivadoRutina(@RequestParam Integer id)
    {
        try{
            tipoRutinaService.actualizarFlagActivadoRutina(id);

            return EXITO_GENERICA.get();


        } catch (Exception e) {
            e.printStackTrace();
            return EX_GENERIC.get();
        }

    }




}
