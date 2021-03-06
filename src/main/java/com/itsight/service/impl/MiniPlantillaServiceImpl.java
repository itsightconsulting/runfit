package com.itsight.service.impl;

import com.itsight.domain.MiniPlantilla;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.MiniPlantillaRepository;
import com.itsight.service.MiniPlantillaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MiniPlantillaServiceImpl extends BaseServiceImpl<MiniPlantillaRepository> implements MiniPlantillaService {

    public MiniPlantillaServiceImpl(MiniPlantillaRepository repository) {
        super(repository);
    }

    @Override
    public MiniPlantilla save(MiniPlantilla entity) {
        return repository.save(entity);
    }

    @Override
    public MiniPlantilla update(MiniPlantilla entity) {
        return repository.save(entity);
    }

    @Override
    public MiniPlantilla findOne(Integer id) {
        return null;
    }

    @Override
    public MiniPlantilla findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {}

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<MiniPlantilla> findAll() {
        return null;
    }

    @Override
    public List<MiniPlantilla> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<MiniPlantilla> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<MiniPlantilla> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<MiniPlantilla> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<MiniPlantilla> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<MiniPlantilla> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<MiniPlantilla> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(MiniPlantilla entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(MiniPlantilla entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public MiniPlantilla findByTrainerIdAndEspecificacionSubCategoriaId(Integer id, Integer espSubCatId) {
        return repository.findByTrainerIdAndEspecificacionSubCategoriaId(id, espSubCatId);
    }

    @Override
    public List<MiniPlantilla> findAllByTrainerId(Integer trainerId) {
        return repository.findAllByTrainerId(trainerId);
    }

    @Override
    public int findPlantillaIdsByTrainerIdAndEspecificacionSubCategoriaId(Integer id, Integer esSubCatId) {
        return repository.findPlantillaIdsByTrainerIdAndEspecificacionSubCategoriaId(id, esSubCatId);
    }

    @Override
    public List<MiniPlantilla> findAllByListTrainerId(List<Integer> list) {
        return repository.findAllByListTrainerId(list);
    }

    @Override
    public List<MiniPlantilla> findAllByListTrainerIdBySubCategoriaId(List<Integer> list, Integer idsubcategoria) {
        return repository.findAllByListTrainerIdBySubCategoriaId(list, idsubcategoria);
    }



}
