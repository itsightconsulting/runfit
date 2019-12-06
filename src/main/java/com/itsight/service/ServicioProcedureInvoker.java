package com.itsight.service;

import com.itsight.domain.dto.TrainerQueryDTO;
import com.itsight.domain.pojo.ServicioPOJO;
import com.itsight.domain.pojo.TrainerFichaPOJO;

import java.util.List;

public interface ServicioProcedureInvoker {

     List<ServicioPOJO> getTopServicioTrainerPlataforma();

    List<ServicioPOJO> getTopServiciobyTrainerId(int trainerId);

    List<ServicioPOJO> getTopServiciobyTrainerIdEmpresa(int trainerId);

}
