package com.itsight.service;

import com.itsight.domain.GrupoVideo;
import com.itsight.generic.BaseService;

import java.util.List;

public interface GrupoVideoService extends BaseService<GrupoVideo, Integer> {

    List<GrupoVideo> encontrarGrupoConSusDepedencias();
}
