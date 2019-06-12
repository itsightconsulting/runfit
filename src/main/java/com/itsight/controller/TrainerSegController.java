package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrainerSegController {
    @GetMapping(value = {"/planes"})
    public String vistaPlanes() {
        return ViewConstant.MAIN_PLANES_PREDISENIADOS;
    }

    @GetMapping(value = {"/planes/elegido"})
    public String planElegido() {
        return ViewConstant.MAIN_PLANES_PREDISENIADOS_ELEGIDO;
    }

    @GetMapping(value = {"/suspendidos"})
    public String suspendido() {
        return ViewConstant.MAIN_SUSPENDIDO;
    }
}
