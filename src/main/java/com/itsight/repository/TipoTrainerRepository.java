package com.itsight.repository;

import com.itsight.domain.TipoTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTrainerRepository extends JpaRepository<TipoTrainer, Integer> {

}
