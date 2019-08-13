package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.CategoriaPlantilla;
import com.itsight.domain.dto.CategoriaPlantillaDTO;
import com.itsight.service.CategoriaPlantillaService;
import com.itsight.util.Parseador;
import org.hibernate.annotations.GeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TrainerSegController {

    CategoriaPlantillaService categoriaPlantillaService;

    @Autowired
    public TrainerSegController(CategoriaPlantillaService categoriaPlantillaService) {

        this.categoriaPlantillaService = categoriaPlantillaService;
    }

    @GetMapping(value = {"/planes"})
    public String vistaPlanes() {
        return ViewConstant.MAIN_PLANES_PREDISENIADOS;
    }


    @GetMapping(value = {"/planes2"})
    public String vistaPlanes2() {
        return ViewConstant.MAIN_PLANES_PREDISENIADOS2;
    }


    @GetMapping(value = {"/planes/seleccion"})
    public String vistaPlanesSeleccion(@RequestParam(name = "key") String redFitnessId, @RequestParam(name = "rn") String runnerId,
                                       @RequestParam(name = "tr") String tipoRutina,  HttpSession session, Model model) {

        int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
        int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);

        session.setAttribute("plantillaRutRedFitId",redFitId );
        session.setAttribute("plantillaRutRunnerId",runneId );
        session.setAttribute("plantillaRutTipo",tipoRutina );


        return ViewConstant.MAIN_PLANES_PREDISENIADOS_SELECCION;
    }


}
