package com.itsight.service;

import com.itsight.domain.PreTrainer;
import com.itsight.generic.BaseService;

public interface PreTrainerService extends BaseService<PreTrainer, Integer> {

    String aprobarDesaprobar(Integer preTrainerId, Integer decision);
}
