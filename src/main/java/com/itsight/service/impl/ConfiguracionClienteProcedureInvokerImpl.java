package com.itsight.service.impl;

import com.itsight.domain.dto.ConfiguracionClienteDTO;
import com.itsight.service.ConfiguracionClienteProcedureInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.List;


@Repository
@Transactional
public class ConfiguracionClienteProcedureInvokerImpl implements ConfiguracionClienteProcedureInvoker {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ConfiguracionClienteDTO> getAllById(Integer clienteId) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_conf_cli_by_id", "ConfiguracionCliente.getAllById");
        storedProcedureQuery.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, clienteId);
        return  storedProcedureQuery.getResultList();
    }
}
