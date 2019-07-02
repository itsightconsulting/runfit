package com.itsight.service;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.Visitante;
import com.itsight.domain.dto.VisitanteDTO;
import org.springframework.stereotype.Service;


public interface VisitanteService {


    Visitante findOne(Integer preViId) ;

    String registrarVisitante(VisitanteDTO visitanteDTO) throws CustomValidationException;






}
