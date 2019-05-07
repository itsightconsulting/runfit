package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.util.Parseador;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

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

    ModelAndView getFichaApropiada(Model model, String hshTrainerId, String trainerMailDecode, String viewName){
        if(hshTrainerId != null){
            Integer trainerId = Parseador.getDecodeHash32Id("rf-public", hshTrainerId);
            if(trainerId == 0){
                return new ModelAndView(ViewConstant.P_ERROR404);
            }else{
                model.addAttribute("trainerId", trainerId);
                model.addAttribute("correoTrainer", trainerMailDecode);
            }
        }
        return new ModelAndView(viewName);
    }
}
