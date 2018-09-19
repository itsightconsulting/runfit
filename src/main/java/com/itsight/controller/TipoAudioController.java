package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.TipoAudio;
import com.itsight.domain.TipoAudio;
import com.itsight.service.TipoAudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    String nuevo(@ModelAttribute TipoAudio tipoAudio) {
        if (tipoAudio.getId() == 0) {
            tipoAudioService.save(tipoAudio);
            return String.valueOf(tipoAudio.getId());
        } else {
            tipoAudioService.update(tipoAudio);
            return String.valueOf(tipoAudio.getId());
        }
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            tipoAudioService.actualizarFlagActivoById(id, flagActivo);
            return "1";
        } catch (Exception e) {
            return "-9";
        }
    }
}
