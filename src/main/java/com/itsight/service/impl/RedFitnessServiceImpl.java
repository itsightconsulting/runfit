package com.itsight.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.domain.AnuncioTrainer;
import com.itsight.domain.RedFitness;
import com.itsight.domain.Trainer;
import com.itsight.domain.dto.RedFitCliDTO;
import com.itsight.domain.jsonb.Mensaje;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ChatRepository;
import com.itsight.repository.RedFitnessRepository;
import com.itsight.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itsight.util.Parseador.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.itsight.util.Enums.Msg.NOTIFICACION_RED_FIT_GENERAL;
import static com.itsight.util.Enums.Msg.NOTIFICACION_RED_FIT_PERSONAL;
import static com.itsight.util.Parseador.fromStringToDateSimple;

@Transactional
@Service
public class RedFitnessServiceImpl extends BaseServiceImpl<RedFitnessRepository> implements RedFitnessService {

    private EmailService emailService;

    private AnuncioTrainerService anuncioTrainerService;

    private AnuncioReceptorService anuncioReceptorService;

    private TrainerFichaService trainerFichaService;

    private ChatRepository chatRepository;

    @Value("${domain.name}")
    private String domainName;

    public RedFitnessServiceImpl(
            RedFitnessRepository repository,
            EmailService emailService,
            AnuncioTrainerService anuncioTrainerService,
            TrainerFichaService trainerFichaService,
            AnuncioReceptorService anuncioReceptorService,
            ChatRepository chatRepository){
        super(repository);
        this.emailService = emailService;
        this.anuncioTrainerService = anuncioTrainerService;
        this.trainerFichaService = trainerFichaService;
        this.anuncioReceptorService = anuncioReceptorService;
        this.chatRepository = chatRepository;
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
        repository.updateFlagActivoById(id, flagActivo);
    }

    /*@Override
    public List<RedFitCliDTO> listarSegunRedTrainerAndCliNom(Integer trainerId, String nombres) {
        return repository.findAllByTrainerIdAndNombreCliente(trainerId, nombres);
    }*/

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
    public Integer findTrainerIdUltimaRutinaByUsuarioId(Integer id) {
        return  repository.findTrainerIdUltimaRutinaByUsuarioId(id);
    }

    @Override
    public String enviarNotificacionPersonal(int runneId, String runneCorreo, Integer trainerId, String cuerpo) throws JsonProcessingException {
        Date now = new Date();
        Mensaje mensaje = new Mensaje();
        mensaje.setFecha(now);
        mensaje.setMsg(cuerpo);
        mensaje.setEsSalida(false);
        List<Mensaje> mensajes = new ArrayList<>();
        mensajes.add(mensaje);

        ObjectMapper objMapper = new ObjectMapper();
        String ultimo = objMapper.writeValueAsString(mensaje);
        String msgs = objMapper.writeValueAsString(mensajes);
        chatRepository.registrar(repository.findIdByRunnerIdAndTrainerId(runneId, trainerId),
                ultimo,
                msgs,
                now);
        emailService.enviarCorreoInformativo("Runfit Notificaciones", runneCorreo, cuerpo);
        return NOTIFICACION_RED_FIT_PERSONAL.get();
    }

    @Override
    public String enviarNotificacionGeneral(Integer trainerId, String asunto, String cuerpo) {
        TrainerFichaPOJO trainerFicha = trainerFichaService.findByTrainerId(trainerId);

        AnuncioTrainer anuncio = new AnuncioTrainer();
        anuncio.setFechaCreacion(new Date());
        anuncio.setTitulo(asunto);
        anuncio.setMensaje(cuerpo);
        anuncio.setNombre(trainerFicha.getNombreCompleto());
        anuncio.setImgTrainer(trainerId+"/"+trainerFicha.getNomImgPerfil());
        anuncio.setTrainer(new Trainer(trainerId));
        anuncioTrainerService.save(anuncio);
        anuncioReceptorService.guardarMultiple(anuncio.getId(), trainerId);
        emailService.enviarCorreoInformativoVariosBbc(asunto, repository.getAllRunnerMailsByTrainerId(trainerId), cuerpo);
        return NOTIFICACION_RED_FIT_GENERAL.get();
    }

    @Override
    public void actualizarFlagActivoByIdAndTrainerId(int id, Integer trainerId, boolean flag) {
        repository.updateFlagActivoByIdAndTrainerId(id, trainerId, flag);
    }

    @Override
    public List<RedFitCliDTO> findSuspendidosbyTrainerId(Integer trainerId, String mes) {

        List<RedFitCliDTO> listaSuspendidos  = repository.findClientesSuspendidosByTrainerId(trainerId,mes);

        return listaSuspendidos;
    }

    @Override
    public String getMesesCliSuspendidos(Integer trainerId) {

        String infoMesesSuspendidos  = repository.getMesesCliSuspendidos(trainerId);

        return infoMesesSuspendidos;
    }
}
