package com.itsight.service;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.dto.PasswordDTO;

public interface SecurityUserService {

    String recuperarPassword(String username) throws CustomValidationException;

    String cambiarPassword(PasswordDTO passwordDTO, Integer id) throws CustomValidationException;

    void updateFlagEnabled(Integer securityUserId , boolean flagEnabled);

}
