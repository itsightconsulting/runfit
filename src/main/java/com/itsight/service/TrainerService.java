package com.itsight.service;

import com.itsight.domain.Trainer;
import com.itsight.domain.dto.RefUploadIds;
import com.itsight.domain.dto.TrainerFichaDTO;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.generic.BaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface TrainerService extends BaseService<Trainer, Integer> {

    void actualizarFechaUltimoAcceso(Date date, String id);

    String cargarRutinarioCe(Integer trainerId);

    List<UsuarioPOJO> listarPorFiltroDto(String comodin, String estado, String fk);

    RefUploadIds registrarPostulante(TrainerFichaDTO trainerFicha);

    void actualizarFlagActivoByIdAndNotificacion(Integer id, boolean flag, String correo);
}
