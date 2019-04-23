package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.TrainerFicha;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.service.TrainerFichaService;
import com.itsight.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/p/trainer")
public class TrainerFichaController {

    private TrainerService trainerService;

    private TrainerFichaService trainerFichaService;

    @Autowired
    public TrainerFichaController(TrainerFichaService trainerFichaService,
                                  TrainerService trainerService) {
        this.trainerFichaService = trainerFichaService;
        this.trainerService = trainerService;
    }

    @GetMapping("/find/all")
    public @ResponseBody List<TrainerFichaPOJO> listarTodos(){
        return trainerFichaService.findAllWithFgEnt();
    }

    /*@GetMapping("/{nomPag:.+}")
    public ModelAndView getTrainerByUsername(Model model, @PathVariable(name = "nomPag") String nomPag){
        TrainerFicha trainerFicha = trainerFichaService.findByNomPag(nomPag);
        if(trainerFicha != null){
            model.addAttribute("trainer", trainerFicha);
            return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER);
        }
        return new ModelAndView(ViewConstant.ERROR404);
    }*/

    @GetMapping("/{nomPag:.+}")
    public @ResponseBody ModelAndView getTrainerByUsername(){
        return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER);
    }

    @GetMapping("/get/{nomPag:.+}")
    public @ResponseBody TrainerFichaPOJO getTrainerByUsername(@PathVariable(name = "nomPag") String nomPag){
        TrainerFichaPOJO  trainerFicha = trainerFichaService.findByNomPagPar(nomPag);
        if(trainerFicha != null){
            return trainerFicha;
        }
        return new TrainerFichaPOJO();
    }
}
