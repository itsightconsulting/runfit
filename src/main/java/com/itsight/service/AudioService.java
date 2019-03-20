package com.itsight.service;

import com.itsight.domain.Audio;
import com.itsight.domain.TipoAudio;
import com.itsight.generic.BaseService;

import java.util.List;

public interface AudioService extends BaseService<Audio, Integer> {

    List<Audio> listarPorFiltroSecundario(String comodin, String tipo);

}
