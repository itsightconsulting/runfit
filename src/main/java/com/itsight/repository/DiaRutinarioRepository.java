package com.itsight.repository;

import com.itsight.domain.DiaRutinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaRutinarioRepository extends JpaRepository<DiaRutinario, Integer> {

    List<DiaRutinario> findAllByIdIn(List<Integer> ids);
}
