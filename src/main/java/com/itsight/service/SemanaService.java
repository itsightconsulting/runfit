package com.itsight.service;

import com.itsight.domain.Dia;
import com.itsight.domain.Semana;
import com.itsight.generic.BaseService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface SemanaService extends BaseService<Semana> {

    Semana findOneWithDaysById(int id);

    List<Semana> findByRutinaIdOrderByIdDesc(int idrutina);

    String actualizarObjetivos(int numSem, String objetivos);

    String agregarSemana(Semana semana, List<Dia> dias, int rutinaId, int totalSemanas, Date fechaFin);

    String actualizarFullMetricasVelocidad(String mVz) throws IOException;
}
