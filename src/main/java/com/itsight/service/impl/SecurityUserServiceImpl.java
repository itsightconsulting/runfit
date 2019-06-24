package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.SecurityUser;
import com.itsight.domain.UsuarioRecover;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.repository.UsuarioRecoverRepository;
import com.itsight.service.EmailService;
import com.itsight.service.SecurityUserService;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    @Autowired
    private SecurityUserRepository securityUserRepository;

    @Autowired
    private UsuarioRecoverRepository usuarioRecoverRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public String recuperarPassword(String username) throws CustomValidationException {
        Boolean isEnabled = securityUserRepository.findEnabledByUsername(username);
        if(isEnabled == null){
            throw new CustomValidationException(Enums.Msg.VALIDACION_FALLIDA.get(), EX_VALIDATION_FAILED.get());
        }
        if(!isEnabled){
            throw new CustomValidationException(Enums.Msg.VALIDACION_FALLIDA.get(), EX_VALIDATION_FAILED.get());
        }

        SecurityUser su =  securityUserRepository.findByUsername(username);

        UsuarioRecover usuRec = usuarioRecoverRepository.findById(su.getId()).orElse(null);
        Date nowPlusOne = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        String schema = Utilitarios.getRandomString(10);
        if(usuRec == null){
            usuarioRecoverRepository.save(new UsuarioRecover(su.getId(), schema, true, nowPlusOne));
        } else {
            usuRec.setFlagRecover(true);
            usuRec.setSchema(schema);
            usuRec.setFechaLimite(nowPlusOne);
            usuarioRecoverRepository.saveAndFlush(usuRec);
        }

        String correo = securityUserRepository.getCorreoById(su.getId());
        String hshId = Parseador.getEncodeHash32Id(schema, su.getId());
        String b64sc = new String(Base64.getEncoder().encode(schema.getBytes()));
        emailService.enviarCorreoInformativo("Recuperación de correo", correo,
                "Ingresa a este link para que cambies tu password: href='http://127.0.0.1:8080/p/cambiar/password/"+hshId+"?sc="+b64sc+"' el enlace caducará en 24 horas");
        return Enums.Msg.REGISTRO_EXITOSO.get();
    }
}
