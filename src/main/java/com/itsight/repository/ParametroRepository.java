package com.itsight.repository;

import com.itsight.domain.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Integer> {

    List<Parametro> findAllByClaveContaining(String clave);

    Parametro findByClave(String clave);

}
