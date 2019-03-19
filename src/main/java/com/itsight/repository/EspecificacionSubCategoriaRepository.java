package com.itsight.repository;

import com.itsight.domain.EspecificacionSubCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecificacionSubCategoriaRepository extends JpaRepository<EspecificacionSubCategoria, Integer> {

    @Override
    @Query("SELECT E FROM EspecificacionSubCategoria E  INNER JOIN FETCH E.subCategoriaEjercicio SC INNER JOIN FETCH SC.categoriaEjercicio CE INNER JOIN FETCH CE.forest ORDER BY E.id ASC, E.nivel ASC,SC.id ASC")
    List<EspecificacionSubCategoria> findAll();

    @Query("SELECT new EspecificacionSubCategoria(E.id, E.nombre, E.nivel, E.flagActivo, E.subCategoriaEjercicio.id, E.subCategoriaEjercicio.nombre) FROM EspecificacionSubCategoria E ORDER BY 1")
    List<EspecificacionSubCategoria> findAllByOrderById();

    @Query("SELECT new EspecificacionSubCategoria(E.id, E.nombre, E.nivel, E.flagActivo, E.subCategoriaEjercicio.id, E.subCategoriaEjercicio.nombre) FROM EspecificacionSubCategoria E WHERE E.flagActivo = ?1 ORDER BY 1")
    List<EspecificacionSubCategoria> findAllByFlagActivoOrderById(Boolean flagActivo);

    List<EspecificacionSubCategoria> findAllByNombreContaining(String nombres);

    @Query("SELECT new EspecificacionSubCategoria(E.id, E.nombre, E.nivel, E.flagActivo, E.subCategoriaEjercicio.id, E.subCategoriaEjercicio.nombre) FROM EspecificacionSubCategoria E WHERE LOWER(E.nombre) LIKE LOWER(CONCAT('%',?1,'%')) ORDER BY 1")
    List<EspecificacionSubCategoria> findAllByNombreContainingIgnoreCase(String nombre);

    List<EspecificacionSubCategoria> findAllByNombreContainingIgnoreCaseAndFlagActivo(String comodin, Boolean flagActivo);

    @Modifying
    @Query(value = "UPDATE EspecificacionSubCategoria E SET E.flagActivo =?2 WHERE E.id = ?1")
    void updateFlagActivoById(Integer id, boolean flagActivo);

    @Query("SELECT new EspecificacionSubCategoria(E.id, E.nombre, E.nivel) FROM EspecificacionSubCategoria E WHERE E.subCategoriaEjercicio.id = ?1 ORDER BY 3,2")
    List<EspecificacionSubCategoria> findBySubCategoriaId(Integer subCategoriaEjercicioId);

    @Modifying
    @Query(value = "INSERT INTO mini_plantilla (especificacion_sub_categoria_id, trainer_id) SELECT especificacion_sub_categoria_id, :id FROM especificacion_sub_categoria order by 1", nativeQuery = true)
    void registrarEspecificacionNuevoEntrenador(@Param("id") Integer id);

    @Query("SELECT new EspecificacionSubCategoria(E.id, E.nombre, E.nivel, E.flagActivo, E.subCategoriaEjercicio.id, E.subCategoriaEjercicio.nombre) FROM EspecificacionSubCategoria E " +
            "WHERE E.subCategoriaEjercicio.id = ?1 ORDER BY 1")
    List<EspecificacionSubCategoria> findAllBySubCategoriaEjercicioId(Integer categoriaEjercicioId);

    @Query("SELECT new EspecificacionSubCategoria(E.id, E.nombre, E.nivel, E.flagActivo, E.subCategoriaEjercicio.id, E.subCategoriaEjercicio.nombre) FROM EspecificacionSubCategoria E " +
            "WHERE E.subCategoriaEjercicio.id = ?1 AND E.flagActivo = ?2 ORDER BY 1")
    List<EspecificacionSubCategoria> findAllBySubCategoriaEjercicioIdAndFlagActivoOrderById(Integer categoriaEjercicioId, Boolean flagActivo);

    @Query("SELECT new EspecificacionSubCategoria(E.id, E.nombre, E.nivel, E.flagActivo, E.subCategoriaEjercicio.id, E.subCategoriaEjercicio.nombre) FROM EspecificacionSubCategoria E " +
            "WHERE LOWER(E.nombre) LIKE LOWER(CONCAT('%',?1,'%')) AND E.flagActivo = ?2 ORDER BY 1")
    List<EspecificacionSubCategoria> findAllByNombreContainingIgnoreCaseAndFlagActivoOrderById(String comodin, Boolean flagActivo);

    @Query("SELECT new EspecificacionSubCategoria(E.id, E.nombre, E.nivel, E.flagActivo, E.subCategoriaEjercicio.id, E.subCategoriaEjercicio.nombre) FROM EspecificacionSubCategoria E " +
            "WHERE E.subCategoriaEjercicio.id = ?1 AND LOWER(E.nombre) LIKE LOWER(CONCAT('%',?2,'%')) ORDER BY 1")
    List<EspecificacionSubCategoria> findAllBySubCategoriaEjercicioIdAndNombreContainingIgnoreCaseOrderById(Integer categoriaEjercicioId, String comodin);

    @Query("SELECT new EspecificacionSubCategoria(E.id, E.nombre, E.nivel, E.flagActivo, E.subCategoriaEjercicio.id, E.subCategoriaEjercicio.nombre) FROM EspecificacionSubCategoria E " +
            "WHERE E.subCategoriaEjercicio.id = ?1 AND LOWER(E.nombre) LIKE LOWER(CONCAT('%',?2,'%')) AND E.flagActivo = ?3 ORDER BY 1")
    List<EspecificacionSubCategoria> findAllBySubCategoriaEjercicioIdAndNombreContainingIgnoreCaseAndFlagActivoOrderById(Integer categoriaEjercicioId, String comodin, Boolean flagActivo);


    @Query("SELECT new EspecificacionSubCategoria(E.id, E.nombre, E.nivel, E.flagActivo, E.subCategoriaEjercicio.id, E.subCategoriaEjercicio.nombre) FROM EspecificacionSubCategoria E " +
            "WHERE E.id in ?1 ORDER BY 1")
    List<EspecificacionSubCategoria> findAllBySubCategoriaId(List<Integer> Ids);


    @Query("SELECT new EspecificacionSubCategoria(E.id, E.nombre, E.nivel, E.flagActivo, E.subCategoriaEjercicio.id, E.subCategoriaEjercicio.nombre) FROM EspecificacionSubCategoria E " +
            "WHERE E.subCategoriaEjercicio.id = ?1 ORDER BY 1")
    List<EspecificacionSubCategoria> findBySubCategoriaEjercicioId(Integer id);





    }
