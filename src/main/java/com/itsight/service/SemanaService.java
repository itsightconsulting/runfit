package com.itsight.service;

import com.itsight.domain.Semana;
import com.itsight.generic.BaseService;

public interface SemanaService extends BaseService<Semana> {

    Semana findOneWithDaysById(int id);

}
