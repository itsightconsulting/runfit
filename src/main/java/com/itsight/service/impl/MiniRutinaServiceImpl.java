package com.itsight.service.impl;

import com.itsight.domain.MiniRutina;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.MiniRutinaRepository;
import com.itsight.service.MiniRutinaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MiniRutinaServiceImpl extends BaseServiceImpl<MiniRutinaRepository> implements MiniRutinaService {

    public MiniRutinaServiceImpl(MiniRutinaRepository repository) {
        super(repository);
    }

    @Override
    public MiniRutina save(MiniRutina entity) {
        return repository.save(entity);
    }

    @Override
    public MiniRutina update(MiniRutina entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public MiniRutina findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public MiniRutina findOneWithFT(int id) {
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
    public List<MiniRutina> findAll() {
        return null;
    }

    @Override
    public List<MiniRutina> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<MiniRutina> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<MiniRutina> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<MiniRutina> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<MiniRutina> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<MiniRutina> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<MiniRutina> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(MiniRutina entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(MiniRutina entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }

    @Override
    public MiniRutina findByUsuarioIdAndCategoriaId(int id, int espSubCatId) {
        return repository.findByUsuarioIdAndEspecificacionSubCategoriaId(id, espSubCatId);
    }

    @Override
    public List<MiniRutina> findAllByUsuarioId(int usuarioId) {
        return repository.findAllByUsuarioId(usuarioId);
    }
}
