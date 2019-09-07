package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Cliente;
import com.itsight.domain.dto.ClienteDTO;
import com.itsight.domain.dto.QueryParamsDTO;
import com.itsight.domain.dto.ResPaginationDTO;
import com.itsight.domain.pojo.TycClientePOJO;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.service.ClienteProcedureInvoker;
import com.itsight.service.ClienteService;
import com.itsight.service.RedFitnessService;
import com.itsight.service.SecUserProcedureInvoker;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.List;

@PreAuthorize("hasRole('ROLE_RUNNER')")
@Controller
@RequestMapping("/gestion/cliente")
public class ClienteController extends BaseController {

    private ClienteService clienteService;

    private SecUserProcedureInvoker secUserProcedureInvoker;

    private ClienteProcedureInvoker clienteProcedureInvoker;

    private RedFitnessService redFitnessService;

    @Autowired
    public ClienteController(ClienteService clienteService,
                             SecUserProcedureInvoker secUserProcedureInvoker,
                             ClienteProcedureInvoker clienteProcedureInvoker,
                             RedFitnessService redFitnessService) {
        this.clienteService = clienteService;
        this.secUserProcedureInvoker = secUserProcedureInvoker;
        this.clienteProcedureInvoker = clienteProcedureInvoker;
        this.redFitnessService = redFitnessService;
    }

    @GetMapping(value = "/videoteca")
    public ModelAndView pageVideoteca(Model model, HttpSession session) {
        return new ModelAndView(ViewConstant.CLIENTE_VIDEOTECA);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{perfil}")
    public @ResponseBody
    ResPaginationDTO listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @ModelAttribute QueryParamsDTO queryParams) {
        List<UsuarioPOJO> users = secUserProcedureInvoker.findAllByNombreAndFlagActivoDynamicSpecific(comodin, estado, queryParams, Enums.TipoUsuario.CLIENTE.ordinal());
        return new ResPaginationDTO(users, users.isEmpty() ? 0 : users.get(0).getRows());
    }

    @GetMapping(value = "/obtenerUsuarioSession")
    public @ResponseBody
    Cliente obtenerUsuarioSession() {
        String nameuser = SecurityContextHolder.getContext().getAuthentication().getName();
        return clienteService.findByUsername(nameuser);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody String nuevo(@ModelAttribute Cliente cliente, @RequestParam(required = false) String perfilId, @RequestParam String tipoDocumentoId, @RequestParam String rols) throws CustomValidationException {
        cliente.setTipoDocumento(Integer.parseInt(tipoDocumentoId));
        if (cliente.getId() == 0) {
            return clienteService.registrar(cliente, rols);
        }
        return clienteService.actualizar(cliente, rols);
    }

    @GetMapping(value = "/distribucion-departamento/obtener")
    public @ResponseBody List<ClienteDTO> getDistribucionDepartamentoCliente(){

        Integer trainerId = null;
        List<ClienteDTO> lstDistribucionCliente =clienteProcedureInvoker.getDistribucionDepartamentoCliente(trainerId);

     return lstDistribucionCliente;

    }

    @GetMapping(value = "/distribucion-departamento/trainer/obtener")
    public @ResponseBody List<ClienteDTO> getDistribucionDepartamentoClientexTrainer(@RequestParam Integer id){

        List<ClienteDTO> lstDistribucionCliente =clienteProcedureInvoker.getDistribucionDepartamentoCliente(id);

        return lstDistribucionCliente;
    }

    @GetMapping(value = "/get/tyc/servicios")
    public @ResponseBody List<TycClientePOJO> getTycServiciosById(HttpSession session){
        Integer id = (Integer) session.getAttribute("id");
        return clienteProcedureInvoker.getTycServiciosById(id);
    }

    @PostMapping(value = "/inscripcion/servicio")
    public @ResponseBody String inscripcionNuevoServicio(
            @RequestParam(name="key") String hshTrainerId,
            @RequestParam(name="ml") String trainerMailDecode,
            @RequestParam(name="sid") String sid,
            @RequestParam(name="fichaId") String fichaId,
            @RequestParam(name="ttId") String tipoTrainerId,
            HttpSession session) throws CustomValidationException {
        String correoTrainer = Parseador.getDecodeBase64(trainerMailDecode);
        String svcId = Parseador.getDecodeBase64(sid);
        Integer ttId = Integer.parseInt(Parseador.getDecodeBase64(tipoTrainerId));

        Integer id = (Integer) session.getAttribute("id");
        Integer trainerId = getDecodeHashId("rf-public", hshTrainerId);
        return redFitnessService.registrarNuevaRedParaClienteAntiguo(
                id, trainerId, Integer.parseInt(svcId), correoTrainer, Integer.parseInt(fichaId), ttId);
    }
}
