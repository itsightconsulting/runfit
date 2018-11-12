package com.itsight.repository;

import com.itsight.domain.RuConsolidado;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuConsolidadoRepository extends JpaRepository<RuConsolidado, Integer> {

    @EntityGraph(value = "ruConsolidado.rutina")
    RuConsolidado getById(int id);


}
