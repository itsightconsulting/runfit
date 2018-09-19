package com.itsight.service;

import com.itsight.domain.MiniRutina;
import com.itsight.generic.BaseService;

import java.util.List;

public interface MiniRutinaService extends BaseService<MiniRutina> {

    MiniRutina findByUsuarioIdAndCategoriaId(int id, int categoriaId);

    List<MiniRutina> findAllByUsuarioId(int usuarioId);

}
