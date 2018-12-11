package com.itsight.repository;

import com.itsight.domain.BagForest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BagForestRepository extends JpaRepository<BagForest, Integer> {
}
