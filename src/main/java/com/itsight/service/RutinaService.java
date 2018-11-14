package com.itsight.service;

import com.itsight.domain.Rutina;
import com.itsight.generic.BaseService;

import java.util.List;

public interface RutinaService extends BaseService<Rutina> {

    List<Rutina> listarPorFiltroPT(int clienteId);

    Rutina findLastByRedFitnessId(int id);

    Rutina findOneWithOneWeekById(int id);

    void updateSemanaIds(int id, int[] semanaIds);

    void updateTotalSemanas(int id, int totalSemanas);

    int obtenerRedFitnessIdById(int rutinaId);

    List<Rutina> getAllRutinasByUser(int id);

    void updateAvance(int id, int indexsemana, String strdias, String avance);

    void updateResetDiasFlagEnvio(List<Integer> id);

    void updateDiasFlagEnvio(int indexsemana, int indexdia);

}

