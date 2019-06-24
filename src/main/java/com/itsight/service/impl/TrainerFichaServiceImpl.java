package com.itsight.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.Correo;
import com.itsight.domain.TrainerFicha;
import com.itsight.domain.dto.PerfilObsDTO;
import com.itsight.domain.dto.ServicioDTO;
import com.itsight.domain.dto.TrainerDTO;
import com.itsight.domain.dto.TrainerFichaDTO;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.TrainerFichaRepository;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.itsight.util.Enums.Mail.PERFIL_CHECK_OBS_SUBS;
import static com.itsight.util.Enums.Mail.PERFIL_POST_OBS;
import static com.itsight.util.Enums.Msg.OBS_PERFIL_TRAINER;
import static com.itsight.util.Enums.TipoTrainer.EMPRESA;
import static com.itsight.util.Enums.TipoTrainer.PARA_EMPRESA;

@Service
@Transactional
public class TrainerFichaServiceImpl extends BaseServiceImpl<TrainerFichaRepository> implements TrainerFichaService {

    private EmailService emailService;

    private CorreoService correoService;

    private ParametroService parametroService;

    private ServicioService servicioService;

    @Value("${domain.name}")
    private String domainName;

    public TrainerFichaServiceImpl(TrainerFichaRepository repository,
                EmailService emailService,
                CorreoService correoService,
                ParametroService parametroService,
                ServicioService servicioService) {
        super(repository);
        this.emailService = emailService;
        this.correoService = correoService;
        this.parametroService = parametroService;
        this.servicioService = servicioService;
    }

    @Override
    public TrainerFicha save(TrainerFicha entity) {
        return null;
    }

    @Override
    public TrainerFicha update(TrainerFicha entity) {
        return null;
    }

    @Override
    public TrainerFicha findOne(Integer id) {
        return null;
    }

    @Override
    public TrainerFicha findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<TrainerFicha> findAll() {
        return repository.findAll();
    }

    @Override
    public List<TrainerFichaPOJO> findAllWithFgEnt(Integer limit, Integer offset) {
        return repository.findAllWithFgEnt(limit, offset);
    }

    @Override
    public List<TrainerFicha> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<TrainerFicha> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<TrainerFicha> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<TrainerFicha> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<TrainerFicha> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<TrainerFicha> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<TrainerFicha> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(TrainerFicha entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(TrainerFicha entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public TrainerFichaPOJO findByNomPagPar(String nomPag) {
        return repository.findByNomPagPar(nomPag);
    }

    @Override
    public TrainerFichaPOJO findByTrainerId(Integer id) {
        return repository.findByTrainerId(id);
    }

    @Override
    public String enviarCorreoPerfilObs(PerfilObsDTO perfilObs, Integer id) {
        //Obtener cuerpo del correo
        Correo correo = correoService.findOne(PERFIL_POST_OBS.get());
        //Envio de correo
        String cuerpo = String.format(correo.getBody(), perfilObs.getObs(), domainName, perfilObs.getHshTrainerId());
        emailService.enviarCorreoInformativo(correo.getAsunto(), perfilObs.getCorreo(), cuerpo);
        if(perfilObs.getTipoTrainerId() == EMPRESA.get()){
            repository.updateFlagFichaAceptadaAndFlagPermisoUpdByTrainerId(false, true, id);
            repository.updateFlagFichaAceptadaAndFlagPermisoUpdByTrEmpId(false, true, id);
        } else {
            repository.updateFlagFichaAceptadaAndFlagPermisoUpdByTrainerId(false, true, id);
        }
        return OBS_PERFIL_TRAINER.get();
    }

    @Override
    public Boolean getFlagFichaAceptadaByTrainerId(Integer id) {
        return repository.getFlagFichaAceptadaByTrainerId(id);
    }

    @Override
    public String actualizarObservacionesPerfil(TrainerFichaDTO trainerFicha, Integer id) throws JsonProcessingException {
        int tipoTrainerId = trainerFicha.getTipoTrainerId();
        repository.actualizarFichaByTrainerId(trainerFicha.getSexo(), trainerFicha.getAcerca(), trainerFicha.getCentroTrabajo(), trainerFicha.getEspecialidad(), trainerFicha.getEspecialidades(), trainerFicha.getEstudios(), trainerFicha.getExperiencias(),  trainerFicha.getFormasTrabajo(), trainerFicha.getHorario(), trainerFicha.getIdiomas(), trainerFicha.getMetodoTrabajo(), trainerFicha.getNiveles(), trainerFicha.getNota(), trainerFicha.getRedes(), trainerFicha.getResultados(), id);
        //Actualizando servicios
        if(!trainerFicha.getServicios().isEmpty()){
            for(int i=0; i<trainerFicha.getServicios().size(); i++){
                ServicioDTO s = trainerFicha.getServicios().get(i);
                servicioService.actualizarByIdAndTrainerId(s.getId(), s.getNombre(), s.getDescripcion(), s.getIncluye(), s.getInfoAdicional(), s.getTarifarios(), id);
            }
        }

        if(trainerFicha.getTipoTrainerId() != PARA_EMPRESA.get()){
            if(tipoTrainerId == EMPRESA.get()){
                repository.updateFlagFichaAceptadaAndFlagPermisoUpdByTrEmpId(false, false, id);
            }
            String runfitCorreo = parametroService.getValorByClave("EMAIL_RECEPTOR_CONSULTAS");
            repository.updateFlagPermisoUpdByTrainerId(false, id);
            //Obtener cuerpo del correo
            Correo correo = correoService.findOne(PERFIL_CHECK_OBS_SUBS.get());
            //Envio de correo
            String cuerpo = String.format(correo.getBody(), domainName, trainerFicha.getTrainerId());//TrainerId esta como hash
            emailService.enviarCorreoInformativo(correo.getAsunto(), runfitCorreo, cuerpo);
        } else {
            repository.updateFlagPermisoUpdByTrainerId(false, id);
        }
        return Parseador.getEncodeHash32Id("rf-load-media", id);
    }

    @Override
    public void actualizarStaffGaleriaByTrainerId(String staffGaleria, Integer id) {
        repository.updateStaffGaleriaByTrainerId(staffGaleria, id);
    }

    @Override
    public Integer getTrEmpIdById(Integer id) {
        return repository.getTrEmpIdById(id);
    }

    @Override
    public String obtenerCcsAndMediosPagoById(Integer trainerId) {
        return repository.getCcsAndMediosPagoById(trainerId);
    }

    @Override
    public Boolean checkNomPagExiste(String nomPag) {
        return repository.findNomPagExist(nomPag);
    }

    @Override
    public Boolean getFlagPermisoUpdByTrainerId(Integer trainerId) {
        return repository.getFlagPermisoUpdByTrainerId(trainerId);
    }

    @Override
    public Boolean getFlagPermisoUpdByNomPag(String nomPag) {
        return repository.getFlagPermisoUpdByNomPag(nomPag);
    }
}
