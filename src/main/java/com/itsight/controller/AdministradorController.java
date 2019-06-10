package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Administrador;
import com.itsight.domain.dto.QueryParamsDTO;
import com.itsight.domain.dto.ResPaginationDTO;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.service.*;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/gestion/administrador")
public class AdministradorController {

    private AdministradorService administradorService;

    private TipoUsuarioService perfilService;

    private TipoDocumentoService tipoDocumentoService;

    private RolService rolService;

    private SecUserProcedureInvoker secUserProcedureInvoker;

    @Autowired
    public AdministradorController(AdministradorService administradorService,
                                   TipoUsuarioService perfilService,
                                   RolService rolService,
                                   TipoDocumentoService tipoDocumentoService,
                                   SecUserProcedureInvoker secUserProcedureInvoker) {
        this.administradorService = administradorService;
        this.perfilService = perfilService;
        this.tipoDocumentoService = tipoDocumentoService;
        this.rolService = rolService;
        this.secUserProcedureInvoker = secUserProcedureInvoker;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal(Model model) {
        model.addAttribute("lstTipoDocumento", tipoDocumentoService.findAll());
        model.addAttribute("lstTipoUsuario", perfilService.listAll());
        model.addAttribute("lstRol", rolService.findAll());
        return new ModelAndView(ViewConstant.MAIN_USUARIO);
    }

    @GetMapping(value = "/micuenta")
    public ModelAndView miAdministrador(Model model) {
        model.addAttribute("lstTipoDocumento", tipoDocumentoService.findAll());
        model.addAttribute("lstTipoUsuario", perfilService.listAll());
        model.addAttribute("lstRol", rolService.findAll());
        return new ModelAndView(ViewConstant.MI_USUARIO);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{perfil}")
    public @ResponseBody
    ResPaginationDTO listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @ModelAttribute QueryParamsDTO queryParams) {
        List<UsuarioPOJO> users = secUserProcedureInvoker.findAllByNombreAndFlagActivoDynamicSpecific(comodin, estado, queryParams, Enums.TipoUsuario.ADMINISTRADOR.ordinal());
        return new ResPaginationDTO(users, users.isEmpty() ? 0 : users.get(0).getRows());
    }

    @GetMapping(value = "/obtenerAdministradorSession")
    public @ResponseBody
    Administrador obtenerAdministradorSession() {
        String nameuser = SecurityContextHolder.getContext().getAuthentication().getName();
        return administradorService.findByUsername(nameuser);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody String nuevo(@ModelAttribute Administrador administrador, @RequestParam(required = false) String perfilId, @RequestParam String tipoDocumentoId,  @RequestParam String rols) throws CustomValidationException {
        administrador.setTipoDocumento(Integer.parseInt(tipoDocumentoId));
        if (administrador.getId() == 0) {
            return administradorService.registrar(administrador, rols);
        }
        return administradorService.actualizar(administrador, rols);
    }
}
