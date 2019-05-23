package com.itsight.service;

import com.itsight.domain.Disciplina;
import com.itsight.generic.BaseService;

import java.util.List;

public interface DisciplinaService extends BaseService<Disciplina, Integer> {

    void guardarMultipleTrainerDisciplina(Integer trainerId, String disIds);

    List<String> obtenerDisciplinasByTrainerId(Integer trainerId);
}
