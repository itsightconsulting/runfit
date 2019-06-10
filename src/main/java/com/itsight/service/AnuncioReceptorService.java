package com.itsight.service;

import com.itsight.domain.AnuncioReceptor;
import com.itsight.domain.pojo.AnuncioPOJO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface AnuncioReceptorService extends BaseService<AnuncioReceptor, Integer> {

    void guardarMultiple(Integer anuncioTrainerId, Integer trainerId);

    List<AnuncioPOJO> findAllAnuncioByClienteId(Integer clienteId);

    String actualizarFlagLeidoByIdAndClienteId(Integer id, Integer clienteId);

    String actualizarAllFlagLeidoByClienteId(Integer clienteId);
}
