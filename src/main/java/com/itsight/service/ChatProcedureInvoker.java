package com.itsight.service;

import com.itsight.domain.pojo.ChatPOJO;

import java.util.List;

public interface ChatProcedureInvoker {

    List<ChatPOJO> getAllByClienteId(Integer id);
}
