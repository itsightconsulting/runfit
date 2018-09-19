package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.CategoriaEjercicio;
import com.itsight.domain.SubCategoriaEjercicio;
import com.itsight.service.CategoriaEjercicioService;
import com.itsight.service.SubCategoriaEjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/gestion/sub-categoria-ejercicio")
public class SubCategoriaEjercicioController {

    private CategoriaEjercicioService categoriaEjercicioCeService;
    private SubCategoriaEjercicioService subCategoriaEjercicioService;

    @Autowired
    public SubCategoriaEjercicioController(CategoriaEjercicioService categoriaEjercicioCeService, SubCategoriaEjercicioService subCategoriaEjercicioService) {
        // TODO Auto-generated constructor stub
        this.categoriaEjercicioCeService = categoriaEjercicioCeService;
        this.subCategoriaEjercicioService = subCategoriaEjercicioService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal(Model model) {
        model.addAttribute("lstCategoriaEjercicio", categoriaEjercicioCeService.findAll());
        return new ModelAndView(ViewConstant.MAIN_RUTINARIO_CE_SCE);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{categoriaId}")
    public @ResponseBody
    List<SubCategoriaEjercicio> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @PathVariable("categoriaId") String categoriaId) throws JsonProcessingException {
        return subCategoriaEjercicioService.listarPorFiltro(comodin, estado, categoriaId);
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    SubCategoriaEjercicio obtenerPorId(@RequestParam(value = "id") int subCategoriaEjercicioId) {
        return subCategoriaEjercicioService.findOne(subCategoriaEjercicioId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute SubCategoriaEjercicio subCategoriaEjercicio, @RequestParam int categoriaEjercicioId) {

        subCategoriaEjercicio.setCategoriaEjercicio(new CategoriaEjercicio(categoriaEjercicioId));
        if (subCategoriaEjercicio.getId() == 0) {

            subCategoriaEjercicioService.save(subCategoriaEjercicio);
            return "1";
        }
        subCategoriaEjercicioService.update(subCategoriaEjercicio);
        return "2";
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            subCategoriaEjercicioService.actualizarFlagActivoById(id, flagActivo);
            return "1";
        } catch (Exception e) {
            return "-9";
        }
    }
}
