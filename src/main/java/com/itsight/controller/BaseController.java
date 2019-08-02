package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.advice.SecCustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.util.Parseador;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static com.itsight.util.Enums.Msg.RESOURCE_NOT_FOUND;
import static com.itsight.util.Enums.Msg.VALIDACION_FALLIDA;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;
import static com.itsight.util.Enums.ResponseCode.NOT_FOUND_MATCHES;


public abstract class BaseController {

    Integer getDecodeHashId(String schema, String hashId) throws CustomValidationException {
        Integer id = 0;

        if(hashId.length() >= 32){
            id = Parseador.getDecodeHash32Id(schema, hashId);
        }

        if(id == 0){
            throw new CustomValidationException(VALIDACION_FALLIDA.get(), EX_VALIDATION_FAILED.get());
        }
        return id;
    }

    Integer getDecodeHashIdSecCustom(String schema, String hashId) throws SecCustomValidationException {
        Integer id = 0;

        if(hashId.length() >= 32){
            id = Parseador.getDecodeHash32Id(schema, hashId);
        }

        if(id == 0){
            throw new SecCustomValidationException(RESOURCE_NOT_FOUND.get(), NOT_FOUND_MATCHES.get());
        }
        return id;
    }



    Integer getDecodeHashIdShorter(String schema, String hashId){
        Integer id = 0;

        if(hashId.length() == 32){
            id = Parseador.getDecodeHash32Id(schema, hashId);
        }
        return id;
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
