package com.itsight.service;

import com.itsight.domain.Descuento;
import com.itsight.generic.BaseService;

import java.util.List;

public interface DescuentoService extends BaseService<Descuento, Integer> {

    List<Descuento> listarPorProductoId(Integer productoId);

}
