package com.itsight.service.impl;

import com.itsight.domain.dto.QueryParamsDTO;
import com.itsight.domain.dto.RedFitCliDTO;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.service.RedFitnessProcedureInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
@Transactional
public class RedFitnessProcedureInvokerImpl implements RedFitnessProcedureInvoker {

    private EntityManager entityManager;

    @Autowired
    public RedFitnessProcedureInvokerImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<RedFitCliDTO> findAllByNombreDynamic(Integer trainerId, String nombreCompleto, QueryParamsDTO queryParams) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_red_fitness_q_dynamic_where", "allRedFitnessByTrainerId");
        storedProcedureQuery.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, trainerId);
        storedProcedureQuery.setParameter(1, nombreCompleto.equals("0") ? null : nombreCompleto);
        storedProcedureQuery.setParameter(2, queryParams.getLimit() == 0 ? null : queryParams.getLimit());
        storedProcedureQuery.setParameter(3, queryParams.getOffset() == 0 ? null : queryParams.getOffset());
        return storedProcedureQuery.getResultList();
    }



}
