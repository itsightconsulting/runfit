package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.Descuento;
import com.itsight.service.DescuentoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itsight.util.Enums.ResponseCode.*;

@Controller
@RequestMapping("/gestion/descuento")
public class DescuentoController {

    private DescuentoService descuentoService;

    public static final Logger LOGGER = LogManager.getLogger(DescuentoController.class);

    @Autowired
    public DescuentoController(
            DescuentoService descuentoService) {
        // TODO Auto-generated constructor stub
        this.descuentoService = descuentoService;
    }

    @GetMapping(value = "/obtenerListado/producto/{productoId}")
    public @ResponseBody
    List<Descuento> listarPorProductoId(@PathVariable int productoId) throws JsonProcessingException {
        return descuentoService.listarPorProductoId(productoId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute Descuento descuento, String tipoDescuentoId, String productoId) {
        if (descuento.getId() == 0) {
            descuento.setTipoDescuento(Integer.parseInt(tipoDescuentoId));
            descuento.setProductoPresentacion(Integer.parseInt(productoId));
            return descuentoService.registrar(descuento, null);
        }
        return descuentoService.actualizar(descuento, tipoDescuentoId + "," + productoId);
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id,@RequestParam boolean flagActivo) {
        try {
            descuentoService.actualizarFlagActivoById(id, flagActivo);
            return EXITO_GENERICA.get();
        } catch (Exception e) {
            return EX_GENERIC.get();
        }
    }
}
