package com.itsight.repository;

import com.itsight.domain.SecurityUser;
import com.itsight.domain.dto.SecurityUserDTO;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SecurityUserRepository extends JpaRepository<SecurityUser, Integer> {

    @Query(value = "SELECT DISTINCT S FROM SecurityUser S JOIN FETCH S.roles R LEFT JOIN FETCH R.privileges P WHERE S.username = ?1")
    SecurityUser findByUsername(String username);

    @Query(nativeQuery = true)
    SecurityUserDTO findByUsernameNative(@Param("username") String username);

    @Query(value = "SELECT SU.id FROM SecurityUser SU WHERE SU.username = ?1")
    Integer findIdByUsername(String username);

    @Query(value = "SELECT SU.enabled FROM SecurityUser SU WHERE SU.id = ?1")
    Boolean findEnabledById(Integer id);

    @Query(value = "SELECT SU.enabled FROM SecurityUser SU WHERE SU.username = ?1")
    Boolean findEnabledByUsername(String username);

    @Modifying
    @Query(value = "UPDATE SecurityUser SU SET SU.enabled =?2 WHERE SU.username = ?1")
    void updateEstadoByUsername(String username, boolean flag);

    @Modifying
    @Query(value = "UPDATE SecurityUser SU SET SU.enabled =?2 WHERE SU.id = ?1")
    void updateEstadoById(Integer id, boolean flag);

    @Query(value = "SELECT SU.password FROM SecurityUser SU WHERE SU.id = ?1")
    String findPasswordById(Integer id);

    @Modifying
    @Query(value = "UPDATE SecurityUser SU SET SU.password = ?1 WHERE SU.id = ?2")
    void actualizarPassword(String password, Integer id);

    @Query("SELECT S.id FROM SecurityUser S WHERE S.username = ?1")
    Integer findUsernameByUsername(String username);

    @Procedure(name = "fn_validacion_correo")
    Boolean findCorreoExist(@Param("_correo") String correo);

    @Procedure(name = "get_correo_by_id")
    String getCorreoById(@Param("_user_id") Integer id);

    @Modifying
    @Query(value = "update security_user set enabled=true where security_user_id in (select trainer_id from trainer_ficha where tr_emp_id = ?1)", nativeQuery = true)
    void updateMultipleEstadoByTrEmpId(Integer id);

    @Query("SELECT CONCAT(S.id, '|', S.enabled) FROM SecurityUser S WHERE S.username = ?1")
    String findIdAndEnabledByUsername(String username);

    @Query("SELECT CONCAT(S.username, '|', S.enabled) FROM SecurityUser S WHERE S.id = ?1")
    String findUsernameAndEnabledById(Integer id);

    @Query("SELECT S.username FROM SecurityUser S WHERE S.id = ?1")
    String findUsernameById(Integer id);
}
