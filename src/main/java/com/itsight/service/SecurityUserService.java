package com.itsight.service;

import com.itsight.advice.CustomValidationException;

public interface SecurityUserService {

    String recuperarPassword(String username) throws CustomValidationException;
}
