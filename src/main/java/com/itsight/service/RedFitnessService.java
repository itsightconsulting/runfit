package com.itsight.service;

import com.itsight.domain.RedFitness;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface RedFitnessService extends BaseService<RedFitness> {

    List<RedFitness> listarSegunRedTrainer(String codigoTrainer);

    void updateFlagEnActividadById(int id, boolean flagEnEntrenamiento);

    void actualizarNotaAIntegrante(int parseInt, String nota);

    void updatePlanStatusAndUltimoDiaPlanificacionAndContadorRutinas(int id, int planStatus, Date ultimoDiaPlanificacion, int contadorRutinas);

    String findCodTrainerById(int redFitnessId);

    void actualizarUltimaFechaPlanificacionById(int id, Date ultimaFecha);

    RedFitness findByTrainerCodigoTrainer(String codTrainer);

    String findCodTrainerByIdAndRunnerId(int redFitId, int runneId);

    List<String> findTrainerByIdUsuario(int id);
}
