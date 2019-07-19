package com.itsight.component;

import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
        if (exception.getClass().isAssignableFrom(AccountStatusException.class)) {
            response.sendRedirect("/login?error=disabled");
        } else if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
            response.sendRedirect("/login?error=true");
        } else if (exception.getClass().isAssignableFrom(SessionAuthenticationException.class)) {
            response.sendRedirect("/login?error=loggedin");
        }
        //super.onAuthenticationFailure(request, response, exception);
    }
}