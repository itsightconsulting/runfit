package com.itsight.service.impl;

import com.itsight.domain.TipoDocumento;
import com.itsight.domain.TipoDocumento;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.TipoDocumentoRepository;
import com.itsight.service.TipoDocumentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TipoDocumentoServiceImpl extends BaseServiceImpl<TipoDocumentoRepository> implements TipoDocumentoService {

    public TipoDocumentoServiceImpl(TipoDocumentoRepository repository) {
        super(repository);
    }

    @Override
    public TipoDocumento save(TipoDocumento entity) {
        return repository.save(entity);
    }

    @Override
    public TipoDocumento update(TipoDocumento entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public TipoDocumento findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public TipoDocumento findOneWithFT(int id) {
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
    public List<TipoDocumento> findAll() {
        return repository.findAll();
    }

    @Override
    public List<TipoDocumento> findByNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<TipoDocumento> findByNombreContainingIgnoreCase(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<TipoDocumento> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<TipoDocumento> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<TipoDocumento> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<TipoDocumento> findByIdsIn(List<Integer> ids) {
        return repository.findByIdIn(ids);
    }

    @Override
    public List<TipoDocumento> listarPorFiltro(String comodin, String estado, String fk) {
        comodin = comodin.equals("0") ? null : comodin;
        List<TipoDocumento> tipoDocumentos;
        if (comodin == null)
            tipoDocumentos = repository.findAllByOrderByIdDesc();
        else
            tipoDocumentos = repository.findAllByNombreContainingIgnoreCaseOrderByIdDesc(comodin);
        return tipoDocumentos;
    }

    @Override
    public String registrar(TipoDocumento entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(TipoDocumento entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }
}
