package com.itsight.repository;

import com.itsight.domain.UsuarioFitness;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioFitnessRepository extends JpaRepository<UsuarioFitness, Integer> {

    @Override
    @EntityGraph(value = "usuarioFitness.usuario")
    List<UsuarioFitness> findAll();

    //@Query("SELECT UF FROM UsuarioFitness UF WHERE UF.usuario.id = ?1")
    @EntityGraph(value = "usuarioFitness.usuario")
    UsuarioFitness findByUsuarioId(int usuarioId);
}
