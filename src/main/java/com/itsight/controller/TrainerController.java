package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Post;
import com.itsight.domain.Trainer;
import com.itsight.domain.dto.ClienteDTO;
import com.itsight.domain.dto.QueryParamsDTO;
import com.itsight.domain.dto.ResPaginationDTO;
import com.itsight.domain.pojo.ClienteFitnessPOJO;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.service.*;
import com.itsight.util.Enums;
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
public class TrainerController extends BaseController{

    private TrainerService trainerService;

    private TipoUsuarioService perfilService;

    private TipoDocumentoService tipoDocumentoService;

    private RolService rolService;

    private PostService postService;

    private UbPeruService ubPeruService;

    private SecUserProcedureInvoker secUserProcedureInvoker;

    private ClienteFitnessProcedureInvoker clienteFitnessProcedureInvoker;

    private ClienteProcedureInvoker clienteProcedureInvoker;


    @Autowired
    public TrainerController(TrainerService trainerService,
                             TipoUsuarioService perfilService,
                             RolService rolService,
                             TipoDocumentoService tipoDocumentoService,
                             PostService postService,
                             UbPeruService ubPeruService,
                             SecUserProcedureInvoker secUserProcedureInvoker,
                             ClienteFitnessProcedureInvoker clienteFitnessProcedureInvoker,
                             ClienteProcedureInvoker clienteProcedureInvoker) {
        this.trainerService = trainerService;
        this.perfilService = perfilService;
        this.tipoDocumentoService = tipoDocumentoService;
        this.rolService = rolService;
        this.postService = postService;
        this.ubPeruService = ubPeruService;
        this.secUserProcedureInvoker = secUserProcedureInvoker;
        this.clienteFitnessProcedureInvoker = clienteFitnessProcedureInvoker;
        this.clienteProcedureInvoker = clienteProcedureInvoker;
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
    ResPaginationDTO listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @ModelAttribute QueryParamsDTO queryParams) {
        List<UsuarioPOJO> users = secUserProcedureInvoker.findAllByNombreAndFlagActivoDynamicSpecific(comodin, estado, queryParams, Enums.TipoUsuario.ENTRENADOR.ordinal());
        return new ResPaginationDTO(users, users.isEmpty() ? 0 : users.get(0).getRows());
    }

    @GetMapping("/empresa/agregar/trainer")
    public ModelAndView agregarTrainerAEmpresa(Model model){
        model.addAttribute("distritos", ubPeruService.findPeDistByDepAndProv("15", "01"));
        return new ModelAndView(ViewConstant.MAIN_REGISTRO_TRAINER_DE_EMPRESA);
    }

    @GetMapping(value = "/consejos_legacy")
    public ModelAndView misConsejosLegacy() {
        return new ModelAndView(ViewConstant.MAIN_CONSEJOS_TRAINER2);
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
        return postService.findAllActivosByTrainerId(trainerId);
    }

    @GetMapping(value = "/distribucion-mercado")
    public ModelAndView getDistribucionClientesTrainer(Model model){

        Integer perfil = 1; // Es Trainer
        model.addAttribute("perfilValue" ,perfil);

        return new ModelAndView(ViewConstant.MAIN_DISTRIBUCION_MERCADO_RED);
    }

    @GetMapping(value = "/distribucion-mercado/obtener")
    public @ResponseBody
    List<ClienteFitnessPOJO> obtenerInfoCompletaClientes(HttpSession session) {

        Integer trainerId = Integer.parseInt(session.getAttribute("id").toString());
        List<ClienteFitnessPOJO> fichaClienteFitness = clienteFitnessProcedureInvoker.getDistribucionMercado(trainerId);

        return fichaClienteFitness;
    }


    @GetMapping(value = "/distribucion-departamento/obtener")
    public @ResponseBody List<ClienteDTO> getDistribucionDepartamentoClientexTrainer(HttpSession session){

        Integer trainerId = Integer.parseInt(session.getAttribute("id").toString());
        List<ClienteDTO> lstDistribucionCliente =clienteProcedureInvoker.getDistribucionDepartamentoCliente(trainerId);

        return lstDistribucionCliente;

    }




}
