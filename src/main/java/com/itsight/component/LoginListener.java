package com.itsight.component;

import com.itsight.service.TrainerService;
import com.itsight.service.UsuarioService;
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
    private UsuarioService usuarioService;

    @Autowired
    private TrainerService trainerService;

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
                Optional<String> optionalCodTrainer = Optional.ofNullable(trainerService.findCodigoTrainerById(Integer.parseInt(id)));
                if(optionalCodTrainer.isPresent() && optionalCodTrainer.get().length()>0) {
                    session.setAttribute("codTrainer", optionalCodTrainer.get());
                }
                trainerService.actualizarFechaUltimoAcceso(new Date(), id);
            }else
                usuarioService.actualizarFechaUltimoAcceso(new Date(), id);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, login.getAuthentication().getCredentials(), SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
