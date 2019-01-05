package com.itsight.service.impl;

import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.service.SecUserProcedureInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
@Transactional
public class SecUserProcedureInvokerImpl implements SecUserProcedureInvoker {

    private EntityManager entityManager;

    @Autowired
    public SecUserProcedureInvokerImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<UsuarioPOJO> findAllByNombreAndFlagActivoDynamic(String nombreCompleto, String flagActivo) {

        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_users_q_dynamic_where", "allUsers");
        storedProcedureQuery.registerStoredProcedureParameter(0, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(1, Boolean.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, nombreCompleto.equals("0") ? null : nombreCompleto);
        storedProcedureQuery.setParameter(1, flagActivo.equals("-1") ? null : Boolean.valueOf(flagActivo));
        return storedProcedureQuery.getResultList();
    }
}
