package com.itsight.service.impl;

import com.itsight.domain.dto.QueryParamsDTO;
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
    public List<UsuarioPOJO> findAllByNombreAndFlagActivoDynamic(String nombreCompleto, String flagActivo, QueryParamsDTO queryParams) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_users_q_dynamic_where", "allUsers");
        storedProcedureQuery.registerStoredProcedureParameter(0, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(1, Boolean.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, nombreCompleto.equals("0") ? null : nombreCompleto);
        storedProcedureQuery.setParameter(1, flagActivo.equals("-1") ? null : Boolean.valueOf(flagActivo));
        storedProcedureQuery.setParameter(2, queryParams.getLimit() == 0 ? null : queryParams.getLimit());
        storedProcedureQuery.setParameter(3, queryParams.getOffset() == 0 ? null : queryParams.getOffset());
        return storedProcedureQuery.getResultList();
    }

    @Override
    public List<UsuarioPOJO> findAllByNombreAndFlagActivoDynamicSpecific(String nombreCompleto, String flagActivo, QueryParamsDTO queryParams, Integer tipoUsuario) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_users_q_dynamic_esp_where", "allUsers");
        storedProcedureQuery.registerStoredProcedureParameter(0, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(1, Boolean.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, nombreCompleto.equals("0") ? null : nombreCompleto);
        storedProcedureQuery.setParameter(1, flagActivo.equals("-1") ? null : Boolean.valueOf(flagActivo));
        storedProcedureQuery.setParameter(2, queryParams.getLimit() == 0 ? null : queryParams.getLimit());
        storedProcedureQuery.setParameter(3, queryParams.getOffset() == 0 ? null : queryParams.getOffset());
        storedProcedureQuery.setParameter(4, tipoUsuario);

        return storedProcedureQuery.getResultList();
    }
}
