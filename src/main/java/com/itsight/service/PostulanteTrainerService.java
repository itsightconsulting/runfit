package com.itsight.service;

import com.itsight.advice.CustomValidationException;
import com.itsight.advice.SecCustomValidationException;
import com.itsight.domain.PostulanteTrainer;
import com.itsight.generic.BaseService;
import org.springframework.web.servlet.ModelAndView;

public interface PostulanteTrainerService extends BaseService<PostulanteTrainer, Integer> {

    String decidir(Integer preTrainerId, Integer decisionId) throws CustomValidationException;


    ModelAndView decidir(Integer preTrainerId, Integer decisionId, String secret) throws SecCustomValidationException;

    void updateFlagRegistradoById(Integer id, boolean flag);

    void updateFlagCuentaConfirmada(PostulanteTrainer post, boolean flag);

    Integer getTipoTrainerIdById(Integer id);
}
