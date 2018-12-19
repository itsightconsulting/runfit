package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.RedFitness;
import com.itsight.service.RedFitnessService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/gestion/trainer/red")
public class RedFitnessController {

    private RedFitnessService redFitnessService;

    public RedFitnessController(RedFitnessService redFitnessService){
        this.redFitnessService = redFitnessService;
    }

    @GetMapping("")
    public ModelAndView principal(){
        return new ModelAndView(ViewConstant.MAIN_TRAINER_RED);
    }

    @GetMapping(value = "/obtenerListado")
    public @ResponseBody
    List<RedFitness> listarConFiltro(HttpSession session) {
        if (session.getAttribute("codTrainer") != null) {
            String codigoTrainer = session.getAttribute("codTrainer").toString();
            return redFitnessService.listarSegunRedTrainer(codigoTrainer);
        }
        return new ArrayList<>();
    }

    @PutMapping(value = "/anadir-nota")
    public @ResponseBody
    String actualizarNotaAIntegrante(@RequestParam String id, @RequestParam String nota) {
        redFitnessService.actualizarNotaAIntegrante(Integer.parseInt(id), nota);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

}
