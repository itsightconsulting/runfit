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
    List<Integer> findIdBySemanaId(Integer semanaId);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(elementos, CAST(:texto as text[]), to_jsonb(CAST(:nombre as text)), false) WHERE dia_id = :id", nativeQuery = true)
    void updateEspecificaColumnaJsonBGenericoByQueryTextAndId(@Param("id") Integer id, @Param("nombre") String nombre, @Param(value = "texto") String texto);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(elementos, CAST(:texto as text[]), CAST(:valor as jsonb), false) WHERE dia_id = :id", nativeQuery = true)
    void updateEspecificaColumnaJsonBGenericoByQueryTextAndIdInt(@Param("id") Integer id, @Param("valor") String valor, @Param(value = "texto") String texto);

    @Modifying
    @Query(value = "UPDATE dia SET minutos = :minutos, elementos = jsonb_set(elementos, CAST(:texto as text[]), CAST(:valor as jsonb), false) WHERE dia_id = :id", nativeQuery = true)
    void updateTiemposDia(@Param("id") Integer id, @Param("valor") String valor, @Param(value = "texto") String texto,@Param(value = "minutos") int totalMinutos);

    @Modifying
    @Query(value = "UPDATE dia SET calorias = calorias + :calorias, distancia = :totalDistancia, elementos = jsonb_set(jsonb_set(elementos, CAST(:txtNombre as text[]), to_jsonb(CAST(:nombre as text)), false), CAST(:txtDistancia as text[]), CAST(:distancia as jsonb), false) WHERE dia_id = :id", nativeQuery = true)
    void updateDiaAndElemento(@Param("id") Integer id, @Param(value = "calorias") double calorias, @Param(value = "totalDistancia") double totalDistancia, @Param(value = "txtNombre") String txtNombre, @Param("nombre") String nombre, @Param(value = "txtDistancia") String txtDistancia, @Param("distancia") String distancia);

    @Modifying
    @Query(value = "UPDATE dia SET calorias = calorias + :calorias, distancia = :totalDistancia, minutos = :totalMinutos, elementos = jsonb_set(jsonb_set(jsonb_set(elementos, CAST(:txtNombre as text[]), to_jsonb(CAST(:nombre as text)), false), CAST(:txtDistancia as text[]), CAST(:distancia as jsonb), false), CAST(:txtMinutos as text[]), CAST(:minutos as jsonb), false) WHERE dia_id = :id", nativeQuery = true)
    void updateDiaAndElemento2(@Param("id") Integer id, @Param(value = "calorias") double calorias, @Param(value = "totalDistancia") double totalDistancia, @Param(value = "totalMinutos") double totalMinutos, @Param(value = "txtNombre") String txtNombre, @Param("nombre") String nombre, @Param(value = "txtDistancia") String txtDistancia, @Param("distancia") String distancia, @Param(value = "txtMinutos") String txtMinutos, @Param("minutos") String minutos);

    //COALESCE retorna el primer valor not null(en caso no haya ninguna lista se guarde una lista vacia)
    @Modifying
    @Query(value = "UPDATE dia SET elementos = COALESCE(elementos, '[]') || CAST(:lista as jsonb) WHERE dia_id = :id", nativeQuery = true)
    void saveElemento(@Param("id") Integer id, @Param("lista") String lista);

    @Modifying
    @Query(value = "UPDATE dia SET calorias = :calorias, distancia = :distancia, minutos = :minutos, elementos = COALESCE(elementos, '[]') || CAST(:elementos as jsonb) WHERE dia_id = :id", nativeQuery = true)
    void updateDiaFromTemplate(@Param("id") Integer id, @Param("calorias") double calorias, @Param("distancia") double distancia, @Param("minutos") int minutos,@Param("elementos") String elementos);

    @Modifying
    @Query(value = "UPDATE dia SET calorias = :calorias, distancia = :distancia, minutos = :minutos, elementos = CAST(:elementos as jsonb) WHERE dia_id = :id", nativeQuery = true)
    void updateDiaRootFromTemplate(@Param("id") Integer id, @Param("calorias") double calorias, @Param("distancia") double distancia, @Param("minutos") int minutos,@Param("elementos") String elementos);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(elementos, CAST(:texto as text[]), CAST(:elemento as jsonb), true) WHERE dia__id = :id", nativeQuery = true)
    void saveElemento(@Param("id") Integer id, @Param("texto") String texto, @Param("elemento") String elemento);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(elementos, CAST(:texto as text[]), COALESCE(elementos->:listaIx->'elementos', '[]') || CAST(:elementos as jsonb), true) WHERE dia__id = :id", nativeQuery = true)
    void saveElementos(@Param("id") Integer id, @Param("listaIx") int listaIx, @Param("texto") String texto, @Param("elementos") String elementos);

    @Modifying
    @Query("UPDATE Dia D SET D.calorias = 0, D.distancia = 0, D.minutos = 0, D.flagDescanso = ?2, D.elementos = null WHERE D.id = ?1 ")
    void updateFlagDescanso(Integer id, boolean flagDescanso);

    @Modifying
    @Query(value = "UPDATE dia SET calorias =  calorias - ?5,distancia = distancia - ?4, minutos = minutos - ?3, elementos = (SELECT elementos-?2 FROM dia WHERE dia_id=?1) WHERE dia_id=?1", nativeQuery = true)
    void deleteElementoById(Integer id, int listaIndice, int minutos, double distancia, double calorias);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = (SELECT elementos #- CAST(?2 as text[]) FROM dia WHERE dia_id=?1) WHERE dia_id=?1", nativeQuery = true)
    void eliminarElementoById(Integer id, String texto);

    List<Dia> findAllBySemanaId(int semanaId);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(elementos, CAST(:texto as text[]), CAST(:elemento as jsonb), true) WHERE dia_id = :id", nativeQuery = true)
    void saveSubElemento(@Param("id") Integer id, @Param("texto") String texto, @Param("elemento") String elemento);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_insert(elementos, CAST(?2 as text[]), CAST(?4 as jsonb), ?3) WHERE dia_id = ?1", nativeQuery = true)
    void saveElementoPosEspecificaGeneric(Integer id, String texto, boolean insertarDespues, String elemento);

    @Modifying
    @Query(value = "UPDATE dia SET calorias =  calorias - ?4, distancia = distancia - ?3, elementos = (SELECT elementos #- CAST(?2 as text[]) FROM dia WHERE dia_id=?1) WHERE dia_id=?1", nativeQuery = true)
    void deleteSubElementoById(Integer id, String texto, double distancia, double calorias);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(elementos, CAST(:texto as text[]), CAST(:elemento as jsonb), false) WHERE dia_id = :id", nativeQuery = true)
    void updateAllJsonBGenericoByQueryTextAndId(@Param("id") Integer id, @Param("elemento") String elemento, @Param(value = "texto") String texto);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(jsonb_set(jsonb_set(elementos, CAST(?2 as text[]), to_jsonb(CAST(?3 as text)), false), CAST(?4 as text[]), to_jsonb(CAST(?5 as text)), false), CAST(?6 as text[]), CAST(?7 as jsonb), false) WHERE dia_id = ?1", nativeQuery = true)
    void updateEspecificasColumnasJsonBGenericoByQueriesTextAndId(Integer id, String textoNombre, String nombre, String textoMedia, String media, String textoTipo, String tipo);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(jsonb_set(elementos, CAST(?2 as text[]), to_jsonb(CAST(?3 as text)), false), CAST(?4 as text[]), to_jsonb(CAST(?5 as text)), false) WHERE dia_id = ?1", nativeQuery = true)
    void updateEspecificasColumnasJsonBGenericoByQueriesTextAndId2(Integer id, String textoNombre, String nombre, String textoMedia, String entryMedia);

    @Modifying
    @Query(value = "UPDATE dia SET elementos = jsonb_set(elementos, CAST(:texto as text[]), COALESCE(elementos #> CAST(:texto as text[]), '[]') || CAST(:subEles as jsonb), true) WHERE dia_id = :id", nativeQuery = true)
    void updateSubElementos(@Param("id") Integer id, @Param("texto") String texto, @Param("subEles") String subEles);

    @Modifying
    @Query(value = "UPDATE dia SET calorias = calorias + :calorias, distancia = :totalDistancia, elementos = jsonb_set(jsonb_set(elementos, CAST(:txtDistancia as text[]), CAST(:distancia as jsonb), false), CAST(:txtNewSubEle as text[]), CAST(:subEle as jsonb), true) WHERE dia_id = :id", nativeQuery = true)
    void updateDiaAndSubEleEle(@Param("id") Integer id, @Param("calorias") double calorias, @Param("totalDistancia") double totalDistancia, @Param("txtDistancia") String txtDistancia, @Param("distancia") String distancia, @Param("txtNewSubEle") String txtNewSubEle, @Param("subEle") String subEle);

    @Modifying
    @Query(value = "WITH SA AS(select calorias, distancia, elementos, literal, flag_descanso, minutos FROM dia WHERE semana_id = ?1) UPDATE dia SET calorias = sa.calorias,distancia = sa.distancia,elementos = sa.elementos,flag_descanso = sa.flag_descanso,minutos = sa.minutos FROM SA WHERE dia.semana_id= ?2 AND SA.literal=dia.literal", nativeQuery = true)
    void updateSemanaFromAnother(int semIdDesde, int semIdPara);
}
