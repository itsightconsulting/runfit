package com.itsight.repository;

import com.itsight.domain.Rutina;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RutinaRepository extends JpaRepository<Rutina, Integer> {

    @EntityGraph(value = "rutina")
    List<Rutina> findByClienteIdOrderByIdDesc(Integer clienteId);

    @EntityGraph(value = "rutina")
    @Query("SELECT R FROM Rutina R WHERE R.redFitness.id = ?1 ORDER BY 1 DESC")
    List<Rutina> findFirstByRedFitnessIdOrderByIdDesc(Integer redFitnessId, Pageable pageable);

    @Query("SELECT R FROM Rutina R JOIN FETCH R.lstSemana S WHERE R.id = ?1")
    Rutina findOneWithOneWeekById(Integer id);

    @Modifying
    @Query("UPDATE Rutina R SET R.semanaIds = ?2 WHERE R.id = ?1")
    void updateSemanaIds(Integer id, int[] semanaIds);

    @Modifying
    @Query("UPDATE Rutina R SET R.totalSemanas = ?2 WHERE R.id = ?1")
    void updateTotalSemanas(Integer id, int totalSemanas);

    @Query("SELECT R.redFitness.id FROM Rutina R WHERE R.id = ?1")
    Integer findRedFitnessIdById(Integer id);

    @Modifying
    @Query(value = "UPDATE Rutina SET control = jsonb_set(jsonb_set(control, CAST(:avance as text[]), CAST(:valorAvance as jsonb), false), CAST(:strdias as text[]), CAST(:valordias as jsonb), false) WHERE rutina_id = :id", nativeQuery = true)
    void updateAvanceSemanaIndex(@Param("id") Integer id, @Param("valorAvance") String valorAvance, @Param("avance") String avance, @Param("strdias") String strdias, @Param("valordias") String valordias );

    @Modifying
    @Query(value = "UPDATE Dia SET flagEnvioCliente = :flag WHERE YEAR(fecha) = :anio and MONTH(fecha) = :mes")
    void updateResetDiasFlagEnvio(@Param("anio") int anio, @Param("mes") int mes , @Param("flag") boolean flag);

    // @Query("select e from Event e where year(e.eventDate) = ?1 and month(e.eventDate) = ?2")

    @Modifying
    @Query(value = "UPDATE Dia SET flag_envio_cliente = :flag WHERE dia = :indexdia and semana_id = :indexsemana", nativeQuery = true)
    void updateDiasFlagEnvio(@Param("indexsemana") int indexsemana, @Param("indexdia") int indexdia, @Param("flag") boolean flag);

    @Modifying
    @Query("UPDATE Rutina R SET R.flagActivo = ?2 WHERE R.id = ?1")
    void updateFlagActivo(Integer id, boolean flagActivo);

    @Modifying
    @Query("UPDATE Rutina R SET R.fechaCliAcceso = ?2 WHERE R.id = ?1")
    void updateFechaCliAccesoById(Integer id, Date fechaMax);

    @Query(value = "SELECT MAX(R.id) FROM Rutina R WHERE R.cliente.id = ?1")
    Integer getMaxRutinaIdByClienteId(Integer clienteId);
}
