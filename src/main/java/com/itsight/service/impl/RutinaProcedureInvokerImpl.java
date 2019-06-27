package com.itsight.service.impl;

import com.itsight.domain.pojo.RuCliPOJO;
import com.itsight.service.RutinaProcedureInvoker;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

@Repository
@Transactional
public class RutinaProcedureInvokerImpl implements RutinaProcedureInvoker {

    private EntityManager entityManager;

    public RutinaProcedureInvokerImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public RuCliPOJO getLastByClienteId(Integer clienteId, Integer limit) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_rutina_q_by_cliente_id", "getLastByClienteId");
        storedProcedureQuery.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, clienteId);
        storedProcedureQuery.setParameter(1, limit);
        return (RuCliPOJO) storedProcedureQuery.getSingleResult();
    }

    @Override
    public RuCliPOJO findById(Integer id) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_rutina_q_by_id", "getLastByClienteId");
        storedProcedureQuery.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, id);
        return (RuCliPOJO) storedProcedureQuery.getSingleResult();
    }
}
