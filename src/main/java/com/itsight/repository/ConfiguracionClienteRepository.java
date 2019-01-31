package com.itsight.repository;

import com.itsight.domain.ConfiguracionCliente;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionClienteRepository extends JpaRepository<ConfiguracionCliente, Integer> {

    @EntityGraph(value = "confCliente")
    ConfiguracionCliente findById(Integer id);

    @Query(value = "SELECT parametros->4->>'valor' FROM configuracion_cliente WHERE cliente_id = ?1", nativeQuery = true)
    String getFavsPostTrainer(int id);

    @Modifying
    @Query(value = "UPDATE configuracion_cliente SET parametros = jsonb_set(parametros, CAST('{4,\"valor\"}' AS text[]), to_jsonb(CAST(?2 as text))) WHERE cliente_id = ?1", nativeQuery = true)
    void updateFavsPostTrainer(int id, String postsFavsIds);
}
