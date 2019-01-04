package com.itsight.repository;

import com.itsight.domain.Usuario;
import com.itsight.domain.pojo.UsuarioPOJO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, JpaSpecificationExecutor<Usuario> {

    @Override
    @EntityGraph(value = "usuario.all")
    List<Usuario> findAll(Specification<Usuario> usuarioSpecification);

    @EntityGraph(value = "usuario.all")
    List<Usuario> findAllByOrderByIdDesc();

    @EntityGraph(value = "usuario.all")
    List<Usuario> findAllByFlagActivoOrderByIdDesc(Boolean flagActivo);

    @Query("SELECT U FROM Usuario U INNER JOIN FETCH U.tipoUsuario P JOIN FETCH U.tipoDocumento D  WHERE LOWER(CONCAT(U.apellidoPaterno,' ',U.apellidoMaterno,' ',U.nombres)) LIKE LOWER(CONCAT('%',?1,'%'))")
    List<Usuario> findByNombreCompleto(String nombreCompleto);

    @EntityGraph(value = "usuario.all")
    Usuario findById(int id);

    @Modifying
    @Query(value = "UPDATE Usuario U SET U.flagActivo =?2 WHERE U.id = ?1")
    void updateFlagActivoById(int id, boolean flagActivo);

    @Modifying
    @Query(value = "UPDATE Usuario U SET U.fechaUltimoAcceso =?1 WHERE U.id = ?2")
    void updateFechaUltimoAcceso(Date fechaUltimoAcceso, int id);

    @Modifying
    @Query(value = "UPDATE Usuario U SET U.flagRutinarioCe =?2 WHERE U.id = ?1")
    void updateFlagRutinarioCeById(int id, boolean flagRutinarioCe);

    @Query(value = "SELECT U.correo FROM Usuario U WHERE U.correo = ?1")
    List<Usuario> findAllByCorreo(String correo);

    @Query(value = "SELECT U.codigoTrainer FROM Usuario U WHERE U.id = ?1")
    String findCodigoTrainerById(int id);

    @Query(value = "SELECT NEW Usuario(codigoTrainer, nombres, apellidoPaterno, apellidoMaterno) FROM Usuario U WHERE U.tipoUsuario.id = 2")
    List<Usuario> findAllTrainers();

    @Query(value = "SELECT U FROM Usuario U INNER JOIN FETCH U.tipoUsuario P JOIN FETCH U.tipoDocumento D  WHERE U.username = ?1")
    Usuario findByUsername(String username);

    @Query(nativeQuery = true)
    List<UsuarioPOJO> getAllFromNativeQuery();
}
