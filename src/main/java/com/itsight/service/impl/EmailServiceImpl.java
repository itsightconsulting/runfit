package com.itsight.service.impl;

import com.itsight.generic.EmailGeneric;
import com.itsight.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl extends EmailGeneric implements EmailService {
	
    public JavaMailSender emailSender;
    
    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
		// TODO Auto-generated constructor stub
    	this.emailSender = emailSender;
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
        MimeMessagePreparator preparator = mimeMessagePreparator(asunto, receptor, contenido);
        try {
            emailSender.send(preparator);
        } catch (MailException ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
    }
}