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
        return null;
    }

    @Override
    public MiniPlantilla update(MiniPlantilla entity) {
        return null;
    }

    @Override
    public MiniPlantilla findOne(int id) {
        return null;
    }

    @Override
    public MiniPlantilla findOneWithFT(int id) {
        return null;
    }

    @Override
    public void delete(int id) {}

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
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }

    @Override
    public MiniPlantilla findByTrainerIdAndEspecificacionSubCategoriaId(int id, int espSubCatId) {
        return repository.findByTrainerIdAndEspecificacionSubCategoriaId(id, espSubCatId);
    }

    @Override
    public List<MiniPlantilla> findAllByTrainerId(int trainerId) {
        return repository.findAllByTrainerId(trainerId);
    }

    @Override
    public void relacionarNuevasEspecificaciones(Integer espSubCatId) {
        repository.saveEspecificacionesMiniPlantilla(espSubCatId);
    }

    @Override
    public int findPlantillaIdsByTrainerIdAndEspecificacionSubCategoriaId(int id, int esSubCatId) {
        return repository.findPlantillaIdsByTrainerIdAndEspecificacionSubCategoriaId(id, esSubCatId);
    }

    @Override
    public List<MiniPlantilla> findAllByListTrainerId(List<Integer> list) {
        return repository.findAllByListTrainerId(list);
    }

    @Override
    public List<MiniPlantilla> findAllByListTrainerIdBySubCategoriaId(List<Integer> list,int idsubcategoria) {
        return repository.findAllByListTrainerIdBySubCategoriaId(list,idsubcategoria);
    }



}
