package com.itsight.repository;

import com.itsight.domain.CategoriaEjercicio;
import com.itsight.domain.CategoriaVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaVideoRepository extends JpaRepository<CategoriaVideo, Integer> {

    @Query("SELECT new CategoriaVideo(S.id, S.nombre, S.flagActivo, S.grupoVideo.id, S.grupoVideo.nombre) FROM CategoriaVideo S WHERE S.id = ?1 ORDER BY 1")
    CategoriaVideo findById(Integer id);

    @Override
    @Query("SELECT new CategoriaVideo(S.id, S.nombre, S.flagActivo, S.grupoVideo.id, S.grupoVideo.nombre) FROM CategoriaVideo S ORDER BY 1")
    List<CategoriaVideo> findAll();

    @Query("SELECT new CategoriaVideo(S.id, S.nombre, S.flagActivo, S.grupoVideo.id, S.grupoVideo.nombre) FROM CategoriaVideo S ORDER BY 1")
    List<CategoriaVideo> findAllByOrderById();

    @Query("SELECT new CategoriaVideo(S.id, S.nombre, S.flagActivo, S.grupoVideo.id, S.grupoVideo.nombre) FROM CategoriaVideo S " +
            "WHERE S.flagActivo = ?1 ORDER BY 1")
    List<CategoriaVideo> findAllByFlagActivoOrderById(Boolean flagActivo);

    List<CategoriaVideo> findAllByNombreContaining(String nombres);

    @Query("SELECT new CategoriaVideo(S.id, S.nombre, S.flagActivo, S.grupoVideo.id, S.grupoVideo.nombre) FROM CategoriaVideo S " +
            "WHERE LOWER(S.nombre) LIKE LOWER(CONCAT('%',?1,'%')) ORDER BY 1")
    List<CategoriaVideo> findAllByNombreContainingIgnoreCase(String nombre);

    List<CategoriaVideo> findAllByNombreContainingIgnoreCaseAndFlagActivo(String comodin, Boolean flagActivo);

    @Modifying
    @Query(value = "UPDATE CategoriaVideo S SET S.flagActivo =?2 WHERE S.id = ?1")
    void updateFlagActivoById(Integer id, boolean flagActivo);

    @Query("SELECT new CategoriaVideo(S.id, S.nombre, S.flagActivo, S.grupoVideo.id, S.grupoVideo.nombre) FROM CategoriaVideo S " +
            "WHERE S.grupoVideo.id = ?1 ORDER BY 1")
    List<CategoriaVideo> findAllByCategoriaVideoId(int grupoVideoId);

    @Query("SELECT new CategoriaVideo(S.id, S.nombre, S.flagActivo, S.grupoVideo.id, S.grupoVideo.nombre) FROM CategoriaVideo S " +
            "WHERE S.grupoVideo.id = ?1 AND S.flagActivo = ?2 ORDER BY 1")
    List<CategoriaVideo> findAllByCategoriaVideoIdAndFlagActivoOrderById(int grupoVideoId, Boolean flagActivo);

    @Query("SELECT new CategoriaVideo(S.id, S.nombre, S.flagActivo, S.grupoVideo.id, S.grupoVideo.nombre) FROM CategoriaVideo S " +
            "WHERE LOWER(S.nombre) LIKE LOWER(CONCAT('%',?1,'%')) AND S.flagActivo = ?2 ORDER BY 1")
    List<CategoriaVideo> findAllByNombreContainingIgnoreCaseAndFlagActivoOrderById(String comodin, Boolean flagActivo);

    @Query("SELECT new CategoriaVideo(S.id, S.nombre, S.flagActivo, S.grupoVideo.id, S.grupoVideo.nombre) FROM CategoriaVideo S " +
            "WHERE S.grupoVideo.id = ?1 AND LOWER(S.nombre) LIKE LOWER(CONCAT('%',?2,'%')) ORDER BY 1")
    List<CategoriaVideo> findAllByCategoriaVideoIdAndNombreContainingIgnoreCaseOrderById(int grupoVideoId, String comodin);

    @Query("SELECT new CategoriaVideo(S.id, S.nombre, S.flagActivo, S.grupoVideo.id, S.grupoVideo.nombre) FROM CategoriaVideo S " +
            "WHERE S.grupoVideo.id = ?1 AND LOWER(S.nombre) LIKE LOWER(CONCAT('%',?2,'%')) AND S.flagActivo = ?3 ORDER BY 1")
    List<CategoriaVideo> findAllByCategoriaVideoIdAndNombreContainingIgnoreCaseAndFlagActivoOrderById(int grupoVideoId, String comodin, Boolean flagActivo);

    @Modifying
    @Query(value = "INSERT INTO bag_forest (id) VALUES(2)",nativeQuery = true)
    void insertArtificio();

}
