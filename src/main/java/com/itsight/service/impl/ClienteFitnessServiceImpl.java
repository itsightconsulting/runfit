package com.itsight.service.impl;

import com.itsight.domain.Cliente;
import com.itsight.domain.ClienteFitness;
import com.itsight.domain.RedFitness;
import com.itsight.domain.dto.ClienteFitnessDTO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ClienteFitnessRepository;
import com.itsight.service.ClienteFitnessService;
import com.itsight.service.ClienteService;
import com.itsight.service.RedFitnessService;
import com.itsight.util.Enums;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ClienteFitnessServiceImpl extends BaseServiceImpl<ClienteFitnessRepository> implements ClienteFitnessService {


    private ClienteService clienteService;

    private RedFitnessService redFitnessService;


    @Autowired
    public ClienteFitnessServiceImpl(ClienteFitnessRepository repository, ClienteService clienteService, RedFitnessService redFitnessService){
        super(repository);
        this.clienteService = clienteService;
        this.redFitnessService = redFitnessService;
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
    public ClienteFitness findOne(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public ClienteFitness findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
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
    public String registrar(ClienteFitnessDTO usuarioFitness) {
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
        cliente.setTipoUsuario(3);//TipoUsuario 3 == Cliente

        clienteService.registrar(cliente, roles);
        clienteFitness1.setCliente(cliente);
        repository.save(clienteFitness1);
        //Añadiendo al cliente a la rUsuarioed del entrenador que eligio
        redFitnessService.save(new RedFitness(Integer.parseInt(usuarioFitness.getTrainerId()), cliente.getId()));
        return Enums.ResponseCode.REGISTRO.get() +","+ "1";
    }

    @Override
    public ClienteFitness findByClienteId(Integer clienteId) {
        Optional<ClienteFitness> qUsuario = Optional.ofNullable(repository.findByClienteId(clienteId));
        if(qUsuario.isPresent()){
            return qUsuario.get();
        }else{
            return new ClienteFitness();
        }
    }
}
