package com.itsight.service;

import com.itsight.domain.Chat;
import com.itsight.generic.BaseService;

public interface ChatService extends BaseService<Chat, Integer> {

    boolean checkFlagLeidoById(Integer id);

    String updateFlagById(Integer id);
}
