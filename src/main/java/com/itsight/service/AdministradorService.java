package com.itsight.service;

import com.itsight.domain.Administrador;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface AdministradorService extends BaseService<Administrador> {

    List<Administrador> findByNombreCompleto(String nombreCompleto);

    String findPasswordById(int id);

    void actualizarFechaUltimoAcceso(Date date, String id);

    String validarCorreo(String correo);

    String validarUsername(String username);

    Administrador findByUsername(String username);

}
