package com.itsight.service.impl;

import com.itsight.domain.SecurityRole;
import com.itsight.domain.SecurityUser;
import com.itsight.domain.TipoRutina;
import com.itsight.domain.Visitante;
import com.itsight.domain.dto.VisitanteDTO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.repository.VisitanteRepository;
import com.itsight.service.VisitanteService;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static com.itsight.util.Utilitarios.encoderPassword;


@Service
@Transactional
public class VisitanteServiceImpl extends BaseServiceImpl<VisitanteRepository> implements VisitanteService {

    private SecurityUserRepository securityUserRepository;


    @Autowired
    public VisitanteServiceImpl(VisitanteRepository repository, SecurityUserRepository securityUserRepository) {
        super(repository);
        this.securityUserRepository = securityUserRepository;
    }


    @Override
    public String registrarVisitante(VisitanteDTO visitanteDTO) {

        if (!securityUserRepository.findCorreoExist(visitanteDTO.getEmail())) {

            try {

                Visitante obj = new Visitante();
                BeanUtils.copyProperties(visitanteDTO, obj);

                String contraseñaEncrypt = encoderPassword(obj.getPassword());

                obj.setPassword(contraseñaEncrypt);

                SecurityUser securityUser = new SecurityUser(obj.getEmail(), obj.getPassword(), true);
                Set<SecurityRole> listSr = new HashSet<>();
                listSr.add(new SecurityRole("ROLE_GUEST", securityUser));
                securityUser.setRoles(listSr);
                obj.setSecurityUser(securityUser);

                repository.save(obj);

                return obj.getId().toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

            return Enums.ResponseCode.VF_USUARIO_REPETIDO.get();
    }
}
