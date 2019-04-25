package com.itsight.repository;

import com.itsight.domain.BandejaTemporal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BandejaTemporalRepository extends JpaRepository<BandejaTemporal, Integer> {

    List<BandejaTemporal> findAllByOrderByIdDesc();
}
