package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.Trainer;
import com.itsight.service.RolService;
import com.itsight.service.TipoDocumentoService;
import com.itsight.service.TipoUsuarioService;
import com.itsight.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/gestion/trainer")
public class TrainerController {

    private TrainerService trainerService;

    private TipoUsuarioService perfilService;

    private TipoDocumentoService tipoDocumentoService;

    private RolService rolService;

    @Autowired
    public TrainerController(TrainerService trainerService,
                             TipoUsuarioService perfilService,
                             RolService rolService,
                             TipoDocumentoService tipoDocumentoService) {
        this.trainerService = trainerService;
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

    @PostMapping(value = "/agregar")
    public @ResponseBody String nuevo(@ModelAttribute Trainer trainer, @RequestParam(required = false) String perfilId, @RequestParam String tipoDocumentoId, @RequestParam String rols) {
        trainer.setTipoDocumento(Integer.parseInt(tipoDocumentoId));
        if (trainer.getId() == 0) {
            trainer.setTipoUsuario(Integer.parseInt(perfilId));
            return trainerService.registrar(trainer, rols);
        }
        return trainerService.actualizar(trainer, rols);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{perfil}")
    public @ResponseBody
    List<Trainer> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @PathVariable("perfil") String perfil) {
        return trainerService.listarPorFiltro(comodin, estado, perfil);
    }

    @GetMapping(value = "/consejos")
    public ModelAndView misConsejos() {
        return new ModelAndView(ViewConstant.MAIN_CONSEJOS_TRAINER);
    }

    @GetMapping(value = "/micuenta")
    public ModelAndView miUsuario(Model model) {
        model.addAttribute("lstTipoDocumento", tipoDocumentoService.findAll());
        model.addAttribute("lstTipoUsuario", perfilService.listAll());
        model.addAttribute("lstRol", rolService.findAll());
        return new ModelAndView(ViewConstant.MI_USUARIO);
    }
}
