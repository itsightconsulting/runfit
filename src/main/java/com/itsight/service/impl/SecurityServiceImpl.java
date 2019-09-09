package com.itsight.service.impl;

import com.itsight.domain.dto.SecurityUserDTO;
import com.itsight.repository.SecurityUserRepository;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SecurityServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(SecurityServiceImpl.class);

    @Autowired
    private SecurityUserRepository securityUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        SecurityUserDTO user = securityUserRepository.findByUsernameNative(username.toLowerCase());
        return buildUser(user, buildAuthorities(user.getRoles(), user.getPrivileges()));
        /*if (user != null) {*/
//            return buildUser(user, buildAuthorities(user.getRoles(), user.getPrivileges()));
        /*}
        LOGGER.info("> UsernameException | (?): " + username.toUpperCase());
        throw new UsernameNotFoundException("UsernameNotFoundException | (?): " + username.toUpperCase());*/
    }

    private User buildUser(SecurityUserDTO securityUser, Set<GrantedAuthority> lstRole) {
        return
                new User(securityUser.getUsername() + "|"+securityUser.getId(),//Artificio
                        securityUser.getPassword(),
                        securityUser.isEnabled(),
                        true,
                        true,
                        securityUser.isEnabled(), lstRole);
    }

    private Set<GrantedAuthority> buildAuthorities(String roles, String privileges) {
        String[] arrRoles = roles.split("\\|");
        String[] arrPrivileges = privileges.split("\\|");
        HashSet<String> flRoles = new HashSet<>(Arrays.asList(arrRoles));
        HashSet<String> flPrivileges = new HashSet<>(Arrays.asList(arrPrivileges).stream().filter((x)->!x.equals("")).collect(Collectors.toSet()));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (String role: flRoles) {
            LOGGER.debug("> USER ROLE: " + role);
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        for (String privilege : flPrivileges) {
            LOGGER.debug("> USER PRIVILEGE: " + privilege);
            grantedAuthorities.add(new SimpleGrantedAuthority(privilege));
        }
        return grantedAuthorities;
    }


}
