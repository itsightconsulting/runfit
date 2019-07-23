package com.itsight.repository;

import com.itsight.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

    @Query("SELECT V.nombre FROM Video V WHERE V.id = ?1 AND V.uuid = ?2")
    String findNombreByIdAndUuid(Integer id, UUID uuid);

    @Query("SELECT V.rutaWeb FROM Video V WHERE V.id = ?1")
    String findRutaWebById(Integer id);

    List<Video> findAllByOrderByIdDesc();

    @Query("SELECT new Video(E.id, E.nombre, E.rutaWeb, E.peso, E.duracion, E.uuid, E.flagActivo, E.subCatVideo.id, E.subCatVideo.nombre) FROM Video E WHERE E.id = ?1 ORDER BY 1")
    Video getById(Integer id);

    @Query("SELECT V FROM Video V INNER JOIN FETCH V.subCatVideo SC  INNER JOIN FETCH SC.categoriaVideo CV  INNER JOIN FETCH CV.grupoVideo GV INNER JOIN FETCH GV.forest ORDER BY GV.id,CV.id, SC.id, V.id")
    List<Video> findAllTree();

    @Query("SELECT new Video(E.id, E.nombre, E.rutaWeb, E.peso, E.duracion, E.uuid, E.flagActivo, E.subCatVideo.id, E.subCatVideo.nombre) FROM Video E ORDER BY 1")
    List<Video> findAllByOrderById();

    @Query("SELECT new Video(E.id, E.nombre, E.rutaWeb, E.peso, E.duracion, E.uuid, E.flagActivo, E.subCatVideo.id, E.subCatVideo.nombre) FROM Video E WHERE E.flagActivo = ?1 ORDER BY 1")
    List<Video> findAllByFlagActivoOrderById(Boolean flagActivo);

    List<Video> findAllByNombreContaining(String nombres);

    @Query("SELECT new Video(E.id, E.nombre, E.rutaWeb, E.peso, E.duracion, E.uuid, E.flagActivo, E.subCatVideo.id, E.subCatVideo.nombre) FROM Video E WHERE LOWER(E.nombre) LIKE LOWER(CONCAT('%',?1,'%')) ORDER BY 1")
    List<Video> findAllByNombreContainingIgnoreCase(String nombre);

    @Modifying
    @Query(value = "UPDATE Video E SET E.flagActivo =?2 WHERE E.id = ?1")
    void updateFlagActivoById(Integer id, boolean flagActivo);

    @Query("SELECT new Video(E.id, E.nombre, E.rutaWeb, E.peso, E.duracion, E.uuid, E.flagActivo, E.subCatVideo.id, E.subCatVideo.nombre) FROM Video E " +
            "WHERE E.subCatVideo.id = ?1 ORDER BY 1")
    List<Video> findAllBySubCategoriaVideoId(Integer categoriaEjercicioId);

    @Query("SELECT new Video(E.id, E.nombre, E.rutaWeb, E.peso, E.duracion, E.uuid, E.flagActivo, E.subCatVideo.id, E.subCatVideo.nombre) FROM Video E " +
            "WHERE E.subCatVideo.id = ?1 AND E.flagActivo = ?2 ORDER BY 1")
    List<Video> findAllBySubCategoriaVideoIdAndFlagActivoOrderById(Integer categoriaEjercicioId, Boolean flagActivo);

    @Query("SELECT new Video(E.id, E.nombre, E.rutaWeb, E.peso, E.duracion, E.uuid, E.flagActivo, E.subCatVideo.id, E.subCatVideo.nombre) FROM Video E " +
            "WHERE LOWER(E.nombre) LIKE LOWER(CONCAT('%',?1,'%')) AND E.flagActivo = ?2 ORDER BY 1")
    List<Video> findAllByNombreContainingIgnoreCaseAndFlagActivoOrderById(String comodin, Boolean flagActivo);

    @Query("SELECT new Video(E.id, E.nombre, E.rutaWeb, E.peso, E.duracion, E.uuid, E.flagActivo, E.subCatVideo.id, E.subCatVideo.nombre) FROM Video E " +
            "WHERE E.subCatVideo.id = ?1 AND LOWER(E.nombre) LIKE LOWER(CONCAT('%',?2,'%')) ORDER BY 1")
    List<Video> findAllBySubCategoriaVideoIdAndNombreContainingIgnoreCaseOrderById(Integer categoriaEjercicioId, String comodin);

    @Query("SELECT new Video(E.id, E.nombre, E.rutaWeb, E.peso, E.duracion, E.uuid, E.flagActivo, E.subCatVideo.id, E.subCatVideo.nombre) FROM Video E " +
            "WHERE E.subCatVideo.id = ?1 AND LOWER(E.nombre) LIKE LOWER(CONCAT('%',?2,'%')) AND E.flagActivo = ?3 ORDER BY 1")
    List<Video> findAllBySubCategoriaVideoIdAndNombreContainingIgnoreCaseAndFlagActivoOrderById(Integer categoriaEjercicioId, String comodin, Boolean flagActivo);

}
