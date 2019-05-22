package com.itsight.service;

import com.itsight.domain.pojo.TrainerFichaPOJO;

import java.util.List;

public interface TrainerProcedureInvoker {

    List<TrainerFichaPOJO> findAllByNombreDynamic(String idiomas, String niveles, String nombreFull, String acerca);

}
