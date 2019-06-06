package com.itsight.repository;

import com.itsight.domain.AudioTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudioTrainerRepository extends JpaRepository<AudioTrainer, Integer> {
}
