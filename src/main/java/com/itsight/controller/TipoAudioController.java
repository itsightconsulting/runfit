package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.TipoAudio;
import com.itsight.service.TipoAudioService;
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
@RequestMapping("/gestion/tipo-audio")
public class TipoAudioController {

    private TipoAudioService tipoAudioService;

    @Autowired
    public TipoAudioController(TipoAudioService tipoAudioService) {
        // TODO Auto-generated constructor stub
        this.tipoAudioService = tipoAudioService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principalTipoAudio() {
        return new ModelAndView(ViewConstant.MAIN_TIPO_AUDIO);
    }

    @GetMapping(value = "/listarTodos")
    public @ResponseBody
    List<TipoAudio> listAllAudioTypesWithoutFilters(){
        return tipoAudioService.findAll();
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}")
    public @ResponseBody
    List<TipoAudio> listAllAudioType(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado) {
        return tipoAudioService.listarPorFiltro(comodin, estado, "");

    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    TipoAudio getAudioTypeById(@RequestParam(value = "id") int tipoAudioId) {
        return tipoAudioService.findOne(tipoAudioId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute @Valid TipoAudio tipoAudio, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            if (tipoAudio.getId() == 0) {
                return tipoAudioService.registrar(tipoAudio, null);
            } else {
                return tipoAudioService.actualizar(tipoAudio, null);
            }
        }
        return Enums.ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            tipoAudioService.actualizarFlagActivoById(id, flagActivo);
            return EXITO_GENERICA.get();
        } catch (Exception e) {
            return EX_GENERIC.get();
        }
    }

    @GetMapping(value = "/obtener/arbol")
    public @ResponseBody List<TipoAudio> getTipoAudioChildrens(){
        return tipoAudioService.findAllWithChildrens();
    }
}
