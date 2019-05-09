package com.itsight.service.impl;

import com.itsight.domain.Contacto;
import com.itsight.domain.Correo;
import com.itsight.domain.Parametro;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ContactoRepository;
import com.itsight.service.ContactoService;
import com.itsight.service.CorreoService;
import com.itsight.service.EmailService;
import com.itsight.service.ParametroService;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ContactoServiceImpl extends BaseServiceImpl<ContactoRepository> implements ContactoService {

    private EmailService emailService;

    private CorreoService correoService;

    private ParametroService parametroService;

    @Value("${domain.name}")
    private String domainName;

    public ContactoServiceImpl(ContactoRepository repository,
            EmailService emailService,
            CorreoService correoService,
            ParametroService parametroService) {
        super(repository);
        this.emailService = emailService;
        this.correoService = correoService;
        this.parametroService = parametroService;
    }

    @Override
    public Contacto save(Contacto entity) {
        return repository.save(entity);
    }

    @Override
    public Contacto update(Contacto entity) {
        return repository.save(entity);
    }

    @Override
    public Contacto findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Contacto findOneWithFT(Integer id) {
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
    public List<Contacto> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Contacto> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Contacto> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Contacto> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Contacto> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Contacto> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Contacto> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Contacto> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Contacto entity, String wildcard) {
        entity.setFechaCreacion(new Date());
        repository.save(entity);

        Correo correo = correoService.findOne(Enums.Mail.CONTACTO_CONSULTA.get());
        //GET correo receptor de consultas generales a la plataforma
        Parametro parametro = parametroService.findByClave("EMAIL_RECEPTOR_CONSULTAS");
        //Envio de correo
        String cuerpo = String.format(correo.getBody(), entity.getNombre(), entity.getCorreo(), entity.getMovil(), entity.getMensaje());
        emailService.enviarCorreoInformativo(correo.getAsunto(), parametro.getValor(), cuerpo);

        return Enums.Msg.CONSULTA_CONTACTO_ENVIADA.get();
    }

    @Override
    public String actualizar(Contacto entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
