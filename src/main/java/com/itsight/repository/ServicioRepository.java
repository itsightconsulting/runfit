package com.itsight.repository;

import com.itsight.domain.Servicio;
import com.itsight.domain.pojo.ServicioPOJO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer>{

    @Query(nativeQuery = true)
    List<ServicioPOJO> findAllByTrainerId(Integer trainerId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE servicio \n" +
            "SET \n" +
            "nombre =:nombre, \n" +
            "descripcion =:descripcion, \n" +
            "incluye =:incluye,\n" +
            "info_adicional =:infoAdicional,\n" +
            "tarifarios =CAST(:tarifarios as jsonb)\n" +
            "WHERE servicio_id=:id AND trainer_id=:trainerId")
    void updateByIdAndTrainerId(
            @Param("id") Integer id, @Param("nombre") String nombre, @Param("descripcion") String descripcion, @Param("incluye") String incluye, @Param("infoAdicional") String infoAdicional, @Param("tarifarios") String tarifarios, @Param("trainerId") Integer trainerId
    );

    @Modifying
    @Query(value = "INSERT INTO cliente_servicio(fecha_creacion, cliente_id, servicio_id) VALUES(now(), ?1, ?2)", nativeQuery = true)
    void addClienteServicio(Integer clienteId, Integer servicioId);

    @Query(value = "SELECT T.correo " +
                   "FROM servicio S " +
                   "INNER JOIN trainer T ON T.security_user_id=S.trainer_id " +
                   "WHERE S.servicio_id " +
                   "IN (SELECT servicio_id FROM CLIENTE_SERVICIO WHERE cliente_id = ?1) " +
                   "LIMIT 1", nativeQuery = true)
    String findTrainerCorreoById(Integer servicioId);

    @Query(value = "    SELECT COUNT(*)" +
            "    FROM cliente_servicio CS" +
            "    INNER JOIN servicio S ON  S.servicio_id = CS.servicio_id" +
            "    WHERE S.trainer_id = ( SELECT CASE WHEN TF.tr_emp_id IS NULL THEN TF.trainer_id ELSE TF.tr_emp_id END" +
            "    FROM trainer_ficha TF" +
            "    WHERE TF.trainer_id = ?1)" +
            "    AND" +
            "    CS.cliente_id IN (" +
            "            SELECT RF.cliente_id  from red_fitness RF where RF.trainer_id = ?1" +
            "            AND EXTRACT(DAY FROM RF.fecha_creacion- CS.fecha_creacion) < 1)", nativeQuery = true)


    Integer getTotalClientesByTrainerId(Integer trainerId);

    @Query(value = "SELECT count(*) " +
            "FROM cliente_servicio CS", nativeQuery = true)
    Integer getTotalClientesServicios();
}
