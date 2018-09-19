package com.itsight.service;

import com.itsight.domain.Usuario;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface UsuarioService extends BaseService<Usuario> {

    List<Usuario> findByNombreCompleto(String nombreCompleto);

    String findPasswordById(int id);

    void actualizarFechaUltimoAcceso(Date date, String userName);

    String cargarRutinarioCe(int usuarioId);

    String validarCorreo(String correo);

    String findCodigoTrainerById(int id);

    List<Usuario> listarEntrenadores();

    String validarUsername(String username);

    Usuario findByUsername(String username);
}
