package com.itsight.repository;

import com.itsight.domain.PreTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreTrainerRepository extends JpaRepository<PreTrainer, Integer> {

    PreTrainer findByCorreo(String correo);
}
