package com.itsight.component;

import com.itsight.domain.dto.UsuGenDTO;
import com.itsight.service.AdministradorService;
import com.itsight.service.ClienteService;
import com.itsight.service.ConfiguracionClienteService;
import com.itsight.service.TrainerService;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private ConfiguracionClienteService configuracionClienteService;

    private ServletContext context;

/*    @Autowired
    private SecurityUserRepository securityUserRepository;*/

    @Autowired
    private HttpSession session;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent login) {
        // TODO Auto-generated method stub
        try {
            String[] usernameAndId = StringUtils.split(login.getAuthentication().getName(), "|");

            String username = usernameAndId[0];
            Integer id = Integer.parseInt(usernameAndId[1]);
            UsuGenDTO usu;
            session.setAttribute("id", id);
            if(login.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TRAINER"))){
                trainerService.actualizarFechaUltimoAcceso(new Date(), id);
                usu = trainerService.getForCookieById(id);

            } else if(login.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
                usu = administradorService.getForCookieById(id);
                administradorService.actualizarFechaUltimoAcceso(new Date(), id);
            } else {
                usu = clienteService.getForCookieById(id);
                clienteService.actualizarFechaUltimoAcceso(new Date(), id);
                String favRutId = configuracionClienteService.obtenerByIdAndClave(id, "FAV_RUTINA_ID");
                Integer favRutinaId = favRutId.equals("") ? null : Integer.parseInt(favRutId);
                if(favRutinaId != null){
                    session.setAttribute("fvrtId", favRutinaId);
                }
            }

            //Fixing authentication object
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, login.getAuthentication().getCredentials(), SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //Generando cookies
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
            String fullName = usu.getNombres() + " " + usu.getApellidos();
            response.addCookie(Utilitarios.createCookie("GLL_NOMBRE_COMPLETO", new String(Base64.getEncoder().encode(fullName.getBytes()))));
            if(usu.getUuidFp().equals("")){
                response.addCookie(Utilitarios.createCookie("GLL_IMG_PERFIL", usu.getUuidFp() + usu.getExtFp()));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
