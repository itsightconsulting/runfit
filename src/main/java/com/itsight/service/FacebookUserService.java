package com.itsight.service;

import com.itsight.domain.FacebookUser;
import com.itsight.repository.TipoRutinaRepository;

public interface FacebookUserService {

    FacebookUser obtenerFacebookUser(Integer facebookUserId);

    FacebookUser registrarFacebookUser(FacebookUser facebookUser );



}
