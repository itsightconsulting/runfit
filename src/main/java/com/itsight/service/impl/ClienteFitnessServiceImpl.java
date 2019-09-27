package com.itsight.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.advice.CustomValidationException;
import com.itsight.domain.Cliente;
import com.itsight.domain.ClienteFitness;
import com.itsight.domain.RedFitness;
import com.itsight.domain.SecurityUser;
import com.itsight.domain.dto.ClienteDTO;
import com.itsight.domain.dto.ClienteFitnessDTO;
import com.itsight.domain.pojo.ClienteFitnessPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ClienteFitnessRepository;
import com.itsight.repository.ClienteRepository;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.itsight.util.Enums.Msg.VALIDACION_FALLIDA;
import static com.itsight.util.Enums.ResponseCode.ACTUALIZACION;

@Service
@Transactional
public class ClienteFitnessServiceImpl extends BaseServiceImpl<ClienteFitnessRepository> implements ClienteFitnessService {


    private ClienteService clienteService;

    private RedFitnessService redFitnessService;

    private ClienteProcedureInvoker clienteProcedureInvoker;

    private ClienteFitnessProcedureInvoker clienteFitnessProcedureInvoker;

    @Autowired
    public ClienteFitnessServiceImpl(ClienteFitnessRepository repository,
                                     ClienteService clienteService,
                                     RedFitnessService redFitnessService,
                                     ClienteProcedureInvoker clienteProcedureInvoker,
                                     ClienteFitnessProcedureInvoker clienteFitnessProcedureInvoker){
        super(repository);
        this.clienteService = clienteService;
        this.redFitnessService = redFitnessService;
        this.clienteProcedureInvoker = clienteProcedureInvoker;
        this.clienteFitnessProcedureInvoker = clienteFitnessProcedureInvoker;
    }

    @Override
    public ClienteFitness save(ClienteFitness entity) {
        return repository.save(entity);
    }

    @Override
    public ClienteFitness update(ClienteFitness entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public ClienteFitness findOne(Integer id) throws EntityNotFoundException{
        return repository.findById(id).orElseThrow(()->new EntityNotFoundException());
    }

    @Override
    public ClienteFitness findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<ClienteFitness> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ClienteFitness> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<ClienteFitness> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<ClienteFitness> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<ClienteFitness> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<ClienteFitness> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<ClienteFitness> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<ClienteFitness> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(ClienteFitness entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(ClienteFitness entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {
    }

    @Override
    public String registrar(ClienteFitnessDTO usuarioFitness) throws CustomValidationException {
        ClienteFitness clienteFitness1 = new ClienteFitness();
        BeanUtils.copyProperties(usuarioFitness, clienteFitness1);
        clienteFitness1.setCompetencias(usuarioFitness.getCompetencias());
        clienteFitness1.setCondicionAnatomica(usuarioFitness.getCondicionAnatomica());
        clienteFitness1.setMejoras(usuarioFitness.getMejoras());
        //clienteFitness1.setKilometrajePromedioSemana(BigDecimal.valueOf(2.0));
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(usuarioFitness.getUsuario(), cliente);
        //Seteando los roles y generando la contraseña y username
        cliente.setPassword(UUID.randomUUID().toString().substring(0,8).toUpperCase());
        String roles = "3";//Representa el rol CLIENTE_RUNNER
        cliente.setTipoDocumento(1);

        clienteService.registrar(cliente, roles);
        clienteFitness1.setCliente(cliente);
        repository.save(clienteFitness1);
        //Añadiendo al cliente a la rUsuarioed del entrenador que eligio
        redFitnessService.save(new RedFitness(Integer.parseInt(usuarioFitness.getTrainerId()), cliente.getId(), ""));
        return Enums.ResponseCode.REGISTRO.get() +","+ "1";
    }

    @Override
    public ClienteFitnessPOJO findByClienteId(Integer clienteId) {
        return Optional.of(clienteFitnessProcedureInvoker.getById(clienteId)).orElseThrow(()->new EntityNotFoundException());
    }

    @Override
    public ResponseEntity<String> actualizarFull(ClienteDTO cliente, Integer id) throws JsonProcessingException {
        cliente.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        boolean updSuccess = clienteProcedureInvoker.actualizarClienteById(cliente, id);
        if(updSuccess){
            return new ResponseEntity<>(Utilitarios.jsonResponse(ACTUALIZACION.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(Utilitarios.jsonResponse(VALIDACION_FALLIDA.get()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
