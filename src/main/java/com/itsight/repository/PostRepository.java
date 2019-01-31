package com.itsight.repository;

import com.itsight.domain.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {


    @Modifying
    @Query(value = "UPDATE Post SET detalle = COALESCE(detalle, '[]') || CAST(?2 as jsonb) WHERE post_id = ?1", nativeQuery = true)
    void updatePostDetalle(int id, String nDetalle);

    @Query("FROM Post P WHERE P.trainer.id = ?1")
    List<Post> findAllByTrainerId(int trainerId);

    @EntityGraph(value = "post.trainer")
    @Query("FROM Post P WHERE P.trainer.id IN (?1)")
    List<Post> findAllByTrainerIdIn(List<Integer> lstTrainerId);

    @Query(value = "SELECT TRUE FROM (SELECT CAST(jsonb_array_elements(detalle)->>'cliId' AS INT) cid FROM post WHERE post_id = ?1) tt WHERE tt.cid=?2", nativeQuery = true)
    Optional<Boolean> checkLikeExists(int id, int clienteId);

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
    void updateGenericBooleanColumnByIdAndClienteId(int id, int clienteId, boolean flag, String camp);

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
}
