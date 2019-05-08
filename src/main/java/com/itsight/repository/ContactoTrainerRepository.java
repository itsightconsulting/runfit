package com.itsight.repository;

import com.itsight.domain.ContactoTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoTrainerRepository extends JpaRepository<ContactoTrainer, Integer> {
}
