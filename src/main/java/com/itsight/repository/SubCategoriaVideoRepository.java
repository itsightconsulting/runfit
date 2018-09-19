package com.itsight.repository;

import com.itsight.domain.SubCategoriaVideo;
import com.itsight.domain.SubCategoriaVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoriaVideoRepository extends JpaRepository<SubCategoriaVideo, Integer> {

    List<SubCategoriaVideo> findAll();

    @Query("SELECT new SubCategoriaVideo(E.id, E.nombre, E.flagActivo, E.categoriaVideo.id, E.categoriaVideo.nombre) FROM SubCategoriaVideo E ORDER BY 1")
    List<SubCategoriaVideo> findAllByOrderById();

    @Query("SELECT new SubCategoriaVideo(E.id, E.nombre, E.flagActivo, E.categoriaVideo.id, E.categoriaVideo.nombre) FROM SubCategoriaVideo E WHERE E.flagActivo = ?1 ORDER BY 1")
    List<SubCategoriaVideo> findAllByFlagActivoOrderById(Boolean flagActivo);

    List<SubCategoriaVideo> findAllByNombreContaining(String nombres);

    @Query("SELECT new SubCategoriaVideo(E.id, E.nombre, E.flagActivo, E.categoriaVideo.id, E.categoriaVideo.nombre) FROM SubCategoriaVideo E WHERE LOWER(E.nombre) LIKE LOWER(CONCAT('%',?1,'%')) ORDER BY 1")
    List<SubCategoriaVideo> findAllByNombreContainingIgnoreCase(String nombre);

    List<SubCategoriaVideo> findAllByNombreContainingIgnoreCaseAndFlagActivo(String comodin, Boolean flagActivo);

    @Modifying
    @Query(value = "UPDATE SubCategoriaVideo E SET E.flagActivo =?2 WHERE E.id = ?1")
    void updateFlagActivoById(int id, boolean flagActivo);

    @Query("SELECT new SubCategoriaVideo(E.id, E.nombre) FROM SubCategoriaVideo E WHERE E.categoriaVideo.id = ?1 ORDER BY 1")
    List<SubCategoriaVideo> findByCategoriaId(int categoriaId);

    @Modifying
    @Query(value = "INSERT INTO mini_plantilla (especificacion_sub_categoria_id, usuario_id) SELECT especificacion_sub_categoria_id, :id FROM especificacion_sub_categoria order by 1", nativeQuery = true)
    void registrarEspecificacionNuevoEntrenador(@Param("id") int id);

    @Query("SELECT new SubCategoriaVideo(E.id, E.nombre, E.flagActivo, E.categoriaVideo.id, E.categoriaVideo.nombre) FROM SubCategoriaVideo E " +
            "WHERE E.categoriaVideo.id = ?1 ORDER BY 1")
    List<SubCategoriaVideo> findAllByCategoriaVideoId(int categoriaVideoId);

    @Query("SELECT new SubCategoriaVideo(E.id, E.nombre, E.flagActivo, E.categoriaVideo.id, E.categoriaVideo.nombre) FROM SubCategoriaVideo E " +
            "WHERE E.categoriaVideo.id = ?1 AND E.flagActivo = ?2 ORDER BY 1")
    List<SubCategoriaVideo> findAllByCategoriaVideoIdAndFlagActivoOrderById(int categoriaVideoId, Boolean flagActivo);

    @Query("SELECT new SubCategoriaVideo(E.id, E.nombre, E.flagActivo, E.categoriaVideo.id, E.categoriaVideo.nombre) FROM SubCategoriaVideo E " +
            "WHERE LOWER(E.nombre) LIKE LOWER(CONCAT('%',?1,'%')) AND E.flagActivo = ?2 ORDER BY 1")
    List<SubCategoriaVideo> findAllByNombreContainingIgnoreCaseAndFlagActivoOrderById(String comodin, Boolean flagActivo);

    @Query("SELECT new SubCategoriaVideo(E.id, E.nombre, E.flagActivo, E.categoriaVideo.id, E.categoriaVideo.nombre) FROM SubCategoriaVideo E " +
            "WHERE E.categoriaVideo.id = ?1 AND LOWER(E.nombre) LIKE LOWER(CONCAT('%',?2,'%')) ORDER BY 1")
    List<SubCategoriaVideo> findAllByCategoriaVideoIdAndNombreContainingIgnoreCaseOrderById(int categoriaVideoId, String comodin);

    @Query("SELECT new SubCategoriaVideo(E.id, E.nombre, E.flagActivo, E.categoriaVideo.id, E.categoriaVideo.nombre) FROM SubCategoriaVideo E " +
            "WHERE E.categoriaVideo.id = ?1 AND LOWER(E.nombre) LIKE LOWER(CONCAT('%',?2,'%')) AND E.flagActivo = ?3 ORDER BY 1")
    List<SubCategoriaVideo> findAllByCategoriaVideoIdAndNombreContainingIgnoreCaseAndFlagActivoOrderById(int categoriaVideoId, String comodin, Boolean flagActivo);
}
