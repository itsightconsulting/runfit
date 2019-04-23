package com.itsight.service;

import com.itsight.domain.PostulanteTrainer;
import com.itsight.generic.BaseService;

public interface PostulanteTrainerService extends BaseService<PostulanteTrainer, Integer> {
    String decidir(Integer preTrainerId, Integer decisionId);

    void updateFlagRegistradoById(Integer id, boolean flag);
}
