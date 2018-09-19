package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.TipoUsuario;
import com.itsight.service.TipoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/gestion/tipo-usuario")
public class TipoUsuarioController {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @GetMapping(value = "/listarTodos")
    public @ResponseBody
    List<TipoUsuario> listAllProfiles() throws JsonProcessingException {
        return tipoUsuarioService.listAll();
    }

}
