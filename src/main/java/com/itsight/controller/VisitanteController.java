package com.itsight.controller;

import com.itsight.domain.dto.TipoRutinaDTO;
import com.itsight.domain.dto.VisitanteDTO;
import com.itsight.service.VisitanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping(value="/p/visitante")
public class VisitanteController {

 private VisitanteService visitanteService;

    @Autowired
    public VisitanteController(VisitanteService visitanteService) {
        this.visitanteService = visitanteService;
    }

    @PostMapping(value="/registro")
    public @ResponseBody String registroVisitante(@ModelAttribute @Valid VisitanteDTO visitante){

    return  visitanteService.registrarVisitante(visitante);
 }


}
