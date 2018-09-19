package com.itsight.service;

import com.itsight.domain.TipoUsuario;

import java.util.List;

public interface TipoUsuarioService {

    List<TipoUsuario> listAll();

    TipoUsuario add(TipoUsuario tipoUsuario);

    TipoUsuario update(TipoUsuario tipoUsuario);

    void delete(int tipoUsuarioId);

    TipoUsuario findOneById(int id);


}
