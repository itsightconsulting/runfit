package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.CategoriaPlantilla;
import com.itsight.domain.dto.CategoriaPlantillaDTO;
import com.itsight.service.CategoriaPlantillaProcedureInvoker;
import com.itsight.service.CategoriaPlantillaService;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller

@RequestMapping("/gestion/categoria-plantilla")
public class CategoriaPlantillaController {

    CategoriaPlantillaService categoriaPlantillaService;
    CategoriaPlantillaProcedureInvoker categoriaPlantillaProcedureInvoker;


    @Autowired
    public CategoriaPlantillaController(CategoriaPlantillaService categoriaPlantillaService, CategoriaPlantillaProcedureInvoker categoriaPlantillaProcedureInvoker) {
        this.categoriaPlantillaService = categoriaPlantillaService;
        this.categoriaPlantillaProcedureInvoker = categoriaPlantillaProcedureInvoker;
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String agregarCategoria(
            @ModelAttribute @Valid CategoriaPlantilla categoriaPlantilla, BindingResult bindingResult) throws JsonProcessingException {

        if(!bindingResult.hasErrors())
            return categoriaPlantillaService.agregarCategoriaPlantilla(categoriaPlantilla);

        return Enums.ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/actualizar")
    public @ResponseBody
    String actualizarCategoria(
            @ModelAttribute @Valid CategoriaPlantilla categoriaPlantilla, BindingResult bindingResult) throws JsonProcessingException {

        if(!bindingResult.hasErrors())
            return categoriaPlantillaService.actualizarCategoriaPlantilla(categoriaPlantilla);

        return Enums.ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @DeleteMapping(value = "/eliminar")
    public @ResponseBody
    String eliminarCategoria(@RequestParam Integer id) throws JsonProcessingException {

        categoriaPlantillaProcedureInvoker.eliminarCategoriaPlantilla(id);

        return Enums.ResponseCode.ELIMINACION.get();
    }




    @PutMapping(value = "/actualizar-favorito")
    public @ResponseBody
    String actualizarFlagFavoritoCategoria(
            @ModelAttribute @Valid CategoriaPlantilla categoriaPlantilla, BindingResult bindingResult) throws JsonProcessingException {

        if(!bindingResult.hasErrors())
            return categoriaPlantillaService.actualizarFlagFavorito(categoriaPlantilla);

        return Enums.ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    List<CategoriaPlantillaDTO> obtenerCategoriasByTrainerIdAndTipo() throws JsonProcessingException {
       List<CategoriaPlantillaDTO> lstCatPlantilla = new ArrayList<>();
       lstCatPlantilla =categoriaPlantillaService.obtenerCategoriasbyTrainerId();

       return lstCatPlantilla;
    }

    @GetMapping(value = "/obtener/tipo")
    public @ResponseBody
    List<CategoriaPlantillaDTO> obtenerCategoriasByTrainerIdAndTipo(@RequestParam String tr) throws JsonProcessingException {

        int tipoRutinaId = Integer.parseInt(tr);

        List<CategoriaPlantillaDTO> lstCategoriaPlantilla = categoriaPlantillaService.obtenerCategoriasbyTrainerIdAndTipo(tipoRutinaId);

        return lstCategoriaPlantilla;
    }






}
