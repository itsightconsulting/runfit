package com.itsight.repository;

import com.itsight.domain.Dia;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaRepository extends JpaRepository<Dia, Integer> {

    @Override
    @EntityGraph("dia")
    List<Dia> findAll();

    @Override
    @EntityGraph("dia")
    Dia findOne(Integer id);

    @Query("SELECT D.id FROM Dia D WHERE D.semana.id = ?1 ORDER BY 1")
    List<Integer> findIdBySemanaId(int semanaId);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(elementos, CAST(:texto as text[]), to_jsonb(CAST(:nombre as text)), false) WHERE dia_id = :id", nativeQuery = true)
    void updateEspecificaColumnaJsonBGenericoByQueryTextAndId(@Param("id") int id, @Param("nombre") String nombre, @Param(value = "texto") String texto);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(elementos, CAST(:texto as text[]), CAST(:valor as jsonb), false) WHERE dia_id = :id", nativeQuery = true)
    void updateEspecificaColumnaJsonBGenericoByQueryTextAndIdInt(@Param("id") int id, @Param("valor") String valor, @Param(value = "texto") String texto);

    @Modifying
    @Query(value = "UPDATE dia SET minutos = :minutos, elementos = jsonb_set(elementos, CAST(:texto as text[]), CAST(:valor as jsonb), false) WHERE dia_id = :id", nativeQuery = true)
    void updateTiemposDia(@Param("id") int id, @Param("valor") String valor, @Param(value = "texto") String texto,@Param(value = "minutos") int totalMinutos);

    @Modifying
    @Query(value = "UPDATE dia SET calorias = calorias + :calorias, distancia = :totalDistancia, elementos = jsonb_set(jsonb_set(elementos, CAST(:txtNombre as text[]), to_jsonb(CAST(:nombre as text)), false), CAST(:txtDistancia as text[]), CAST(:distancia as jsonb), false) WHERE dia_id = :id", nativeQuery = true)
    void updateDiaAndElemento(@Param("id") int id, @Param(value = "calorias") double calorias, @Param(value = "totalDistancia") double totalDistancia, @Param(value = "txtNombre") String txtNombre, @Param("nombre") String nombre, @Param(value = "txtDistancia") String txtDistancia, @Param("distancia") String distancia);

    @Modifying
    @Query(value = "UPDATE dia SET calorias = calorias + :calorias, distancia = :totalDistancia, minutos = :totalMinutos, elementos = jsonb_set(jsonb_set(jsonb_set(elementos, CAST(:txtNombre as text[]), to_jsonb(CAST(:nombre as text)), false), CAST(:txtDistancia as text[]), CAST(:distancia as jsonb), false), CAST(:txtMinutos as text[]), CAST(:minutos as jsonb), false) WHERE dia_id = :id", nativeQuery = true)
    void updateDiaAndElemento2(@Param("id") int id, @Param(value = "calorias") double calorias, @Param(value = "totalDistancia") double totalDistancia, @Param(value = "totalMinutos") double totalMinutos, @Param(value = "txtNombre") String txtNombre, @Param("nombre") String nombre, @Param(value = "txtDistancia") String txtDistancia, @Param("distancia") String distancia, @Param(value = "txtMinutos") String txtMinutos, @Param("minutos") String minutos);

    //COALESCE retorna el primer valor not null(en caso no haya ninguna lista se guarde una lista vacia)
    @Modifying
    @Query(value = "UPDATE dia SET elementos = COALESCE(elementos, '[]') || CAST(:lista as jsonb) WHERE dia_id = :id", nativeQuery = true)
    void saveElemento(@Param("id") int id, @Param("lista") String lista);

    @Modifying
    @Query(value = "UPDATE dia SET distancia = :distancia, minutos = :minutos, elementos = COALESCE(elementos, '[]') || CAST(:elementos as jsonb) WHERE dia_id = :id", nativeQuery = true)
    void updateDiaFromTemplate(@Param("id") int id, @Param("distancia") int distancia, @Param("minutos") int minutos,@Param("elementos") String elementos);

    @Modifying
    @Query(value = "UPDATE dia SET distancia = :distancia, minutos = :minutos, elementos = CAST(:elementos as jsonb) WHERE dia_id = :id", nativeQuery = true)
    void updateDiaRootFromTemplate(@Param("id") int id, @Param("distancia") int distancia, @Param("minutos") int minutos,@Param("elementos") String elementos);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(elementos, CAST(:texto as text[]), CAST(:elemento as jsonb), true) WHERE dia__id = :id", nativeQuery = true)
    void saveElemento(@Param("id") int id, @Param("texto") String texto, @Param("elemento") String elemento);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(elementos, CAST(:texto as text[]), COALESCE(elementos->:listaIx->'elementos', '[]') || CAST(:elementos as jsonb), true) WHERE dia__id = :id", nativeQuery = true)
    void saveElementos(@Param("id") int id, @Param("listaIx") int listaIx, @Param("texto") String texto, @Param("elementos") String elementos);

    @Modifying
    @Query("UPDATE Dia D SET D.calorias = 0, D.distancia = 0, D.minutos = 0, D.flagDescanso = ?2, D.elementos = null WHERE D.id = ?1 ")
    void updateFlagDescanso(int id, boolean flagDescanso);

    @Modifying
    @Query(value = "UPDATE dia SET distancia = distancia - ?4, minutos = minutos - ?3, elementos = (SELECT elementos-?2 FROM dia WHERE dia_id=?1) WHERE dia_id=?1", nativeQuery = true)
    void deleteElementoById(int id, int listaIndice, int minutos, double distancia);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = (SELECT elementos #- CAST(?2 as text[]) FROM dia WHERE dia_id=?1) WHERE dia_id=?1", nativeQuery = true)
    void eliminarElementoById(int id, String texto);

    List<Dia> findAllBySemanaId(int semanaId);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(elementos, CAST(:texto as text[]), CAST(:elemento as jsonb), true) WHERE dia_id = :id", nativeQuery = true)
    void saveSubElemento(@Param("id") int id, @Param("texto") String texto, @Param("elemento") String elemento);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_insert(elementos, CAST(?2 as text[]), CAST(?4 as jsonb), ?3) WHERE dia_id = ?1", nativeQuery = true)
    void saveElementoPosEspecificaGeneric(int id, String texto, boolean insertarDespues, String elemento);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = (SELECT elementos #- CAST(?2 as text[]) FROM dia WHERE dia_id=?1) WHERE dia_id=?1", nativeQuery = true)
    void deleteSubElementoById(int id, String texto);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(elementos, CAST(:texto as text[]), CAST(:elemento as jsonb), false) WHERE dia_id = :id", nativeQuery = true)
    void updateAllJsonBGenericoByQueryTextAndId(@Param("id") int id, @Param("elemento") String elemento, @Param(value = "texto") String texto);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(jsonb_set(jsonb_set(elementos, CAST(?2 as text[]), to_jsonb(CAST(?3 as text)), false), CAST(?4 as text[]), to_jsonb(CAST(?5 as text)), false), CAST(?6 as text[]), CAST(?7 as jsonb), false) WHERE dia_id = ?1", nativeQuery = true)
    void updateEspecificasColumnasJsonBGenericoByQueriesTextAndId(int id, String textoNombre, String nombre, String textoMedia, String media, String textoTipo, String tipo);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(jsonb_set(elementos, CAST(?2 as text[]), to_jsonb(CAST(?3 as text)), false), CAST(?4 as text[]), to_jsonb(CAST(?5 as text)), false) WHERE dia_id = ?1", nativeQuery = true)
    void updateEspecificasColumnasJsonBGenericoByQueriesTextAndId2(int id, String textoNombre, String nombre, String textoMedia, String entryMedia);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(elementos, CAST(:texto as text[]), COALESCE(elementos #> CAST(:texto as text[]), '[]') || CAST(:subEles as jsonb), true) WHERE dia_id = :id", nativeQuery = true)
    void updateSubElementos(@Param("id") int id, @Param("texto") String texto, @Param("subEles") String subEles);
}
