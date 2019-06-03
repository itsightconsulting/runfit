package com.itsight.util;

public class MailContents {

    public static StringBuilder contenidoNuevoUsuario(String username, String originalPassword, int tipoUsuario, String domainName){
        StringBuilder sb = new StringBuilder(1000);
        if(tipoUsuario == 1 || tipoUsuario == 2){
            sb.append("<h4>Bienvenido a Dennys Workout Team,</h4>");
            sb.append("<p>Su perfil ya ha sido registrado en nuestra base de datos es por ello que le enviamos sus credenciales de ingreso las ");
            sb.append("cuales podrán ser usadas después de que nuestro staff apruebe la información registrada de su perfil:</p>");
            sb.append("<ul>");
            sb.append("<li>Cliente: <b>"+ username+"</b></li>");
            sb.append("<li>Password: <b>"+ originalPassword+"</b></li>");
            sb.append("</ul>");
            sb.append("<p>Automaticamente después que aprueben la información usted será notificado mediante correo para que ya acceder ");
            sb.append("con las credenciales anteriormente específicadas.</p>");
            sb.append("<div class='' style='text-align: center;'><a style='text-decoration:none;width: 115px;height: 25px;background: #28a745;padding: 10px;text-align: center;border-radius: 5px;color: white;font-weight: bold;' class='' href=\""+domainName+"/login/\" target=\"_blanket\">Plataforma Workout</a></div>");
            sb.append("<h4>Dennys Workout, <br/> Los Eucaliptos, Los Olivos 15008, Perú.</h4>");
        }else{
            sb.append("<h4>Bienvenido a Dennys Workout,</h4>");
            sb.append("<p> Nos hace muy feliz recibir la confianza que usted nos ha brindado y de esa misma forma de ahora en adelante ");
            sb.append("nuestros expertos pondrán todo de sí para cumplir juntos los objetivos trazados. </p>");
            sb.append("<p>Sin más, sus credenciales de ingreso:</p>");
            sb.append("<ul>");
            sb.append("<li>Cliente: <b>"+ username+"</b></li>");
            sb.append("<li>Password: <b>"+ originalPassword+"</b></li>");
            sb.append("</ul>");
            sb.append("<div class='' style='text-align: center;'><a style='text-decoration:none;width: 115px;height: 25px;background: #28a745;padding: 10px;text-align: center;border-radius: 5px;color: white;font-weight: bold;' class='' href=\""+domainName+"/login/\" target=\"_blanket\">Plataforma Workout</a></div>");
            sb.append("<h4>Dennys Workout, <br/> Los Eucaliptos, Los Olivos 15008, Perú.</h4>");
        }

        return sb;
    }
}
