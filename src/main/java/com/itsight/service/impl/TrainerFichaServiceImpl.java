package com.itsight.service.impl;

import com.itsight.domain.Correo;
import com.itsight.domain.TrainerFicha;
import com.itsight.domain.dto.PerfilObsDTO;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.TrainerFichaRepository;
import com.itsight.service.CorreoService;
import com.itsight.service.EmailService;
import com.itsight.service.TrainerFichaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.itsight.util.Enums.Mail.PERFIL_POST_OBS;
import static com.itsight.util.Enums.Msg.OBS_PERFIL_TRAINER;

@Service
@Transactional
public class TrainerFichaServiceImpl extends BaseServiceImpl<TrainerFichaRepository> implements TrainerFichaService {

    private EmailService emailService;

    private CorreoService correoService;

    @Value("${domain.name}")
    private String domainName;

    public TrainerFichaServiceImpl(TrainerFichaRepository repository, EmailService emailService, CorreoService correoService) {
        super(repository);
        this.emailService = emailService;
        this.correoService = correoService;
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
}
