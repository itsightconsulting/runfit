package com.itsight.repository;

import com.itsight.domain.UsuarioPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioPlanRepository extends JpaRepository<UsuarioPlan, Integer> {

    List<UsuarioPlan> findAllByFlagActivo(Boolean flagActivo);

}
