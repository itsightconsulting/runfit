package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.Rutina;
import com.itsight.service.*;
import com.itsight.util.Parseador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/rutina")
public class RutinaHshController {

    private RutinaService rutinaService;

    private TipoAudioService tipoAudioService;

    private CategoriaEjercicioService categoriaEjercicioService;

    private RedFitnessService redFitnessService;

    private CategoriaService categoriaService;

    @Value("${domain.name}")
    private String domainName;

    @Autowired
    public RutinaHshController(RutinaService rutinaService, TipoAudioService tipoAudioService, CategoriaEjercicioService categoriaEjercicioService, RedFitnessService redFitnessService, CategoriaService categoriaService) {
        this.rutinaService = rutinaService;
        this.tipoAudioService = tipoAudioService;
        this.categoriaEjercicioService = categoriaEjercicioService;
        this.redFitnessService = redFitnessService;
        this.categoriaService = categoriaService;
    }

    @GetMapping(value = "/edicion")
    public ModelAndView edicionRutina(@RequestParam(name = "key") String redFitnessId, @RequestParam(name = "rn") String runnerId, Model model, HttpSession session) {
        int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
        int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);
        String codTrainer = session.getAttribute("codTrainer").toString();
        String qCodTrainer = redFitnessService.findCodTrainerByIdAndRunnerId(redFitId, runneId);
        if(codTrainer.equals(qCodTrainer)){
            //Se obtiene la última rutina del cliente
            Rutina rutina = rutinaService.findLastByRedFitnessId(redFitId);
            if(rutina.getId() != 0){
                //rutina.getSemanaIds()[0]:  Obtener el id de la primera semana de la rutina y la guardamos en session del entrenador
                session.setAttribute("edicionRutinaId", rutina.getId());
                session.setAttribute("edicionUsuarioId", runneId);
                session.setAttribute("semanaRutinaId", rutina.getSemanaIds()[0]);
                session.setAttribute("semanaIds", rutina.getSemanaIds());
                model.addAttribute("lstTipoAudioConHijos", tipoAudioService.findAllWithChildrens());
                model.addAttribute("lstCategoriaEjercicio", categoriaEjercicioService.encontrarCategoriaConSusDepedencias());
                model.addAttribute("lstCategoriaPlantRutina", categoriaService.findByFlagActivo(true));
                return new ModelAndView(ViewConstant.MAIN_RUTINA_CLIENTE_EDICION);
            }
            return new ModelAndView(ViewConstant.ERROR404);
        }else{
            return new ModelAndView(ViewConstant.ERROR403);
        }
    }

    @GetMapping(value = "/pre")
    public ModelAndView preAsignarRutina(@RequestParam(name = "key") String redFitnessId, @RequestParam(name = "rn") String runnerId, HttpSession session){
        int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
        int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);
        String codTrainer = session.getAttribute("codTrainer").toString();
        String qCodTrainer = redFitnessService.findCodTrainerByIdAndRunnerId(redFitId, runneId);
        if(codTrainer.equals(qCodTrainer)){
            return new ModelAndView(ViewConstant.MAIN_TRAINER_NR_PRE_ASIGNAR);
        }
        return new ModelAndView(ViewConstant.ERROR404);
    }
}
