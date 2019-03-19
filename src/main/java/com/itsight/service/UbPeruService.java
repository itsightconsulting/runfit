package com.itsight.service;

import com.itsight.domain.UbPeru;
import com.itsight.domain.dto.UbPeruLimDTO;
import com.itsight.generic.BaseService;

public interface UbPeruService extends BaseService<UbPeru, Integer> {

    UbPeru findById(String id);

    UbPeruLimDTO findPeLimUbigeo();

    UbPeruLimDTO findPeProvByDep(String depId);

    UbPeruLimDTO findPeDistByDepAndProv(String depId, String provId);
}
