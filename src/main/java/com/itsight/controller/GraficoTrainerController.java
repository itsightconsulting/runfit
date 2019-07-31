package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/gestion/trainer/estadistica")
public class GraficoTrainerController {

    @GetMapping("/red")
    public ModelAndView getDistribucionClientes(){
        return new ModelAndView(ViewConstant.MAIN_DISTRIBUCION_RED_FITNESS);
    }


    @GetMapping("/distribucion-mercado")
    public ModelAndView getDistribucionMercado(){

        return new ModelAndView(ViewConstant.MAIN_DISTRIBUCION_RED_FITNESS);
    }


}
