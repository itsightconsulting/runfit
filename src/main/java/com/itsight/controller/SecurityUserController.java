package com.itsight.controller;

import com.itsight.domain.dto.PasswordDto;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.service.AdministradorService;
import com.itsight.service.SecUserProcedureInvoker;
import com.itsight.service.TrainerService;
import com.itsight.service.UsuarioService;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/gestion/sec-user")
public class SecurityUserController {

    private SecUserProcedureInvoker secUserProcedureInvoker;

    private UsuarioService usuarioService;

    private TrainerService trainerService;

    private AdministradorService administradorService;

    private SecurityUserRepository securityUserRepository;

    @Autowired
    public SecurityUserController(SecUserProcedureInvoker secUserProcedureInvoker, UsuarioService usuarioService, TrainerService trainerService, SecurityUserRepository securityUserRepository, AdministradorService administradorService){
        this.secUserProcedureInvoker = secUserProcedureInvoker;
        this.usuarioService = usuarioService;
        this.trainerService = trainerService;
        this.securityUserRepository = securityUserRepository;
        this.administradorService = administradorService;
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}")
    public @ResponseBody
    List<UsuarioPOJO> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado) {
        return secUserProcedureInvoker.findAllByNombreAndFlagActivoDynamic(comodin, estado);
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
                usuarioService.actualizarFlagActivoById(id, flagActivo);
            securityUserRepository.updateEstadoByUsername(username, flagActivo);
            return "1";
        } catch (Exception e) {
            return "-9";
        }
    }

    @PostMapping(value = "/cambio-contrasena")
    public @ResponseBody
    String cambioContrasena(@ModelAttribute PasswordDto password) {
        boolean val = false;
        if(password.getTipoUsuario() == 1){
            if (administradorService.findOne(Integer.parseInt(password.getUserId())).getUsername().trim().equals(password.getUsername().trim())) {
                if (password.getNuevaPassword().equals(password.getNuevaPasswordRe()))
                    val = true;
            }
        }else if(password.getTipoUsuario() == 2){
            if (trainerService.findOne(Integer.parseInt(password.getUserId())).getUsername().trim().equals(password.getUsername().trim())) {
                if (password.getNuevaPassword().equals(password.getNuevaPasswordRe()))
                    val = true;
            }
        }else if(password.getTipoUsuario() == 3){
            if (usuarioService.findOne(Integer.parseInt(password.getUserId())).getUsername().trim().equals(password.getUsername().trim())) {
                if (password.getNuevaPassword().equals(password.getNuevaPasswordRe()))
                    val = true;
            }
        }
        if(val) {
            securityUserRepository.actualizarPassword(Utilitarios.encoderPassword(password.getNuevaPassword()), Integer.parseInt(password.getUserId()));
            return "1";
        }
        return "-9";
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    Object obtenerPorId(@RequestParam(value = "id") int usuarioId, @RequestParam(value = "tipoUsuario") int tipoUsuario) {
        if(tipoUsuario == 1)
            return administradorService.findOneWithFT(usuarioId);
        else if(tipoUsuario == 2)
            return trainerService.findOneWithFT(usuarioId);
        else if(tipoUsuario == 3)
            return usuarioService.findOneWithFT(usuarioId);
        return null;
    }

    @GetMapping(value = "/validacion-correo")
    public @ResponseBody String validarCorreoUnico(@RequestParam String correo){
        return null;
    }
}
