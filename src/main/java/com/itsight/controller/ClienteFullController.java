package com.itsight.controller;

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
import java.util.List;

import static com.itsight.util.Enums.ResponseCode.ACTUALIZACION;

@Controller
@RequestMapping("/cliente")

public class ClienteFullController {

    private SemanaService semanaService;
    private RutinaService rutinaService;
    private MultimediaEntrenadorService multimediaEntrenadorService;
    private RedFitnessService redFitnessService;
    private MultimediaDetalleRepository multimediaDetalleRepository;
    private MiniPlantillaService miniPlantillaService;
    private DiaRutinarioService diaRutinarioService;
    private EspecificacionSubCategoriaService especificacionSubCategoriaService;


    @Value("${main.repository}")
    private String mainRoute;

    @Autowired
    public ClienteFullController(SemanaService semanaService, RutinaService rutinaService,
                                 MultimediaEntrenadorService multimediaEntrenadorService, RedFitnessService redFitnessService, MultimediaDetalleRepository multimediaDetalleRepository,
                                 MiniPlantillaService miniPlantillaService, DiaRutinarioService diaRutinarioService,
                                 EspecificacionSubCategoriaService especificacionSubCategoriaService){
        this.semanaService = semanaService;
        this.rutinaService = rutinaService;
        this.multimediaEntrenadorService = multimediaEntrenadorService;
        this.redFitnessService = redFitnessService;
        this.multimediaDetalleRepository = multimediaDetalleRepository;
        this.miniPlantillaService = miniPlantillaService;
        this.diaRutinarioService = diaRutinarioService;
        this.especificacionSubCategoriaService = especificacionSubCategoriaService;
    }

    @GetMapping(value = "")
    public ModelAndView principal(Model model) {
        return new ModelAndView(ViewConstant.CLIENTE_PRINCIPAL);
    }


    @GetMapping(value = "/get/obtenerSemanasPorRutina")
    public @ResponseBody List<Semana> obtenerPrimeraSemanaRutina(@RequestParam int idrutina) {
        return semanaService.findByRutinaIdOrderByIdDesc(idrutina);
    }

    @GetMapping(value = "/get/rutinas")
    public @ResponseBody List<Rutina> obtenerRutinas(HttpSession session) {
        int id = (int)session.getAttribute("id");
        return rutinaService.getAllRutinasByUser(id);
    }

    @GetMapping(value = "/novedades")
    public ModelAndView pageNovedades() {
        return new ModelAndView(ViewConstant.CLIENTE_NOVEDADES);
    }

    @GetMapping(value = "/misRutinas")
    public ModelAndView pageMisRutinas() {
        return new ModelAndView(ViewConstant.CLIENTE_MIS_RUTINAS);
    }



    @GetMapping(value = "/get/publicacionesentrenador")
    public @ResponseBody List<MultimediaEntrenador> obtenerMultimediaEntrenador(HttpSession session) {
        int id = (int)session.getAttribute("id");
        List<String> listaCodigosEntranadores = redFitnessService.findTrainerByUsuarioId(id);
        List<MultimediaEntrenador> lista =  multimediaEntrenadorService.findByListEntrenador(listaCodigosEntranadores);
        String fullPath = mainRoute +"/Multimedia/";
        for (MultimediaEntrenador obj : lista) {
            if (obj.getNombreArchivoUnico() != null) {
                String nuevaruta = obj.getTrainer().getId() +"/" + obj.getNombreArchivoUnico()+obj.getExtension();
                obj.setRutaWeb(nuevaruta);
            }

            List<MultimediaDetalle> detalle = multimediaEntrenadorService.findByIdEntrenador(obj.getId());
            obj.setCantidadLikes(detalle.size());

            obj.setMylike(false);

            for(MultimediaDetalle p : detalle) {
                if(p.getTrainer().getId() == id) {
                    obj.setMylike(true);
                    break;
                }
            }
        }
        return lista;
    }

    @PostMapping(value = "/updateMyLike")
    public @ResponseBody String updateMyLike(@RequestParam int id,@RequestParam int estado,HttpSession session) {
        int iduser = (int)session.getAttribute("id");
        MultimediaDetalle detalle = new MultimediaDetalle();

        if (estado == 0) {
            detalle.setTrainer(iduser);
            detalle.setMultimediaentrenador(id);
            detalle.setFlagActivo(true);
            multimediaDetalleRepository.save(detalle);
        } else {
            detalle =  multimediaDetalleRepository.findbyTrainerByEntrenador(id,iduser);
            if(detalle  != null){
                multimediaDetalleRepository.delete(detalle.getId());
            }
        }
        return ACTUALIZACION.get();
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
