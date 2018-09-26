package com.itsight.repository;

import com.itsight.domain.PorcentajesKilometraje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PorcentajesKilometrajeRepository extends JpaRepository<PorcentajesKilometraje, Integer> {

    List<PorcentajesKilometraje> findAllByTrainerId(int trainerId);
}
