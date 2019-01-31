package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.*;
import com.itsight.repository.MultimediaDetalleRepository;
import com.itsight.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.itsight.util.Enums.ResponseCode.ACTUALIZACION;
import static com.itsight.util.Enums.ResponseCode.REGISTRO;

@Controller
@RequestMapping("/cliente")
public class ClienteFullController {

    private SemanaService semanaService;
    private RutinaService rutinaService;
    private RedFitnessService redFitnessService;
    private MiniPlantillaService miniPlantillaService;
    private DiaRutinarioService diaRutinarioService;
    private EspecificacionSubCategoriaService especificacionSubCategoriaService;
    private PostService postService;
    private ClienteService clienteService;
    private ConfiguracionClienteService configuracionClienteService;

    @Value("${main.repository}")
    private String mainRoute;

    @Autowired
    public ClienteFullController(SemanaService semanaService, RutinaService rutinaService,
                                 MultimediaEntrenadorService multimediaEntrenadorService, RedFitnessService redFitnessService, MultimediaDetalleRepository multimediaDetalleRepository,
                                 MiniPlantillaService miniPlantillaService, DiaRutinarioService diaRutinarioService,
                                 EspecificacionSubCategoriaService especificacionSubCategoriaService, PostService postService, ClienteService clienteService,
                                 ConfiguracionClienteService configuracionClienteService){
        this.semanaService = semanaService;
        this.rutinaService = rutinaService;
        this.redFitnessService = redFitnessService;
        this.miniPlantillaService = miniPlantillaService;
        this.diaRutinarioService = diaRutinarioService;
        this.especificacionSubCategoriaService = especificacionSubCategoriaService;
        this.postService = postService;
        this.clienteService = clienteService;
        this.configuracionClienteService = configuracionClienteService;
    }

    @GetMapping(value = "/t")
    public @ResponseBody List<Post> getMyFavs(HttpSession session){
        return postService.obtenerPostFavoritos((int) session.getAttribute("id"));
    }

    @GetMapping(value = "")
    public ModelAndView principal() {
        return new ModelAndView(ViewConstant.CLIENTE_PRINCIPAL);
    }


    @GetMapping(value = "/get/obtenerSemanasPorRutina")
    public @ResponseBody List<Semana> obtenerPrimeraSemanaRutina(@RequestParam int idrutina) {
        return semanaService.findByRutinaIdOrderByIdDesc(idrutina);
    }

    @GetMapping(value = "/get/rutinas")
    public @ResponseBody List<Rutina> obtenerRutinas(HttpSession session) {
        int id = (int) session.getAttribute("id");
        return rutinaService.getAllRutinasByUser(id);
    }

    @GetMapping(value = "/novedades")
    public ModelAndView pageNovedades(Model model, HttpSession session) {
        model.addAttribute("clienteId", session.getAttribute("id"));
        return new ModelAndView(ViewConstant.CLIENTE_NOVEDADES);
    }

    @GetMapping(value = "/misRutinas")
    public ModelAndView pageMisRutinas() {
        return new ModelAndView(ViewConstant.CLIENTE_MIS_RUTINAS);
    }



    @GetMapping(value = "/get/publicaciones")
    public @ResponseBody List<Post> obtenerMultimediaEntrenador(HttpSession session) {
        int id = (int)session.getAttribute("id");
        List<String> listaCodigosEntranadores = redFitnessService.findTrainerIdByUsuarioId(id).stream().map(x->String.valueOf(x)).collect(Collectors.toList());
        List<Integer> lstTrainerId = listaCodigosEntranadores.stream().map(x-> Integer.parseInt(x)).collect(Collectors.toList());
        return postService.findAllByTrainerIdIn(lstTrainerId);
    }

