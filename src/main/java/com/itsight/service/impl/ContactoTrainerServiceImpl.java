package com.itsight.service.impl;

import com.itsight.domain.ContactoTrainer;
import com.itsight.domain.Correo;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ContactoTrainerRepository;
import com.itsight.service.ContactoTrainerService;
import com.itsight.service.CorreoService;
import com.itsight.service.EmailService;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static com.itsight.util.Enums.Mail.POSTULACION_TRAINER;

@Service
@Transactional
public class ContactoTrainerServiceImpl extends BaseServiceImpl<ContactoTrainerRepository> implements ContactoTrainerService {

    private EmailService emailService;

    private CorreoService correoService;

    @Value("${domain.name}")
    private String domainName;

    @Autowired
    public ContactoTrainerServiceImpl(ContactoTrainerRepository repository,
                            EmailService emailService,
                            CorreoService correoService) {
        super(repository);
        this.emailService = emailService;
        this.correoService = correoService;
    }

    @Override
    public ContactoTrainer save(ContactoTrainer entity) {
        return repository.save(entity);
    }

    @Override
    public ContactoTrainer update(ContactoTrainer entity) {
        return repository.save(entity);
    }

    @Override
    public ContactoTrainer findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ContactoTrainer findOneWithFT(Integer id) {
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
    public List<ContactoTrainer> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ContactoTrainer> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<ContactoTrainer> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<ContactoTrainer> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<ContactoTrainer> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<ContactoTrainer> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<ContactoTrainer> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<ContactoTrainer> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(ContactoTrainer entity, String trainerId) {
        entity.setTrainer(Integer.parseInt(trainerId));
        entity.setFechaExpiracion(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)));
        entity.setFechaCreacion(new Date());
        repository.save(entity);

        Correo correo = correoService.findOne(Enums.Mail.CONTACTO_TRAINER.get());
        //Envio de correo
        String hashId = Parseador.getEncodeHash32Id("rf-cont-tra", entity.getId());
        String cuerpo = String.format(correo.getBody(), domainName, hashId);
        emailService.enviarCorreoInformativo(correo.getAsunto(), entity.getCorreoTrainer(), cuerpo);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(ContactoTrainer entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
