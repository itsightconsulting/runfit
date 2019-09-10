package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Rutina;
import com.itsight.domain.dto.ResponseDTO;
import com.itsight.domain.dto.RutinaDTO;
import com.itsight.domain.pojo.RutinaPOJO;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/rutina")
public class RutinaHshController {

    private RutinaService rutinaService;

    private TipoAudioService tipoAudioService;

    private CategoriaEjercicioService categoriaEjercicioService;

    private RedFitnessService redFitnessService;

    private RuConsolidadoService ruConsolidadoService;

    @Value("${domain.name}")
    private String domainName;

    @Autowired
    public RutinaHshController(
            RutinaService rutinaService,
            TipoAudioService tipoAudioService,
            CategoriaEjercicioService categoriaEjercicioService,
            RedFitnessService redFitnessService,
            RuConsolidadoService ruConsolidadoService) {
        this.rutinaService = rutinaService;
        this.tipoAudioService = tipoAudioService;
        this.categoriaEjercicioService = categoriaEjercicioService;
        this.redFitnessService = redFitnessService;
        this.ruConsolidadoService = ruConsolidadoService;
    }


    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @RequestMapping(value = "/edicion")
    public ModelAndView editorRutina(@RequestParam(name = "key") String redFitnessId, @RequestParam(name = "rn") String runnerId, Model model, HttpSession session) throws JsonProcessingException {
        int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
        int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);
        if(redFitId > 0 && runneId > 0) {
            Integer trainerId = (Integer) session.getAttribute("id");
            Integer qTrainerId = redFitnessService.findTrainerIdByIdAndRunnerId(redFitId, runneId);
            if (trainerId.equals(qTrainerId)) {
                //Se obtiene la última rutina del cliente
                Rutina rutina = rutinaService.findLastByRedFitnessId(redFitId);
                if (rutina.getId() != null) {
                    //rutina.getSemanaIds()[0]:  Obtener el id de la primera semana de la rutina y la guardamos en session del entrenador
                    session.setAttribute("edicionRutinaId", rutina.getId());
                    session.setAttribute("edicionUsuarioId", runneId);
                    session.setAttribute("semanaRutinaId", rutina.getSemanaIds()[0]);
                    session.setAttribute("semanaIds", rutina.getSemanaIds());
                    model.addAttribute("rutina", new ObjectMapper().writeValueAsString(new RutinaPOJO(rutina)));
                    model.addAttribute("lstCategoriaEjercicio", categoriaEjercicioService.encontrarCategoriaConSusDepedencias());
                    return new ModelAndView(ViewConstant.MAIN_RUTINA_CLIENTE_EDICION);
                }
            }
        }
        return new ModelAndView(ViewConstant.ERROR404);
    }


    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @RequestMapping(value = "/editor")
    public ModelAndView edicionRutina(@RequestParam(name = "key") String redFitnessId, @RequestParam(name = "rn") String runnerId, Model model, HttpSession session) throws JsonProcessingException {
        int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
        int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);
        if(redFitId > 0 && runneId > 0) {
            Integer trainerId = (Integer) session.getAttribute("id");
            Integer qTrainerId = redFitnessService.findTrainerIdByIdAndRunnerId(redFitId, runneId);
            if (trainerId.equals(qTrainerId)) {
                //Se obtiene la última rutina del cliente
                Rutina rutina = rutinaService.findLastByRedFitnessId(redFitId);
                if (rutina.getId() != null) {
                    //rutina.getSemanaIds()[0]:  Obtener el id de la primera semana de la rutina y la guardamos en session del entrenador
                    session.setAttribute("edicionRutinaId", rutina.getId());
                    session.setAttribute("edicionUsuarioId", runneId);
                    session.setAttribute("semanaRutinaId", rutina.getSemanaIds()[0]);
                    session.setAttribute("semanaIds", rutina.getSemanaIds());
                    model.addAttribute("rutina", new ObjectMapper().writeValueAsString(new RutinaPOJO(rutina)));
                    model.addAttribute("lstCategoriaEjercicio", categoriaEjercicioService.encontrarCategoriaConSusDepedencias());
                    return new ModelAndView(ViewConstant.MAIN_RUTINA_EDITOR);
                }
            }
        }
        return new ModelAndView(ViewConstant.ERROR404);
    }



    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @RequestMapping(value = "/editor-legacy")
    public ModelAndView editorRutinaDiseño() throws JsonProcessingException {


      return new ModelAndView(ViewConstant.MAIN_RUTINA_EDITOR_LEGACY);
      }


    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @RequestMapping(value = "/edicion-historial")
    public ModelAndView edicionRutinaHistorial( @RequestParam(name = "si") String semIndex, @RequestParam(name = "ri") String rutIndex,@RequestParam(name = "rn") String runnerId ,  Model model, HttpSession session) throws JsonProcessingException {
        int redFitId =  (Integer) session.getAttribute("historialRedFitId");
        int runneId =  (Integer) session.getAttribute("historialRunnerId");
        int semanaIdx = (Integer.parseInt(Parseador.getDecodeBase64(semIndex))) -1;
        int rutinaIdx = ((Integer) Parseador.getDecodeHash16Id("rf-rutina", rutIndex)) ;
        if(redFitId > 0 && runneId > 0) {
            Integer trainerId = (Integer) session.getAttribute("id");
            Integer qTrainerId = redFitnessService.findTrainerIdByIdAndRunnerId(redFitId, runneId);
            if (trainerId.equals(qTrainerId)) {
                //Se obtiene la última rutina del cliente
                Rutina rutina = rutinaService.findByIndexRedFitness(redFitId,rutinaIdx);
                if (rutina.getId() != null) {
                    //rutina.getSemanaIds()[0]:  Obtener el id de la primera semana de la rutina y la guardamos en session del entrenador
                    session.setAttribute("edicionRutinaId", rutina.getId());
                    session.setAttribute("edicionUsuarioId", runneId);
                    session.setAttribute("semanaRutinaId", rutina.getSemanaIds()[semanaIdx]);
                    session.setAttribute("semanaIds", rutina.getSemanaIds());
                    model.addAttribute("rutina", new ObjectMapper().writeValueAsString(new RutinaPOJO(rutina)));
                    model.addAttribute("lstCategoriaEjercicio", categoriaEjercicioService.encontrarCategoriaConSusDepedencias());
                    return new ModelAndView(ViewConstant.MAIN_RUTINA_CLIENTE_EDICION);
                }
            }
        }
        return new ModelAndView(ViewConstant.ERROR404);
    }




    @RequestMapping(value = "/pre")
    public ModelAndView preAsignarRutina(@RequestParam(name = "key") String redFitnessId, @RequestParam(name = "rn") String runnerId, HttpSession session){
        int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
        int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);
        if(redFitId > 0 && runneId > 0){
            Integer trainerId = (Integer) session.getAttribute("id");
            Integer qTrainerId = redFitnessService.findTrainerIdByIdAndRunnerId(redFitId, runneId);
            if (trainerId.equals(qTrainerId)) {
                return new ModelAndView(ViewConstant.MAIN_TRAINER_NR_PRE_ASIGNAR);
            }
        }
        return new ModelAndView(ViewConstant.ERROR404);
    }

    @GetMapping(value = "/obtenerConsolidado")
    public @ResponseBody
    ResponseDTO obtenerConsolidadoRutina(@RequestParam(name = "key") String redFitnessId, @RequestParam(name = "rn") String runnerId, HttpSession session){
        int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
        int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);
        if(redFitId > 0 && runneId > 0) {
            Integer trainerId = (Integer) session.getAttribute("id");
            Integer qTrainerId = redFitnessService.findTrainerIdByIdAndRunnerId(redFitId, runneId);
            if (trainerId.equals(qTrainerId)) {
                int rutinaEdicionId = (int) session.getAttribute("edicionRutinaId");
                return new ResponseDTO(Integer.parseInt(Enums.ResponseCode.EXITO_GENERICA.get()), ruConsolidadoService.findOne(rutinaEdicionId));
            }
        }
        return new ResponseDTO(Integer.parseInt(Enums.ResponseCode.EX_VALIDATION_FAILED.get()), "El cliente ha intentado acceder a un recurso o inexistente o que no tiene acceso");
    }

    @PutMapping(value = "/actualizarEstado/{flag}")
    public @ResponseBody String actualizarFlagActivo(
                @PathVariable(name = "flag") String flagActivo,
                @RequestParam(name = "key") String redFitnessId,
                @RequestParam(name = "rn") String runnerId,
                HttpSession session){
        if(flagActivo != null && flagActivo.equals("1") || flagActivo.equals("0")){
            int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
            int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);
            if(redFitId > 0 && runneId > 0) {
                Integer trainerId = (Integer) session.getAttribute("id");
                Integer qTrainerId = redFitnessService.findTrainerIdByIdAndRunnerId(redFitId, runneId);
                if (trainerId.equals(qTrainerId)) {
                    return rutinaService.actualizarFlagActivo(flagActivo.equals("1") ? true : false);
                }
            }
        }
        return Enums.ResponseCode.EX_VALIDATION_FAILED.get();
    }
}
