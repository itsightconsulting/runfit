package com.itsight.controller;

import com.itsight.domain.KilometrajeBase;
import com.itsight.domain.PorcentajesKilometraje;
import com.itsight.domain.dto.PorcentajeKilometrajeDTO;
import com.itsight.service.KilometrajeBaseService;
import com.itsight.service.PorcentajesKilometrajeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.itsight.util.Enums.ResponseCode.ACTUALIZACION;

@Controller
@RequestMapping("/calculo")
public class CalculoController {

    private PorcentajesKilometrajeService porcentajesKilometrajeService;

    private KilometrajeBaseService kilometrajeBaseService;

    public CalculoController(PorcentajesKilometrajeService porcentajesKilometrajeService, KilometrajeBaseService kilometrajeBaseService){
        this.porcentajesKilometrajeService = porcentajesKilometrajeService;
        this.kilometrajeBaseService = kilometrajeBaseService;
    }

    @GetMapping("/porcentajes-kilo/obtener/{distancia}")
    public @ResponseBody PorcentajesKilometraje obtenerPorcetanjesKilometrajeByDistancia(@PathVariable String distancia, HttpSession session){
        Integer trainerId = (Integer) session.getAttribute("id");
        return porcentajesKilometrajeService.findByTrainerIdAndDistancia(trainerId, Integer.parseInt(distancia));
    }

    @PutMapping("/porcentajes-kilo/actualizar")
    public @ResponseBody String actualizarPorcetanjesKilometrajeByDistancia(@RequestBody List<PorcentajeKilometrajeDTO> porcentajes, HttpSession session){
        Integer trainerId = (Integer) session.getAttribute("id");
        int distancia = porcentajes.get(0).getDistancia();
        porcentajesKilometrajeService.actualizarPorcentajesComplexByTrainerIdAndDistancia(trainerId, distancia, porcentajes);
        return ACTUALIZACION.get();
    }

    @GetMapping("/kilometraje/base/obtener")
    public @ResponseBody
    List<KilometrajeBase> obtenerKilometrajeBase(@RequestParam(name = "nivelAtleta") String nivelAtleta, @RequestParam(name = "distancia") String distancia){
        return kilometrajeBaseService.findAllByNivelAndDistancia(Integer.parseInt(nivelAtleta), Integer.parseInt(distancia));
    }
}
