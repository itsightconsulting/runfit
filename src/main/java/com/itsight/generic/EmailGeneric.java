package com.itsight.generic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;

public abstract class EmailGeneric {

    @Value("${spring.mail.username}")
    private String hostMail = "";

    private static final String MAIL_CONTENT_TYPE = "text/html; charset=utf-8";
    private static final String PLATFORM_NAME = "RunFit Platform";

    public MimeMessagePreparator mimeMessagePreparator(String asunto, String receptor, String contenido) {

        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setFrom(new InternetAddress(hostMail, PLATFORM_NAME));
            mimeMessage.setSubject(asunto);
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(receptor, PLATFORM_NAME));
            mimeMessage.setContent(contenido
                    , MAIL_CONTENT_TYPE);
        };
        return preparator;
    }

    public MimeMessagePreparator mimeMessagePreparatorForRecepientsBbc(String asunto, String receptors, String contenido) {

        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setFrom(new InternetAddress(hostMail, PLATFORM_NAME));
            mimeMessage.setSubject(asunto);
            mimeMessage.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(receptors));
            mimeMessage.setContent(contenido
                    , MAIL_CONTENT_TYPE);
        };
        return preparator;
    }

    public MimeMessagePreparator mimeMessagePreparatorForRecepientAndOnlyOneCc(String asunto, String receptor, String ccReceptor, String contenido) {

        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setFrom(new InternetAddress(hostMail, PLATFORM_NAME));
            mimeMessage.setSubject(asunto);
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
            mimeMessage.setRecipient(Message.RecipientType.CC, new InternetAddress(ccReceptor));
            mimeMessage.setContent(contenido
                    , MAIL_CONTENT_TYPE);
        };
        return preparator;
    }
}