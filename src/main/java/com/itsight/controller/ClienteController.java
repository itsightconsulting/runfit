package com.itsight.controller;

import com.itsight.domain.Cliente;
import com.itsight.domain.dto.ClienteDTO;
import com.itsight.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/gestion/cliente")
public class ClienteController {

    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{perfil}")
    public @ResponseBody
    List<Cliente> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @PathVariable("perfil") String perfil) {
        return clienteService.listarPorFiltro(comodin, estado, perfil);
    }

    @GetMapping(value = "/obtenerUsuarioSession")
    public @ResponseBody
    Cliente obtenerUsuarioSession() {
        String nameuser = SecurityContextHolder.getContext().getAuthentication().getName();
        return clienteService.findByUsername(nameuser);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody String nuevo(@ModelAttribute Cliente cliente, @RequestParam(required = false) String perfilId, @RequestParam String tipoDocumentoId, @RequestParam String rols) {
        cliente.setTipoDocumento(Integer.parseInt(tipoDocumentoId));
        if (cliente.getId() == 0) {
            cliente.setTipoUsuario(Integer.parseInt(perfilId));
            return clienteService.registrar(cliente, rols);
        }
        return clienteService.actualizar(cliente, rols);
    }

    @PostMapping(value = "/fitness/agregar")
    public @ResponseBody String nuevo(@RequestBody @Valid ClienteDTO cliente) {
        /*if(!bindingResult.hasErrors()){*/
            return clienteService.registroFull(cliente);
        /*}else{
            List<ApiSubError> errors = new ArrayList<>();
            for(FieldError x :  bindingResult.getFieldErrors()){
                errors.add(new ApiSubError(x.getField(), x.getObjectName(), x.getRejectedValue(), x.getDefaultMessage()));
            }
            return new ObjectMapper().writeValueAsString(errors);
        }*/
    }
}
