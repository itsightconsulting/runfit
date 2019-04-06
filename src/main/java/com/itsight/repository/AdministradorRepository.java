package com.itsight.repository;

import com.itsight.domain.Administrador;
import com.itsight.domain.pojo.UsuarioPOJO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Integer>{

    @EntityGraph(value = "administrador.all")
    List<Administrador> findAllByOrderByIdDesc();

    @EntityGraph(value = "administrador.all")
    List<Administrador> findAllByFlagActivoOrderByIdDesc(Boolean flagActivo);

    @Query("SELECT NEW com.itsight.domain.pojo.UsuarioPOJO(T.id, T.fechaCreacion, CONCAT(T.apellidos,' ',T.nombres), T.flagActivo, T.correo, T.username, T.fechaUltimoAcceso, P.id, P.nombre) FROM Trainer T INNER JOIN T.tipoUsuario P INNER JOIN T.tipoDocumento D WHERE LOWER(CONCAT(T.apellidos,' ',T.nombres)) LIKE LOWER(CONCAT('%',?1,'%'))")
    List<UsuarioPOJO> findByNombreCompleto(String nombreCompleto);

    @EntityGraph(value = "administrador.all")
    Administrador getById(Integer id);

    @Modifying
    @Query(value = "UPDATE Administrador A SET A.flagActivo =?2 WHERE A.id = ?1")
    void updateFlagActivoById(Integer id, boolean flagActivo);

    @Modifying
    @Query(value = "UPDATE Administrador A SET A.fechaUltimoAcceso =?1 WHERE A.id = ?2")
    void updateFechaUltimoAcceso(Date fechaUltimoAcceso, Integer id);

    @Query(value = "SELECT A.correo FROM Administrador A WHERE A.correo = ?1")
    List<Administrador> findAllByCorreo(String correo);

    @Query(value = "SELECT A FROM Administrador A INNER JOIN FETCH A.tipoUsuario P JOIN FETCH A.tipoDocumento D  WHERE A.username = ?1")
    Administrador findByUsername(String username);

}
