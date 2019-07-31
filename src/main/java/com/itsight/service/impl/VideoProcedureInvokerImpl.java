package com.itsight.service.impl;

import com.itsight.domain.dto.VideoQueryDTO;
import com.itsight.domain.pojo.VideoPOJO;
import com.itsight.service.VideoProcedureInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Component
public class VideoProcedureInvokerImpl implements VideoProcedureInvoker {

    private EntityManager entityManager;

    @Autowired
    public VideoProcedureInvokerImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<VideoPOJO> findAllByDynamic(VideoQueryDTO query) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_video_q_dynamic_where", "Video.getAllByDynamic");
        storedProcedureQuery.registerStoredProcedureParameter(0, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(3, Boolean.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, query.getNombre());
        storedProcedureQuery.setParameter(1, query.getCatVideoId());
        storedProcedureQuery.setParameter(2, query.getSubCatVideoId());
        storedProcedureQuery.setParameter(3, query.getFlag());
        storedProcedureQuery.setParameter(4, query.getLimit());
        storedProcedureQuery.setParameter(5, query.getOffset());
        return storedProcedureQuery.getResultList();
    }
}
