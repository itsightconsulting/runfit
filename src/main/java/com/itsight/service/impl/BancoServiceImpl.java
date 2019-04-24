package com.itsight.service.impl;

import com.itsight.domain.Banco;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.BancoRepository;
import com.itsight.service.BancoService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class BancoServiceImpl extends BaseServiceImpl<BancoRepository> implements BancoService {

    public BancoServiceImpl(BancoRepository repository) {
        super(repository);
    }

    @Override
    public Banco save(Banco entity) {
        return repository.save(entity);
    }

    @Override
    public Banco update(Banco entity) {
        return repository.save(entity);
    }

    @Override
    public Banco findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Banco findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Banco> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Banco> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Banco> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Banco> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Banco> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Banco> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Banco> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Banco> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Banco entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(Banco entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
