package com.itsight.service;

import com.itsight.domain.dto.QueryParamsDTO;
import com.itsight.domain.pojo.UsuarioPOJO;

import java.util.List;

public interface SecUserProcedureInvoker {

    List<UsuarioPOJO> findAllByNombreAndFlagActivoDynamic(String nombreCompleto, String flagActivo, QueryParamsDTO queryParams);

    List<UsuarioPOJO> findAllByNombreAndFlagActivoDynamicSpecific(String nombreCompleto, String flagActivo, QueryParamsDTO queryParams, Integer tipoUsuario);
}
