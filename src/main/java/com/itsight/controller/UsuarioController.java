package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.Usuario;
import com.itsight.service.RolService;
import com.itsight.service.TipoDocumentoService;
import com.itsight.service.TipoUsuarioService;
import com.itsight.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/gestion/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    private TipoUsuarioService perfilService;

    private TipoDocumentoService tipoDocumentoService;

    private RolService rolService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService,
                             TipoUsuarioService perfilService,
                             RolService rolService,
                             TipoDocumentoService tipoDocumentoService) {
        this.usuarioService = usuarioService;
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

    @GetMapping(value = "/micuenta")
    public ModelAndView miUsuario(Model model) {
        model.addAttribute("lstTipoDocumento", tipoDocumentoService.findAll());
        model.addAttribute("lstTipoUsuario", perfilService.listAll());
        model.addAttribute("lstRol", rolService.findAll());
        return new ModelAndView(ViewConstant.MI_USUARIO);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{perfil}")
    public @ResponseBody
    List<Usuario> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @PathVariable("perfil") String perfil) {
        return usuarioService.listarPorFiltro(comodin, estado, perfil);
    }

    @GetMapping(value = "/obtenerUsuarioSession")
    public @ResponseBody
    Usuario obtenerUsuarioSession() {
        String nameuser = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioService.findByUsername(nameuser);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody String nuevo(@ModelAttribute Usuario usuario, @RequestParam(required = false) String perfilId, @RequestParam String tipoDocumentoId,  @RequestParam String rols) {
        usuario.setTipoDocumento(Integer.parseInt(tipoDocumentoId));
        if (usuario.getId() == 0) {
            usuario.setTipoUsuario(Integer.parseInt(perfilId));
            return usuarioService.registrar(usuario, rols);
        }
        return usuarioService.actualizar(usuario, rols);
    }

    @GetMapping(value = "/validacion-correo")
    public @ResponseBody String validarCorreoUnico(@RequestParam String correo){
        return usuarioService.validarCorreo(correo);
    }

    @GetMapping(value = "/validacion-username")
    public @ResponseBody String validarUsernameUnico(@RequestParam String username){
        return usuarioService.validarUsername(username);
    }

    @GetMapping(value = "/mimediafavorito")
    public ModelAndView miMediaFavorito() {
        return new ModelAndView(ViewConstant.MI_MEDIA_FAVORITO);
    }

}
