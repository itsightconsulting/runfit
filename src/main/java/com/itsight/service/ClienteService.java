package com.itsight.service;

import com.itsight.domain.Cliente;
import com.itsight.domain.dto.ClienteDTO;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface ClienteService extends BaseService<Cliente, Integer> {

    List<Cliente> findByNombreCompleto(String nombreCompleto);

    String findPasswordById(Integer id);

    void actualizarFechaUltimoAcceso(Date date, String id);

    String validarCorreo(String correo);

    String validarUsername(String username);

    Cliente findByUsername(String username);

    String findNombreCompletoById(Integer id);

    String registroFull(ClienteDTO cliente);
}
