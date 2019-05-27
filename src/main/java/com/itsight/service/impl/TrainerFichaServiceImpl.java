package com.itsight.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.Correo;
import com.itsight.domain.TrainerFicha;
import com.itsight.domain.dto.PerfilObsDTO;
import com.itsight.domain.dto.ServicioDTO;
import com.itsight.domain.dto.TrainerFichaDTO;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.TrainerFichaRepository;
import com.itsight.service.*;
import com.itsight.util.Parseador;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.itsight.util.Enums.Mail.PERFIL_CHECK_OBS_SUBS;
import static com.itsight.util.Enums.Mail.PERFIL_POST_OBS;
import static com.itsight.util.Enums.Msg.OBS_PERFIL_TRAINER;

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
    public List<TrainerFichaPOJO> findAllWithFgEnt() {
        return repository.findAllWithFgEnt();
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
    public TrainerFichaPOJO findByTrainerId(Integer trainerId) {
        return repository.findByTrainerId(trainerId);
    }

    @Override
    public String enviarCorreoPerfilObs(PerfilObsDTO perfilObs, Integer trainerId) {
        //Obtener cuerpo del correo
        Correo correo = correoService.findOne(PERFIL_POST_OBS.get());
        //Envio de correo
        String cuerpo = String.format(correo.getBody(), perfilObs.getObs(), domainName, perfilObs.getHshTrainerId());
        emailService.enviarCorreoInformativo(correo.getAsunto(), perfilObs.getCorreo(), cuerpo);
        repository.updateFlagFichaAceptadaByTrainerId(false, trainerId);
        return OBS_PERFIL_TRAINER.get();
    }

    @Override
    public Boolean getFlagFichaAceptadaByTrainerId(Integer trainerId) {
        return repository.getFlagFichaAceptadaByTrainerId(trainerId);
    }

    @Override
    public String actualizarObservacionesPerfil(TrainerFichaDTO trainerFicha, Integer trainerId) throws JsonProcessingException {
        repository.actualizarFichaByTrainerId(trainerFicha.getSexo(), trainerFicha.getAcerca(), trainerFicha.getCentroTrabajo(), trainerFicha.getEspecialidad(), trainerFicha.getEspecialidades(), trainerFicha.getEstudios(), trainerFicha.getExperiencias(), trainerFicha.getImgExt(),  trainerFicha.getFormasTrabajo(), trainerFicha.getHorario(), trainerFicha.getIdiomas(), trainerFicha.getMetodoTrabajo(), trainerFicha.getNiveles(), trainerFicha.getNota(), trainerFicha.getRedes(), trainerFicha.getResultados(), trainerId);
        //Actualizando servicios
        if(!trainerFicha.getServicios().isEmpty()){
            for(int i=0; i<trainerFicha.getServicios().size(); i++){
                ServicioDTO s = trainerFicha.getServicios().get(i);
                servicioService.actualizarByIdAndTrainerId(s.getId(), s.getNombre(), s.getDescripcion(), s.getIncluye(), s.getInfoAdicional(), s.getTarifarios(), trainerId);
            }
        }
        String runfitCorreo = parametroService.getValorByClave("EMAIL_RECEPTOR_CONSULTAS");
        //Obtener cuerpo del correo
        Correo correo = correoService.findOne(PERFIL_CHECK_OBS_SUBS.get());
        //Envio de correo
        String cuerpo = String.format(correo.getBody(), domainName, trainerFicha.getTrainerId());//TrainerId esta como hash
        emailService.enviarCorreoInformativo(correo.getAsunto(), runfitCorreo, cuerpo);
        return Parseador.getEncodeHash32Id("rf-load-media", trainerId);
    }
}
