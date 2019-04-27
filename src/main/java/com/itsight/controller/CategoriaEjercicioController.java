package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.CategoriaEjercicio;
import com.itsight.service.CategoriaEjercicioService;
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

import static com.itsight.util.Enums.ResponseCode.EXITO_GENERICA;
import static com.itsight.util.Enums.ResponseCode.EX_GENERIC;

@Controller
@RequestMapping("/gestion/categoria-ejercicio")
public class CategoriaEjercicioController {

    @Value("${main.repository}")
    private String mainRoute;

    private CategoriaEjercicioService categoriaEjercicioService;

    public static final Logger LOGGER = LogManager.getLogger(CategoriaEjercicioController.class);

    @Autowired
    public CategoriaEjercicioController(CategoriaEjercicioService categoriaEjercicioService) {
        // TODO Auto-generated constructor stub
        this.categoriaEjercicioService = categoriaEjercicioService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal() {
        return new ModelAndView(ViewConstant.MAIN_RUTINARIO_CE_CE);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}")
    public @ResponseBody
    List<CategoriaEjercicio> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado) {
        return categoriaEjercicioService.listarPorFiltro(comodin, estado, null);
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    CategoriaEjercicio obtener(@RequestParam(value = "id") int categoriaEjercicioId) {
        return categoriaEjercicioService.findOneWithFT(categoriaEjercicioId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute CategoriaEjercicio categoriaEjercicio) throws CustomValidationException {
        if (categoriaEjercicio.getId() == 0) {
            return categoriaEjercicioService.registrar(categoriaEjercicio, null);
        }
        return categoriaEjercicioService.actualizar(categoriaEjercicio, null);
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            categoriaEjercicioService.actualizarFlagActivoById(id, flagActivo);
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
                String fullPath = mainRoute + "/CategoriasEjercicio/"+categoriaId;
                Utilitarios.createDirectory(fullPath);
                fullPath += "/" + uuid + extension;

                File nuevoFile = new File(fullPath);

                // Agregando la ruta a la base de datos

                CategoriaEjercicio qCategoria = categoriaEjercicioService.findOne(categoriaId);
                qCategoria.setRutaReal(fullPath);
                qCategoria.setRutaWeb("/" + categoriaId + "/" + uuid + extension);
                qCategoria.setUuid(uuid);

                // Pasando la imagen desde la web hacia el servidor en donde se guardará en la ruta especificada en la instacia nueva de File creada
                file.transferTo(nuevoFile);

                categoriaEjercicioService.save(qCategoria);
                LOGGER.info("> ROUTE: " + fullPath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("> Isn't a file");
        }
    }
}
