package com.itsight.repository;

import com.itsight.domain.ClienteFitness;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteFitnessRepository extends JpaRepository<ClienteFitness, Integer> {

    @Override
    @EntityGraph(value = "clienteFitness.cliente")
    List<ClienteFitness> findAll();

    //@Query("SELECT UF FROM ClienteFitness UF WHERE UF.cliente.id = ?1")
    @EntityGraph(value = "clienteFitness.cliente")
    ClienteFitness findByClienteId(Integer clienteId);
}
