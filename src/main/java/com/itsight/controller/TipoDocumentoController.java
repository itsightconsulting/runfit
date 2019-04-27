package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.TipoDocumento;
import com.itsight.service.TipoDocumentoService;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

import static com.itsight.util.Enums.ResponseCode.EXITO_GENERICA;
import static com.itsight.util.Enums.ResponseCode.EX_GENERIC;

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
    String nuevo(@ModelAttribute @Valid TipoDocumento tipoDocumento, BindingResult bindingResult) throws CustomValidationException {
        if(!bindingResult.hasErrors()){
            if (tipoDocumento.getId() == 0) {
                return tipoDocumentoService.registrar(tipoDocumento, null);
            } else {
                return tipoDocumentoService.actualizar(tipoDocumento, null);
            }
        }
        return Enums.ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            tipoDocumentoService.actualizarFlagActivoById(id, flagActivo);
            return EXITO_GENERICA.get();
        } catch (Exception e) {
            return EX_GENERIC.get();
        }
    }
}
