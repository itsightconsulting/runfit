 package com.itsight.service;

 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.itsight.domain.dto.ClienteDTO;

 import java.util.List;

 public interface RutinaPlantillaProcedureInvoker {

   boolean eliminarRutinaPlantilla(Integer rutinaId) throws JsonProcessingException;

 }
