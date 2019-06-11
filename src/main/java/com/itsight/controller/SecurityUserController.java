package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.dto.PasswordDTO;
import com.itsight.domain.dto.QueryParamsDTO;
import com.itsight.domain.dto.ResPaginationDTO;
import com.itsight.domain.dto.UsuGenDTO;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gestion/usuario")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class SecurityUserController {

    private SecUserProcedureInvoker secUserProcedureInvoker;

    private ClienteService clienteService;

    private TrainerService trainerService;

    private AdministradorService administradorService;

    private SecurityUserRepository securityUserRepository;

    private TipoUsuarioService perfilService;

    private TipoDocumentoService tipoDocumentoService;

    private RolService rolService;

    @Autowired
    public SecurityUserController(SecUserProcedureInvoker secUserProcedureInvoker,
             ClienteService clienteService,
             TrainerService trainerService,
             SecurityUserRepository securityUserRepository,
             AdministradorService administradorService,
             TipoUsuarioService perfilService,
             RolService rolService,
             TipoDocumentoService tipoDocumentoService){
        this.secUserProcedureInvoker = secUserProcedureInvoker;
        this.clienteService = clienteService;
        this.trainerService = trainerService;
        this.securityUserRepository = securityUserRepository;
        this.administradorService = administradorService;
        this.perfilService = perfilService;
        this.tipoDocumentoService = tipoDocumentoService;
        this.rolService = rolService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal(Model model) {
        model.addAttribute("lstTipoDocumento", tipoDocumentoService.findAll());
        model.addAttribute("lstTipoUsuario", perfilService.listAll());
        model.addAttribute("lstRol", rolService.findAll());
        return new ModelAndView(ViewConstant.MAIN_USUARIO);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}")
    public @ResponseBody
    ResPaginationDTO listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @ModelAttribute QueryParamsDTO queryParams) {
            List<UsuarioPOJO> users = secUserProcedureInvoker.findAllByNombreAndFlagActivoDynamic(comodin, estado, queryParams);
        return new ResPaginationDTO(users, users.isEmpty() ? 0 : users.get(0).getRows());
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody String desactivar(
                @RequestParam(value = "id") int id,
                @RequestParam(value = "tipoUsuario") int tipoUsuario,
                @RequestParam String username,
                @RequestParam boolean flagActivo) {
        try {
            if(tipoUsuario == 1)
                administradorService.actualizarFlagActivoById(id, flagActivo);
            else if(tipoUsuario == 2)
                trainerService.actualizarFlagActivoById(id, flagActivo);
            else if(tipoUsuario == 3)
                clienteService.actualizarFlagActivoById(id, flagActivo);
            securityUserRepository.updateEstadoByUsername(username, flagActivo);
            return "1";
        } catch (Exception e) {
            return "-9";
        }
    }

    @PostMapping(value = "/cambio-contrasena")
    public @ResponseBody
    String cambioContrasena(@ModelAttribute PasswordDTO password) {
        boolean val = false;
        if(password.getTipoUsuario() == 1){
            if (administradorService.getUsernameById(Integer.parseInt(password.getUserId())).equals(password.getUsername().trim())) {
                if (password.getNuevaPassword().equals(password.getNuevaPasswordRe()))
                    val = true;
            }
        }else if(password.getTipoUsuario() == 2){
            if (trainerService.getUsernameById(Integer.parseInt(password.getUserId())).equals(password.getUsername().trim())) {
                if (password.getNuevaPassword().equals(password.getNuevaPasswordRe()))
                    val = true;
            }
        }else if(password.getTipoUsuario() == 3){
            if (clienteService.getUsernameById(Integer.parseInt(password.getUserId())).equals(password.getUsername().trim())) {
                if (password.getNuevaPassword().equals(password.getNuevaPasswordRe()))
                    val = true;
            }
        }
        if(val) {
            securityUserRepository.actualizarPassword(Utilitarios.encoderPassword(password.getNuevaPassword()), Integer.parseInt(password.getUserId()));
            return Enums.ResponseCode.EXITO_GENERICA.get();
        }
        return Enums.ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    UsuGenDTO obtenerPorId(@RequestParam(value = "id") int usuarioId, @RequestParam(value = "tipoUsuario") int tipoUsuario) {
        if(tipoUsuario == 1)
            return administradorService.obtenerById(usuarioId);
        else if(tipoUsuario == 2)
            return trainerService.obtenerById(usuarioId);
        else if(tipoUsuario == 3)
            return clienteService.obtenerById(usuarioId);
        return null;
    }

    @GetMapping(value = "/validacion-correo")
    public @ResponseBody Boolean validarCorreoUnico(@RequestParam String correo){
        return securityUserRepository.findCorreoExist(correo);
    }

    @GetMapping(value = "/validacion-username")
    public @ResponseBody Boolean validarUsernameUnico(@RequestParam String username){
        return Optional.ofNullable(securityUserRepository.findIdByUsername(username)).isPresent();
    }

    @GetMapping(value = "/verificar/max-role")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_TRAINER')")
    public @ResponseBody Boolean verificarMaxRoleDG(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DG"));
    }
}
