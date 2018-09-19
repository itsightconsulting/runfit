package com.itsight.service.impl;

import com.itsight.domain.TipoAudio;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.TipoAudioRepository;
import com.itsight.service.TipoAudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TipoAudioServiceImpl extends BaseServiceImpl<TipoAudioRepository> implements TipoAudioService {
    
    @Autowired
    public TipoAudioServiceImpl(TipoAudioRepository repository) {
        super(repository);
    }

    @Override
    public TipoAudio update(TipoAudio tipoAudio) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(tipoAudio);
    }

    @Override
    public void delete(int tipoAudioId) {
        // TODO Auto-generated method stub
        repository.delete(new Integer(tipoAudioId));
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {
        repository.updateFlagActivoById(id, flagActivo);
    }

    @Override
    public List<TipoAudio> listarPorFiltro(String comodin, String estado, String s) {
        comodin = comodin.equals("0") ? null : comodin;
        List<TipoAudio> lstAudio;
        if (comodin == null && estado.equals("-1"))
            lstAudio = repository.findAllByOrderByIdDesc();
        else if (comodin == null)
            lstAudio = repository.findAllByFlagActivoOrderByIdDesc(Boolean.valueOf(estado));
        else if (estado.equals("-1"))
            lstAudio = repository.findAllByNombreContainingIgnoreCaseOrderByIdDesc(comodin);
        else
            lstAudio = repository.findAllByNombreContainingIgnoreCaseAndFlagActivoOrderByIdDesc(comodin, Boolean.valueOf(estado));
        return lstAudio;
    }

    @Override
    public TipoAudio save(TipoAudio entity) {
        return repository.save(entity);
    }

    @Override
    public TipoAudio findOne(int id) {
        return repository.findOne(id);
    }

    @Override
    public TipoAudio findOneWithFT(int id) {
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<TipoAudio> findAll() {
        return repository.findAll();
    }

    @Override
    public List<TipoAudio> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<TipoAudio> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<TipoAudio> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<TipoAudio> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<TipoAudio> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<TipoAudio> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public String registrar(TipoAudio entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(TipoAudio entity, String wildcard) {
        return null;
    }

    @Override
    public List<TipoAudio> findAllWithChildrens() {
        return repository.findDistinctByOrderById();
    }
}
