package com.itsight.service;

import com.itsight.domain.Rol;
import com.itsight.generic.BaseService;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import java.io.File;

import static java.nio.charset.StandardCharsets.UTF_8;

public interface RolService extends BaseService<Rol, Integer> {

}
