package com.itsight.service.impl;

import com.itsight.domain.BandejaTemporal;
import com.itsight.generic.EmailGeneric;
import com.itsight.repository.BandejaTemporalRepository;
import com.itsight.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl extends EmailGeneric implements EmailService {

    @Value("${spring.profiles.active}")
    private String profile;
	
    public JavaMailSender emailSender;

    public BandejaTemporalRepository bandejaTemporalRepository;
    
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
            boolean isProdOrHku = profile.equals("production") || profile.equals("herokudev");
            if(isProdOrHku) {
                if(profile.equals("herokudev")){
                    preparator = mimeMessagePreparator(asunto, "contoso.peru@gmail.com", contenido);
                }else{
                    preparator = mimeMessagePreparator(asunto, receptor, contenido);
                }
                emailSender.send(preparator);
            }else {
                Integer ixUrl = contenido.indexOf("href=");
                String url = ixUrl == -1 ? "" : contenido.substring(contenido.indexOf("href=")+6).split("'")[0];
                bandejaTemporalRepository.save(new BandejaTemporal(asunto, contenido, url));
            }
        } catch (MailException ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
    }
}
