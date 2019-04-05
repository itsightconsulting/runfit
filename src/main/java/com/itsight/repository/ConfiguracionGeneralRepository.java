package com.itsight.repository;

import com.itsight.domain.ConfiguracionGeneral;
import com.itsight.domain.dto.ConfiguracionClienteDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfiguracionGeneralRepository extends JpaRepository<ConfiguracionGeneral, Integer> {

    @EntityGraph(value = "confGen.tipoUsuario")
    List<ConfiguracionGeneral> findByFlagActivo(boolean flagActivo);

    @Query(value = "SELECT NEW com.itsight.domain.dto.ConfiguracionClienteDTO(CG.nombre, CG.valor) FROM ConfiguracionGeneral CG")
    List<ConfiguracionClienteDTO> getAll();
}
