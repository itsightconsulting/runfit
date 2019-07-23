package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Categoria;
import com.itsight.service.CategoriaService;
import com.itsight.util.Utilitarios;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static com.itsight.util.Enums.ResponseCode.*;


@Controller
@RequestMapping("/gestion/categoria")
public class CategoriaController {

    @Value("${main.repository}")
    private String mainRoute;

    private CategoriaService categoriaService;

    public static final Logger LOGGER = LogManager.getLogger(CategoriaController.class);

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        // TODO Auto-generated constructor stub
        this.categoriaService = categoriaService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal() {
        return new ModelAndView(ViewConstant.MAIN_CATEGORIA);
    }

    @GetMapping(value = "/listarTodos")
    public @ResponseBody
    List<Categoria> listar() throws JsonProcessingException {
        return categoriaService.findAll();
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}")
    public @ResponseBody
    List<Categoria> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado) throws JsonProcessingException {

        return categoriaService.listarPorFiltro(comodin, estado, null);
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    Categoria obtenerPorId(@RequestParam(value = "id") int categoriaId) {
        return categoriaService.findOne(categoriaId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute Categoria categoria) throws CustomValidationException {
        if (categoria.getId() == 0) {
            return categoriaService.registrar(categoria, null);
        }
        return categoriaService.actualizar(categoria, null);
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            categoriaService.actualizarFlagActivoById(id, flagActivo);
            return EXITO_GENERICA.get();
        } catch (Exception e) {
            return EX_GENERIC.get();
        }
    }

    @RequestMapping(value = "/upload/imagen", method = RequestMethod.POST)
    public @ResponseBody
    String guardarArchivo(
            @RequestPart MultipartFile imagen,
            @RequestParam(value = "categoriaId") Integer categoriaId) {

        if (imagen != null) {
            guardarFile(imagen, categoriaId);
        }
        return EXITO_GENERICA.get();
    }

    private void guardarFile(MultipartFile file, int categoriaId) {
        if (!file.isEmpty()) {
            try {
                UUID uuid = UUID.randomUUID();
                String[] splitNameFile = file.getOriginalFilename().split("\\.");
                String extension = "." + splitNameFile[splitNameFile.length - 1];
                String fullPath = mainRoute + "/Categorias/"+categoriaId;
                Utilitarios.createDirectory(fullPath);
                fullPath += "/" + uuid + extension;

                File nuevoFile = new File(fullPath);

                // Agregando la ruta a la base de datos

                Categoria qCategoria = categoriaService.findOne(categoriaId);
                qCategoria.setRutaWeb("/" + categoriaId + "/" + uuid + extension);
                qCategoria.setUuid(uuid);

                // Pasando la imagen desde la web hacia el servidor en donde se guardará en la ruta especificada en la instacia nueva de File creada
                file.transferTo(nuevoFile);

                categoriaService.save(qCategoria);
                LOGGER.info("> ROUTE: " + fullPath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("> Isn't a file");
        }
    }
}
