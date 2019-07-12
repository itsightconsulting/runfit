package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.Correo;
import com.itsight.domain.SecurityUser;
import com.itsight.domain.UsuarioRecover;
import com.itsight.domain.dto.PasswordDTO;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.repository.UsuarioRecoverRepository;
import com.itsight.service.CorreoService;
import com.itsight.service.EmailService;
import com.itsight.service.SecurityUserService;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import static com.itsight.util.Enums.Mail.FINAL_CAMBIO_PASSWORD;
import static com.itsight.util.Enums.Mail.INIT_CAMBIO_PASSWORD;
import static com.itsight.util.Enums.Msg.*;
import static com.itsight.util.Enums.ResponseCode.EXITO_GENERICA;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    @Value("${domain.name}")
    private String domainName;

    @Autowired
    private SecurityUserRepository securityUserRepository;

    @Autowired
    private UsuarioRecoverRepository usuarioRecoverRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CorreoService correoService;

    @Override
    public String recuperarPassword(String username) throws CustomValidationException {
        String idAndEnabled = securityUserRepository.findIdAndEnabledByUsername(username);
        if(idAndEnabled == null){
            throw new CustomValidationException(USUARIO_NO_EXISTE.get(), EX_VALIDATION_FAILED.get());
        }
        Boolean isEnabled = Boolean.valueOf(idAndEnabled.split("\\|")[1]);

        if(!isEnabled){
            throw new CustomValidationException(USUARIO_INACTIVO.get(), EX_VALIDATION_FAILED.get());
        }

        Integer secUserId =  Integer.parseInt(idAndEnabled.split("\\|")[0]);

        UsuarioRecover usuRec = usuarioRecoverRepository.findById(secUserId).orElse(null);
        Date nowPlusOne = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        String schema = Utilitarios.getRandomString(10);
        if(usuRec == null){
            usuarioRecoverRepository.save(new UsuarioRecover(secUserId, schema, true, nowPlusOne));
        } else {
            usuRec.setFlagRecover(true);
            usuRec.setSchema(schema);
            usuRec.setFechaLimite(nowPlusOne);
            usuarioRecoverRepository.save(usuRec);
        }

        String hshId = Parseador.getEncodeHash32Id(schema, secUserId);
        String b64sc = new String(Base64.getEncoder().encode(schema.getBytes()));

        Correo correo = correoService.findOne(INIT_CAMBIO_PASSWORD.get());
        //Envio de correo
        String cuerpo = String.format(correo.getBody(), domainName, hshId, b64sc);
        String correoDestinatario = securityUserRepository.getCorreoById(secUserId);
        emailService.enviarCorreoInformativo(correo.getAsunto(), correoDestinatario, cuerpo);
        return EXITO_GENERICA.get();
    }

    @Override
    public String cambiarPassword(PasswordDTO passwordDTO, Integer id) throws CustomValidationException {
        if(!passwordDTO.getNuevaPassword().equals(passwordDTO.getNuevaPasswordRe())){
            throw new CustomValidationException(VALIDACION_FALLIDA.get(), EX_VALIDATION_FAILED.get());
        }

        UsuarioRecover usuRec = usuarioRecoverRepository.findById(id).orElse(null);
        if(usuRec == null || !usuRec.isFlagRecover()){
            throw new CustomValidationException(ENLACE_RECUPERACION_PASS_UTILIZADO.get(), EX_VALIDATION_FAILED.get());
        }

        if(usuRec.getFechaLimite().before(new Date())){
            throw new CustomValidationException(ENLACE_CADUCADO.get(), EX_VALIDATION_FAILED.get());
        }
        usuRec.setFlagRecover(false);
        usuarioRecoverRepository.saveAndFlush(usuRec);
        securityUserRepository.actualizarPassword(Utilitarios.encoderPassword(passwordDTO.getNuevaPassword()), id);
        String correoDestinatario = securityUserRepository.getCorreoById(id);

        Correo correo = correoService.findOne(FINAL_CAMBIO_PASSWORD.get());
        //Envio de correo
        String cuerpo = String.format(correo.getBody(), domainName);
        emailService.enviarCorreoInformativo(correo.getAsunto(), correoDestinatario, cuerpo);
        return EXITO_GENERICA.get();
    }


    @Override
    public void updateFlagEnabled(Integer securityUserId, boolean flagEnabled) {
        securityUserRepository.updateEstadoById(securityUserId, !flagEnabled);
    }
}
