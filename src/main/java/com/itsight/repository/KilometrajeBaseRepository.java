package com.itsight.repository;

import com.itsight.domain.KilometrajeBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KilometrajeBaseRepository extends JpaRepository<KilometrajeBase, Integer> {

}
