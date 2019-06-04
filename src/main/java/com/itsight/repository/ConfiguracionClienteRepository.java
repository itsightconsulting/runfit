package com.itsight.repository;

import com.itsight.domain.ConfiguracionCliente;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionClienteRepository extends JpaRepository<ConfiguracionCliente, Integer> {

    @Query(value = "SELECT parametros->4->>'valor' FROM configuracion_cliente WHERE cliente_id = ?1", nativeQuery = true)
    String getFavsPostTrainer(Integer id);

    @Modifying
    @Query(value = "UPDATE configuracion_cliente SET parametros = jsonb_set(parametros, CAST('{4,\"valor\"}' AS text[]), to_jsonb(CAST(?2 as text))) WHERE cliente_id = ?1", nativeQuery = true)
    void updateFavsPostTrainer(Integer id, String postsFavsIds);

    @Query(value = "select cast(params as jsonb)->>'valor' " +
            "from (SELECT jsonb_array_elements(parametros) params from configuracion_cliente where cliente_id=?1) as t " +
            "where cast(params as jsonb)->>'nombre' = ?2", nativeQuery = true)
    String findByIdAndClave(int id, String clave);
}
