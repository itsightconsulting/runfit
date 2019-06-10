package com.itsight.controller;

import com.itsight.domain.pojo.AnuncioPOJO;
import com.itsight.service.AnuncioReceptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.itsight.util.Utilitarios.jsonResponse;

@Controller
@RequestMapping("/gestion/anuncio")
public class AnuncioReceptorController {

    private AnuncioReceptorService anuncioReceptorService;

    @Autowired
    public AnuncioReceptorController(AnuncioReceptorService anuncioReceptorService) {
        this.anuncioReceptorService = anuncioReceptorService;
    }

    @GetMapping(value = "/list/cliente")
    public @ResponseBody
    List<AnuncioPOJO> listarTodosLosAnunciosSegunCliente(HttpSession session){
        Integer clienteId = (Integer) session.getAttribute("id");
        return anuncioReceptorService.findAllAnuncioByClienteId(clienteId);
    }

    @PutMapping(value = "/actualizar/flag-check/{id}")
    public @ResponseBody
    String actualizarFlagCheck(@PathVariable(name = "id") Integer id, HttpSession session){
        Integer clienteId = (Integer) session.getAttribute("id");
        return jsonResponse(anuncioReceptorService.actualizarFlagLeidoByIdAndClienteId(id, clienteId));
    }

    @PutMapping(value = "/actualizar/all/flag-check")
    public @ResponseBody String actualizarAllFlagCheck(HttpSession session){
        Integer clienteId = (Integer) session.getAttribute("id");
        return jsonResponse(anuncioReceptorService.actualizarAllFlagLeidoByClienteId(clienteId));
    }

}
