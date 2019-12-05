package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Post;
import com.itsight.domain.Trainer;
import com.itsight.domain.dto.ClienteDTO;
import com.itsight.domain.dto.QueryParamsDTO;
import com.itsight.domain.dto.ResPaginationDTO;
import com.itsight.domain.pojo.ClienteFitnessPOJO;
import com.itsight.domain.pojo.ServicioPOJO;
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

    private ServicioProcedureInvoker servicioProcedureInvoker;

    private ServicioService servicioService;


    @Autowired
    public TrainerController(TrainerService trainerService,
                             TipoUsuarioService perfilService,
                             RolService rolService,
                             TipoDocumentoService tipoDocumentoService,
                             PostService postService,
                             UbPeruService ubPeruService,
                             SecUserProcedureInvoker secUserProcedureInvoker,
                             ClienteFitnessProcedureInvoker clienteFitnessProcedureInvoker,
                             ClienteProcedureInvoker clienteProcedureInvoker,
                             ServicioProcedureInvoker servicioProcedureInvoker,
                             ServicioService servicioService) {
        this.trainerService = trainerService;
        this.perfilService = perfilService;
        this.tipoDocumentoService = tipoDocumentoService;
        this.rolService = rolService;
        this.postService = postService;
        this.ubPeruService = ubPeruService;
        this.secUserProcedureInvoker = secUserProcedureInvoker;
        this.clienteFitnessProcedureInvoker = clienteFitnessProcedureInvoker;
        this.clienteProcedureInvoker = clienteProcedureInvoker;
        this.servicioProcedureInvoker = servicioProcedureInvoker;
        this.servicioService = servicioService;
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

    @GetMapping(value = "/distribucion-provincia/obtener")
    public @ResponseBody List<ClienteDTO> getDistribucionProvinciaClientexTrainer(HttpSession session){

        Integer trainerId = Integer.parseInt(session.getAttribute("id").toString());
        List<ClienteDTO> lstDistribucionCliente =clienteProcedureInvoker.getDistribucionProvinciaCliente(trainerId);

        return lstDistribucionCliente;
    }

    @GetMapping(value = "/distribucion-distrito/obtener")
    public @ResponseBody List<ClienteDTO> getDistribucionDistritoLimaClientexTrainer(HttpSession session){

        Integer trainerId = Integer.parseInt(session.getAttribute("id").toString());
        List<ClienteDTO> lstDistribucionCliente =clienteProcedureInvoker.getDistribucionDistritoCliente(trainerId);

        return lstDistribucionCliente;
    }

    @GetMapping(value = "/consolidado-cliente/obtener")
    public @ResponseBody String getKeyConsolidadoCliente( @RequestParam int  cliId,  HttpSession session){

        String clienteKey = Parseador.getEncodeHash32Id("rf-rutina", cliId);

        return clienteKey;
    }

    @GetMapping(value = "/servicio/top/obtener")
    public @ResponseBody List<ServicioPOJO> getTopServiciosxTrainer(HttpSession session){

        Integer trainerId = Integer.parseInt(session.getAttribute("id").toString());
        List<ServicioPOJO> lstServicio = servicioProcedureInvoker.getTopServiciobyTrainerId(trainerId);

        return lstServicio;
    }

    @GetMapping(value = "/servicio/total/obtener")
    public @ResponseBody int getTotalClientesServiciosxTrainer(HttpSession session){

        Integer trainerId = Integer.parseInt(session.getAttribute("id").toString());
        Integer total = servicioService.getTotalClientesByTrainerId(trainerId);

        return total;
    }




}
