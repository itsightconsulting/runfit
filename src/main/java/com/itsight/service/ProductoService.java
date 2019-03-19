package com.itsight.service;

import com.itsight.domain.Producto;
import com.itsight.generic.BaseService;

public interface ProductoService extends BaseService<Producto, Integer> {

    public Producto findProductoById(Integer id);
}
