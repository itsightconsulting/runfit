package com.itsight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.TrainerFicha;
import com.itsight.domain.dto.PerfilObsDTO;
import com.itsight.domain.dto.TrainerDTO;
import com.itsight.domain.dto.TrainerFichaDTO;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface TrainerFichaService extends BaseService<TrainerFicha, Integer> {

    List<TrainerFichaPOJO> findAllWithFgEnt();

    TrainerFichaPOJO findByNomPagPar(String nomPag);

    TrainerFichaPOJO findByTrainerId(Integer id);

    String enviarCorreoPerfilObs(PerfilObsDTO perfilObs, Integer id);

    Boolean getFlagFichaAceptadaByTrainerId(Integer id);

    String actualizarObservacionesPerfil(TrainerFichaDTO trainerFicha, Integer id) throws JsonProcessingException;

    void actualizarStaffGaleriaByTrainerId(String staffGaleria, Integer id);

    Integer getTrEmpIdById(Integer id);
}
