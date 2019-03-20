package com.itsight.repository;

import com.itsight.domain.Audio;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AudioRepository extends JpaRepository<Audio, Integer> {

    @Override
    @Query(value = "SELECT NEW Audio(id, nombre, descripcion, duracion, flagActivo, rutaWeb, A.tipoAudio.nombre) FROM Audio A")
    List<Audio> findAll();

    @EntityGraph(value = "audio.tipoaudio")
    List<Audio> findAllByFlagActivo(Boolean flagActivo);

    @EntityGraph(value = "audio.tipoaudio")
    List<Audio> findAllByNombreContaining(String nombre);

    @EntityGraph(value = "audio.tipoaudio")
    List<Audio> findAllByNombreContainingIgnoreCase(String nombre);

    @EntityGraph(value = "audio.tipoaudio")
    Audio findById(Integer id);

    @Modifying
    @Query(value = "UPDATE Audio A SET A.flagActivo =?2 WHERE A.id = ?1")
    void updateFlagActivoById(Integer id, boolean flagActivo);

    @EntityGraph(value = "audio")
    List<Audio> findAllByFlagActivoTrueOrderByIdDesc();

    @EntityGraph(value = "audio")
    List<Audio> findAllByTipoAudioIdAndFlagActivoTrueOrderByIdDesc(int tipo);

    @EntityGraph(value = "audio")
    List<Audio> findAllByNombreContainingIgnoreCaseAndFlagActivoTrueOrderByIdDesc(String comodin);

    @EntityGraph(value = "audio")
    List<Audio> findAllByTipoAudioIdAndNombreContainingIgnoreCaseAndFlagActivoTrueOrderByIdDesc(int tipo, String comodin);
}
