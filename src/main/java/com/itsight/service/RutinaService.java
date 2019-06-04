package com.itsight.service;

import com.itsight.domain.Rutina;
import com.itsight.domain.dto.RuTpGeneralDTO;
import com.itsight.domain.dto.RutinaDTO;
import com.itsight.generic.BaseService;

import java.util.Date;
import java.util.List;

public interface RutinaService extends BaseService<Rutina, Integer> {

    Rutina findLastByRedFitnessId(Integer redFitId);

    Rutina findOneWithOneWeekById(Integer id);

    void updateSemanaIds(Integer id, int[] semanaIds);

    void updateTotalSemanas(Integer id, int totalSemanas);

    Integer obtenerRedFitnessIdById(Integer id);

    List<Rutina> getAllRutinasByUser(Integer id);

    void updateAvance(Integer id, int indexsemana, String strdias, String avance);

    void updateResetDiasFlagEnvio(int anio, int mes);

    void updateDiasFlagEnvio(int indexsemana, int indexdia);

    String registrarByCascada(RutinaDTO rutinaDto, Integer redFitId, Integer runneId);

    String actualizarFlagActivo(boolean flagActivo);

    void updateFechaCliAccesoById(Integer id, Date fechaMax);

    Integer getMaxRutinaIdByClienteId(Integer clienteId);

    String registrarGenByCascada(RuTpGeneralDTO rutina, Integer redFitnessId, Integer clienteId);
}

