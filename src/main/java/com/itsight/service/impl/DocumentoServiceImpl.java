package com.itsight.service.impl;

import com.itsight.domain.Documento;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.DocumentoRepository;
import com.itsight.service.DocumentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DocumentoServiceImpl extends BaseServiceImpl<DocumentoRepository> implements DocumentoService {

    public DocumentoServiceImpl(DocumentoRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Documento save(Documento entity) {
        // TODO Auto-generated method stub
        return repository.save(entity);
    }

    @Override
    public Documento update(Documento entity) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(entity);
    }

    @Override
    public Documento findOne(int id) {
        // TODO Auto-generated method stub
        return repository.findOne(new Integer(id));
    }

    @Override
    public Documento findOneWithFT(int id) {
        // TODO Auto-generated method stub
        return repository.findById(new Integer(id));
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        repository.delete(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Documento> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Documento> findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContaining(nombre);
    }

    @Override
    public List<Documento> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Documento> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Documento> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Documento> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Documento> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Documento> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        List<Documento> lstDocumento = new ArrayList<Documento>();
        if (comodin.equals("0") && estado.equals("-1")) {
            lstDocumento = repository.findAll();
        } else {
            if (comodin.equals("0")) {
                lstDocumento = repository.findAllByFlagActivo(Boolean.valueOf(estado));
            } else {
                if (!estado.equals("-1"))
                    lstDocumento = repository.findAllByNombreContainingIgnoreCaseAndFlagActivo(comodin, Boolean.valueOf(estado));
                else
                    lstDocumento = repository.findAllByNombreContainingIgnoreCase(comodin);
            }
        }
        return lstDocumento;
    }

    @Override
    public String registrar(Documento entity, String wildcard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String actualizar(Documento entity, String wildcard) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String obtenerNombrePorId(int id, String uuid) {
        // TODO Auto-generated method stub
        return repository.findNombreByIdAndUuid(id, UUID.fromString(uuid));
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }
}
