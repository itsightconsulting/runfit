package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.CategoriaEjercicio;
import com.itsight.domain.EspecificacionSubCategoria;
import com.itsight.domain.MiniPlantilla;
import com.itsight.domain.SubCategoriaEjercicio;
import com.itsight.service.CategoriaEjercicioService;
import com.itsight.service.EspecificacionSubCategoriaService;
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
@RequestMapping("/gestion/especificacion-sub-categoria-ejercicio")
public class EspecificacionSubCategoriaController {

    @Autowired
    private EspecificacionSubCategoriaService especificacionSubCategoriaService;

    @Autowired
    private CategoriaEjercicioService categoriaEjercicioService;

    @Autowired
    private MiniPlantillaService miniPlantillaService;

    @Autowired
    public EspecificacionSubCategoriaController(CategoriaEjercicioService categoriaEjercicioService) {
        // TODO Auto-generated constructor stub
        this.categoriaEjercicioService = categoriaEjercicioService;
    }

    @GetMapping(value = {"/v2"})
    public @ResponseBody
    String obtenerArbolMiniPlantilla(HttpSession session) throws JsonProcessingException {
        List<MiniPlantilla> minis = miniPlantillaService.findAllByUsuarioId(Integer.parseInt(session.getAttribute("id").toString()));
        if(minis.isEmpty()){
            return "-9";
        }
        BagForest forest = reconstructForest(minis, 1);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(forest.getTrees());
    }

    protected BagForest reconstructForest(List<MiniPlantilla> leaves, int forestId) {
        EntityGraphBuilder entityGraphBuilder = new EntityGraphBuilder(new EntityVisitor[]{
                CategoriaEjercicio.ENTITY_VISITOR, SubCategoriaEjercicio.ENTITY_VISITOR, EspecificacionSubCategoria.ENTITY_VISITOR, MiniPlantilla.ENTITY_VISITOR, BagForest.ENTITY_VISITOR
        }).build(leaves);
        ClassId<BagForest> forestClassId = new ClassId<BagForest>(BagForest.class, forestId);
        return entityGraphBuilder.getEntityContext().getObject(forestClassId);
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal(Model model) {
        model.addAttribute("lstCategoriaEjercicio", categoriaEjercicioService.encontrarCategoriaConSusDepedencias());
        return new ModelAndView(ViewConstant.MAIN_RUTINARIO_CE_ESCE);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{subCategoriaId}")
    public @ResponseBody
    List<EspecificacionSubCategoria> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @PathVariable("subCategoriaId") String subCategoriaId) {
        return especificacionSubCategoriaService.listarPorFiltro(comodin, estado, subCategoriaId);
    }

    @GetMapping(value = "/listarPorSubCategoria")
    public @ResponseBody
    List<EspecificacionSubCategoria> listarConFiltro(
            @RequestParam("subCatId") String subCatId) {
        return especificacionSubCategoriaService.listarPorSubCategoria(Integer.parseInt(subCatId));
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    EspecificacionSubCategoria obtenerPorId(@RequestParam(value = "id") int especificacionSubCategoriaId) {
        return especificacionSubCategoriaService.findOne(especificacionSubCategoriaId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute EspecificacionSubCategoria especificacionSubCategoria, @RequestParam int subCategoriaEjercicioId) {

        especificacionSubCategoria.setSubCategoriaEjercicio(new SubCategoriaEjercicio(subCategoriaEjercicioId));
        if (especificacionSubCategoria.getId() == 0) {
            List<Integer> ids = new ArrayList<>();
            for (int i=1; i<4;i++){
                EspecificacionSubCategoria obj = new EspecificacionSubCategoria(especificacionSubCategoria.getNombre(), subCategoriaEjercicioId, i);
                especificacionSubCategoriaService.save(obj);
                ids.add(obj.getId());

            }
            for (int i=0; i<3;i++){
                miniPlantillaService.relacionarNuevasEspecificaciones(ids.get(i));
            }
            return "1";
        }
        especificacionSubCategoriaService.update(especificacionSubCategoria);
        return "2";
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            especificacionSubCategoriaService.actualizarFlagActivoById(id, flagActivo);
            return "1";
        } catch (Exception e) {
            return "-9";
        }
    }
}