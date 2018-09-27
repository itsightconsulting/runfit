package com.itsight.controller;

import com.itsight.domain.PorcentajesKilometraje;
import com.itsight.service.PorcentajesKilometrajeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/calculo")
public class CalculoController {

    private PorcentajesKilometrajeService porcentajesKilometrajeService;

    public CalculoController(PorcentajesKilometrajeService porcentajesKilometrajeService){
        this.porcentajesKilometrajeService = porcentajesKilometrajeService;
    }

    @GetMapping("/porcentajes-kilo/obtener/{distancia}")
    public @ResponseBody PorcentajesKilometraje obtenerPorcetanjesKilometrajeByDistancia(@PathVariable String distancia, HttpSession session){
        int trainerId = (int) session.getAttribute("id");
        return porcentajesKilometrajeService.findByTrainerIdAndDistancia(trainerId, Integer.parseInt(distancia));
    }
}
