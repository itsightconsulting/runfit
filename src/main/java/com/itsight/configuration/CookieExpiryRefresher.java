package com.itsight.configuration;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class CookieExpiryRefresher extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {

        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return;
        }

        for (Cookie cookie : cookies){
            if (cookie.getName().contentEquals("JSESSIONID")){
                Optional<HttpSession> session = Optional.ofNullable(request.getSession(false));
                if(!session.isPresent()){
                    break;
                }
                if (cookie.getValue().contentEquals(session.get().getId())){
                    cookie.setMaxAge(72000);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }
}
