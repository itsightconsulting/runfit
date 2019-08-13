package com.itsight.component;

import com.itsight.domain.dto.UsuGenDTO;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
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

import static com.itsight.util.Enums.CfsCliente.*;
import static com.itsight.util.Enums.Galletas.*;
import static com.itsight.util.Utilitarios.createCookie;
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
    private VisitanteService visitanteService;

    @Autowired
    private ConfiguracionClienteService configuracionClienteService;

    /*@Autowired
    private SecurityUserRepository securityUserRepository;*/

    @Autowired(required = false)
    private HttpSession session;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent login) {
        // TODO Auto-generated method stub
        try {
            //For cookies
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

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
            } else if(login.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GUEST"))) {
                usu = visitanteService.getForCookieById(id);
            } else {//ROLE RUNNER
                usu = clienteService.getForCookieById(id);
                clienteService.actualizarFechaUltimoAcceso(new Date(), id);

                String favRutId = configuracionClienteService.obtenerByIdAndClave(id, FAV_RUTINA_ID.name());
                if(favRutId != null && !favRutId.equals("")){
                    response.addCookie(
                            createCookie(GLL_FAV_RUTINA.name(),
                            Parseador.getEncodeHash32Id(
                                        "rf-gallcoks",
                                                Integer.parseInt(configuracionClienteService.obtenerByIdAndClave(id, FAV_RUTINA_ID.name())))));
                }else{
                    response.addCookie(createCookie(GLL_FAV_RUTINA.name(), ""));
                }

                response.addCookie(createCookie(GLL_CONTROL_ENTRENAMIENTO.name(), configuracionClienteService.obtenerByIdAndClave(id, CONTROL_ENTRENAMIENTO.name())));
                response.addCookie(createCookie(GLL_CONTROL_REP_VIDEO.name(), configuracionClienteService.obtenerByIdAndClave(id, CONTROL_REP_VIDEO.name())));
            }

            //Fixing authentication object
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, login.getAuthentication().getCredentials(), SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //Generando cookies
            if(usu == null){
                response.addCookie(createCookie(GLL_NOMBRE_COMPLETO.name(), new String(Base64.getEncoder().encode(username.getBytes()))));
            }else{
                String fullName = usu.getNombres() + " " + usu.getApellidos();
                response.addCookie(createCookie(GLL_NOMBRE_COMPLETO.name(), new String(Base64.getEncoder().encode(fullName.getBytes()))));
                if(usu.getUuidFp().equals("")){
                    response.addCookie(createCookie(GLL_IMG_PERFIL.name(), usu.getUuidFp() + usu.getExtFp()));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
