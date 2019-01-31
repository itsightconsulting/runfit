package com.itsight.repository;

import com.itsight.domain.Cliente;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>, JpaSpecificationExecutor<Cliente> {

    @Override
    @EntityGraph(value = "cliente.all")
    List<Cliente> findAll(Specification<Cliente> clienteSpecification);

    @EntityGraph(value = "cliente.all")
    List<Cliente> findAllByOrderByIdDesc();

    @EntityGraph(value = "cliente.all")
    List<Cliente> findAllByFlagActivoOrderByIdDesc(Boolean flagActivo);

    @Query("SELECT U FROM Cliente U INNER JOIN FETCH U.tipoUsuario P JOIN FETCH U.tipoDocumento D  WHERE LOWER(CONCAT(U.apellidoPaterno,' ',U.apellidoMaterno,' ',U.nombres)) LIKE LOWER(CONCAT('%',?1,'%'))")
    List<Cliente> findByNombreCompleto(String nombreCompleto);

    @EntityGraph(value = "cliente.all")
    Cliente findById(int id);

    @Modifying
    @Query(value = "UPDATE Cliente U SET U.flagActivo =?2 WHERE U.id = ?1")
    void updateFlagActivoById(int id, boolean flagActivo);

    @Modifying
    @Query(value = "UPDATE Cliente U SET U.fechaUltimoAcceso =?1 WHERE U.id = ?2")
    void updateFechaUltimoAcceso(Date fechaUltimoAcceso, int id);

    @Query(value = "SELECT U.correo FROM Cliente U WHERE U.correo = ?1")
    List<Cliente> findAllByCorreo(String correo);

    @Query(value = "SELECT U FROM Cliente U INNER JOIN FETCH U.tipoUsuario P JOIN FETCH U.tipoDocumento D  WHERE U.username = ?1")
    Cliente findByUsername(String username);

    @Query(value = "SELECT CONCAT(C.nombres, ' ', C.apellidoPaterno, ' ', C.apellidoMaterno) FROM Cliente C WHERE C.id = ?1")
    String findNombreCompletoById(int id);
}
