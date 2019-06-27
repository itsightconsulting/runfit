package com.itsight.service;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.Dia;
import com.itsight.domain.Semana;
import com.itsight.generic.BaseService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface SemanaService extends BaseService<Semana, Integer> {

    Semana findOneWithDaysById(Integer id);

    List<Semana> findByRutinaIdOrderByIdDesc(Integer rutinaId);

    String actualizarObjetivos(int numSem, String objetivos);

    String agregarSemana(Semana semana, List<Dia> dias, Integer rutinaId, int totalSemanas, Date fechaFin);

    String actualizarFullMetricasVelocidad(String mVz) throws IOException;

    List<Semana> findByRutinaIdOrderByIdDesc(Integer rutinaId, int semanaIx);

    Semana findOneWithDaysEspById(Integer rutinaId, int semanaIx) throws CustomValidationException;
}
