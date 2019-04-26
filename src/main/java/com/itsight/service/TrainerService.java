package com.itsight.service;

import com.itsight.domain.Trainer;
import com.itsight.domain.dto.TrainerFichaDTO;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface TrainerService extends BaseService<Trainer, Integer> {

    void actualizarFechaUltimoAcceso(Date date, String id);

    String cargarRutinarioCe(Integer trainerId);

    List<UsuarioPOJO> listarPorFiltroDto(String comodin, String estado, String fk);

    String registrarPostulante(TrainerFichaDTO trainerFicha);
}
