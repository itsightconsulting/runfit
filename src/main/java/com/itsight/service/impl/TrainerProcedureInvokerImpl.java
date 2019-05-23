package com.itsight.service.impl;

import com.itsight.domain.dto.TrainerQueryDTO;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.domain.pojo.UsuarioPOJO;
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
public class TrainerProcedureInvokerImpl implements TrainerProcedureInvoker {

    private EntityManager entityManager;

    @Autowired
    public TrainerProcedureInvokerImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<TrainerFichaPOJO> findAllByDynamic(TrainerQueryDTO query) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_trainers_q_dynamic_where", "getAllByDemo");
        storedProcedureQuery.registerStoredProcedureParameter(0, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, query.getIdiomas());
        storedProcedureQuery.setParameter(1, query.getNiveles());
        storedProcedureQuery.setParameter(2, query.getFormasTrabajo());
        storedProcedureQuery.setParameter(3, query.getNombres());
        storedProcedureQuery.setParameter(4, query.getAcerca());
        storedProcedureQuery.setParameter(5, query.getSexo());
        storedProcedureQuery.setParameter(6, query.getUbigeo());
        return storedProcedureQuery.getResultList();
    }
}
