package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.*;
import com.itsight.domain.pojo.RuCliPOJO;
import com.itsight.service.*;
import com.itsight.util.Parseador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import static com.itsight.util.Enums.CfsCliente.CONTROL_ENTRENAMIENTO;
import static com.itsight.util.Enums.CfsCliente.CONTROL_REP_VIDEO;
import static com.itsight.util.Enums.ResponseCode.*;

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
    private RutinaProcedureInvoker rutinaProcedureInvoker;

    @Value("${main.repository}")
    private String mainRoute;



    @Autowired
    public ClienteFullController(SemanaService semanaService, RutinaService rutinaService, RedFitnessService redFitnessService,
                                 MiniPlantillaService miniPlantillaService, DiaRutinarioService diaRutinarioService,
                                 EspecificacionSubCategoriaService especificacionSubCategoriaService, PostService postService, ClienteService clienteService,
                                 ConfiguracionClienteService configuracionClienteService,
                                 RutinaProcedureInvoker rutinaProcedureInvoker){
        this.semanaService = semanaService;
        this.rutinaService = rutinaService;
        this.miniPlantillaService = miniPlantillaService;
        this.diaRutinarioService = diaRutinarioService;
        this.especificacionSubCategoriaService = especificacionSubCategoriaService;
        this.postService = postService;
        this.clienteService = clienteService;
        this.redFitnessService = redFitnessService;
        this.configuracionClienteService = configuracionClienteService;
        this.rutinaProcedureInvoker = rutinaProcedureInvoker;
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
    public @ResponseBody List<Semana> obtenerTodasSemanasDeUltimaRutina(HttpSession session) {
        int userId = (int) session.getAttribute("id");
        Integer lastRutinaId = rutinaService.getMaxRutinaIdByClienteId(userId);
        return semanaService.findByRutinaIdOrderByIdDesc(lastRutinaId);
    }

    @GetMapping(value = "/get/ultima-rutina")
    public @ResponseBody ResponseEntity<RuCliPOJO> obtenerInfoUltimaRutinaGeneradaORutinaFavorita (
                @CookieValue(name = "GLL_FAV_RUTINA", defaultValue = "") String
                hshFavRutinaId, HttpSession session){
        if (!hshFavRutinaId.equals("")) {
            Integer rutinaId = Parseador.getDecodeHash32Id("rf-gallcoks", hshFavRutinaId);
            session.setAttribute("rId", rutinaId);
            return new ResponseEntity<>(rutinaProcedureInvoker.findById(rutinaId), HttpStatus.OK);
        }

        Integer id = (Integer) session.getAttribute("id");
        RuCliPOJO rutina = rutinaProcedureInvoker.getLastByClienteId(id, 1);
        if (rutina != null) {
            session.setAttribute("rId", rutina.getId());
            return new ResponseEntity<>(rutina, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/get/semana/ix")
    public @ResponseBody Semana obtenerSemanaEspecifica(
            @RequestParam String semanaIx,
            HttpSession session) throws CustomValidationException {
        Integer rutinaId = (Integer) session.getAttribute("rId");
        return semanaService.findOneWithDaysEspById(rutinaId, Integer.parseInt(semanaIx));
    }

    @GetMapping(value = "/get/rutinas")
    public @ResponseBody List<Rutina> obtenerRutinas(HttpSession session) {
        int id = (int) session.getAttribute("id");
        return rutinaService.getAllRutinasByUser(id);
    }

    @PreAuthorize("hasRole('RUNNER')")
    @GetMapping(value = "/novedades")
    public ModelAndView pageNovedades(Model model, HttpSession session) {
        model.addAttribute("clienteId", session.getAttribute("id"));
        return new ModelAndView(ViewConstant.CLIENTE_NOVEDADES);
    }

    @PreAuthorize("hasRole('RUNNER')")
    @GetMapping(value = "/consejos")
    public ModelAndView pageConsejos(Model model, HttpSession session) {
        int id = (int) session.getAttribute("id");
        model.addAttribute("clienteId", id);
        RuCliPOJO rutina = rutinaProcedureInvoker.getLastByClienteId(id, 1);
        String postIds = configuracionClienteService.obtenerPostIdFavoritos(id);
        model.addAttribute("postsFavoritos" , postIds);
        if(rutina == null){
            model.addAttribute("tipoRutina" , 0);
        }else {
            model.addAttribute("tipoRutina" , rutina.getTipoRutina());

        }
        return new ModelAndView(ViewConstant.MAIN_CONSEJOS_CLIENTE);
    }



    @GetMapping(value = {"/mi-chat"})
    public ModelAndView verChat() {
        return new ModelAndView(ViewConstant.MAIN_CHAT_CLIENTE);
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

    @GetMapping(value = "/get/consejos")
    public @ResponseBody List<Post> obtenerConsejosEntrenador(HttpSession session) {
        int id = (int)session.getAttribute("id");
        Integer trainerId = redFitnessService.findTrainerIdUltimaRutinaByUsuarioId(id);

        return postService.findAllActivosByTrainerId(trainerId);
    }


    @PostMapping(value = "/post/updateFavorito")
    public @ResponseBody String updateFavorito(@RequestParam int postId, @RequestParam int estado, HttpSession session) throws JsonProcessingException {
        int clienteId = (int)session.getAttribute("id");
        boolean hasFavedBefore = postService.verificarExisteFav(postId, clienteId);
        if(hasFavedBefore){

                postService.actualizarFlagFav(postId, clienteId, !(estado == 0));
                String postIdsString = configuracionClienteService.obtenerPostIdFavoritos(clienteId);
                String[] postIdsFavs =  postIdsString.length()> 0 ? postIdsString.split(",") : new String[0];
                int idsLen = postIdsFavs.length;
                if(estado == 1){
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
            return ACTUALIZACION.get();
        } else {
            String nombreCompleto = clienteService.findNombreCompletoById(clienteId);
            postService.updatePostDetalle(postId, clienteId, nombreCompleto, true);
            configuracionClienteService.actualizarPostIdFavoritos(clienteId, String.valueOf(postId));
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



    @GetMapping("/get/rutina/ids")
    public @ResponseBody List<String> getRutinasActivasById(HttpSession session){
        int id = (Integer) session.getAttribute("id");
        return rutinaService.findRutinaIdsByClienteId(id);

    }

    @PutMapping("/configuracion/actualizar/ctrl-rep-video")
    public @ResponseBody String actualizarConfiguracionControlRepVideo(@RequestParam String valor, HttpSession session){
        int id = (Integer) session.getAttribute("id");
        configuracionClienteService.actualizarById(id, CONTROL_REP_VIDEO.name(), valor);
        return EXITO_GENERICA.get();
    }

    @PutMapping("/configuracion/actualizar/ctrl-entrenamiento")
    public @ResponseBody String actualizarConfiguracionControlEntrenamiento(@RequestParam String valor, HttpSession session){
        int id = (Integer) session.getAttribute("id");
        configuracionClienteService.actualizarById(id, CONTROL_ENTRENAMIENTO.name(), valor);
        return EXITO_GENERICA.get();
    }
}
