package com.itsight.repository;

import com.itsight.domain.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer> {

    List<TipoUsuario> findAllByNombreContaining(String nombres);

}
