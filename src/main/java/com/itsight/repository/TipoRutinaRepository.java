package com.itsight.repository;

import com.itsight.domain.TipoRutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRutinaRepository extends JpaRepository<TipoRutina,Integer> {
}
