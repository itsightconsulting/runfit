package com.itsight.service;

import com.itsight.domain.Administrador;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface AdministradorService extends BaseService<Administrador, Integer> {

    List<Administrador> findByNombreCompleto(String nombreCompleto);

    String findPasswordById(Integer id);

    void actualizarFechaUltimoAcceso(Date date, String id);

    String validarCorreo(String correo);

    String validarUsername(String username);

    Administrador findByUsername(String username);

    List<UsuarioPOJO> listarPorFiltroDto(String comodin, String estado, String fk);

}
