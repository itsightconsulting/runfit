package com.itsight.service;

import com.itsight.domain.PorcentajesKilometraje;
import com.itsight.domain.Usuario;
import com.itsight.domain.VideoAudioFavorito;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface UsuarioService extends BaseService<Usuario> {

    List<Usuario> findByNombreCompleto(String nombreCompleto);

    String findPasswordById(int id);

    void actualizarFechaUltimoAcceso(Date date, String id);

    String validarCorreo(String correo);

    String validarUsername(String username);

    Usuario findByUsername(String username);

}
