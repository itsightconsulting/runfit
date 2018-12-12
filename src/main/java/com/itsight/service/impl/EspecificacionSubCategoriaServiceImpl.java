package com.itsight.service.impl;

import com.itsight.domain.EspecificacionSubCategoria;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.EspecificacionSubCategoriaRepository;
import com.itsight.repository.MiniPlantillaRepository;
import com.itsight.service.EspecificacionSubCategoriaService;
import com.itsight.service.MiniPlantillaService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.itsight.util.Enums.ResponseCode.*;

@Service
@Transactional
public class EspecificacionSubCategoriaServiceImpl extends BaseServiceImpl<EspecificacionSubCategoriaRepository> implements EspecificacionSubCategoriaService {

    private MiniPlantillaRepository miniPlantillaRepository;

    public EspecificacionSubCategoriaServiceImpl(EspecificacionSubCategoriaRepository repository, MiniPlantillaRepository miniPlantillaRepository) {
        super(repository);
        this.miniPlantillaRepository = miniPlantillaRepository;
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<EspecificacionSubCategoria> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public List<EspecificacionSubCategoria> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivoOrderById(flagActivo);
    }

    @Override
    public EspecificacionSubCategoria findOne(int subTipoVideoId) {
        // TODO Auto-generated method stub
        return repository.findOne(new Integer(subTipoVideoId));
    }

    @Override
    public EspecificacionSubCategoria save(EspecificacionSubCategoria subTipoVideo) {
        // TODO Auto-generated method stub
        return repository.save(subTipoVideo);
    }

    @Override
    public EspecificacionSubCategoria update(EspecificacionSubCategoria subTipoVideo) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(subTipoVideo);
    }

    @Override
    public void delete(int subTipoVideoId) {
        // TODO Auto-generated method stub
        repository.delete(new Integer(subTipoVideoId));
    }

    @Override
    public EspecificacionSubCategoria findOneWithFT(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<EspecificacionSubCategoria> findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<EspecificacionSubCategoria> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<EspecificacionSubCategoria> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<EspecificacionSubCategoria> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<EspecificacionSubCategoria> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return repository.findAllBySubCategoriaId(ids);
    }

    @Override
    public List<EspecificacionSubCategoria> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        List<EspecificacionSubCategoria> lstEspecificacionSubCategoria;
        String subCategoria = fk.equals("0") ? null : fk;
        comodin = comodin.equals("0") ? null : comodin;
        estado = estado.equals("-1") ? null : estado;

        if (comodin == null && estado == null && subCategoria == null)
            lstEspecificacionSubCategoria = repository.findAllByOrderById();
        else if (estado == null && subCategoria == null)
            lstEspecificacionSubCategoria = repository.findAllByNombreContainingIgnoreCase(comodin);
        else if (comodin == null && subCategoria == null)
            lstEspecificacionSubCategoria = repository.findAllByFlagActivoOrderById(Boolean.parseBoolean(estado));
        else if (comodin == null && estado == null)
            lstEspecificacionSubCategoria = repository.findAllBySubCategoriaEjercicioId(Integer.parseInt(subCategoria));
        else if (comodin == null)
            lstEspecificacionSubCategoria = repository.findAllBySubCategoriaEjercicioIdAndFlagActivoOrderById(Integer.parseInt(subCategoria), Boolean.valueOf(estado));
        else if (subCategoria == null)
            lstEspecificacionSubCategoria = repository.findAllByNombreContainingIgnoreCaseAndFlagActivoOrderById(comodin, Boolean.valueOf(estado));
        else if (estado == null)
            lstEspecificacionSubCategoria = repository.findAllBySubCategoriaEjercicioIdAndNombreContainingIgnoreCaseOrderById(Integer.parseInt(subCategoria), comodin);
        else
            lstEspecificacionSubCategoria = repository.findAllBySubCategoriaEjercicioIdAndNombreContainingIgnoreCaseAndFlagActivoOrderById(Integer.parseInt(subCategoria), comodin, Boolean.valueOf(estado));

        return lstEspecificacionSubCategoria;
    }

    @Override
    public String registrar(EspecificacionSubCategoria entity, String wildcard) {
        // TODO Auto-generated method stub
        List<Integer> ids = new ArrayList<>();
        for (int i=1; i<4;i++){
            EspecificacionSubCategoria obj = new EspecificacionSubCategoria(entity.getNombre(), Integer.parseInt(wildcard), i);
            repository.save(obj);
            ids.add(obj.getId());
        }
        for (int i=0; i<3;i++){
            miniPlantillaRepository.saveEspecificacionesMiniPlantilla(ids.get(i));
        }
        return REGISTRO.get();
    }

    @Override
    public String actualizar(EspecificacionSubCategoria entity, String wildcard) {
        // TODO Auto-generated method stub
        repository.saveAndFlush(entity);
        return ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

    @Override
    public List<EspecificacionSubCategoria> listarPorSubCategoria(int subCategoriaEjercicioId) {
        return repository.findBySubCategoriaId(subCategoriaEjercicioId);
    }

    @Override
    public List<EspecificacionSubCategoria> findBySubCategoriaEjercicioId(int id) {
        return repository.findBySubCategoriaEjercicioId(id);
    }

}
