package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.CategoriaVideo;
import com.itsight.domain.GrupoVideo;
import com.itsight.domain.SubCategoriaVideo;
import com.itsight.domain.Video;
import com.itsight.service.GrupoVideoService;
import com.itsight.service.VideoService;
import com.itsight.util.ClassId;
import com.itsight.util.EntityGraphBuilder;
import com.itsight.util.EntityVisitor;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/gestion/video")
public class VideoController {

    public static final Logger LOGGER = LogManager.getLogger(VideoController.class);

    @Value("${main.repository}")
    private String mainRoute;

    private VideoService videoService;

    private GrupoVideoService grupoVideoService;

    @Autowired
    public VideoController(VideoService videoService, GrupoVideoService grupoVideoService) {
        // TODO Auto-generated constructor stub
        this.videoService = videoService;
        this.grupoVideoService = grupoVideoService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal(Model model) {
        model.addAttribute("lstGrupoVideo", grupoVideoService.encontrarGrupoConSusDepedencias());
        return new ModelAndView(ViewConstant.MAIN_VIDEO);
    }

    @GetMapping(value = "/listarTodos")
    public @ResponseBody
    List<Video> listar() {
        return videoService.findAll();
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{subCatVideo}")
    public @ResponseBody
    List<Video> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @PathVariable("subCatVideo") String subCatVideo) {
        return videoService.listarPorFiltro(comodin, estado, subCatVideo);
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    Video obtenerPorId(@RequestParam(value = "id") int videoId) {
        return videoService.findOneWithFT(videoId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(
            @ModelAttribute Video video,
            @RequestParam String subCategoriaVideoId) {
        video.setSubCatVideo(Integer.parseInt(subCategoriaVideoId));
        if (video.getId() == 0) {
            videoService.save(video);
            return String.valueOf(video.getId());
        }
        Video qVideo = videoService.findOne(video.getId());
        video.setRutaWeb(qVideo.getRutaWeb());
        video.setRutaReal(qVideo.getRutaReal());
        videoService.update(video);
        return String.valueOf(qVideo.getId());
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            videoService.actualizarFlagActivoById(id, flagActivo);
            return "1";
        } catch (Exception e) {
            return "-9";
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String guardarArchivo(
            @RequestPart(value = "video", required = true) MultipartFile video,
            @RequestParam(value = "videoId", required = true) Integer videoId, HttpServletRequest request) {

        if (video != null) {
            guardarFile(video, videoId);
        }
        return "1";
    }

    private void guardarFile(MultipartFile file, int videoId) {
        if (!file.isEmpty()) {
            try {

                UUID uuid = UUID.randomUUID();
                String[] splitNameFile = file.getOriginalFilename().split("\\.");
                String extension = "." + splitNameFile[splitNameFile.length - 1];
                String fullPath = "";
                String rutaBase = mainRoute;
                fullPath = rutaBase + "/Videos/" + videoId;
                Utilitarios.createDirectory(fullPath);
                fullPath += "/" + uuid + extension;

                File nuevoFile = new File(fullPath);

                // Agregando la ruta a la base de datos

                Video qVideo = videoService.findOneWithFT(videoId);//findOneWithFT para este caso aplica a todas las ForeignKeys
                qVideo.setSubCatVideo(qVideo.getSubCatVideoId());
                qVideo.setRutaReal(fullPath);
                qVideo.setRutaWeb("/" + videoId + "/" + uuid + extension);
                qVideo.setUuid(uuid);

                // Pasando la imagen  o archivo desde la web hacia el servidor en donde se guardará en la ruta especificada en la instacia nueva de File creada
                file.transferTo(nuevoFile);

                videoService.update(qVideo);
                LOGGER.info("> ROUTE: " + fullPath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("> Isn't a file");
        }
    }

    protected BagForest reconstructForest(List<Video> leaves, int forestId) {
        EntityGraphBuilder entityGraphBuilder = new EntityGraphBuilder(new EntityVisitor[]{
                GrupoVideo.ENTITY_VISITOR, CategoriaVideo.ENTITY_VISITOR, SubCategoriaVideo.ENTITY_VISITOR, Video.ENTITY_VISITOR, BagForest.ENTITY_VISITOR
        }).build(leaves);
        ClassId<BagForest> forestClassId = new ClassId<BagForest>(BagForest.class, forestId);
        return entityGraphBuilder.getEntityContext().getObject(forestClassId);
    }

    @GetMapping(value = {"/obtener/arbol"})
    public @ResponseBody
    String obtenerArbolVideo() throws JsonProcessingException {
        List<Video> videos = videoService.obtenerTodosConJerarquia();
        if(videos.isEmpty()){
            return "-9";
        }
        BagForest forest = reconstructForest(videos, 2);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(forest.getTreesGb());
    }
}
