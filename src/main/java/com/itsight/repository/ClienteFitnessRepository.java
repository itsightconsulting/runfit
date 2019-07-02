package com.itsight.repository;

import com.itsight.domain.ClienteFitness;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteFitnessRepository extends JpaRepository<ClienteFitness, Integer> {

    @Override
    @EntityGraph(value = "clienteFitness.cliente")
    List<ClienteFitness> findAll();

    @Query("SELECT CF FROM ClienteFitness CF WHERE CF.cliente.id = ?1")
    ClienteFitness findByClienteId(Integer clienteId);

    @Query(value="SELECT CF FROM ClienteFitness CF JOIN FETCH CF.cliente C WHERE CF.cliente.id = ?1")
    ClienteFitness findClienteAllDataByClienteId(Integer clienteId);
}
