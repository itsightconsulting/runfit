package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.RuConsolidado;
import com.itsight.domain.Rutina;
import com.itsight.domain.dto.ResponseDto;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    private VideoAudioFavoritoService videoAudioFavoritoService;

    private UsuarioService usuarioService;

    private RuConsolidadoService ruConsolidadoService;

    @Value("${domain.name}")
    private String domainName;

    @Autowired
    public RutinaHshController(RutinaService rutinaService, TipoAudioService tipoAudioService, CategoriaEjercicioService categoriaEjercicioService, RedFitnessService redFitnessService, CategoriaService categoriaService,VideoAudioFavoritoService videoAudioFavoritoService, UsuarioService usuarioService, RuConsolidadoService ruConsolidadoService) {
        this.rutinaService = rutinaService;
        this.tipoAudioService = tipoAudioService;
        this.categoriaEjercicioService = categoriaEjercicioService;
        this.redFitnessService = redFitnessService;
        this.categoriaService = categoriaService;
        this.videoAudioFavoritoService = videoAudioFavoritoService;
        this.usuarioService = usuarioService;
        this.ruConsolidadoService = ruConsolidadoService;
    }


    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @GetMapping(value = "/edicion")
    public ModelAndView edicionRutina(@RequestParam(name = "key") String redFitnessId, @RequestParam(name = "rn") String runnerId, Model model, HttpSession session) {
        int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
        int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);
        if(redFitId > 0 && runneId > 0) {
            String codTrainer = session.getAttribute("codTrainer").toString();
            String qCodTrainer = redFitnessService.findCodTrainerByIdAndRunnerId(redFitId, runneId);
            if (codTrainer.equals(qCodTrainer)) {
                //Se obtiene la última rutina del cliente
                Rutina rutina = rutinaService.findLastByRedFitnessId(redFitId);
                if (rutina.getId() != 0) {
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
            }
        }
        return new ModelAndView(ViewConstant.ERROR404);
    }

    @GetMapping(value = "/pre")
    public ModelAndView preAsignarRutina(@RequestParam(name = "key") String redFitnessId, @RequestParam(name = "rn") String runnerId, HttpSession session){
        int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
        int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);
        if(redFitId > 0 && runneId > 0){
            String codTrainer = session.getAttribute("codTrainer").toString();
            String qCodTrainer = redFitnessService.findCodTrainerByIdAndRunnerId(redFitId, runneId);
            if(codTrainer.equals(qCodTrainer)){
                return new ModelAndView(ViewConstant.MAIN_TRAINER_NR_PRE_ASIGNAR);
            }
        }
        return new ModelAndView(ViewConstant.ERROR404);
    }

    @GetMapping(value = "/obtenerConsolidado")
    public @ResponseBody ResponseDto obtenerConsolidadoRutina(@RequestParam(name = "key") String redFitnessId, @RequestParam(name = "rn") String runnerId, HttpSession session){
        int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
        int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);
        if(redFitId > 0 && runneId > 0) {
            String codTrainer = session.getAttribute("codTrainer").toString();
            String qCodTrainer = redFitnessService.findCodTrainerByIdAndRunnerId(redFitId, runneId);
            if (codTrainer.equals(qCodTrainer)) {
                int rutinaEdicionId = (int) session.getAttribute("edicionRutinaId");
                return new ResponseDto(Integer.parseInt(Enums.ResponseCode.EXITO_GENERICA.get()), ruConsolidadoService.findOne(rutinaEdicionId));
            }
        }
        return new ResponseDto(Integer.parseInt(Enums.ResponseCode.EX_VALIDATION_FAILED.get()), "El usuario ha intentado acceder a un recurso o inexistente o que no tiene acceso");
    }
}
