package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.Correo;
import com.itsight.domain.PostulanteTrainer;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.PostulanteTrainerRepository;
import com.itsight.service.CorreoService;
import com.itsight.service.EmailService;
import com.itsight.service.PostulanteTrainerService;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.itsight.util.Enums.Mail.POSTULACION_TRAINER;
import static com.itsight.util.Enums.Mail.POSTULANTE_TRAINER_CONFIRMAR_CORREO;
import static com.itsight.util.Enums.Msg.POSTULACION_NUEVA;
import static com.itsight.util.Enums.ResponseCode.EMPTY_RESPONSE;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;

@Service
@Transactional
public class PostulanteTrainerServiceImpl extends BaseServiceImpl<PostulanteTrainerRepository> implements PostulanteTrainerService {

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${spring.mail.username}")
    private String emitterMail;

    @Value("${domain.name}")
    private String domainName;

    private EmailService emailService;

    private CorreoService correoService;

    public PostulanteTrainerServiceImpl(PostulanteTrainerRepository repository,
                                        EmailService emailService,
                                        CorreoService correoService) {
        super(repository);
        this.emailService = emailService;
        this.correoService = correoService;
    }

    @Override
    public PostulanteTrainer save(PostulanteTrainer entity) {
        return repository.save(entity);
    }

