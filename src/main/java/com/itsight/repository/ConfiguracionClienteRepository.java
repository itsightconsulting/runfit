package com.itsight.repository;

import com.itsight.domain.ConfiguracionCliente;
import com.itsight.domain.dto.ConfiguracionClienteDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfiguracionClienteRepository extends JpaRepository<ConfiguracionCliente, Integer> {

    @Query(value = "SELECT parametros->3->>'valor' FROM configuracion_cliente WHERE cliente_id = ?1", nativeQuery = true)
    String getFavsPostTrainer(Integer id);

    @Modifying
    @Query(value = "UPDATE configuracion_cliente SET parametros = jsonb_set(parametros, CAST('{3,\"valor\"}' AS text[]), to_jsonb(CAST(?2 as text))) WHERE cliente_id = ?1", nativeQuery = true)
    void updateFavsPostTrainer(Integer id, String postsFavsIds);

    @Query(value = "select cast(params as jsonb)->>'valor' " +
            "from (SELECT jsonb_array_elements(parametros) params from configuracion_cliente where cliente_id=?1) as t " +
            "where cast(params as jsonb)->>'nombre' = ?2", nativeQuery = true)
    String findByIdAndClave(int id, String clave);

    @Modifying
    @Query(value = "UPDATE configuracion_cliente SET parametros = jsonb_set(parametros, " +
            "(select cast(concat('{',ix-1,',\"valor\"}') as text[]) from ( " +
            "select row_number() over() ix, * from (select jsonb_array_elements(parametros)->>'nombre' nom from configuracion_cliente where cliente_id = ?1) as t) as r " +
            "where lower(nom) = lower(?2))" +
            ",to_jsonb(CAST(?3 as text))) WHERE cliente_id = ?1", nativeQuery = true)
    void updateById(Integer id, String clave, String valor);

    @Modifying
    @Query(value = "UPDATE configuracion_cliente\n" +
            "SET parametros = jsonb_set(parametros, CAST('{5,\"valor\"}' as text[]), CAST(\n" +
            "        CAST(\n" +
            "            case when (CAST(COALESCE(NULLIF(parametros -> 5 ->> 'valor', ''), '0') as int) + :sustractOrPlusNumber) < 0 THEN 0 ELSE\n" +
            "                (CAST(COALESCE(NULLIF(parametros -> 5 ->> 'valor', ''), '0') as int) + :sustractOrPlusNumber)\n" +
            "            END\n" +
            "            as text) as jsonb))\n" +
            "WHERE cliente_id = :cliId", nativeQuery = true)
    void updateNotificacionChatById(Integer cliId, Integer sustractOrPlusNumber);

    @Modifying
    @Query(value = "UPDATE configuracion_cliente\n" +
            "SET parametros = jsonb_set(parametros, CAST('{6,\"valor\"}' as text[]), CAST(\n" +
            "        CAST(\n" +
            "            case when (CAST(COALESCE(NULLIF(parametros -> 6 ->> 'valor', ''), '0') as int) + :sustractOrPlusNumber) < 0 THEN 0 ELSE\n" +
            "                (CAST(COALESCE(NULLIF(parametros -> 6 ->> 'valor', ''), '0') as int) + :sustractOrPlusNumber)\n" +
            "            END\n" +
            "            as text) as jsonb))\n" +
            "WHERE cliente_id = :cliId", nativeQuery = true)
    void updateNotificacionRutinasTotById(Integer cliId, Integer sustractOrPlusNumber);


}
