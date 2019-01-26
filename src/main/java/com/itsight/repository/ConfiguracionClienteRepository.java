package com.itsight.repository;

import com.itsight.domain.ConfiguracionCliente;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionClienteRepository extends JpaRepository<ConfiguracionCliente, Integer> {

    @EntityGraph(value = "confCliente")
    ConfiguracionCliente findById(Integer id);

}
