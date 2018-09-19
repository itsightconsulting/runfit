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
            descuentoService.save(descuento);
            return String.valueOf(descuento.getId());
        }
        Descuento qDescuento = descuentoService.findOne(descuento.getId());
        qDescuento.setTipoDescuento(Integer.parseInt(tipoDescuentoId));
        qDescuento.setProductoPresentacion(Integer.parseInt(productoId));
        qDescuento.setPrecioInicial(descuento.getPrecioInicial());
        qDescuento.setDscto(descuento.getDscto());
        qDescuento.setPrecioFinal(descuento.getPrecioFinal());
        qDescuento.setFechaInicio(descuento.getFechaInicio());
        qDescuento.setFechaFin(descuento.getFechaFin());
        descuentoService.update(qDescuento);
        return String.valueOf(qDescuento.getId());
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id,@RequestParam boolean flagActivo) {
        try {
            descuentoService.actualizarFlagActivoById(id, flagActivo);
            return "1";
        } catch (Exception e) {
            return "-9";
        }
    }
}
