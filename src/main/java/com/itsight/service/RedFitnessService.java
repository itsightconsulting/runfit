package com.itsight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.RedFitness;
import com.itsight.domain.dto.ChatDTO;
import com.itsight.domain.dto.RedFitCliDTO;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface RedFitnessService extends BaseService<RedFitness, Integer> {

    /*List<RedFitCliDTO> listarSegunRedTrainerAndCliNom(Integer trainerId, String nombres);*/

    void actualizarNotaACliente(Integer id, String nota);

    void updatePlanStatusAndUltimoDiaPlanificacionAndContadorRutinas(Integer id, int planStatus, Date fechaInicialPlanificacion, Date ultimoDiaPlanificacion, int contadorRutinas);

    Integer findTrainerIdByIdAndRunnerId(Integer id, Integer runneId);

    List<Integer> findTrainerIdByUsuarioId(Integer id);

    String enviarNotificacionPersonal(ChatDTO chat, Integer trainerId) throws JsonProcessingException;

    String enviarNotificacionGeneral(Integer trainerId, String asunto, String cuerpo);

    void actualizarFlagActivoByIdAndTrainerId(int id, Integer trainerId, boolean flag);

    List<RedFitCliDTO> findSuspendidosbyTrainerId(Integer trainerId,String mes);

    String getMesesCliSuspendidos(Integer trainerId);

    Integer findTrainerIdUltimaRutinaByUsuarioId(Integer trainerId);

    String registrarNuevaRedParaClienteAntiguo(Integer clienteId,
                                               Integer trainerId,
                                               Integer servicioId,
                                               String correoTrainer,
                                               Integer fichaId,
                                               Integer ttId);
}
