package com.itsight.repository;

import com.itsight.domain.PorcentajesKilometraje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PorcentajesKilometrajeRepository extends JpaRepository<PorcentajesKilometraje, Integer> {

    List<PorcentajesKilometraje> findAllByTrainerId(Integer trainerId);

    PorcentajesKilometraje findByTrainerIdAndDistancia(Integer trainerId, int distancia);

    @Modifying
    @Query(value = "UPDATE porcentajes_kilometraje SET porc_kilo_tipos = jsonb_set(jsonb_set(jsonb_set(porc_kilo_tipos, CAST(?3 as text[]), CAST(?4 as jsonb), false), CAST(?5 as text[]), CAST(?6 as jsonb), false), CAST(?7 as text[]), CAST(?8 as jsonb), false) WHERE trainer_id=?1 AND distancia = ?2", nativeQuery = true)
    void updatePorcentajesComplex(Integer trainerId, int distancia, String textNombre1, String porcentajes, String textNombre2, String porcentajes1, String textNombre3, String porcentajes2);
}
