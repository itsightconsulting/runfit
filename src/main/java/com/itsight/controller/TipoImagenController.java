package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.TipoImagen;
import com.itsight.service.TipoImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/gestion/tipo-imagen")
public class TipoImagenController {

    private TipoImagenService tipoImagenService;

    @Autowired
    public TipoImagenController(TipoImagenService tipoImagenService) {
        // TODO Auto-generated constructor stub
        this.tipoImagenService = tipoImagenService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principalTipoImagen() {
        return new ModelAndView(ViewConstant.MAIN_TIPO_IMAGEN);
    }

    @GetMapping(value = "/listarTodos")
    public @ResponseBody
    String listAll() throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.registerModule(new Hibernate5Module());
        List<TipoImagen> lstTipoImagen = tipoImagenService.listAll();
        String result = "{"
                + "\"total\":" + lstTipoImagen.size() + ","
                + "\"rows\":" + objMapper.writeValueAsString(lstTipoImagen)
                + "}";
        return result;
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String addTipoImagen(@ModelAttribute TipoImagen tipoImagen) {

        if (tipoImagen.getId() == 0) {

            tipoImagenService.add(tipoImagen);
            return "1";
        } else {
            tipoImagenService.update(tipoImagen);
            return "2";
        }
    }
}
