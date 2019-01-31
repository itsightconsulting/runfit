package com.itsight.service;

import com.itsight.domain.ConfiguracionCliente;
import com.itsight.generic.BaseService;

public interface ConfiguracionClienteService extends BaseService<ConfiguracionCliente> {

    String obtenerPostIdFavoritos(int id);

    void actualizarPostIdFavoritos(int id, String postsFavsIds);

}
