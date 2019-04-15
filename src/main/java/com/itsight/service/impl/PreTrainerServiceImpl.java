package com.itsight.service.impl;

import com.itsight.domain.PreTrainer;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.PreTrainerRepository;
import com.itsight.service.EmailService;
import com.itsight.service.PreTrainerService;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PreTrainerServiceImpl extends BaseServiceImpl<PreTrainerRepository> implements PreTrainerService {

    private EmailService emailService;

    public PreTrainerServiceImpl(PreTrainerRepository repository,
                                 EmailService emailService) {
        super(repository);
        this.emailService = emailService;
    }

    @Override
    public PreTrainer save(PreTrainer entity) {
        return repository.save(entity);
    }

    @Override
    public PreTrainer update(PreTrainer entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public PreTrainer findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public PreTrainer findOneWithFT(Integer id) {
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
    public List<PreTrainer> findAll() {
        return repository.findAll();
    }

    @Override
    public List<PreTrainer> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<PreTrainer> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<PreTrainer> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<PreTrainer> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<PreTrainer> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<PreTrainer> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<PreTrainer> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(PreTrainer entity, String wildcard) {
        //Verificación correo único
        Optional<PreTrainer> optPreTrainer = Optional.ofNullable(repository.findByCorreo(entity.getCorreo()));
        if(optPreTrainer.isPresent()){
            PreTrainer obj = optPreTrainer.get();
            if(obj.isFlagAceptado()){
                return "-11";
            }

            if(obj.isFlagRechazado()){
                return "-22";
            }
        }
        entity.setFechaCreacion(new Date());
        repository.save(entity);
        //Envio de correo
        emailService.enviarCorreoInformativo("Solicitud para ser parte del staff de entrenadores", "contoso.peru@gmail.com", "<h1>Más detalles en: <a href=\"http://127.0.0.1:8080/p/q/pre-trainer/"+ Parseador.getEncodeHash32Id("rf-request", entity.getId()) +"\">Ver candidato</a></h1>");
        //Save

        return Enums.ResponseCode.REGISTRO.get() +"|"+ entity.getId();
    }

    @Override
    public String actualizar(PreTrainer entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public String aprobarDesaprobar(Integer preTrainerId, Integer decision) {
        PreTrainer preTrainer = findOne(preTrainerId);
        if(preTrainer == null){
           return Enums.ResponseCode.EMPTY_RESPONSE.get();
        }
        Date timestamp = new Date();
        if(decision == 1){
            preTrainer.setFechaAprobacion(timestamp);
            preTrainer.setFlagAceptado(true);
        }else{
            preTrainer.setFechaRechazo(timestamp);
            preTrainer.setFlagRechazado(true);
        }
        //Save
        repository.save(preTrainer);

        //Envio de correo
        if(decision == 1) {
            emailService.enviarCorreoInformativo("Solicitud aprobada",
                                                "contoso.peru@gmail.com",
                                                "<h1>Más detalles en: <a href=\"http://127.0.0.1:8080/p/registro/trainer/" + Parseador.getEncodeHash32Id("rf-request", preTrainer.getId()) + "\">Llenar ficha de entrenador oficial</a></h1>");
        }else{
            emailService.enviarCorreoInformativo("Gracias por su postulación",
                                                "contoso.peru@gmail.com",
                                                "<h1>Podrá volver a intentarlo después de 3 meses. Siga mejorando y seguramente la próxima vez será admitido</h1>");
        }


        return Enums.ResponseCode.EXITO_GENERICA.get();
    }
}
