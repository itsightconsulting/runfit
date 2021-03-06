package com.itsight.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.advice.CustomValidationException;
import com.itsight.domain.AnuncioTrainer;
import com.itsight.domain.RedFitness;
import com.itsight.domain.Trainer;
import com.itsight.domain.dto.ChatDTO;
import com.itsight.domain.dto.RedFitCliDTO;
import com.itsight.domain.jsonb.Mensaje;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.*;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.itsight.util.Enums.Msg.NOTIFICACION_RED_FIT_GENERAL;
import static com.itsight.util.Enums.Msg.NOTIFICACION_RED_FIT_PERSONAL;
import static com.itsight.util.Enums.TipoUsuario.ENTRENADOR;
import static com.itsight.util.Utilitarios.jsonResponse;

@Transactional
@Service
public class RedFitnessServiceImpl extends BaseServiceImpl<RedFitnessRepository> implements RedFitnessService {

    private EmailService emailService;

    private AnuncioTrainerService anuncioTrainerService;

    private AnuncioReceptorService anuncioReceptorService;

    private TrainerFichaService trainerFichaService;

    private ChatRepository chatRepository;

    private ServicioRepository servicioRepository;

    private SecurityUserRepository securityUserRepository;

    private ConfiguracionClienteRepository configuracionClienteRepository;

    @Value("${domain.name}")
    private String domainName;

    public RedFitnessServiceImpl(
            RedFitnessRepository repository,
            EmailService emailService,
            AnuncioTrainerService anuncioTrainerService,
            TrainerFichaService trainerFichaService,
            AnuncioReceptorService anuncioReceptorService,
            ChatRepository chatRepository,
            ServicioRepository servicioRepository,
            SecurityUserRepository securityUserRepository,
            ConfiguracionClienteRepository configuracionClienteRepository){
        super(repository);
        this.emailService = emailService;
        this.anuncioTrainerService = anuncioTrainerService;
        this.trainerFichaService = trainerFichaService;
        this.anuncioReceptorService = anuncioReceptorService;
        this.chatRepository = chatRepository;
        this.servicioRepository = servicioRepository;
        this.securityUserRepository = securityUserRepository;
        this.configuracionClienteRepository = configuracionClienteRepository;
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
    public String enviarNotificacionPersonal(ChatDTO chat, Integer userId, Integer tipoUsuario) throws JsonProcessingException {
        Boolean isTrainer = tipoUsuario == ENTRENADOR.ordinal();
        Date now = new Date();
        Mensaje mensaje = new Mensaje();
        mensaje.setFecha(now);
        mensaje.setMsg(chat.getCuerpo());
        mensaje.setEsSalida(!isTrainer);
        List<Mensaje> mensajes = new ArrayList<>();
        mensajes.add(mensaje);

        ObjectMapper objMapper = new ObjectMapper();
        String ultimo = objMapper.writeValueAsString(mensaje);
        String msgs = objMapper.writeValueAsString(mensajes);
        chatRepository.registrar(chat.getRedFitId(),
                ultimo,
                msgs,
                now,
                chat.getFpTrainer(),
                chat.getNomTrainer());
        if(isTrainer){
            Integer sustractOrPlusNumber = 1;
            configuracionClienteRepository.updateNotificacionChatById(chat.getCliId(), sustractOrPlusNumber);
        }
        //Correo puede ser de Trainer o Cliente
        if (isTrainer) {
            emailService.enviarCorreoInformativo("Runfit Notificaciones", chat.getCorreo(), chat.getCuerpo());
        }else{
            String trainerCorreo = repository.getCorreoTrainerByRedFitnessId(chat.getRedFitId());
            emailService.enviarCorreoInformativo("Runfit Notificaciones", trainerCorreo, chat.getCuerpo());
        }
        return Utilitarios.jsonResponse(NOTIFICACION_RED_FIT_PERSONAL.get());
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

    @Override
    public String registrarNuevaRedParaClienteAntiguo(
            Integer clienteId, Integer trainerId, Integer servicioId,
            String correoTrainer, Integer fichaId, Integer ttId) throws CustomValidationException {
        //Relacionando servicio con el nuevo cliente
        if(trainerId != null && clienteId > 0){
            Boolean suserActive = securityUserRepository.findEnabledById(trainerId);
            if(suserActive == null || !suserActive ){
                throw new CustomValidationException(Enums.Msg.TRAINER_INACTIVO.get(), Enums.ResponseCode.EX_GENERIC.get());
            } else {
                //Continue with normal flow
            }
        }

        //Se valida con el exception controller, violation of composite unique key | Lo mismo para la tabla red_fitness
        servicioRepository.addClienteServicio(clienteId, servicioId);

        //Agregandolo a la red de su entrenador
        Boolean existsRf = repository.checkExistsByTrainerIdAndClienteId(trainerId, clienteId);
        if(!existsRf){
            RedFitness rf = new RedFitness(trainerId, clienteId, Utilitarios.getPeticionParaTipoRutina(fichaId.toString()));
            rf.setPredeterminadaFichaId(fichaId);
            rf.setFlagActivo(true);
            this.save(rf);

            //-- MAIL --
            if(ttId == Enums.TipoTrainer.PARA_EMPRESA.get()){
                String correoTrainerEmpresa = servicioRepository.findTrainerCorreoById(servicioId);
                emailService.enviarCorreoInformativoConUnicoCc(
                        "Nuevo cliente Runfit", correoTrainer, correoTrainerEmpresa,
                        "<h1>Tienes un nuevo cliente</h1>");
            } else {
                emailService.enviarCorreoInformativo("Nuevo cliente Runfit",
                        correoTrainer,
                        "<h1>Tienes un nuevo cliente</h1>");
            }

        } else {
            if(ttId == Enums.TipoTrainer.PARA_EMPRESA.get()){
                String correoTrainerEmpresa = servicioRepository.findTrainerCorreoById(servicioId);
                emailService.enviarCorreoInformativoConUnicoCc(
                        "Cliente contrato otro de tus servicios", correoTrainer, correoTrainerEmpresa,
                        "<h1>Un cliente antiguo tuyo ha contratado otro de tus servicios</h1>");
            } else {
                emailService.enviarCorreoInformativo("Cliente contrato otro de tus servicios",
                        correoTrainer,
                        "<h1>Un cliente antiguo tuyo ha contratado otro de tus servicios</h1>");
            }
        }

        return jsonResponse(Enums.Msg.CLIENTE_ANTIGUO_SUSC_A_NUE_SERVICIO.get());
    }
}
