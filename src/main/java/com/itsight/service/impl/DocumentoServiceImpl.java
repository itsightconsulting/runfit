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

import static com.itsight.util.Enums.ResponseCode.ACTUALIZACION;
import static com.itsight.util.Enums.ResponseCode.REGISTRO;
import static com.itsight.util.Utilitarios.customResponse;

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
    public Documento findOne(Integer id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    public Documento findOneWithFT(Integer id) {
        // TODO Auto-generated method stub
        return repository.getById(id);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Documento> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
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
        repository.save(entity);
        return customResponse(REGISTRO.get(), String.valueOf(entity.getId()));
    }

    @Override
    public String actualizar(Documento entity, String wildcard) {
        Documento qDocumento = repository.getById(entity.getId());
        qDocumento.setNombre(entity.getNombre());
        qDocumento.setRutaWeb(entity.getRutaWeb());
        qDocumento.setRutaReal(entity.getRutaReal());
        return customResponse(ACTUALIZACION.get(), String.valueOf(qDocumento.getId()));

    }

    @Override
    public String obtenerNombrePorId(Integer id, String uuid) {
        // TODO Auto-generated method stub
        return repository.findNombreByIdAndUuid(id, UUID.fromString(uuid));
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }
}
