package com.itsight.service;

import com.itsight.domain.TipoAudio;
import com.itsight.generic.BaseService;

import java.util.List;

public interface TipoAudioService extends BaseService<TipoAudio, Integer> {

    List<TipoAudio> findAllWithChildrens();
}
