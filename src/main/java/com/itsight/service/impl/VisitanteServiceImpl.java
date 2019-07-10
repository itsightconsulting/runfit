package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.aop.AspectController;
import com.itsight.domain.*;
import com.itsight.domain.dto.VisitanteDTO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.repository.VisitanteRepository;
import com.itsight.service.CorreoService;
import com.itsight.service.EmailService;
import com.itsight.service.VisitanteService;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import static com.itsight.util.Enums.Mail.REGISTRO_VISITANTE_CONFIRMAR_CORREO;
import static com.itsight.util.Enums.Msg.CORREO_REPETIDO;
import static com.itsight.util.Enums.Msg.FAIL_SUBIDA_IMG_PERFIL;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;
import static com.itsight.util.Utilitarios.encoderPassword;


@Service
@Transactional
public class VisitanteServiceImpl extends BaseServiceImpl<VisitanteRepository> implements VisitanteService {

    private static final Logger logger = LogManager.getLogger(VisitanteServiceImpl.class);



    private SecurityUserRepository securityUserRepository;

    @Value("${domain.name}")
    private String domainName;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CorreoService correoService;

    @Autowired
    public VisitanteServiceImpl(VisitanteRepository repository, SecurityUserRepository securityUserRepository) {
        super(repository);
        this.securityUserRepository = securityUserRepository;
    }

    @Override
    public String registrarVisitante(VisitanteDTO visitanteDTO) throws CustomValidationException {

        if (!securityUserRepository.findCorreoExist(visitanteDTO.getCorreo())) {

            try {

                Visitante obj = new Visitante();
                String contraseñaEncrypt = encoderPassword(visitanteDTO.getPassword());

                BeanUtils.copyProperties(visitanteDTO, obj, new String[] {"password"});

                String schema = Utilitarios.getRandomString(10);

                SecurityUser securityUser = new SecurityUser(obj.getCorreo(), contraseñaEncrypt, false);
                Set<SecurityRole> listSr = new HashSet<>();
                listSr.add(new SecurityRole("ROLE_GUEST", securityUser));
                securityUser.setRoles(listSr);
                obj.setSecurityUser(securityUser);

                repository.save(obj);


                String hshId = Parseador.getEncodeHash32Id(schema, obj.getId());
                String b64sc = new String(Base64.getEncoder().encode(schema.getBytes()));

                Correo correo = correoService.findOne(REGISTRO_VISITANTE_CONFIRMAR_CORREO.get());

                //Envio de correo
                String correoDestinatario = securityUser.getUsername();
                String cuerpo = String.format(correo.getBody(), domainName, hshId, b64sc);

                emailService.enviarCorreoInformativo(correo.getAsunto(), correoDestinatario, cuerpo);

                return ""+obj.getId();

            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "some error", e);
            }

        }else{

            throw new CustomValidationException(CORREO_REPETIDO.get(), EX_VALIDATION_FAILED.get());



        }

           // return Enums.ResponseCode.VF_USUARIO_REPETIDO.get();
    }

    @Override
    public Visitante findOne(Integer preViId) {
        return repository.findById(preViId).orElse(null);
    }
}
