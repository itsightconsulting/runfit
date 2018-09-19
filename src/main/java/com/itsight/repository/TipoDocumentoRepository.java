package com.itsight.repository;

import com.itsight.domain.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {

    List<TipoDocumento> findByNombreContainingIgnoreCase(String nombre);

    List<TipoDocumento> findByIdIn(List<Integer> ids);

    List<TipoDocumento> findAllByOrderById();

    List<TipoDocumento> findAllByOrderByIdDesc();

    List<TipoDocumento> findAllByNombreContainingIgnoreCaseOrderByIdDesc(String comodin);
}
