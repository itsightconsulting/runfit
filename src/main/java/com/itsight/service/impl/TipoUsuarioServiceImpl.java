package com.itsight.service.impl;

import com.itsight.domain.TipoUsuario;
import com.itsight.repository.TipoUsuarioRepository;
import com.itsight.service.TipoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TipoUsuarioServiceImpl implements TipoUsuarioService {

    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    public TipoUsuarioServiceImpl(TipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    @Override
    public List<TipoUsuario> listAll() {
        // TODO Auto-generated method stub
        return tipoUsuarioRepository.findAll();
    }

    @Override
    public TipoUsuario add(TipoUsuario tipoUsuario) {
        // TODO Auto-generated method stub
        return tipoUsuarioRepository.save(tipoUsuario);
    }

    @Override
    public TipoUsuario update(TipoUsuario tipoUsuario) {
        // TODO Auto-generated method stub
        return tipoUsuarioRepository.saveAndFlush(tipoUsuario);
    }

    @Override
    public void delete(int tipoUsuarioId) {
        // TODO Auto-generated method stub
        tipoUsuarioRepository.delete(new Integer(tipoUsuarioId));
    }

    @Override
    public TipoUsuario findOneById(int id) {
        // TODO Auto-generated method stub
        return tipoUsuarioRepository.findOne(new Integer(id));
    }

}
