package com.itsight.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
                if (cookie.getValue().contentEquals(request.getSession().getId())){
                    cookie.setMaxAge(72000);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }
}
