package com.itsight.util;

import org.springframework.beans.factory.annotation.Value;

public class MailContents {

    public static StringBuilder contenidoNuevoUsuario(String username, String originalPassword, int tipoUsuario, String domainName){
        StringBuilder sb = new StringBuilder(1000);
        if(tipoUsuario == 1 || tipoUsuario == 2){
            sb.append("<h4>Bienvenido a Dennys Workout Team,</h4>");
            sb.append("<p>Nos es grato comunicarle que desde hoy usted forma parte de esta plataforma y hacerle llegar");
            sb.append("nuestra enhorabuena, desde ya estamos muy contentos por la confianza y sin más, sus credenciales de ingreso:</p>");
            sb.append("<ul>");
            sb.append("<li>Usuario: <b>"+ username+"</b></li>");
            sb.append("<li>Password: <b>"+ originalPassword+"</b></li>");
            sb.append("</ul>");
            sb.append("<div class='' style='text-align: center;'><a style='text-decoration:none;width: 115px;height: 25px;background: #28a745;padding: 10px;text-align: center;border-radius: 5px;color: white;font-weight: bold;' class='' href=\""+domainName+"/login/\" target=\"_blanket\">Plataforma Workout</a></div>");
            sb.append("<h4>Dennys Workout, <br/> Los Eucaliptos, Los Olivos 15008, Perú.</h4>");
        }else{
            sb.append("<h4>Bienvenido a Dennys Workout,</h4>");
            sb.append("<p> Nos hace muy feliz recibir la confianza que usted nos ha brindado y de esa misma forma de ahora en adelante ");
            sb.append("nuestros expertos pondrán todo de sí para cumplir juntos los objetivos trazados. </p>");
            sb.append("<p>Sin más, sus credenciales de ingreso:</p>");
            sb.append("<ul>");
            sb.append("<li>Usuario: <b>"+ username+"</b></li>");
            sb.append("<li>Password: <b>"+ originalPassword+"</b></li>");
            sb.append("</ul>");
            sb.append("<div class='' style='text-align: center;'><a style='text-decoration:none;width: 115px;height: 25px;background: #28a745;padding: 10px;text-align: center;border-radius: 5px;color: white;font-weight: bold;' class='' href=\""+domainName+"/login/\" target=\"_blanket\">Plataforma Workout</a></div>");
            sb.append("<h4>Dennys Workout, <br/> Los Eucaliptos, Los Olivos 15008, Perú.</h4>");
        }

        return sb;
    }
}
