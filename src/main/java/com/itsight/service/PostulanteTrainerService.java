package com.itsight.service;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.PostulanteTrainer;
import com.itsight.generic.BaseService;

public interface PostulanteTrainerService extends BaseService<PostulanteTrainer, Integer> {
    String decidir(Integer preTrainerId, Integer decisionId) throws CustomValidationException;

    void updateFlagRegistradoById(Integer id, boolean flag);

    void updateFlagCuentaConfirmada(Integer id, boolean flag, String receptor);

    Integer getTipoTrainerIdById(Integer id);
}
