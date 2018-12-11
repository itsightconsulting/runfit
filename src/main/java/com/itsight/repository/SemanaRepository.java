package com.itsight.repository;

import com.itsight.domain.Semana;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import java.util.List;

@Repository
public interface SemanaRepository extends JpaRepository<Semana, Integer> {

    @EntityGraph("semana")
    Semana findOne(int id);

    @Query("SELECT DISTINCT S FROM Semana S LEFT JOIN FETCH S.lstDia D LEFT JOIN FETCH S.rutina R WHERE S.id=?1")
    Semana findOneWithDays(int id);

    @Query("SELECT DISTINCT S FROM Semana S LEFT JOIN FETCH S.lstDia D WHERE S.rutina.id=?1 and D.flagEnvioCliente = true")
    List<Semana> findByRutinaIdOrderByIdDesc(int idrutina);

    @Modifying
    @Query("UPDATE Semana SET objetivos = ?2 WHERE id = ?1")
    void updateObjetivoById(int id, String objetivos);

    @Modifying
    @Query(value = "update semana as t set metricas_velocidad = c.mets from (SELECT CAST(unnest(string_to_array(?1, ',')) as int), unnest(string_to_array(?2, ' ')) ORDER BY 1) as c(id, mets) where c.id = t.semana_id", nativeQuery = true)
    void actualizarMetsVelocidadByIds(String ids, String mets);

}
