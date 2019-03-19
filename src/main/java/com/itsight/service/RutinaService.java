package com.itsight.service;

import com.itsight.domain.Rutina;
import com.itsight.domain.dto.RutinaDTO;
import com.itsight.generic.BaseService;

import java.util.List;

public interface RutinaService extends BaseService<Rutina, Long> {

    Rutina findLastByRedFitnessId(Long redFitId);

    Rutina findOneWithOneWeekById(Long id);

    void updateSemanaIds(Long id, Long[] semanaIds);

    void updateTotalSemanas(Long id, int totalSemanas);

    Long obtenerRedFitnessIdById(Long id);

    List<Rutina> getAllRutinasByUser(Long id);

    void updateAvance(Long id, int indexsemana, String strdias, String avance);

    void updateResetDiasFlagEnvio(int anio, int mes);

    void updateDiasFlagEnvio(int indexsemana, int indexdia);

    String registrarByCascada(RutinaDTO rutinaDto, Long redFitId, Long runneId);

    String actualizarFlagActivo(boolean flagActivo);

}

