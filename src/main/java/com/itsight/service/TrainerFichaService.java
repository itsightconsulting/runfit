package com.itsight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.TrainerFicha;
import com.itsight.domain.dto.PerfilObsDTO;
import com.itsight.domain.dto.TrainerEmpresaDTO;
import com.itsight.domain.dto.TrainerFichaDTO;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface TrainerFichaService extends BaseService<TrainerFicha, Integer> {

    List<TrainerFichaPOJO> findAllWithFgEnt(Integer limit, Integer offset);

    TrainerFichaPOJO findByNomPagPar(String nomPag);

    TrainerFichaPOJO findByTrainerId(Integer id);

    String enviarCorreoPerfilObs(PerfilObsDTO perfilObs, Integer id);

    Boolean getFlagFichaAceptadaByTrainerId(Integer id);

    String actualizarObservacionesPerfil(TrainerFichaDTO trainerFicha, Integer id) throws JsonProcessingException;

    void actualizarStaffGaleriaByTrainerId(String staffGaleria, Integer id);

    Integer getTrEmpIdById(Integer id);

    String obtenerCcsAndMediosPagoById(Integer trainerId);

    Boolean checkNomPagExiste(String nomPag);

    Boolean getFlagPermisoUpdByTrainerId(Integer trainerId);

    Boolean getFlagPermisoUpdByNomPag(String nomPag);

    String enviarFichaTrainerEmpresa(TrainerEmpresaDTO trainer, Integer trainerId);

    Integer getTotalColaboradoresById(Integer trainerId);

    String actualizarMiniGaleriaById(Integer trainerId, int length);
}
