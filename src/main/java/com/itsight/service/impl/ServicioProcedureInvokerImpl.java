package com.itsight.service.impl;

import com.itsight.domain.dto.TrainerQueryDTO;
import com.itsight.domain.pojo.ServicioPOJO;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.service.ServicioProcedureInvoker;
import com.itsight.service.TrainerProcedureInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
@Transactional
public class ServicioProcedureInvokerImpl implements ServicioProcedureInvoker {

    private EntityManager entityManager;

    @Autowired
    public ServicioProcedureInvokerImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<ServicioPOJO> getTopServicioTrainerPlataforma() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_get_count_servicio_top_plataforma", "resultMappingTopServiciosTrainer");
        return storedProcedureQuery.getResultList();
    }

    @Override
    public List<ServicioPOJO> getTopServiciobyTrainerId(int trainerId) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_get_count_servicio_by_trainer_id","resultMappingTopServiciosByTrainerId");
        storedProcedureQuery.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, trainerId);
        return storedProcedureQuery.getResultList();
    }


    @Override
    public List<ServicioPOJO> getTopServiciobyTrainerIdEmpresa(int trainerId) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_get_count_servicio_by_trainer_id_empresa","resultMappingTopServiciosByTrainerId");
        storedProcedureQuery.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, trainerId);
        return storedProcedureQuery.getResultList();
    }





}
