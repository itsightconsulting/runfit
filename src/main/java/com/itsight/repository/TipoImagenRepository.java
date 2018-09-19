package com.itsight.repository;

import com.itsight.domain.TipoImagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoImagenRepository extends JpaRepository<TipoImagen, Integer> {

}
