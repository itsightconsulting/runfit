package com.itsight.service;

import com.itsight.domain.Semana;
import com.itsight.generic.BaseService;

import java.util.List;

public interface SemanaService extends BaseService<Semana> {

    Semana findOneWithDaysById(int id);
    List<Semana> findByRutinaIdOrderByIdDesc(int idrutina);

    void actualizarObjetivos(int id, String objetivos);
}
