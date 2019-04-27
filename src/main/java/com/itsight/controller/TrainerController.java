package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Post;
import com.itsight.domain.Trainer;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/gestion/trainer")
@PreAuthorize("hasRole('TRAINER') OR hasRole('ADMIN')")
public class TrainerController {

    private TrainerService trainerService;

    private TipoUsuarioService perfilService;

    private TipoDocumentoService tipoDocumentoService;

    private RolService rolService;

    private PostService postService;

    @Autowired
    public TrainerController(TrainerService trainerService,
                             TipoUsuarioService perfilService,
                             RolService rolService,
                             TipoDocumentoService tipoDocumentoService,
                             PostService postService) {
        this.trainerService = trainerService;
        this.perfilService = perfilService;
        this.tipoDocumentoService = tipoDocumentoService;
        this.rolService = rolService;
        this.postService = postService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal(Model model) {
        System.out.println("Demo log");
        System.out.println("Demo logger2");
        model.addAttribute("lstTipoDocumento", tipoDocumentoService.findAll());
        model.addAttribute("lstTipoUsuario", perfilService.listAll());
        model.addAttribute("lstRol", rolService.findAll());
        return new ModelAndView(ViewConstant.MAIN_USUARIO);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody String nuevo(@ModelAttribute Trainer trainer, @RequestParam(required = false) String perfilId, @RequestParam String tipoDocumentoId, @RequestParam String rols) throws CustomValidationException {
        trainer.setTipoDocumento(Integer.parseInt(tipoDocumentoId));
        if (trainer.getId() == 0) {
            trainer.setTipoUsuario(Integer.parseInt(perfilId));
            return trainerService.registrar(trainer, rols);
        }
        return trainerService.actualizar(trainer, rols);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{perfil}")
    public @ResponseBody
    List<UsuarioPOJO> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @PathVariable("perfil") String perfil) {
        return trainerService.listarPorFiltroDto(comodin, estado, perfil);
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



    @GetMapping(value = "/obtenerListadoPost")
    public @ResponseBody
    List<Post> listarPostsEntrenador(HttpSession session) {
        Integer trainerId = Integer.parseInt(session.getAttribute("id").toString());
        return postService.findAllByTrainerId(trainerId);
    }
}
