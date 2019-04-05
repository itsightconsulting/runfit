package com.itsight.service.impl;

import com.itsight.domain.KilometrajeBase;
import com.itsight.domain.PorcentajesKilometraje;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.KilometrajeBaseRepository;
import com.itsight.service.KilometrajeBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class KilometrajeBaseServiceImpl extends BaseServiceImpl<KilometrajeBaseRepository> implements KilometrajeBaseService {

    public KilometrajeBaseServiceImpl(KilometrajeBaseRepository repository) {
        super(repository);
    }

    @Override
    public KilometrajeBase save(KilometrajeBase entity) {
        return repository.save(entity);
    }

    @Override
    public KilometrajeBase update(KilometrajeBase entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public KilometrajeBase findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public KilometrajeBase findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(new Integer(id));
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<KilometrajeBase> findAll() {
        return repository.findAll();
    }

    @Override
    public List<KilometrajeBase> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<KilometrajeBase> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<KilometrajeBase> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<KilometrajeBase> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<KilometrajeBase> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<KilometrajeBase> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<KilometrajeBase> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(KilometrajeBase entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(KilometrajeBase entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public List<KilometrajeBase> findAllByNivelAndDistancia(int nivel, int distancia) {
        return repository.findAllByNivelAndDistancia(nivel, distancia);
    }
}
