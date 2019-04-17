package com.itsight.repository;

import com.itsight.domain.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Integer> {

    Modulo findByNombre(String nombre);
}
