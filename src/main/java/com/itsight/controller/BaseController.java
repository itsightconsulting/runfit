package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.util.Parseador;

import static com.itsight.util.Enums.Msg.VALIDACION_FALLIDA;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;


public abstract class BaseController {

    Integer getDecodeHashId(String schema, String hashId) throws CustomValidationException {
        Integer trainerId = 0;

        if(hashId.length() == 32){
            trainerId = Parseador.getDecodeHash32Id(schema, hashId);
        }

        if(trainerId == 0){
            throw new CustomValidationException(VALIDACION_FALLIDA.get(), EX_VALIDATION_FAILED.get());
        }
        return trainerId;
    }

    Integer getDecodeHashIdShorter(String schema, String hashId){
        Integer trainerId = 0;

        if(hashId.length() == 32){
            trainerId = Parseador.getDecodeHash32Id(schema, hashId);
        }
        return trainerId;
    }
}
