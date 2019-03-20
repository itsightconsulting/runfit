package com.itsight.repository;

import com.itsight.domain.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {


    @Modifying
    @Query(value = "UPDATE Post SET detalle = COALESCE(detalle, '[]') || CAST(?2 as jsonb) WHERE post_id = ?1", nativeQuery = true)
    void updatePostDetalle(Integer id, String nDetalle);

    @Query("FROM Post P WHERE P.trainer.id = ?1")
    List<Post> findAllByTrainerId(Integer trainerId);

    @EntityGraph(value = "post.trainer")
    @Query("FROM Post P WHERE P.trainer.id IN (?1)")
    List<Post> findAllByTrainerIdIn(List<Integer> lstTrainerId);

    @Query(value = "SELECT TRUE FROM (SELECT CAST(jsonb_array_elements(detalle)->>'cliId' AS INT) cid FROM post WHERE post_id = ?1) tt WHERE tt.cid=?2", nativeQuery = true)
    Optional<Boolean> checkLikeExists(Integer id, Integer clienteId);

    @Modifying
    @Query(value = "UPDATE post SET detalle = jsonb_set(detalle, (SELECT " +
                   "CAST(concat('{',pos-1,',',?4,'}') AS TEXT[]) " +
                   "FROM " +
                       "post, " +
                       "jsonb_array_elements(detalle) WITH ORDINALITY arr(elem, pos) " +
                   "WHERE " +
                   "post_id = ?1 AND " +
                   "CAST(elem->>'cliId' AS INT) = ?2), CAST(CAST(?3 as text) as jsonb)) " +
                   "WHERE post_id = ?1", nativeQuery = true)
    void updateGenericBooleanColumnByIdAndClienteId(Integer id, Integer clienteId, boolean flag, String camp);

    @Query(value = "SELECT * FROM post " +
            "where post_id = ANY " +
            "(" +
            "SELECT unnest(CAST(string_to_array(" +
            "(SELECT parametros->4->>'valor' from configuracion_cliente where cliente_id = ?1)," +
            "','" +
            ")AS int[])" +
            ")" +
            ")", nativeQuery = true)
    List<Post> getPostFavoritos(Integer clienteId);

    @Query(value = "select update_post_detalle_flag(?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    Boolean actualizarPostDetalleFlag(Integer id, Integer cliId, boolean flag, String flagName, String fecMod);
}
