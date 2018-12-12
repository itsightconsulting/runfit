package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.Usuario;
import com.itsight.domain.dto.PasswordDto;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.service.RolService;
import com.itsight.service.TipoDocumentoService;
import com.itsight.service.TipoUsuarioService;
import com.itsight.service.UsuarioService;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/portal/facturacion")
public class FacturacionController {

    @GetMapping(value = "")
    public ModelAndView facturacionMensual() {
        return new ModelAndView(ViewConstant.FACTURACION_MENSUAL);
    }

}
