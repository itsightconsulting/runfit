package com.itsight.repository;

import com.itsight.domain.KilometrajeBase;
import com.itsight.domain.PorcentajesKilometraje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KilometrajeBaseRepository extends JpaRepository<KilometrajeBase, Integer> {

    List<KilometrajeBase> findAllByNivelAndDistancia(int nivel, int distancia);
}
