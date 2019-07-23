package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.CategoriaVideo;
import com.itsight.domain.GrupoVideo;
import com.itsight.domain.SubCategoriaVideo;
import com.itsight.service.GrupoVideoService;
import com.itsight.service.SubCategoriaVideoService;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

import static com.itsight.util.Enums.Msg.FLAG_BLOQUEADO_TIENE_DEPS;
import static com.itsight.util.Enums.ResponseCode.*;

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
    String nuevo(@ModelAttribute @Valid SubCategoriaVideo subCategoriaVideo,
                 @RequestParam int categoriaVideoId,
                 BindingResult bindingResult) throws CustomValidationException {
        subCategoriaVideo.setCategoriaVideo(new CategoriaVideo(categoriaVideoId));
        if(!bindingResult.hasErrors()){
            if (subCategoriaVideo.getId() == 0) {
                return subCategoriaVideoService.registrar(subCategoriaVideo, null);
            }
            return subCategoriaVideoService.actualizar(subCategoriaVideo, null);
        }
        return Enums.ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) throws CustomValidationException {
        if(flagActivo){
            subCategoriaVideoService.actualizarFlagActivoById(id, flagActivo);
            return EXITO_GENERICA.get();
        }

        boolean hasChildren = subCategoriaVideoService.checkHaveChildrenById(id);

        if(!hasChildren){
            subCategoriaVideoService.actualizarFlagActivoById(id, flagActivo);
            return EXITO_GENERICA.get();
        }
        throw new CustomValidationException(FLAG_BLOQUEADO_TIENE_DEPS.get(), EX_VALIDATION_FAILED.get());
    }
}
