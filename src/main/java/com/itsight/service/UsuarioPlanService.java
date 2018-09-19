package com.itsight.service;

import com.itsight.domain.UsuarioPlan;

import java.util.List;

public interface UsuarioPlanService   {

    List<UsuarioPlan> listAll();

    UsuarioPlan add(UsuarioPlan usuarioPlan);

    UsuarioPlan update(UsuarioPlan usuarioPlan);

    void delete(int usuarioPlanId);

    UsuarioPlan findOneById(int id);
}
