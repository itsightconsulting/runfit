package com.itsight.repository;

import com.itsight.domain.Servicio;
import com.itsight.domain.pojo.ServicioPOJO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer>{

    @Query(nativeQuery = true)
    List<ServicioPOJO> findAllByTrainerId(Integer trainerId);
}
