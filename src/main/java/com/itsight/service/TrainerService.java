package com.itsight.service;

import com.itsight.domain.Trainer;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface TrainerService extends BaseService<Trainer, Integer> {

    String findCodigoTrainerById(Integer id);

    void actualizarFechaUltimoAcceso(Date date, String id);

    String cargarRutinarioCe(Integer trainerId);

    List<UsuarioPOJO> listarPorFiltroDto(String comodin, String estado, String fk);

}
