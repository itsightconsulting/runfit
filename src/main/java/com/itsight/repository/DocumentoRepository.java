package com.itsight.repository;

import com.itsight.domain.Documento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

    @Override
    @EntityGraph(value = "documento", attributePaths = {})
    List<Documento> findAll();

    @EntityGraph(value = "documento", attributePaths = {})
    List<Documento> findAllByFlagActivo(Boolean flagActivo);

    @EntityGraph(value = "documento", attributePaths = {})
    List<Documento> findAllByNombreContaining(String nombre);

    @EntityGraph(value = "documento", attributePaths = {})
    List<Documento> findAllByNombreContainingIgnoreCase(String nombre);

    @EntityGraph(value = "documento", attributePaths = {})
    Documento findById(int id);

    @Query("SELECT D.nombre FROM Documento D WHERE D.id = ?1 AND D.uuid = ?2")
    String findNombreByIdAndUuid(int id, UUID uuid);

    @EntityGraph(value = "documento", attributePaths = {})
    List<Documento> findAllByNombreContainingIgnoreCaseAndFlagActivo(String nombre, Boolean flagActivo);

    @Modifying
    @Query(value = "UPDATE Documento D SET D.flagActivo =?2 WHERE D.id = ?1")
    void updateFlagActivoById(int id, boolean flagActivo);
}
