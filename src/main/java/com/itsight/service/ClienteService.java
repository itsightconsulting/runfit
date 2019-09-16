package com.itsight.service;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.Cliente;
import com.itsight.domain.dto.ClienteDTO;
import com.itsight.domain.dto.QueryParamsDTO;
import com.itsight.domain.dto.UsuGenDTO;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface ClienteService extends BaseService<Cliente, Integer> {

    List<Cliente> findByNombreCompleto(String nombreCompleto);

    String findPasswordById(Integer id);

    void actualizarFechaUltimoAcceso(Date date, Integer id);

    String validarCorreo(String correo);

    String validarUsername(String username);

    Cliente findByUsername(String username);

    String findNombreCompletoById(Integer id);

    String registroFull(ClienteDTO cliente, Integer tipoTrainerId) throws CustomValidationException;

    List<UsuarioPOJO> listarPorFiltroDto(String comodin, String estado, QueryParamsDTO queryParams);

    UsuGenDTO obtenerById(int id);

    String getUsernameById(int id);

    UsuGenDTO getForCookieById(Integer id);


}
