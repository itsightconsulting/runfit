package com.itsight.service.impl;

import com.itsight.domain.dto.ClienteDTO;
import com.itsight.service.ClienteProcedureInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.Date;
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

    @Override
    public void actualizarClienteById(ClienteDTO cliente, Integer clienteId) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_update_cliente_by_id");
        storedProcedureQuery.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(6, Date.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(7, Date.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(9, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(10, Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(11, String.class, ParameterMode.IN);

        storedProcedureQuery.setParameter(0, clienteId);
        storedProcedureQuery.setParameter(1, cliente.getMovil());
        storedProcedureQuery.setParameter(2, cliente.getNombres());
        storedProcedureQuery.setParameter(3, cliente.getApellidos());
        storedProcedureQuery.setParameter(4, cliente.getNumeroDocumento());
        storedProcedureQuery.setParameter(5, cliente.getPaisId());
        storedProcedureQuery.setParameter(6, new Date());//_fecha_modificacion
        storedProcedureQuery.setParameter(7, cliente.getFechaNacimiento());
        storedProcedureQuery.setParameter(8, null);
        storedProcedureQuery.setParameter(9, null);
        storedProcedureQuery.setParameter(10, cliente.getTipoDocumentoId());
        storedProcedureQuery.setParameter(11, cliente.getUbigeo());
        storedProcedureQuery.execute();
    }
}
