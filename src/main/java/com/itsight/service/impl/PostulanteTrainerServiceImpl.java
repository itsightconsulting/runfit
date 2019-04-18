package com.itsight.service.impl;

import com.itsight.domain.Correo;
import com.itsight.domain.PostulanteTrainer;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.PostulanteTrainerRepository;
import com.itsight.service.CorreoService;
import com.itsight.service.EmailService;
import com.itsight.service.PostulanteTrainerService;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.itsight.util.Enums.Mail.POSTULACION_TRAINER;

@Service
@Transactional
public class PostulanteTrainerServiceImpl extends BaseServiceImpl<PostulanteTrainerRepository> implements PostulanteTrainerService {

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${spring.mail.username}")
    private String emitterMail;

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
    public String registrar(PostulanteTrainer entity, String wildcard) {
        //Verificación correo único
        Optional<PostulanteTrainer> optPreTrainer = Optional.ofNullable(repository.findByCorreo(entity.getCorreo()));
        if(optPreTrainer.isPresent()){
            PostulanteTrainer obj = optPreTrainer.get();
            if(obj.isFlagAceptado()){
                return "-11";
            }

            if(obj.isFlagRechazado()){
                Date now = new Date();
                if(now.after(obj.getFechaLimiteAccion())){
                    //Obtener cuerpo del correo
                    Correo correo = correoService.findOne(POSTULACION_TRAINER.get());
                    //Envio de correo
                    String hashId = Parseador.getEncodeHash32Id("rf-request", obj.getId());
                    String cuerpo = String.format(correo.getBody(), hashId);
                    emailService.enviarCorreoInformativo(correo.getAsunto(), obj.getCorreo(), cuerpo);
                    return Enums.ResponseCode.ACTUALIZACION.get() +"|"+ entity.getId();
                }else{
                    return "Usted no puede volver a postular hasta la fecha: "+obj.getFechaLimiteAccion();
                }
            }
            return "Usted ya se encuentra registrado, por favor espere hasta que nuestros gestores puedan aprobar " +
             "o rechazar su postulación. Será notificado vía correo electrónico en el transcurso del día. Gracias " +
              "por su espera";
        }
        entity.setFechaCreacion(new Date());
        entity.setIntentos(1);
        repository.save(entity);
        //Receptor
        String receptor = !profile.equals("production") ? emitterMail : entity.getCorreo();
        //Obtener cuerpo del correo
        Correo correo = correoService.findOne(POSTULACION_TRAINER.get());
        //Envio de correo
        String hashId = Parseador.getEncodeHash32Id("rf-request", entity.getId());
        String cuerpo = String.format(correo.getBody(), hashId);
        emailService.enviarCorreoInformativo(correo.getAsunto(), receptor, cuerpo);
        //Save
        return Enums.ResponseCode.REGISTRO.get() +"|"+ entity.getId();
    }

    @Override
    public String actualizar(PostulanteTrainer entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public String decidir(Integer preTrainerId, Integer decisionId) {
        PostulanteTrainer preTrainer = findOne(preTrainerId);
        if(preTrainer == null){
           return Enums.ResponseCode.EMPTY_RESPONSE.get();
        }
        Date timestamp = new Date();
        if(decisionId == 1){
            preTrainer.setFechaAprobacion(timestamp);
            preTrainer.setFechaLimiteAccion(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)));
            preTrainer.setFlagAceptado(true);
        }else{
            preTrainer.setFechaRechazo(timestamp);
            preTrainer.setFechaLimiteAccion(Date.from(Instant.now().plus(90, ChronoUnit.DAYS)));
            preTrainer.setFlagRechazado(true);
        }
        //Save
        repository.save(preTrainer);

        //Receptor
        String receptor = !profile.equals("production") ? emitterMail : preTrainer.getCorreo();
        //Envio de correo
        if(decisionId == 1) {
            emailService.enviarCorreoInformativo("Solicitud aprobada",
                    receptor,
                                                "<h1>Más detalles en: <a href=\"http://127.0.0.1:8080/p/formulario/trainer/" + Parseador.getEncodeHash32Id("rf-request", preTrainer.getId()) + "\">Llenar ficha de entrenador oficial</a></h1>");
        }else{
            emailService.enviarCorreoInformativo("Gracias por su postulación",
                    receptor,
                                                "<h1>Podrá volver a intentarlo después de 90 días. Siga mejorando y seguramente la próxima vez será admitido</h1>");
        }


        return Enums.ResponseCode.EXITO_GENERICA.get();
    }
}
