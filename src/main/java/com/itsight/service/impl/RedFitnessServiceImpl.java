package com.itsight.service.impl;

import com.itsight.domain.RedFitness;
import com.itsight.domain.dto.RedFitCliDTO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.RedFitnessRepository;
import com.itsight.service.CorreoService;
import com.itsight.service.EmailService;
import com.itsight.service.RedFitnessService;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.itsight.util.Enums.Msg.NOTIFICACION_RED_FIT_GENERAL;
import static com.itsight.util.Enums.Msg.NOTIFICACION_RED_FIT_PERSONAL;

@Transactional
@Service
public class RedFitnessServiceImpl extends BaseServiceImpl<RedFitnessRepository> implements RedFitnessService {

    private EmailService emailService;

    private CorreoService correoService;

    @Value("${domain.name}")
    private String domainName;

    public RedFitnessServiceImpl(RedFitnessRepository repository, EmailService emailService, CorreoService correoService){
        super(repository);
        this.correoService = correoService;
        this.emailService = emailService;
    }

    @Override
    public RedFitness save(RedFitness entity) {
        return repository.save(entity);
    }

    @Override
    public RedFitness update(RedFitness entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public RedFitness findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public RedFitness findOneWithFT(Integer id) {
        return repository.findOneById(id);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<RedFitness> findAll() {
        return repository.findAll();
    }

    @Override
    public List<RedFitness> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<RedFitness> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<RedFitness> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<RedFitness> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<RedFitness> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<RedFitness> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<RedFitness> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(RedFitness entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(RedFitness entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public List<RedFitCliDTO> listarSegunRedTrainerAndCliNom(Integer trainerId, String nombres) {
        return repository.findAllByTrainerIdAndNombreCliente(trainerId, nombres);
    }

    @Override
    public void actualizarNotaACliente(Integer id, String nota) {
        repository.actualizarNotaACliente(id, nota);
    }

    @Override
    public void updatePlanStatusAndUltimoDiaPlanificacionAndContadorRutinas(Integer id, int planStatus, Date fechaInicialPlanificacion, Date ultimoDiaPlanificacion, int contadorRutinas) {
        repository.updatePlanStatusAndUltimoDiaPlanificacion(id, planStatus, fechaInicialPlanificacion, ultimoDiaPlanificacion, contadorRutinas);
    }

    @Override
    public Integer findTrainerIdByIdAndRunnerId(Integer id, Integer runneId) {
        return repository.findTrainerIdByIdAndRunnerId(id, runneId);
    }

    @Override
    public List<Integer> findTrainerIdByUsuarioId(Integer id) {
        return  repository.findTrainerIdByUsuarioId(id);
    }

    @Override
    public String enviarNotificacionPersonal(int runneId, String runneCorreo, Integer trainerId, String asunto, String cuerpo) {
        emailService.enviarCorreoInformativo(asunto, runneCorreo, cuerpo);
        return NOTIFICACION_RED_FIT_PERSONAL.get();
    }

    @Override
    public String enviarNotificacionGeneral(Integer trainerId, String asunto, String cuerpo) {
        emailService.enviarCorreoInformativoVariosBbc(asunto, repository.getAllRunnerMailsByTrainerId(trainerId), cuerpo);
        return NOTIFICACION_RED_FIT_GENERAL.get();
    }
}
