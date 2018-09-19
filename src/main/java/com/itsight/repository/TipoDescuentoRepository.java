package com.itsight.repository;

import com.itsight.domain.TipoDescuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TipoDescuentoRepository extends JpaRepository<TipoDescuento, Integer> {

}
