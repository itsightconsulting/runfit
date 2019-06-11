package com.itsight.repository;

import com.itsight.domain.AnuncioTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnuncioTrainerRepository extends JpaRepository<AnuncioTrainer, Integer> {
}
