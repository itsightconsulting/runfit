package com.itsight.service;

import com.itsight.domain.RedFitness;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface RedFitnessService extends BaseService<RedFitness, Integer> {

    List<RedFitness> listarSegunRedTrainer(String codigoTrainer);

    void actualizarNotaACliente(Integer id, String nota);

    void updatePlanStatusAndUltimoDiaPlanificacionAndContadorRutinas(Integer id, int planStatus, Date ultimoDiaPlanificacion, int contadorRutinas);

    String findCodTrainerByIdAndRunnerId(Integer id, Integer runneId);

    List<Integer> findTrainerIdByUsuarioId(Integer id);
}
