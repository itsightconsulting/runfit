package com.itsight.repository;

import com.itsight.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    List<Rol> findByNombreContainingIgnoreCase(String nombre);

    List<Rol> findByIdIn(List<Integer> ids);

    List<Rol> findAllByOrderById();
}
