package com.itsight.repository;

import com.itsight.domain.MiniRutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MiniRutinaRepository extends JpaRepository<MiniRutina, Integer> {

    @Query("SELECT M FROM MiniRutina M INNER JOIN FETCH M.categoria C WHERE M.usuario.id = ?1 ORDER BY M.id")
    List<MiniRutina> findAllByUsuarioId(int usuarioId);

    @Query("SELECT M FROM MiniRutina M WHERE M.usuario.id = ?1 AND M.categoria.id = ?2")
    MiniRutina findByUsuarioIdAndEspecificacionSubCategoriaId(int usuarioId, int especificacionSubCatId);

}
