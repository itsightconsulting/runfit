package com.itsight.repository;

import com.itsight.domain.Visitante;
import com.itsight.domain.dto.UsuGenDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitanteRepository extends JpaRepository<Visitante, Integer> {

    @Query(nativeQuery = true)
    UsuGenDTO getForCookieById(Integer id);
}
