package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Audio;
import com.itsight.service.AudioService;
import com.itsight.service.TipoAudioService;
import com.itsight.util.Utilitarios;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.UUID;

import static com.itsight.util.Enums.ResponseCode.EXITO_GENERICA;
import static com.itsight.util.Enums.ResponseCode.EX_GENERIC;

@Controller
@RequestMapping("/gestion/audio")
public class AudioController {

    private static final Logger LOGGER = LogManager.getLogger(AudioController.class);

    @Value("${main.repository}")
    private String mainRoute;
    private AudioService audioService;
    private TipoAudioService tipoAudioService;

    @Autowired
    public AudioController(AudioService audioService,
                           TipoAudioService tipoAudioService) {
        // TODO Auto-generated constructor stub
        this.audioService = audioService;
        this.tipoAudioService = tipoAudioService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal(Model model) {
        model.addAttribute("lstTipoAudio", tipoAudioService.findAll());
        return new ModelAndView(ViewConstant.MAIN_AUDIO);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{tipovideo}")
    public @ResponseBody
    List<Audio> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @PathVariable("tipovideo") String tipovideo) {
        return audioService.listarPorFiltro(comodin, estado, tipovideo);
    }

    @GetMapping(value = "/obtenerListadoSecundario")
    public @ResponseBody
    List<Audio> listarConFiltroSecundario(
            @RequestParam(name = "comodin", required = false) String comodin,
            @RequestParam(name = "tipo", required = false) String tipo) {
        return audioService.listarPorFiltroSecundario(comodin, tipo);
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    Audio obtener(@RequestParam(value = "id") int audioId) {
        return audioService.findOneWithFT(audioId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute @Valid Audio audio, String tipoAudioId) throws CustomValidationException {
        audio.setTipoAudio(Integer.parseInt(tipoAudioId));
        if (audio.getId() == 0)
            return audioService.registrar(audio, null);
        Audio qAudio = audioService.findOne(audio.getId());
        audio.setRutaWeb(qAudio.getRutaWeb());
        audio.setRutaReal(qAudio.getRutaReal());
        return audioService.actualizar(audio, null);

    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            audioService.actualizarFlagActivoById(id, flagActivo);
            return EXITO_GENERICA.get();
        } catch (Exception e) {
            return EX_GENERIC.get();
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String guardarArchivo(
            @RequestPart MultipartFile audio,
            @RequestParam Integer audioId) {
        if (audio != null) {
            guardarFile(audio, audioId);
        }
        return EXITO_GENERICA.get();
    }

    private void guardarFile(MultipartFile file, int audioId) {
        if (!file.isEmpty()) {
            try {
                UUID uuid = UUID.randomUUID();
                String[] splitNameFile = file.getOriginalFilename().split("\\.");
                String extension = "." + splitNameFile[splitNameFile.length - 1];
                String fullPath = mainRoute + "/Audios/" + audioId;
                Utilitarios.createDirectory(fullPath);
                fullPath += "/" + uuid + extension;

                File nuevoFile = new File(fullPath);

                // Agregando la ruta a la base de datos

                Audio qAudio = audioService.findOneWithFT(audioId);//findOneWithFT para este caso aplica a todas las ForeignKeys
                qAudio.setRutaReal(fullPath);
                qAudio.setRutaWeb("/" + audioId + "/" + uuid + extension);
                qAudio.setUuid(uuid);

                // Pasando la imagen  o archivo desde la web hacia el servidor en donde se guardará en la ruta especificada en la instacia nueva de File creada
                file.transferTo(nuevoFile);

                audioService.update(qAudio);
                LOGGER.info("> ROUTE: " + fullPath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("> Isn't a file");
        }
    }
}
