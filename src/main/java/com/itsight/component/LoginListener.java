package com.itsight.component;

import com.itsight.domain.dto.ConfiguracionClienteDTO;
import com.itsight.domain.dto.UsuGenDTO;
import com.itsight.service.*;
import com.itsight.util.Parseador;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static com.itsight.util.Enums.CfsCliente.*;
import static com.itsight.util.Enums.Galletas.*;
import static com.itsight.util.Utilitarios.createCookie;

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

    @Autowired(required = false)
    private HttpSession session;

    @Autowired
    private ConfiguracionClienteProcedureInvoker configuracionClienteProcedureInvoker;

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
            if (login.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TRAINER"))) {
                trainerService.actualizarFechaUltimoAcceso(new Date(), id);
                usu = trainerService.getForCookieById(id);
            } else if (login.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                usu = administradorService.getForCookieById(id);
                administradorService.actualizarFechaUltimoAcceso(new Date(), id);
            } else if (login.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GUEST"))) {
                usu = visitanteService.getForCookieById(id);
            } else {//ROLE RUNNER
                usu = clienteService.getForCookieById(id);
                clienteService.actualizarFechaUltimoAcceso(new Date(), id);

                String favRutId = configuracionClienteService.obtenerByIdAndClave(id, FAV_RUTINA_ID.name());
                if (favRutId != null && !favRutId.equals("")) {
                    response.addCookie(
                            createCookie(GLL_FAV_RUTINA.name(),
                                    Parseador.getEncodeHash32Id(
                                            "rf-gallcoks",
                                            Integer.parseInt(configuracionClienteService.obtenerByIdAndClave(id, FAV_RUTINA_ID.name())))));
                } else {
                    response.addCookie(createCookie(GLL_FAV_RUTINA.name(), ""));
                }
                List<ConfiguracionClienteDTO> lstConfCli = configuracionClienteProcedureInvoker.getAllById(id);
                lstConfCli.forEach(e -> {
                    if (e.getNombre().equals(CONTROL_ENTRENAMIENTO.name())) {
                        response.addCookie(createCookie(GLL_CONTROL_ENTRENAMIENTO.name(), e.getValor()));
                    }

                    if (e.getNombre().equals(CONTROL_REP_VIDEO.name())) {
                        response.addCookie(createCookie(GLL_CONTROL_REP_VIDEO.name(), e.getValor()));
                    }

                    if (e.getNombre().equals(NOTIFICACION_CHAT.name())) {
                        response.addCookie(createCookie(GLL_NOTIFICACION_CHAT.name(), e.getValor()));
                    }

                    if (e.getNombre().equals(TOTAL_RUTINAS_ACTIVAS.name())) {
                        response.addCookie(createCookie(GLL_TOTAL_RUTINAS_ACTIVAS.name(), e.getValor()));
                    }
                });
            }

            //Fixing authentication object
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, login.getAuthentication().getCredentials(), SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //Generando cookies
            if (usu == null) {
                response.addCookie(createCookie(GLL_NOMBRE_COMPLETO.name(), new String(Base64.getEncoder().encode(username.getBytes()))));
            } else {
                String fullName = usu.getNombres() + " " + usu.getApellidos();
                response.addCookie(createCookie(GLL_NOMBRE_COMPLETO.name(), new String(Base64.getEncoder().encode(fullName.getBytes()))));
                if (!usu.getUuidFp().equals("")) {
                    response.addCookie(createCookie(GLL_IMG_PERFIL.name(), id + "/" + usu.getUuidFp() + usu.getExtFp()));
                } else {
                    response.addCookie(createCookie(GLL_IMG_PERFIL.name(), ""));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