    @PostMapping(value = "/post/updateFlag")
    public @ResponseBody String updateFlag(@RequestParam int postId, @RequestParam int estado, @RequestParam int tipoFlag, HttpSession session) throws JsonProcessingException {
        int clienteId = (int)session.getAttribute("id");
        boolean hasLikedBefore = postService.verificarExisteLike(postId, clienteId);
        if(hasLikedBefore){
            if(tipoFlag == 1) postService.actualizarFlagLiked(postId, clienteId, !(estado == 0));
            else {
                postService.actualizarFlagFav(postId, clienteId, !(estado == 0));
                String postIdsString = configuracionClienteService.obtenerPostIdFavoritos(clienteId);
                String[] postIdsFavs =  postIdsString.length()> 0 ? postIdsString.split(",") : new String[0];
                int idsLen = postIdsFavs.length;
                if(estado == 0){
                    if(idsLen>0){
                        List<String> favs =  new ArrayList<>(Arrays.asList(postIdsFavs));
                        int index = (IntStream.range(0, idsLen).filter(ix->favs.get(ix).equals(String.valueOf(postId))).findFirst().orElse(-1));
                        if(index > -1){
                            favs.remove(index);
                            String postIdsFiltered = favs.stream().collect(Collectors.joining(","));
                            configuracionClienteService.actualizarPostIdFavoritos(clienteId, postIdsFiltered);
                        }
                    }
                }else{
                    if(idsLen>0) {
                        List<String> listPostId =  new ArrayList<>(Arrays.asList(postIdsFavs));
                        listPostId.add(String.valueOf(postId));
                        configuracionClienteService.actualizarPostIdFavoritos(clienteId, listPostId.stream().collect(Collectors.joining(",")));
                    }else
                        configuracionClienteService.actualizarPostIdFavoritos(clienteId, String.valueOf(postId));
                }
            }
            return ACTUALIZACION.get();
        } else {
            String nombreCompleto = clienteService.findNombreCompletoById(clienteId);
            postService.updatePostDetalle(postId, clienteId, nombreCompleto, tipoFlag == 1, tipoFlag == 2);
            if(tipoFlag == 2) configuracionClienteService.actualizarPostIdFavoritos(clienteId, String.valueOf(postId));
        }
        return REGISTRO.get();
    }

    @GetMapping(value = "/get/subcategorias")
    public @ResponseBody List<EspecificacionSubCategoria> entrenadorSubCategorias(HttpSession session) {
        int id = (int)session.getAttribute("id");
        List<Integer> listEntrenadores = redFitnessService.findTrainerIdByUsuarioId(id);
        List<MiniPlantilla> listMiniPlantilla = miniPlantillaService.findAllByListTrainerId(listEntrenadores);
        List<EspecificacionSubCategoria> lstresult = new ArrayList<>();
        if(listEntrenadores.size() >0 && listMiniPlantilla.size()>0) {
            List<Integer> diaIds = new ArrayList<>();

            for (int i = 0; i < listMiniPlantilla.size(); i++) {
                diaIds.add(listMiniPlantilla.get(i).getId());
            }
            lstresult = especificacionSubCategoriaService.findByIdsIn(diaIds);
        }
        return lstresult;
    }

    @GetMapping(value = "/get/miniplantillasentrenador")
    public @ResponseBody List<DiaRutinario> miniPlantillaEntrenador(@RequestParam int id,HttpSession session) {
        int idUser = (int)session.getAttribute("id");
        List<Integer> listEntrenadores = redFitnessService.findTrainerIdByUsuarioId(idUser);
        List<MiniPlantilla> listMiniPlantilla = miniPlantillaService.findAllByListTrainerIdBySubCategoriaId(listEntrenadores,id);

        List<Integer> diaIds = new ArrayList<>();
        for (int i=0; i< listMiniPlantilla.size() ;i++){
            for (int j = 0; j < listMiniPlantilla.get(i).getDiaRutinarioIds().size(); j++) {
                diaIds.add(listMiniPlantilla.get(i).getDiaRutinarioIds().get(j).getId());
            }
        }
        return diaRutinarioService.findByIdsIn(diaIds);
    }

    @GetMapping(value = "/get/subcategoriasespecificacion")
    public @ResponseBody List<EspecificacionSubCategoria> subCategoriasEspecificacion(@RequestParam int id) {
        return especificacionSubCategoriaService.findBySubCategoriaEjercicioId(id);
    }
}
