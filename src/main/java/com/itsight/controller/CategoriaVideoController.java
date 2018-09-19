package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.CategoriaVideo;
import com.itsight.domain.GrupoVideo;
import com.itsight.service.CategoriaVideoService;
import com.itsight.service.EspecificacionSubCategoriaService;
import com.itsight.service.GrupoVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/gestion/categoria-video")
public class CategoriaVideoController {

    private GrupoVideoService grupoVideoService;
    private CategoriaVideoService categoriaVideoService;

    @Autowired
    public CategoriaVideoController(GrupoVideoService grupoVideoService, CategoriaVideoService categoriaVideoService) {
        // TODO Auto-generated constructor stub
        this.grupoVideoService = grupoVideoService;
        this.categoriaVideoService = categoriaVideoService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal(Model model) {
        model.addAttribute("lstGrupoVideo", grupoVideoService.findAll());
        return new ModelAndView(ViewConstant.MAIN_ASSETS_CATEGORIA_VIDEO);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{categoriaId}")
    public @ResponseBody
    List<CategoriaVideo> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @PathVariable("categoriaId") String categoriaId) {
        return categoriaVideoService.listarPorFiltro(comodin, estado, categoriaId);
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    CategoriaVideo obtenerPorId(@RequestParam(value = "id") int grupoVideoId) {
        return categoriaVideoService.findOne(grupoVideoId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute CategoriaVideo categoriaVideo, @RequestParam int grupoVideoId) {

        categoriaVideo.setGrupoVideo(new GrupoVideo(grupoVideoId));
        if (categoriaVideo.getId() == 0) {
            categoriaVideoService.save(categoriaVideo);
            return "1";
        }
        categoriaVideoService.update(categoriaVideo);
        return "2";
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            categoriaVideoService.actualizarFlagActivoById(id, flagActivo);
            return "1";
        } catch (Exception e) {
            return "-9";
        }
    }
}
