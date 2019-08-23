package com.itsight.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.service.RutinaPlantillaProcedureInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

@Repository
@Transactional
public class RutinaPlantillaProcedureInvokerImpl implements RutinaPlantillaProcedureInvoker {

    @Autowired
    private EntityManager entityManager;

    @Override
    public boolean eliminarRutinaPlantilla(Integer rutinaId) throws JsonProcessingException {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_delete_rutina_plantilla_by_id");
        storedProcedureQuery.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, rutinaId);
        return storedProcedureQuery.execute();
    }

}
