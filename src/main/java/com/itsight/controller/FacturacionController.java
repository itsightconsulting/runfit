package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/portal/facturacion")
public class FacturacionController {

    @GetMapping(value = "")
    public ModelAndView facturacionMensual() {
        return new ModelAndView(ViewConstant.FACTURACION_MENSUAL);
    }

}
