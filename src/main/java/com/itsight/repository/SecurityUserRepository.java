package com.itsight.repository;

import com.itsight.domain.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SecurityUserRepository extends JpaRepository<SecurityUser, Long> {

    @Query(value = "SELECT DISTINCT S FROM SecurityUser S JOIN FETCH S.roles R LEFT JOIN FETCH R.privileges P WHERE S.username = ?1")
    SecurityUser findByUsername(String username);

    @Query(value = "SELECT SU.id FROM SecurityUser SU WHERE SU.username = ?1")
    Integer findIdByUsername(String username);

    @Modifying
    @Query(value = "UPDATE SecurityUser SU SET SU.enabled =?2 WHERE SU.username = ?1")
    void updateEstadoByUsername(String username, boolean flag);

    @Query(value = "SELECT SU.password FROM SecurityUser SU WHERE SU.id = ?1")
    String findPasswordById(Long id);

    @Modifying
    @Query(value = "UPDATE SecurityUser SU SET SU.password = ?1 WHERE SU.id = ?2")
    void actualizarPassword(String password, Long id);

    @Query("SELECT S.id FROM SecurityUser S WHERE S.username = ?1")
    Integer findUsernameByUsername(String username);

    @Procedure(name = "fn_validacion_correo")
    Boolean findCorreoExist(@Param("_correo") String correo);

}
