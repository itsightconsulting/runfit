package com.itsight.service.impl;

import com.itsight.domain.SecurityPrivilege;
import com.itsight.domain.SecurityRole;
import com.itsight.domain.SecurityUser;
import com.itsight.repository.SecurityUserRepository;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SecurityServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(SecurityServiceImpl.class);

    @Autowired
    private SecurityUserRepository securityUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        try {
            SecurityUser user = securityUserRepository.findByUsername(username);
            if (user != null) {
                return buildUser(user, buildAuthorities(user.getRoles()));
            }
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.info("> Excepción | (?): " + username.toUpperCase());
        }
        LOGGER.info("> UsernameNotFoundException | (?): " + username.toUpperCase());
        throw new UsernameNotFoundException("UsernameNotFoundException | (?): " + username.toUpperCase());
    }

    private User buildUser(SecurityUser securityUser, Set<GrantedAuthority> lstRole) {
        return
                new User(securityUser.getUsername() + "|"+securityUser.getId(),
                        securityUser.getPassword(),
                        securityUser.isEnabled(),
                        true,
                        true,
                        securityUser.isEnabled(), lstRole);
    }

    private Set<GrantedAuthority> buildAuthorities(Set<SecurityRole> roles) {
        Set<GrantedAuthority> lstRole = new HashSet<>();
        for (SecurityRole role : roles) {
            //System.out.println("> USER ROLE: " + role.getRole());
            LOGGER.debug("> USER ROLE: " + role.getRole());
            lstRole.add(new SimpleGrantedAuthority(role.getRole()));
            for (SecurityPrivilege privilege : role.getPrivileges()) {
                lstRole.add(new SimpleGrantedAuthority(privilege.getPrivilege()));
                LOGGER.debug("> USER PRIVILEGE: " + privilege.getPrivilege());
            }
        }
        return lstRole;
    }


}
