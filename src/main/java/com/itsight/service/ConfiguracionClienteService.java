package com.itsight.service;

import com.itsight.domain.ConfiguracionCliente;
import com.itsight.generic.BaseService;

public interface ConfiguracionClienteService extends BaseService<ConfiguracionCliente, Integer> {

    String obtenerPostIdFavoritos(Integer id);

    void actualizarPostIdFavoritos(Integer id, String postsFavsIds);

}
