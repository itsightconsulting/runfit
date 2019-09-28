package com.itsight.service;

import com.itsight.domain.dto.TrainerQueryDTO;
import com.itsight.domain.pojo.TrainerFichaPOJO;

import java.util.List;

public interface TrainerProcedureInvoker {

    List<TrainerFichaPOJO> findAllByDynamic(TrainerQueryDTO query);


}
