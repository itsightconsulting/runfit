package com.itsight.repository;

import com.itsight.domain.Administrador;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long>{

    @EntityGraph(value = "administrador.all")
    List<Administrador> findAllByOrderByIdDesc();

    @EntityGraph(value = "administrador.all")
    List<Administrador> findAllByFlagActivoOrderByIdDesc(Boolean flagActivo);

    @Query("SELECT A FROM Administrador A INNER JOIN FETCH A.tipoUsuario P JOIN FETCH A.tipoDocumento D  WHERE LOWER(CONCAT(A.apellidos,' ',A.nombres)) LIKE LOWER(CONCAT('%',?1,'%'))")
    List<Administrador> findByNombreCompleto(String nombreCompleto);

    @EntityGraph(value = "administrador.all")
    Administrador findById(Long id);

    @Modifying
    @Query(value = "UPDATE Administrador A SET A.flagActivo =?2 WHERE A.id = ?1")
    void updateFlagActivoById(Long id, boolean flagActivo);

    @Modifying
    @Query(value = "UPDATE Administrador A SET A.fechaUltimoAcceso =?1 WHERE A.id = ?2")
    void updateFechaUltimoAcceso(Date fechaUltimoAcceso, Long id);

    @Query(value = "SELECT A.correo FROM Administrador A WHERE A.correo = ?1")
    List<Administrador> findAllByCorreo(String correo);

    @Query(value = "SELECT A FROM Administrador A INNER JOIN FETCH A.tipoUsuario P JOIN FETCH A.tipoDocumento D  WHERE A.username = ?1")
    Administrador findByUsername(String username);

}
