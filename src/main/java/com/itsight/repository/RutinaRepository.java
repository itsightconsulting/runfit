package com.itsight.repository;

import com.itsight.domain.Rutina;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutinaRepository extends JpaRepository<Rutina, Integer> {

    @EntityGraph(value = "rutina")
    List<Rutina> findByClienteIdOrderByIdDesc(int clienteId);

    @EntityGraph(value = "rutina")
    @Query("SELECT R FROM Rutina R WHERE R.redFitness.id = ?1 ORDER BY 1 DESC")
    List<Rutina> findFirstByRedFitnessIdOrderByIdDesc(int redFitnessId, Pageable pageable);

    @Query("SELECT R FROM Rutina R JOIN FETCH R.lstSemana S WHERE R.id = ?1")
    Rutina findOneWithOneWeekById(int id);

    @Modifying
    @Query("UPDATE Rutina R SET R.semanaIds = ?2 WHERE R.id = ?1")
    void updateSemanaIds(int id, int[] semanaIds);

    @Modifying
    @Query("UPDATE Rutina R SET R.totalSemanas = ?2 WHERE R.id = ?1")
    void updateTotalSemanas(int id, int totalSemanas);

    @Query("SELECT R.redFitness.id FROM Rutina R WHERE R.id = ?1")
    int findRedFitnessIdById(int rutinaId);


    @EntityGraph(value = "rutina")
    @Query("SELECT R FROM Rutina R LEFT JOIN FETCH R.lstSemana D where R.cliente.id =?1")
    List<Rutina> findByClienteId(int id);

    @Modifying
    @Query(value = "UPDATE Rutina SET control = jsonb_set(jsonb_set(control, CAST(:avance as text[]), CAST(:valorAvance as jsonb), false), CAST(:strdias as text[]), CAST(:valordias as jsonb), false) WHERE rutina_id = :id", nativeQuery = true)
    void updateAvanceSemanaIndex(@Param("id") int id, @Param("valorAvance") String valorAvance, @Param("avance") String avance, @Param("strdias") String strdias, @Param("valordias") String valordias );

    @Modifying
    @Query(value = "UPDATE Dia SET flagEnvioCliente = :flag WHERE  year(fecha) = :anio and month(fecha) = :mes")
    void updateResetDiasFlagEnvio(@Param("anio") int anio, @Param("mes") int mes , @Param("flag") boolean flag);

    // @Query("select e from Event e where year(e.eventDate) = ?1 and month(e.eventDate) = ?2")

    @Modifying
    @Query(value = "UPDATE Dia SET flag_envio_cliente = :flag WHERE dia = :indexdia and semana_id = :indexsemana", nativeQuery = true)
    void updateDiasFlagEnvio(@Param("indexsemana") int indexsemana, @Param("indexdia") int indexdia, @Param("flag") boolean flag);

    @Modifying
    @Query("UPDATE Rutina R SET R.flagActivo = ?2 WHERE R.id = ?1")
    void updateFlagActivo(int id, boolean flagActivo);

}
