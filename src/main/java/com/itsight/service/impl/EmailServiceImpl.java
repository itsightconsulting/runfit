package com.itsight.service.impl;

import com.itsight.domain.BandejaTemporal;
import com.itsight.generic.EmailGeneric;
import com.itsight.repository.BandejaTemporalRepository;
import com.itsight.service.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class EmailServiceImpl extends EmailGeneric implements EmailService {

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${spring.mail.username}")
    private String emitterMail;
	
    private JavaMailSender emailSender;

    private BandejaTemporalRepository bandejaTemporalRepository;

    public static final Logger LOGGER = LogManager.getLogger(EmailServiceImpl.class);
    
    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender, BandejaTemporalRepository bandejaTemporalRepository) {
		// TODO Auto-generated constructor stub
    	this.emailSender = emailSender;
    	this.bandejaTemporalRepository = bandejaTemporalRepository;
	}

    @Override
    public void enviarCorreoActivarUsuario(String asunto, String receptor, String contenido) {
        MimeMessagePreparator preparator = mimeMessagePreparator(asunto, receptor, contenido);
        try {
            emailSender.send(preparator);
        } catch (MailException ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void enviarCorreoRecuperarContrasena(String asunto, String receptor, String contenido) {
        MimeMessagePreparator preparator = mimeMessagePreparator(asunto, receptor, contenido);
        try {
            emailSender.send(preparator);
        } catch (MailException ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void enviarCorreoInformativo(String asunto, String receptor, String contenido) {
        MimeMessagePreparator preparator;
        try {
            if(profile.equals("production")){
                preparator = mimeMessagePreparator(asunto, receptor, contenido);
                emailSender.send(preparator);
                return;
            }
            //Block development/qa
            if(profile.equals("qa-azure")){
                receptor = "monica.diaz@itsight.pe";
                preparator = mimeMessagePreparator(asunto, receptor, contenido);
                emailSender.send(preparator);
            }

            if(profile.equals("development")){
                Integer ixUrl = contenido.indexOf("href=");
                String url = ixUrl == -1 ? "" : contenido.substring(contenido.indexOf("href=")+6).split("'")[0];
                bandejaTemporalRepository.save(new BandejaTemporal(asunto, contenido, url));
            }

        } catch (MailException ex) {
            //Importante el log.error ya que este dispara el envío del error al correo configurado en el SMTP del log4j2.xml
            LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public void enviarCorreoInformativoVariosBbc(String asunto, String receptores, String contenido) {
        MimeMessagePreparator preparator;
        try {
            boolean isProdOrHku = profile.equals("production") || profile.equals("herokudev");
            if(isProdOrHku) {
                //Receptor
                if(profile.equals("herokudev")){
                    receptores = "monica.diaz@itsight.pe";
                    preparator = mimeMessagePreparatorForRecepientsBbc(asunto, receptores, contenido);
                }else{
                    preparator = mimeMessagePreparatorForRecepientsBbc(asunto, receptores, contenido);
                }
                emailSender.send(preparator);
            } else {
                Integer ixUrl = contenido.indexOf("href=");
                String url = ixUrl == -1 ? "" : contenido.substring(contenido.indexOf("href=")+6).split("'")[0];
                bandejaTemporalRepository.save(new BandejaTemporal(asunto, contenido, url));
            }
        } catch (MailException ex) {
            //Importante el log.error ya que este dispara el envío del error al correo configurado en el SMTP del log4j2.xml
            LOGGER.error(ex.getMessage());
        }
    }
}
