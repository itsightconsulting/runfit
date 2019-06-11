package com.itsight.repository;

import com.itsight.domain.AnuncioReceptor;
import com.itsight.domain.pojo.AnuncioPOJO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnuncioReceptorRepository extends JpaRepository<AnuncioReceptor, Integer> {

    @Modifying
    @Query(value = "insert into anuncio_receptor(flag_leido, anuncio_trainer_id, cliente_id) select false, ?1, cliente_id from red_fitness where trainer_id= ?2 and flag_activo=true", nativeQuery = true)
    void saveMultiple(Integer anuncioTrainerId, Integer trainerId);

    @Query(value = "select new com.itsight.domain.pojo.AnuncioPOJO(A.id, A.nombre, A.titulo, A.mensaje, A.imgTrainer, A.fechaCreacion, R.flagLeido) from AnuncioTrainer A inner join AnuncioReceptor R on A.id=R.anuncioTrainer.id where R.cliente.id = ?1 order by 1 desc")
    List<AnuncioPOJO> findAllAnuncioByClienteId(Integer clienteId);

    @Modifying
    @Query(value = "UPDATE AnuncioReceptor A SET A.flagLeido = true WHERE A.id = ?1 AND A.cliente.id = ?2")
    void updateFlagLeidoByIdAndClienteId(Integer id, Integer clienteId);

    @Modifying
    @Query(value = "UPDATE AnuncioReceptor A SET A.flagLeido = true WHERE A.cliente.id = ?1")
    void updateAllFlagLeidoByIdAndClienteId(Integer clienteId);
}
