package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.*;
import com.itsight.domain.dto.*;
import com.itsight.domain.pojo.VideoPOJO;
import com.itsight.service.GrupoVideoService;
import com.itsight.service.VideoProcedureInvoker;
import com.itsight.service.VideoService;
import com.itsight.util.*;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static com.itsight.util.Utilitarios.jsonResponse;

@Controller
@RequestMapping("/gestion/video")
public class VideoController {

    public static final Logger LOGGER = LogManager.getLogger(VideoController.class);

    @Value("${main.repository}")
    private String mainRoute;

    private VideoService videoService;

    private GrupoVideoService grupoVideoService;

    private VideoProcedureInvoker videoProcedureInvoker;

    private HttpSession session;

    @Autowired
    public VideoController(VideoService videoService,
                           GrupoVideoService grupoVideoService,
                           VideoProcedureInvoker videoProcedureInvoker,
                           HttpSession session) {
        // TODO Auto-generated constructor stub
        this.videoService = videoService;
        this.grupoVideoService = grupoVideoService;
        this.videoProcedureInvoker = videoProcedureInvoker;
        this.session = session;
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

    @GetMapping(value = "/obtener/grupo-video")
    public @ResponseBody
    List<Video> listarActivosPorGrupoVideoId(@RequestParam(value = "id") int grupoVideoId) {
        return videoService.findAllActiveByGrupoVideoIdOrderById(grupoVideoId);
    }


    @GetMapping(value = "/obtener/favoritos")
    public @ResponseBody
    List<Video> listarFavoritosPorClienteId() {

        Integer clienteId = (Integer) session.getAttribute("id");

        return videoService.findAllVideosFavByClienteIdOrderById(clienteId);
    }


    @GetMapping("/obtenerListado/dinamico")
    public @ResponseBody ResPaginationDTO getAllVideosByDynamic(@ModelAttribute @Valid VideoQueryDTO query){
        List<VideoPOJO> lstRed = videoProcedureInvoker.findAllByDynamic(query);
        return new ResPaginationDTO(lstRed, lstRed.isEmpty() ? 0 : lstRed.get(0).getRows());
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    Video obtenerPorId(@RequestParam(value = "id") int videoId) {
        return videoService.findOneWithFT(videoId);
    }

    @GetMapping(value = "/obtener/full")
    public @ResponseBody
    VideoPOJO obtenerPorIdFull(@RequestParam(value = "id") int videoId) {
        return videoService.obtenerFullById(videoId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(
            @ModelAttribute Video video,
            @RequestParam String subCategoriaVideoId) throws CustomValidationException {
        video.setSubCatVideo(Integer.parseInt(subCategoriaVideoId));
        if (video.getId() == 0) {
            RefUpload refUpload = videoService.registrarConSubida(video);
            return jsonResponse(String.valueOf(refUpload.getId()),
                    refUpload.getUuid().toString() + "@" + refUpload.getAditionalUuid());
        }
        return videoService.actualizar(video, null);
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
            @RequestPart(value = "thumnailVideo", required = true) MultipartFile thumbVideo,
            @RequestPart(name = "randomUUIDs") String uuids,
            @RequestParam(value = "videoId", required = true) Integer videoId) throws CustomValidationException {
        MultipartFile[] files = new MultipartFile[]{video, thumbVideo};
        //Enums.FileExt.JPEG.get() se usa para el thumbnail del video
        return Utilitarios.jsonResponse(videoService.subirFiles(files, videoId, uuids, Enums.FileExt.JPEG.get()));
    }

    protected BagForest reconstructForest(List<Video> leaves, Integer forestId) {
        EntityGraphBuilder entityGraphBuilder = new EntityGraphBuilder(new EntityVisitor[]{
                GrupoVideo.ENTITY_VISITOR, CategoriaVideo.ENTITY_VISITOR, SubCategoriaVideo.ENTITY_VISITOR, Video.ENTITY_VISITOR, BagForest.ENTITY_VISITOR
        }).build(leaves);
        ClassId<BagForest> forestClassId = new ClassId<>(BagForest.class, forestId);
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


    @PostMapping(value = {"/agregar-favorito"})
    public @ResponseBody
    String agregarVideoFavoritos( @RequestParam(value = "vidId") Integer videoId){

        Integer clienteId = (Integer) session.getAttribute("id");

        return videoService.addClienteServicio(clienteId, videoId);
    }

    @DeleteMapping(value = {"/eliminar-favorito"})
    public @ResponseBody
    String eliminarVideoFavoritos( @RequestParam(value = "vidId") Integer videoId){

        Integer clienteId = (Integer) session.getAttribute("id");

        return videoService.deleteClienteServicio(clienteId, videoId);
    }


    protected BagForestDTO reconstructForestDto(List<VideoDTO> leaves, Integer forestId) {
        EntityGraphBuilder entityGraphBuilder = new EntityGraphBuilder(new EntityVisitor[]{
                GrupoVideoDTO.ENTITY_VISITOR, CategoriaVideoDTO.ENTITY_VISITOR, SubCategoriaVideoDTO.ENTITY_VISITOR, VideoDTO.ENTITY_VISITOR, BagForestDTO.ENTITY_VISITOR
        }).build(leaves);
        ClassId<BagForestDTO> forestClassId = new ClassId<>(BagForestDTO.class, forestId);
        return entityGraphBuilder.getEntityContext().getObject(forestClassId);
    }
}