    @Override
    public PostulanteTrainer update(PostulanteTrainer entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public PostulanteTrainer findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public PostulanteTrainer findOneWithFT(Integer id) {
        return null;
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
    public List<PostulanteTrainer> findAll() {
        return repository.findAll();
    }

    @Override
    public List<PostulanteTrainer> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<PostulanteTrainer> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<PostulanteTrainer> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<PostulanteTrainer> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<PostulanteTrainer> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<PostulanteTrainer> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<PostulanteTrainer> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(PostulanteTrainer entity, String wildcard) throws CustomValidationException {
        //Verificación correo único
        Optional<PostulanteTrainer> optPreTrainer = Optional.ofNullable(repository.findByCorreo(entity.getCorreo()));
        if(optPreTrainer.isPresent()){
            PostulanteTrainer obj = optPreTrainer.get();
            if(obj.isFlagAceptado()){
                throw new CustomValidationException(Enums.Msg.CORREO_REPETIDO.get(), EX_VALIDATION_FAILED.get());
            }

            if(obj.isFlagRechazado()){
                Date now = new Date();
                if(now.after(obj.getFechaLimiteAccion())){
                    //Obtener cuerpo del correo
                    Correo correo = correoService.findOne(POSTULACION_TRAINER.get());
                    //Envio de correo
                    String hashId = Parseador.getEncodeHash32Id("rf-request", obj.getId());
                    String cuerpo = String.format(correo.getBody(), domainName, hashId);
                    emailService.enviarCorreoInformativo(correo.getAsunto(), obj.getCorreo(), cuerpo);
                    //Actualizando el flag en esta nueva postulación
                    obj.setFlagRechazado(false);
                    obj.setIntentos(obj.getIntentos()+1);
                    repository.save(obj);
                    return Enums.Msg.POSTULACION_RE_INTENTO.get();
                } else {
                    throw new CustomValidationException(String.format(Enums.Msg.POSTULACION_BLOQUEADA.get(), obj.getFechaLimiteAccion()), EX_VALIDATION_FAILED.get());
                }
            }
            throw new CustomValidationException(Enums.Msg.POSTULACION_EN_PROCESO.get(), EX_VALIDATION_FAILED.get());
        }
        entity.setFechaCreacion(new Date());
        entity.setIntentos(1);
        repository.save(entity);

        //Obtener cuerpo del correo
        Correo correo = correoService.findOne(POSTULANTE_TRAINER_CONFIRMAR_CORREO.get());
        //Envio de correo
        String hashId = Parseador.getEncodeHash32Id("rf-request", entity.getId());
        String cuerpo = String.format(correo.getBody(), domainName, hashId);
        emailService.enviarCorreoInformativo(correo.getAsunto(), entity.getCorreo(), cuerpo);
        return POSTULACION_NUEVA.get();
    }

    @Override
    public String actualizar(PostulanteTrainer entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public String decidir(Integer preTrainerId, Integer decisionId, String secret) throws CustomValidationException{
        PostulanteTrainer preTrainer = findOne(preTrainerId);
        if(preTrainer == null || !secret.equals(preTrainer.getSchema())){
           return EMPTY_RESPONSE.get();
        }

        if(!preTrainer.isFlagCuentaConfirmada()){
            throw new CustomValidationException(Enums.Msg.POSTULANTE_MAIL_SC.get(), EX_VALIDATION_FAILED.get());
        }

        if(preTrainer.isFlagAceptado()){
            throw new CustomValidationException(Enums.Msg.POSTULANTE_ACEPTADO_ANT.get(), EX_VALIDATION_FAILED.get());
        }

        if(preTrainer.isFlagRechazado() && new Date().before(preTrainer.getFechaLimiteAccion())){
            throw new CustomValidationException(Enums.Msg.POSTULANTE_RECH_PV.get(), EX_VALIDATION_FAILED.get());
        }

        Date timestamp = new Date();
        if(decisionId == 1){
            preTrainer.setFechaAprobacion(timestamp);
            //preTrainer.setFechaLimiteAccion(Date.from(Instant.now().plusSeconds(10)));
            preTrainer.setFechaLimiteAccion(Date.from(Instant.now().plus(14, ChronoUnit.DAYS)));
            preTrainer.setFlagAceptado(true);
        }else {
            preTrainer.setFechaRechazo(timestamp);
            preTrainer.setFechaLimiteAccion(Date.from(Instant.now().plus(90, ChronoUnit.DAYS)));
            //preTrainer.setFechaLimiteAccion(Date.from(Instant.now().plusSeconds(30)));
            preTrainer.setFlagRechazado(true);
        }
        //Save
        repository.save(preTrainer);

        //Receptor
        String receptor = preTrainer.getCorreo();

        //Envio de correo
        String hshId = Parseador.getEncodeHash32Id("rf-request", preTrainer.getId());
        if(decisionId == 1) {
            Integer tipoTrainerId = preTrainer.getTipoTrainerId();
            String rutaSegunTipoTrainer;
            if(tipoTrainerId == Enums.TipoTrainer.PARTICULAR.get()){
                rutaSegunTipoTrainer = "trainer";
            }else{
                rutaSegunTipoTrainer = "empresa";
            }
            Correo mail = correoService.findOne(4);
            emailService.enviarCorreoInformativo(mail.getAsunto(), receptor,
                    String.format(mail.getBody(), domainName, rutaSegunTipoTrainer, hshId));
        } else{
            Correo mail = correoService.findOne(5);
            emailService.enviarCorreoInformativo(mail.getAsunto(),
                    receptor,
                    mail.getBody());
        }
        return decisionId == 1 ? Enums.Msg.POSTULANTE_ACEP.get() : Enums.Msg.POSTULANTE_RECH.get();
    }

    @Override
    public void updateFlagRegistradoById(Integer id, boolean flag) {
        repository.updateFlagRegistradoById(id, flag);
    }

    @Override
    public void updateFlagCuentaConfirmada(Integer id, boolean flag, String receptor) {
        String secret = Utilitarios.getRandomString(10);
        repository.updateFlagCuentaConfirmadaAndSecret(id, flag, secret);
        //Obtener cuerpo del correo
        Correo correo = correoService.findOne(POSTULACION_TRAINER.get());
        //Envio de correo
        String hashId = Parseador.getEncodeHash32Id("rf-request", id);
        String cuerpo = String.format(correo.getBody(), domainName, hashId, secret);
        emailService.enviarCorreoInformativo(correo.getAsunto(), receptor, cuerpo);
    }

    @Override
    public Integer getTipoTrainerIdById(Integer id) {
        return repository.getTipoTrainerIdById(id);
    }
}
