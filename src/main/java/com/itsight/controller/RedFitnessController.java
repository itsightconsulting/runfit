package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.Dia;
import com.itsight.domain.RedFitness;
import com.itsight.domain.Rutina;
import com.itsight.domain.Semana;
import com.itsight.domain.dto.DiaPlantillaDto;
import com.itsight.domain.dto.RutinaDto;
import com.itsight.domain.dto.RutinaPlantillaDto;
import com.itsight.domain.dto.SemanaPlantillaDto;
import com.itsight.service.RedFitnessService;
import com.itsight.service.RutinaService;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import org.hashids.Hashids;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
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
