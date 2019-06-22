package com.itsight.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("securityServiceImpl")
    private UserDetailsService securityService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        //http.csrf().disable();
        http.authorizeRequests().antMatchers("/rest/**", "/p/**").permitAll();
        //http.addFilterAfter(new AjaxAuthenticationFilter(), BasicAuthenticationFilter.class);
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
                .antMatchers("/gestion/cliente-fitness").permitAll()
                .antMatchers("/gestion/cliente-fitness/agregar").permitAll()
                .antMatchers("/gestion/cliente/validacion-correo").permitAll()
                .antMatchers("/gestion/cliente/validacion-username").permitAll()
                .antMatchers("/session-expirada").permitAll()
                .antMatchers("/session-multiple").permitAll();

        http.authorizeRequests()
                .antMatchers(
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/fonts/**",
                        "/sound/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginCheck")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/bienvenido")
                .failureUrl("/login?error=error").permitAll()
                .and()
                .logout()
                .deleteCookies("SESSION")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login").permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accesoDenegado")
                .authenticationEntryPoint(new AjaxAuthenticationFilter("/login"))
                .and()
                .sessionManagement()
                .maximumSessions(5)
                .expiredUrl("/login?error=session-expired")
                .maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry());
    }

    // Work around https://jira.spring.io/browse/SEC-2855
    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }
}
