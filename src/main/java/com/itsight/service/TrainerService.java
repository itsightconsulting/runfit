package com.itsight.service;

import com.itsight.domain.Trainer;
import com.itsight.generic.BaseService;

import java.util.Date;

public interface TrainerService extends BaseService<Trainer> {

    String findCodigoTrainerById(int id);

    void actualizarFechaUltimoAcceso(Date date, String id);
}
