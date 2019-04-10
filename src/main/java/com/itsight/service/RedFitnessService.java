package com.itsight.service;

import com.itsight.domain.RedFitness;
import com.itsight.domain.dto.RedFitCliDTO;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface RedFitnessService extends BaseService<RedFitness, Integer> {

    List<RedFitCliDTO> listarSegunRedTrainer(Integer trainerId);

    void actualizarNotaACliente(Integer id, String nota);

    void updatePlanStatusAndUltimoDiaPlanificacionAndContadorRutinas(Integer id, int planStatus, Date ultimoDiaPlanificacion, int contadorRutinas);

    Integer findTrainerIdByIdAndRunnerId(Integer id, Integer runneId);

    List<Integer> findTrainerIdByUsuarioId(Integer id);
}
