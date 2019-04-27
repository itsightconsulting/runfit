package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Documento;
import com.itsight.service.DocumentoService;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

import static com.itsight.util.Enums.ResponseCode.EXITO_GENERICA;
import static com.itsight.util.Enums.ResponseCode.EX_GENERIC;

@Controller
@RequestMapping("/gestion/documento")
public class DocumentoController {

    @Value("${main.repository}")
    private String mainRoute;

    private DocumentoService documentoService;

    public static final Logger LOGGER = LogManager.getLogger(DocumentoController.class);

    @Autowired
    public DocumentoController(
            DocumentoService documentoService) {
        // TODO Auto-generated constructor stub
        this.documentoService = documentoService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal() {
        return new ModelAndView(ViewConstant.MAIN_DOCUMENTO);
    }

    @GetMapping(value = "/listarTodos")
    public @ResponseBody List<Documento> listar() {
        return documentoService.findAll();
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}")
    public @ResponseBody
    List<Documento> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado) {
        return documentoService.listarPorFiltro(comodin, estado, null);
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    Documento obtenerPorId(@RequestParam(value = "id") Integer id) {
        return documentoService.findOne(id);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(
            @ModelAttribute Documento documento) throws CustomValidationException {

        if (documento.getId() == 0) {
            return documentoService.registrar(documento, null);
        }
        return documentoService.actualizar(documento, null);
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") Integer id, @RequestParam boolean flagActivo) {
        try {
            documentoService.actualizarFlagActivoById(id, flagActivo);
            return EXITO_GENERICA.get();
        } catch (Exception e) {
            return EX_GENERIC.get();
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String guardarArchivo(
            @RequestPart(value = "documento") MultipartFile documento,
            @RequestParam(value = "id") Integer id, HttpServletRequest request) {
        if (documento != null) {
            guardarFile(documento, id);
        }
        return EXITO_GENERICA.get();
    }

    private void guardarFile(MultipartFile file, Integer id) {
        if (!file.isEmpty()) {
            try {

                UUID uuid = UUID.randomUUID();
                String[] splitNameFile = file.getOriginalFilename().split("\\.");
                String extension = "." + splitNameFile[splitNameFile.length - 1];
                String fullPath;
                String rutaBase = mainRoute;
                fullPath = rutaBase + "/Documentos/" + id;
                Utilitarios.createDirectory(fullPath);
                fullPath += "/" + uuid + extension;

                File nuevoFile = new File(fullPath);

                // Agregando la ruta a la base de datos

                Documento qDocumento = documentoService.findOne(id);//findOneWithFT para este caso aplica a todas las ForeignKeys
                qDocumento.setRutaReal(fullPath);
                qDocumento.setRutaWeb("/" + id + "/" + uuid + extension);
                qDocumento.setUuid(uuid);

                // Pasando la imagen  o archivo desde la web hacia el servidor en donde se guardará en la ruta especificada en la instacia nueva de File creada
                file.transferTo(nuevoFile);

                documentoService.update(qDocumento);
                LOGGER.info("> ROUTE: " + fullPath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("> Isn't a file");
        }
    }
}
