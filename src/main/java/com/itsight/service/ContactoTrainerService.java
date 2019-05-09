package com.itsight.service;

import com.itsight.domain.ContactoTrainer;
import com.itsight.generic.BaseService;

public interface ContactoTrainerService extends BaseService<ContactoTrainer, Integer> {
    void updateFlagLeido(boolean flag, Integer id);

    void updateFlagLeidoFueraFecha(boolean flag, Integer id);
}
