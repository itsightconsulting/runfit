package com.itsight.service;

import com.itsight.domain.pojo.UsuarioPOJO;

import java.util.List;

public interface SecUserProcedureInvoker {

    List<UsuarioPOJO> findAllByNombreAndFlagActivoDynamic(String nombreCompleto, String flagActivo);
}
