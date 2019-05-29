package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Post;
import com.itsight.domain.PostulanteTrainer;
import com.itsight.domain.Trainer;
import com.itsight.domain.dto.RefUploadIds;
import com.itsight.domain.dto.TrainerDTO;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static com.itsight.util.Enums.Msg.VALIDACION_FALLIDA;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;
import static com.itsight.util.Enums.ResponseCode.REGISTRO;
import static com.itsight.util.Enums.TipoTrainer.*;
import static com.itsight.util.Utilitarios.jsonResponse;

@Controller
@RequestMapping("/gestion/trainer")
@PreAuthorize("hasRole('TRAINER') OR hasRole('ADMIN')")
public class TrainerController extends BaseController{

    private TrainerService trainerService;

    private TipoUsuarioService perfilService;

    private TipoDocumentoService tipoDocumentoService;

    private RolService rolService;

    private PostService postService;

    private DisciplinaService disciplinaService;

    private UbPeruService ubPeruService;

    private PostulanteTrainerService postulanteTrainerService;

    @Autowired
    public TrainerController(TrainerService trainerService,
                             TipoUsuarioService perfilService,
                             RolService rolService,
                             TipoDocumentoService tipoDocumentoService,
                             PostService postService,
                             DisciplinaService disciplinaService,
                             UbPeruService ubPeruService,
                             PostulanteTrainerService postulanteTrainerService) {
        this.trainerService = trainerService;
        this.perfilService = perfilService;
        this.tipoDocumentoService = tipoDocumentoService;
        this.rolService = rolService;
        this.postService = postService;
        this.disciplinaService = disciplinaService;
        this.ubPeruService = ubPeruService;
        this.postulanteTrainerService = postulanteTrainerService;
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

    @GetMapping("/empresa/agregar/trainer")
    public ModelAndView agregarTrainerAEmpresa(Model model){
        model.addAttribute("disciplinas", disciplinaService.findAll());
        model.addAttribute("distritos", ubPeruService.findPeDistByDepAndProv("15", "01"));
        return new ModelAndView(ViewConstant.MAIN_REGISTRO_TRAINER_DE_EMPRESA);
    }

    @PostMapping("/empresa/agregar/trainer/registro")
    public @ResponseBody String registroTrainerParaEmpresa(
            @RequestBody @Valid TrainerDTO trainerFicha,
            HttpSession session) throws CustomValidationException {
            Integer id = (Integer) session.getAttribute("id");
            trainerFicha.setCorreo(trainerFicha.getCorreo());
            RefUploadIds refsUpload = trainerService.registrarPostulante(trainerFicha, PARA_EMPRESA.get(), id);
            String finalUploadNames = refsUpload.getUuidFp()+refsUpload.getExtFp();
            return jsonResponse(
                    Parseador.getEncodeHash32Id("rf-load-media", refsUpload.getTrainerId()),
                    finalUploadNames);
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
