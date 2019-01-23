package com.itsight.service;

import com.itsight.domain.Cliente;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface ClienteService extends BaseService<Cliente> {

    List<Cliente> findByNombreCompleto(String nombreCompleto);

    String findPasswordById(int id);

    void actualizarFechaUltimoAcceso(Date date, String id);

    String validarCorreo(String correo);

    String validarUsername(String username);

    Cliente findByUsername(String username);

}
