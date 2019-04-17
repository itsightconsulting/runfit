package com.itsight.repository;

import com.itsight.domain.Correo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorreoRepository extends CrudRepository<Correo, Integer> {
}
