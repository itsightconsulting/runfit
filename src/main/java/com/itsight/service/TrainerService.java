package com.itsight.service;

import com.itsight.domain.Trainer;
import com.itsight.domain.dto.AprobacionDTO;
import com.itsight.domain.dto.RefUploadIds;
import com.itsight.domain.dto.TrainerDTO;
import com.itsight.domain.dto.UsuGenDTO;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.generic.BaseService;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

public interface TrainerService extends BaseService<Trainer, Integer> {

    void actualizarFechaUltimoAcceso(Date date, Integer id);

    String cargarRutinarioCe(Integer trainerId);

    List<UsuarioPOJO> listarPorFiltroDto(String comodin, String estado, String fk);

    RefUploadIds registrarPostulante(TrainerDTO trainerFicha, int tipoTrainerId, Integer trEmpId);

    void actualizarFlagActivoByIdAndNotificacion(Integer id, boolean flag, String correo, Integer ttId);

    ModelAndView actualizarFlagActivoByIdAndNotificacionSec(AprobacionDTO aprobacionDTO, Integer id, boolean flag);

    Integer getTipoTrainerIdById(Integer id);

    String getCorreoById(Integer id);

    UsuGenDTO obtenerById(int id);

    String getUsernameById(int id);

    UsuGenDTO getForCookieById(Integer id);

    String getAllDistinctNomUbigeoAsString();
}
