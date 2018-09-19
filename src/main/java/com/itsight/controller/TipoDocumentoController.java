package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.TipoDocumento;
import com.itsight.service.TipoDocumentoService;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/gestion/tipo-documento")
public class TipoDocumentoController {

    private TipoDocumentoService tipoDocumentoService;

    @Autowired
    public TipoDocumentoController(TipoDocumentoService tipoDocumentoService) {
        // TODO Auto-generated constructor stub
        this.tipoDocumentoService = tipoDocumentoService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principalTipoDocumento() {
        return new ModelAndView(ViewConstant.MAIN_TIPO_DOCUMENTO);
    }

    @GetMapping(value = "/listarTodos")
    public @ResponseBody
    List<TipoDocumento> listarConFiltrosWithoutFilters(){
        return tipoDocumentoService.findAll();
    }

    @GetMapping(value = "/obtenerListado/{comodin}")
    public @ResponseBody
    List<TipoDocumento> listarConFiltro(
            @PathVariable("comodin") String comodin){
        return tipoDocumentoService.listarPorFiltro (comodin, "", "");
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    TipoDocumento obtenerEspecifico(@RequestParam(value = "id") int tipoDocumentoId) {
        return tipoDocumentoService.findOne(tipoDocumentoId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute TipoDocumento tipoDocumento) {
        if (tipoDocumento.getId() == 0) {
            tipoDocumentoService.save(tipoDocumento);
            return Enums.ResponseCode.REGISTRO.get()+","+String.valueOf(tipoDocumento.getId());
        } else {
            tipoDocumentoService.update(tipoDocumento);
            return Enums.ResponseCode.ACTUALIZACION.get()+","+String.valueOf(tipoDocumento.getId());
        }
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            tipoDocumentoService.actualizarFlagActivoById(id, flagActivo);
            return "1";
        } catch (Exception e) {
            return "-9";
        }
    }
}
