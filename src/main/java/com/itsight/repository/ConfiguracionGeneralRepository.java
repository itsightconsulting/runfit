package com.itsight.repository;

import com.itsight.domain.ConfiguracionGeneral;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfiguracionGeneralRepository extends JpaRepository<ConfiguracionGeneral, Integer> {

    @EntityGraph(value = "confGen.tipoUsuario")
    List<ConfiguracionGeneral> findByFlagActivo(boolean flagActivo);

}
