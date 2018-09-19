package com.itsight.repository;

import com.itsight.domain.TipoAudio;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoAudioRepository extends JpaRepository<TipoAudio, Integer> {

    @Override
    @Query("SELECT NEW TipoAudio(id, nombre) FROM TipoAudio ORDER BY 1")
    List<TipoAudio> findAll();

    List<TipoAudio> findAllByFlagActivo(Boolean flagActivo);

    List<TipoAudio> findAllByNombreContaining(String nombres);

    @Query("SELECT DISTINCT T FROM TipoAudio T LEFT JOIN FETCH T.lstAudio ORDER BY 1")
    List<TipoAudio> findDistinctByOrderById();

    @Modifying
    @Query(value = "UPDATE TipoAudio A SET A.flagActivo =?2 WHERE A.id = ?1")
    void updateFlagActivoById(int id, boolean flagActivo);

    @EntityGraph(value = "tipoAudio")
    @Query("SELECT NEW TipoAudio(id, nombre, descripcion, flagActivo) FROM TipoAudio T ORDER BY 1 ASC")
    List<TipoAudio> findAllByOrderByIdDesc();

    @EntityGraph(value = "tipoAudio")
    @Query("SELECT NEW TipoAudio(id, nombre, descripcion, flagActivo) FROM TipoAudio T WHERE T.flagActivo = ?1 ORDER BY 1 ASC")
    List<TipoAudio> findAllByFlagActivoOrderByIdDesc(Boolean valueOf);

    @EntityGraph(value = "tipoAudio")
    @Query("SELECT NEW TipoAudio(id, nombre, descripcion, flagActivo) FROM TipoAudio T WHERE LOWER(T.nombre) LIKE LOWER(CONCAT('%', ?1,'%')) ORDER BY 1 ASC")
    List<TipoAudio> findAllByNombreContainingIgnoreCaseOrderByIdDesc(String comodin);

    @EntityGraph(value = "tipoAudio")
    @Query("SELECT NEW TipoAudio(id, nombre, descripcion, flagActivo) FROM TipoAudio T WHERE LOWER(T.nombre) LIKE LOWER(CONCAT('%', ?1,'%')) AND T.flagActivo = ?2 ORDER BY 1 ASC")
    List<TipoAudio> findAllByNombreContainingIgnoreCaseAndFlagActivoOrderByIdDesc(String comodin, Boolean flag);
}
