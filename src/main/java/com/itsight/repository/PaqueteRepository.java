package com.itsight.repository;

import com.itsight.domain.Paquete;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaqueteRepository extends JpaRepository<Paquete, Integer> {

    @Override
    @EntityGraph(value = "paquete.plan", attributePaths = {})
    List<Paquete> findAll();

    @EntityGraph(value = "paquete.plan", attributePaths = {})
    List<Paquete> findAllByFlagActivo(Boolean flagActivo);

    @EntityGraph(value = "paquete.plan", attributePaths = {})
    List<Paquete> findAllByNombreContainingOrDescripcionContaining(String nombre, String descripcion);

    @Query(value = "SELECT new Paquete(nombreContrato, rutaContrato) FROM Paquete WHERE id = ?1")
    Paquete findRouteNamesById(int paqueteId);

}
