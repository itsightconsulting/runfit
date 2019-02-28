package com.itsight.repository;

import com.itsight.domain.UbPeru;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbPeruRepository extends JpaRepository<UbPeru, Integer> {
}
