package com.itsight.repository;

import com.itsight.domain.ClientePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientePlanRepository extends JpaRepository<ClientePlan, Integer> {

    List<ClientePlan> findAllByFlagActivo(Boolean flagActivo);

}
