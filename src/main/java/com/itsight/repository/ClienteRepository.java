package com.itsight.repository;

import com.itsight.domain.Cliente;
import com.itsight.domain.dto.UsuGenDTO;
import com.itsight.domain.pojo.UsuarioPOJO;
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

    @Query("SELECT NEW com.itsight.domain.pojo.UsuarioPOJO(T.id, T.fechaCreacion, CONCAT(T.apellidos,' ',T.nombres), T.flagActivo, T.correo, T.username, T.fechaUltimoAcceso, 'Cliente') FROM Cliente T INNER JOIN T.tipoDocumento D WHERE LOWER(CONCAT(T.apellidos,' ',T.nombres)) LIKE LOWER(CONCAT('%',?1,'%'))")
    List<UsuarioPOJO> findByNombreCompleto(String nombreCompleto);

    @Query(nativeQuery = true)
    UsuGenDTO getById(Integer id);

    @Modifying
    @Query(value = "UPDATE Cliente U SET U.flagActivo =?2 WHERE U.id = ?1")
    void updateFlagActivoById(Integer id, boolean flagActivo);

    @Modifying
    @Query(value = "UPDATE Cliente U SET U.fechaUltimoAcceso =?1 WHERE U.id = ?2")
    void updateFechaUltimoAcceso(Date fechaUltimoAcceso, Integer id);

    @Query(value = "SELECT U.correo FROM Cliente U WHERE U.correo = ?1")
    List<Cliente> findAllByCorreo(String correo);

    @Query(value = "SELECT U FROM Cliente U JOIN FETCH U.tipoDocumento D  WHERE U.username = ?1")
    Cliente findByUsername(String username);

    @Query(value = "SELECT CONCAT(C.nombres, ' ', C.apellidos) FROM Cliente C WHERE C.id = ?1")
    String findNombreCompletoById(Integer id);

    @Query(value = "SELECT username FROM cliente WHERE security_user_id = ?1", nativeQuery = true)
    String getUsernameById(int id);
}
