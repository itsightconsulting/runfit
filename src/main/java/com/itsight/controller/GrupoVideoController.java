package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.GrupoVideo;
import com.itsight.domain.dto.RefUpload;
import com.itsight.domain.dto.RefUploadIds;
import com.itsight.service.GrupoVideoService;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
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

import static com.itsight.util.Enums.FileExt.JPEG;
import static com.itsight.util.Enums.Msg.FLAG_BLOQUEADO_TIENE_DEPS;
import static com.itsight.util.Enums.Msg.VALIDACION_FALLIDA;
import static com.itsight.util.Enums.ResponseCode.*;
import static com.itsight.util.Utilitarios.jsonResponse;

@Controller
@RequestMapping("/gestion/grupo-video")
public class GrupoVideoController extends BaseController {

    @Value("${main.repository}")
    private String mainRoute;

    private GrupoVideoService grupoVideoService;

    public static final Logger LOGGER = LogManager.getLogger(GrupoVideoController.class);

    @Autowired
    public GrupoVideoController(GrupoVideoService grupoVideoService) {
        // TODO Auto-generated constructor stub
        this.grupoVideoService = grupoVideoService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal() {
        return new ModelAndView(ViewConstant.MAIN_ASSETS_GRUPO_VIDEO);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}")
    public @ResponseBody
    List<GrupoVideo> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado) {
        return grupoVideoService.listarPorFiltro(comodin, estado, null);
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    GrupoVideo obtener(@RequestParam(value = "id") int grupoVideoId) {
        return grupoVideoService.findOneWithFT(grupoVideoId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute GrupoVideo grupoVideo) throws CustomValidationException {
        if (grupoVideo.getId() == 0) {
            RefUpload refUpload = grupoVideoService.registrarConSubida(grupoVideo);
            return jsonResponse(String.valueOf(refUpload.getId()),
                    refUpload.getUuid().toString());
        }
        return grupoVideoService.actualizar(grupoVideo, null);
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) throws CustomValidationException {
        if(flagActivo){
            grupoVideoService.actualizarFlagActivoById(id, flagActivo);
            return EXITO_GENERICA.get();
        }

        boolean hasChildren = grupoVideoService.checkHaveChildrenById(id);

        if(!hasChildren){
            grupoVideoService.actualizarFlagActivoById(id, flagActivo);
            return EXITO_GENERICA.get();
        }
        throw new CustomValidationException(FLAG_BLOQUEADO_TIENE_DEPS.get(), EX_VALIDATION_FAILED.get());
    }

    @RequestMapping(value = "/upload/imagen/{rdmUUID}", method = RequestMethod.POST)
    public @ResponseBody
    String guardarArchivo(
            @RequestPart MultipartFile imagen,
            @RequestParam(value = "grupoId") Integer grupoId,
            @PathVariable(name = "rdmUUID") String uuid) throws CustomValidationException {
        return jsonResponse(grupoVideoService.subirFile(imagen, grupoId, uuid, JPEG.get()));
    }

    private void guardarFile(MultipartFile file, int grupoId) {
        if (!file.isEmpty()) {
            try {
                UUID uuid = UUID.randomUUID();
                String[] splitNameFile = file.getOriginalFilename().split("\\.");
                String extension = "." + splitNameFile[splitNameFile.length - 1];
                String fullPath = mainRoute + "/GruposVideo/"+grupoId;
                Utilitarios.createDirectory(fullPath);
                fullPath += "/" + uuid + extension;

                File nuevoFile = new File(fullPath);

                // Agregando la ruta a la base de datos

                GrupoVideo qCategoria = grupoVideoService.findOne(grupoId);
                qCategoria.setRutaWeb("/" + grupoId + "/" + uuid + extension);
                qCategoria.setUuid(uuid);

                // Pasando la imagen desde la web hacia el servidor en donde se guardará en la ruta especificada en la instacia nueva de File creada
                file.transferTo(nuevoFile);

                grupoVideoService.save(qCategoria);
                LOGGER.info("> ROUTE: " + fullPath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("> Isn't a file");
        }
    }
}
