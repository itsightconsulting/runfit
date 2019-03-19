package com.itsight.repository;

import com.itsight.domain.Paquete;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaqueteRepository extends JpaRepository<Paquete, Integer> {

    @Override
    @EntityGraph(value = "paquete.plan")
    List<Paquete> findAll();

    @EntityGraph(value = "paquete.plan")
    List<Paquete> findAllByFlagActivo(Boolean flagActivo);

    @EntityGraph(value = "paquete.plan")
    List<Paquete> findAllByNombreContainingOrDescripcionContaining(String nombre, String descripcion);

    @Query(value = "SELECT new Paquete(nombreContrato, rutaContrato) FROM Paquete WHERE id = ?1")
    Paquete findRouteNamesById(Integer id);

    @Modifying
    @Query(value = "UPDATE Paquete P SET P.flagActivo =?2 WHERE P.id = ?1")
    void updateFlagActivoById(Integer id, boolean flagActivo);
}
