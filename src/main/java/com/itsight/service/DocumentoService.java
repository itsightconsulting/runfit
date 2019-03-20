package com.itsight.service;

import com.itsight.domain.Documento;
import com.itsight.generic.BaseService;

public interface DocumentoService extends BaseService<Documento, Integer> {

    String obtenerNombrePorId(Integer id, String uuid);

}
