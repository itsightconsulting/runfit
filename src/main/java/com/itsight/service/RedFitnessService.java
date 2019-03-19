package com.itsight.service;

import com.itsight.domain.RedFitness;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface RedFitnessService extends BaseService<RedFitness, Long> {

    List<RedFitness> listarSegunRedTrainer(String codigoTrainer);

    void actualizarNotaACliente(Long id, String nota);

    void updatePlanStatusAndUltimoDiaPlanificacionAndContadorRutinas(Long id, int planStatus, Date ultimoDiaPlanificacion, int contadorRutinas);

    String findCodTrainerByIdAndRunnerId(Integer id, Integer runneId);

    List<Integer> findTrainerIdByUsuarioId(Long id);
}
