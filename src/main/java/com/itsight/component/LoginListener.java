package com.itsight.component;

import com.itsight.domain.SecurityUser;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Component
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SecurityUserRepository securityUserRepository;

    @Autowired
    private HttpSession session;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent login) {
        // TODO Auto-generated method stub
        try {
            String userName = login.getAuthentication().getName();
            int id = securityUserRepository.findIdByUsername(userName);
            session.setAttribute("id", id);
            String codTrainer = usuarioService.findCodigoTrainerById(id);
            if(codTrainer != null) {
                session.setAttribute("codTrainer", codTrainer);
            }
            usuarioService.actualizarFechaUltimoAcceso(new Date(), userName);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
