package com.itsight.repository;

import com.itsight.domain.DiaPlantilla;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaPlantillaRepository extends JpaRepository<DiaPlantilla, Integer> {

    @Override
    @EntityGraph("diaPlantilla")
    List<DiaPlantilla> findAll();

    @EntityGraph("diaPlantilla")
    DiaPlantilla getById(Integer id);

    @Query("SELECT D.id FROM DiaPlantilla D WHERE D.semanaPlantilla.id = ?1 ORDER BY 1")
    List<Integer> findIdBySemanaId(Integer semanaId);

    @Modifying
    @Query(value = "UPDATE dia_plantilla SET listas = jsonb_set(listas, CAST(:texto as text[]), to_jsonb(CAST(:nombre as text)), false) WHERE dia_plantilla_id = :id", nativeQuery = true)
    void updateEspecificaColumnaJsonBGenericoByQueryTextAndId(@Param("id") Integer id, @Param("nombre") String nombre, @Param(value = "texto") String texto);

    //COALESCE retorna el primer valor not null(en caso no haya ninguna lista se guarde una lista vacia)
    @Modifying
    @Query(value = "UPDATE dia_plantilla SET listas = COALESCE(listas, '[]') || CAST(:lista as jsonb) WHERE dia_plantilla_id = :id", nativeQuery = true)
    void saveListaDia(@Param("id") Integer id, @Param("lista") String lista);

    @Modifying
    @Query(value = "UPDATE dia_plantilla SET listas = jsonb_set(listas, CAST(:texto as text[]), CAST(:elemento as jsonb), true) WHERE dia_plantilla_id = :id", nativeQuery = true)
    void saveElemento(@Param("id") Integer id, @Param("texto") String texto, @Param("elemento") String elemento);

    @Modifying
    @Query(value = "UPDATE dia_plantilla SET listas = jsonb_set(listas, CAST(:texto as text[]), COALESCE(listas->:listaIx->'elementos') || CAST(:elementos as jsonb), true) WHERE dia_plantilla_id = :id", nativeQuery = true)
    void saveElementos(@Param("id") Integer id, @Param("listaIx") int listaIx ,@Param("texto") String texto, @Param("elementos") String elementos);

    @Modifying
    @Query("UPDATE DiaPlantilla D SET D.flagDescanso = ?2, D.listas = null WHERE D.id = ?1 ")
    void updateFlagDescanso(Integer id, boolean flagDescanso);

    @Modifying
    @Query(value = "UPDATE dia_plantilla SET listas = (SELECT listas-?2 FROM dia_plantilla WHERE dia_plantilla_id=?1) WHERE dia_plantilla_id=?1", nativeQuery = true)
    void deleteListaById(Integer id, int listaIndice);

    @Modifying
    @Query(value = "UPDATE dia_plantilla SET listas = (SELECT listas #- CAST(?2 as text[]) FROM dia_plantilla WHERE dia_plantilla_id=?1) WHERE dia_plantilla_id=?1", nativeQuery = true)
    void eliminarElementoById(Integer id, String texto);

}
