package com.itsight.service.impl;

import com.itsight.domain.dto.ClienteDTO;
import com.itsight.service.ClienteProcedureInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
@Transactional
public class ClienteProcedureInvokerImpl implements ClienteProcedureInvoker {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ClienteDTO> getDistribucionDepartamentoCliente() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_get_count_departamento_cliente", "resultMappingClienteDistribucionDepartamento");
        return  storedProcedureQuery.getResultList();
    }
}
