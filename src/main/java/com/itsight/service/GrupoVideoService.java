package com.itsight.service;

import com.itsight.domain.GrupoVideo;
import com.itsight.domain.dto.RefUpload;
import com.itsight.generic.BaseService;

import java.util.List;

public interface GrupoVideoService extends BaseService<GrupoVideo, Integer> {

    List<GrupoVideo> encontrarGrupoConSusDepedencias();

    RefUpload registrarConSubida(GrupoVideo grupoVideo);

    boolean checkHaveChildrenById(int id);
}
