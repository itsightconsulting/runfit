package com.itsight.repository;

import com.itsight.domain.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {

    @Modifying
    @Query(value = "INSERT INTO trainer_disciplina SELECT ?1, CAST(unnest(string_to_array(?2, '|')) as int)", nativeQuery = true)
    void saveMultipleTrainerDisciplina(Integer trainerId, String disIds);

    @Query(value = "select concat_ws(',', td.disciplina_id, nombre)  from trainer_disciplina td " +
            "INNER JOIN disciplina d ON d.disciplina_id=td.disciplina_id " +
            "where security_user_id = ?1", nativeQuery = true)
    List<String> getDisciplinasByTrainerId(Integer trainerId);
}
