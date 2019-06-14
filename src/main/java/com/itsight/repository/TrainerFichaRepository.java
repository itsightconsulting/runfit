package com.itsight.repository;

import com.itsight.domain.TrainerFicha;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerFichaRepository extends JpaRepository<TrainerFicha, Integer> {

    @Query(nativeQuery = true)
    List<TrainerFichaPOJO> findAllWithFgEnt(Integer limit, Integer offset);

    @EntityGraph(value = "trainerFicha.trainer")
    TrainerFicha findByNomPag(String nomPag);

    @Query(nativeQuery = true)
    TrainerFichaPOJO findByNomPagPar(String nomPag);

    @Query(nativeQuery = true)
    TrainerFichaPOJO findByTrainerId(Integer trainerId);

    @Modifying
    @Query("UPDATE TrainerFicha T SET T.flagFichaAceptada = ?1 WHERE T.trainer.id = ?2")
    void updateFlagFichaAceptadaByTrainerId(Boolean flagFichaAceptada, Integer trainerId);

    @Query("SELECT T.flagFichaAceptada FROM TrainerFicha T WHERE T.trainer.id = ?1")
    Boolean getFlagFichaAceptadaByTrainerId(Integer trainerId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE trainer_ficha \n" +
            "SET \n" +
            "sexo =:sexo, \n" +
            "acerca =:acerca, \n" +
            "centro_trabajo =:centroTrabajo,\n" +
            "especialidad =:especialidad,\n" +
            "especialidades =:especilidades,\n" +
            "estudios =:estudios,\n" +
            "experiencias =:experiencias,\n" +
            "flag_ficha_aceptada = null,\n" +
            "formas_trabajo =:formasTrabajo,\n" +
            "horario =:horario,\n" +
            "idiomas =:idiomas,\n" +
            "metodo_trabajo =:metodoTrabajo,\n" +
            "niveles =:niveles,\n" +
            "nota =:nota,\n" +
            "redes =:redes,\n" +
            "resultados =:resultados\n" +
            "WHERE trainer_id=:trainerId")
    void actualizarFichaByTrainerId(
            @Param(value = "sexo") Integer sexo, @Param(value = "acerca") String acerca, @Param(value = "centroTrabajo") String centroTrabajo, @Param(value = "especialidad") String especialidad, @Param(value = "especilidades") String especilidades, @Param(value = "estudios") String estudios, @Param(value = "experiencias") String experiencias, @Param(value = "formasTrabajo") String formasTrabajo, @Param(value = "horario") String horario, @Param(value = "idiomas") String idiomas, @Param(value = "metodoTrabajo") String metodoTrabajo, @Param(value = "niveles") String niveles, @Param(value = "nota") String nota, @Param(value = "redes") String redes, @Param(value = "resultados") String resultados, @Param(value = "trainerId") Integer trainerId
    );

    @Modifying
    @Query(value = "UPDATE trainer_ficha SET staff_galeria = COALESCE(staff_galeria,'') || case when COALESCE(NULLIF(staff_galeria,''), '0') = '0' then ?1 else '|' || ?1 end WHERE trainer_id = ?2", nativeQuery = true)
    void updateStaffGaleriaByTrainerId(String staffGaleria, Integer trainerId);

    @Query(value = "SELECT tr_emp_id FROM trainer_ficha WHERE trainer_id = ?1", nativeQuery = true)
    Integer getTrEmpIdById(Integer id);

    @Query(value = "SELECT concat_ws('@',cuentas, medios_pago) FROM trainer_ficha WHERE trainer_id = ?1", nativeQuery = true)
    String getCcsAndMediosPagoById(Integer trainerId);

    @Procedure(name = "fn_validacion_nom_pag")
    Boolean findNomPagExist(@Param("_nom_pag") String correo);
}
