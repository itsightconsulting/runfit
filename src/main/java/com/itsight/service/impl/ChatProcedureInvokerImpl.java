package com.itsight.service.impl;

import com.itsight.domain.dto.ConfiguracionClienteDTO;
import com.itsight.domain.pojo.ChatPOJO;
import com.itsight.service.ChatProcedureInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
@Transactional
public class ChatProcedureInvokerImpl implements ChatProcedureInvoker {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ChatPOJO> getAllByClienteId(Integer clienteId) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_get_all_chat_by_cliente_id", "Chat.getAllByClienteId");
        storedProcedureQuery.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, clienteId);
        return  storedProcedureQuery.getResultList();
    }
}
