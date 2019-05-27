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
}
