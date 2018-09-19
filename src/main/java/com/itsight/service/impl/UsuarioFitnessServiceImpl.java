package com.itsight.service.impl;

import com.itsight.domain.RedFitness;
import com.itsight.domain.Usuario;
import com.itsight.domain.UsuarioFitness;
import com.itsight.domain.dto.UsuarioFitnessDto;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.UsuarioFitnessRepository;
import com.itsight.service.RedFitnessService;
import com.itsight.service.UsuarioFitnessService;
import com.itsight.service.UsuarioService;
import com.itsight.util.Enums;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UsuarioFitnessServiceImpl extends BaseServiceImpl<UsuarioFitnessRepository> implements UsuarioFitnessService {


    private UsuarioService usuarioService;

    private RedFitnessService redFitnessService;


    @Autowired
    public UsuarioFitnessServiceImpl(UsuarioFitnessRepository repository, UsuarioService usuarioService, RedFitnessService redFitnessService){
        super(repository);
        this.usuarioService = usuarioService;
        this.redFitnessService = redFitnessService;
    }

    @Override
    public UsuarioFitness save(UsuarioFitness entity) {
        return repository.save(entity);
    }

    @Override
    public UsuarioFitness update(UsuarioFitness entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public UsuarioFitness findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public UsuarioFitness findOneWithFT(int id) {
        return null;
    }

    @Override
    public void delete(int id) {
        repository.delete(new Integer(id));
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<UsuarioFitness> findAll() {
        return repository.findAll();
    }

    @Override
    public List<UsuarioFitness> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<UsuarioFitness> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<UsuarioFitness> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<UsuarioFitness> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<UsuarioFitness> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<UsuarioFitness> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<UsuarioFitness> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(UsuarioFitness entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(UsuarioFitness entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }

    @Override
    public String registrar(UsuarioFitnessDto usuarioFitness) {
        UsuarioFitness usuarioFitness1 = new UsuarioFitness();
        BeanUtils.copyProperties(usuarioFitness, usuarioFitness1);
        usuarioFitness1.setCompetencias(usuarioFitness.getCompetencias());
        usuarioFitness1.setCondicionAnatomica(usuarioFitness.getCondicionAnatomica());
        usuarioFitness1.setMejoras(usuarioFitness.getMejoras());
        usuarioFitness1.setObjetivos(usuarioFitness.getObjetivos());
        usuarioFitness1.setTiemposDisponibles(usuarioFitness.getTiemposDisponibles());
        //usuarioFitness1.setKilometrajePromedioSemana(BigDecimal.valueOf(2.0));
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioFitness.getUsuario(), usuario);
        //Seteando los roles y generando la contraseña y username
        usuario.setPassword(UUID.randomUUID().toString().substring(0,8).toUpperCase());
        String roles = "3";//Representa el rol CLIENTE_RUNNER
        usuario.setTipoDocumento(1);
        usuario.setTipoUsuario(3);//TipoUsuario 3 == Cliente

        usuarioService.registrar(usuario, roles);
        usuarioFitness1.setUsuario(usuario);
        repository.save(usuarioFitness1);
        //Añadiendo al usuario a la rUsuarioed del entrenador que eligio
        redFitnessService.save(new RedFitness(usuarioFitness.getEntrenadorId(), usuario.getId()));
        return Enums.ResponseCode.REGISTRO.get() +","+ "1";
    }

    @Override
    public UsuarioFitness findByUsuarioId(int usuarioId) {
        Optional<UsuarioFitness> qUsuario = Optional.ofNullable(repository.findByUsuarioId(usuarioId));
        if(qUsuario.isPresent()){
            return qUsuario.get();
        }else{
            return new UsuarioFitness();
        }
    }
}
