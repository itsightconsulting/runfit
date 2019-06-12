package com.itsight.service;

import com.itsight.domain.ConfiguracionCliente;
import com.itsight.generic.BaseService;

public interface ConfiguracionClienteService extends BaseService<ConfiguracionCliente, Integer> {

    String obtenerPostIdFavoritos(Integer id);

    void actualizarPostIdFavoritos(Integer id, String postsFavsIds);

    String obtenerByIdAndClave(int id, String clave);

    void actualizarById(Integer id, String clave, String valor);

}
