package com.itsight.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.advice.CustomValidationException;
import com.itsight.domain.CategoriaPlantilla;
import com.itsight.domain.SubCategoriaPlantilla;
import com.itsight.domain.dto.CategoriaPlantillaDTO;
import com.itsight.domain.dto.SubCategoriaPlantillaDTO;
import com.itsight.service.CategoriaPlantillaProcedureInvoker;
import com.itsight.service.SubCategoriaPlantillaProcedureInvoker;
import com.itsight.service.SubCategoriaPlantillaService;
import com.itsight.service.impl.SubCategoriaPlantillaProcedureInvokerImpl;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/gestion/subcategoria-plantilla")
public class SubCategoriaPlantillaController {

    SubCategoriaPlantillaService subCategoriaPlantillaService;
    SubCategoriaPlantillaProcedureInvoker subCategoriaPlantillaProcedureInvoker;

    @Autowired
    public SubCategoriaPlantillaController(SubCategoriaPlantillaService subCategoriaPlantillaService ,SubCategoriaPlantillaProcedureInvoker subCategoriaPlantillaProcedureInvoker) {
        this.subCategoriaPlantillaService = subCategoriaPlantillaService;
        this.subCategoriaPlantillaProcedureInvoker = subCategoriaPlantillaProcedureInvoker;
    }


    @GetMapping(value = "/obtener")
    public @ResponseBody
    List<SubCategoriaPlantillaDTO> obtenerSubCategorias(@RequestParam Integer categoriaId) throws JsonProcessingException {
        List<SubCategoriaPlantillaDTO> lstSubCatPlantilla = new ArrayList<>();
        lstSubCatPlantilla =subCategoriaPlantillaService.obtenerSubCategoriasbyCategoriaId(categoriaId);

        return lstSubCatPlantilla;
    }



    @PostMapping(value = "/agregar")
    public @ResponseBody
    String agregarSubCategoria(@ModelAttribute @Valid SubCategoriaPlantilla subCategoriaPlantilla, @RequestParam Integer catPlantillaId ) throws CustomValidationException {
        return subCategoriaPlantillaService.agregarSubCategoriaPlantilla(subCategoriaPlantilla, catPlantillaId);
    }


    @PutMapping(value = "/actualizar")
    public @ResponseBody
    String actualizarSubCategoria(
            @ModelAttribute @Valid SubCategoriaPlantilla subCategoriaPlantilla, BindingResult bindingResult) throws JsonProcessingException {

        if(!bindingResult.hasErrors())
            return subCategoriaPlantillaService.actualizarSubCategoriaPlantilla(subCategoriaPlantilla);

        return Enums.ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @DeleteMapping(value = "/eliminar")
    public @ResponseBody
    String eliminarSubCategoria(@RequestParam Integer id) throws JsonProcessingException {

        subCategoriaPlantillaProcedureInvoker.eliminarSubCategoria(id);

        return Enums.ResponseCode.ELIMINACION.get();
    }

    @PutMapping(value = "/actualizar-favorito")
    public @ResponseBody
    String actualizarFlagFavoritoSubCategoria(
            @ModelAttribute @Valid SubCategoriaPlantilla subCategoriaPlantilla, BindingResult bindingResult) throws JsonProcessingException {

        if(!bindingResult.hasErrors())
            return subCategoriaPlantillaService.actualizarFlagFavorito(subCategoriaPlantilla);

        return Enums.ResponseCode.EX_VALIDATION_FAILED.get();
    }





}
