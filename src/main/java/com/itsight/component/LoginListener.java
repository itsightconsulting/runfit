package com.itsight.component;

import com.itsight.service.AdministradorService;
import com.itsight.service.ClienteService;
import com.itsight.service.ConfiguracionClienteService;
import com.itsight.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Optional;

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

/*    @Autowired
    private SecurityUserRepository securityUserRepository;*/

    @Autowired
    private HttpSession session;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent login) {
        // TODO Auto-generated method stub
        try {
            String[] usernameAndId = StringUtils.split(login.getAuthentication().getName(), "|");
            String username = usernameAndId[0], id = usernameAndId[1];
            session.setAttribute("id", Integer.parseInt(id));
            if(login.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TRAINER"))){
                trainerService.actualizarFechaUltimoAcceso(new Date(), id);
            }else if(login.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
                administradorService.actualizarFechaUltimoAcceso(new Date(), id);
            }else{
                clienteService.actualizarFechaUltimoAcceso(new Date(), id);
                Integer favRutinaId = Integer.parseInt(configuracionClienteService.obtenerByIdAndClave(Integer.parseInt(id), "FAV_RUTINA_ID"));
                if(favRutinaId != null){
                    session.setAttribute("fvrtId", favRutinaId);
                }
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, login.getAuthentication().getCredentials(), SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
