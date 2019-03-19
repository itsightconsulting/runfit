package com.itsight.repository;

import com.itsight.domain.SubCategoriaEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoriaEjercicioRepository extends JpaRepository<SubCategoriaEjercicio, Integer> {

    @Query("SELECT new SubCategoriaEjercicio(S.id, S.nombre, S.flagActivo, S.categoriaEjercicio.id, S.categoriaEjercicio.nombre) FROM SubCategoriaEjercicio S WHERE S.id = ?1 ORDER BY 1")
    SubCategoriaEjercicio findById(Integer id);

    @Override
    @Query("SELECT new SubCategoriaEjercicio(S.id, S.nombre, S.flagActivo, S.categoriaEjercicio.id, S.categoriaEjercicio.nombre) FROM SubCategoriaEjercicio S ORDER BY 1")
    List<SubCategoriaEjercicio> findAll();

    @Query("SELECT new SubCategoriaEjercicio(S.id, S.nombre, S.flagActivo, S.categoriaEjercicio.id, S.categoriaEjercicio.nombre) FROM SubCategoriaEjercicio S ORDER BY 1")
    List<SubCategoriaEjercicio> findAllByOrderById();

    @Query("SELECT new SubCategoriaEjercicio(S.id, S.nombre, S.flagActivo, S.categoriaEjercicio.id, S.categoriaEjercicio.nombre) FROM SubCategoriaEjercicio S " +
            "WHERE S.flagActivo = ?1 ORDER BY 1")
    List<SubCategoriaEjercicio> findAllByFlagActivoOrderById(Boolean flagActivo);

    List<SubCategoriaEjercicio> findAllByNombreContaining(String nombres);

    @Query("SELECT new SubCategoriaEjercicio(S.id, S.nombre, S.flagActivo, S.categoriaEjercicio.id, S.categoriaEjercicio.nombre) FROM SubCategoriaEjercicio S " +
            "WHERE LOWER(S.nombre) LIKE LOWER(CONCAT('%',?1,'%')) ORDER BY 1")
    List<SubCategoriaEjercicio> findAllByNombreContainingIgnoreCase(String nombre);

    List<SubCategoriaEjercicio> findAllByNombreContainingIgnoreCaseAndFlagActivo(String comodin, Boolean flagActivo);

    @Modifying
    @Query(value = "UPDATE SubCategoriaEjercicio S SET S.flagActivo =?2 WHERE S.id = ?1")
    void updateFlagActivoById(Integer id, boolean flagActivo);

    @Modifying
    @Query(value = "INSERT INTO bag_forest (id) VALUES(1)",nativeQuery = true)
    void insertArtificio();

    @Query("SELECT new SubCategoriaEjercicio(S.id, S.nombre, S.flagActivo, S.categoriaEjercicio.id, S.categoriaEjercicio.nombre) FROM SubCategoriaEjercicio S " +
            "WHERE S.categoriaEjercicio.id = ?1 ORDER BY 1")
    List<SubCategoriaEjercicio> findAllByCategoriaEjercicioId(Integer categoriaEjercicioId);

    @Query("SELECT new SubCategoriaEjercicio(S.id, S.nombre, S.flagActivo, S.categoriaEjercicio.id, S.categoriaEjercicio.nombre) FROM SubCategoriaEjercicio S " +
            "WHERE S.categoriaEjercicio.id = ?1 AND S.flagActivo = ?2 ORDER BY 1")
    List<SubCategoriaEjercicio> findAllByCategoriaEjercicioIdAndFlagActivoOrderById(Integer categoriaEjercicioId, Boolean flagActivo);

    @Query("SELECT new SubCategoriaEjercicio(S.id, S.nombre, S.flagActivo, S.categoriaEjercicio.id, S.categoriaEjercicio.nombre) FROM SubCategoriaEjercicio S " +
            "WHERE LOWER(S.nombre) LIKE LOWER(CONCAT('%',?1,'%')) AND S.flagActivo = ?2 ORDER BY 1")
    List<SubCategoriaEjercicio> findAllByNombreContainingIgnoreCaseAndFlagActivoOrderById(String comodin, Boolean flagActivo);

    @Query("SELECT new SubCategoriaEjercicio(S.id, S.nombre, S.flagActivo, S.categoriaEjercicio.id, S.categoriaEjercicio.nombre) FROM SubCategoriaEjercicio S " +
            "WHERE S.categoriaEjercicio.id = ?1 AND LOWER(S.nombre) LIKE LOWER(CONCAT('%',?2,'%')) ORDER BY 1")
    List<SubCategoriaEjercicio> findAllByCategoriaEjercicioIdAndNombreContainingIgnoreCaseOrderById(Integer categoriaEjercicioId, String comodin);

    @Query("SELECT new SubCategoriaEjercicio(S.id, S.nombre, S.flagActivo, S.categoriaEjercicio.id, S.categoriaEjercicio.nombre) FROM SubCategoriaEjercicio S " +
            "WHERE S.categoriaEjercicio.id = ?1 AND LOWER(S.nombre) LIKE LOWER(CONCAT('%',?2,'%')) AND S.flagActivo = ?3 ORDER BY 1")
    List<SubCategoriaEjercicio> findAllByCategoriaEjercicioIdAndNombreContainingIgnoreCaseAndFlagActivoOrderById(Integer categoriaEjercicioId, String comodin, Boolean flagActivo);
}
