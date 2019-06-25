package com.itsight.service.impl;


import com.itsight.domain.FacebookUser;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.FacebookUserRepository;
import com.itsight.repository.TipoRutinaRepository;
import com.itsight.service.FacebookUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FacebookUserServiceImpl  extends BaseServiceImpl<FacebookUserRepository> implements FacebookUserService {

    public FacebookUserServiceImpl(FacebookUserRepository repository) {
        super(repository);
    }


    @Override
    public FacebookUser obtenerFacebookUser(Integer facebookUserId) {

        return repository.findByFacebookId(facebookUserId) ;
    }

    @Override
    public FacebookUser registrarFacebookUser(FacebookUser facebookUser) {

        return repository.save(facebookUser);
    }
}
