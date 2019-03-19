package com.itsight.repository;

import com.itsight.domain.CondicionMejora;
import com.itsight.domain.dto.CondicionMejoraDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondicionMejoraRepository extends JpaRepository<CondicionMejora, Integer> {

    List<CondicionMejora> findByNombreContainingIgnoreCase(String nombre);

    List<CondicionMejora> findByIdIn(List<Integer> ids);

    List<CondicionMejora> findAllByOrderById();

    @Query("SELECT CM FROM CondicionMejora CM ORDER BY 1 ASC")
    List<CondicionMejora> findAllByOrderByIdDesc();

    List<CondicionMejora> findAllByNombreContainingIgnoreCaseOrderByIdDesc(String comodin);

    @Query(value = "SELECT NEW com.itsight.domain.dto.CondicionMejoraDTO(C.id, C.nombre) FROM CondicionMejora C")
    List<CondicionMejoraDTO> findAllByOrderByIdAsc();
}
