package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.*;
import com.itsight.service.CategoriaVideoService;
import com.itsight.service.GrupoVideoService;
import com.itsight.service.SubCategoriaVideoService;
import com.itsight.service.MiniPlantillaService;
import com.itsight.util.ClassId;
import com.itsight.util.EntityGraphBuilder;
import com.itsight.util.EntityVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/gestion/sub-categoria-video")
public class SubCategoriaVideoController {

    private SubCategoriaVideoService subCategoriaVideoService;

    private GrupoVideoService grupoVideoService;

    @Autowired
    public SubCategoriaVideoController(SubCategoriaVideoService subCategoriaVideoService, GrupoVideoService grupoVideoService) {
        // TODO Auto-generated constructor stub
        this.subCategoriaVideoService = subCategoriaVideoService;
        this.grupoVideoService = grupoVideoService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal(Model model) {
        model.addAttribute("lstGrupoVideo", grupoVideoService.encontrarGrupoConSusDepedencias());
        return new ModelAndView(ViewConstant.MAIN_ASSETS_SUB_CATEGORIA_VIDEO);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{subCategoriaId}")
    public @ResponseBody
    List<SubCategoriaVideo> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @PathVariable("subCategoriaId") String subCategoriaId) {
        return subCategoriaVideoService.listarPorFiltro(comodin, estado, subCategoriaId);
    }

    @GetMapping(value = "/listarPorCategoria")
    public @ResponseBody
    List<SubCategoriaVideo> listarPorCategoria(
            @RequestParam("catVideoId") String catVideoId) {
        return subCategoriaVideoService.listarPorCategoria(Integer.parseInt(catVideoId));
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    SubCategoriaVideo obtenerPorId(@RequestParam(value = "id") int id) {
        return subCategoriaVideoService.findOne(id);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute SubCategoriaVideo subCategoriaVideo, @RequestParam int categoriaVideoId) {

        subCategoriaVideo.setCategoriaVideo(new CategoriaVideo(categoriaVideoId));
        if (subCategoriaVideo.getId() == 0) {
            SubCategoriaVideo obj = new SubCategoriaVideo(subCategoriaVideo.getNombre(), categoriaVideoId);
            subCategoriaVideoService.save(obj);
            return "1";
        }
        subCategoriaVideoService.update(subCategoriaVideo);
        return "2";
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            subCategoriaVideoService.actualizarFlagActivoById(id, flagActivo);
            return "1";
        } catch (Exception e) {
            return "-9";
        }
    }
}