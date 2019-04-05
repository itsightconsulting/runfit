package com.itsight.service.impl;

import com.itsight.domain.Categoria;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.CategoriaRepository;
import com.itsight.service.CategoriaService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.itsight.util.Utilitarios.customResponse;

@Service
@Transactional
public class CategoriaServiceImpl extends BaseServiceImpl<CategoriaRepository> implements CategoriaService {

    public CategoriaServiceImpl(CategoriaRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<Categoria> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public Categoria save(Categoria categoria) {
        // TODO Auto-generated method stub
        return repository.save(categoria);
    }

    @Override
    public Categoria update(Categoria categoria) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(categoria);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    public Categoria findOne(Integer id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Categoria> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivo(flagActivo);
    }

    @Override
    public List<Categoria> findByNombre(String nombres) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContaining(nombres);
    }

    @Override
    public Categoria findOneWithFT(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Categoria> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Categoria> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Categoria> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Categoria> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Categoria> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        List<Categoria> lstCategoria;
        if (comodin.equals("0") && estado.equals("-1")) lstCategoria = repository.findAll();
        else {
            if (comodin.equals("0")) lstCategoria = repository.findAllByFlagActivo(Boolean.valueOf(estado));
            else {
                if (!estado.equals("-1"))
                    lstCategoria = repository.findAllByNombreContainingIgnoreCaseAndFlagActivo(comodin, Boolean.valueOf(estado));
                else
                    lstCategoria = repository.findAllByNombreContainingIgnoreCase(comodin);
            }
        }
        return lstCategoria;
    }

    @Override
    public String registrar(Categoria entity, String wildcard) {
        // TODO Auto-generated method stub
        return customResponse(Enums.ResponseCode.REGISTRO.get(), String.valueOf(repository.save(entity).getId()));
    }

    @Override
    public String actualizar(Categoria entity, String wildcard) {
        // TODO Auto-generated method stub
        Categoria qCategoria = repository.findById(entity.getId()).orElse(null);
        entity.setRutaReal(qCategoria.getRutaReal());
        entity.setRutaWeb(qCategoria.getRutaWeb());
        entity.setUuid(qCategoria.getUuid());
        return customResponse(Enums.ResponseCode.ACTUALIZACION.get(), String.valueOf(entity.getId()));
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

}
