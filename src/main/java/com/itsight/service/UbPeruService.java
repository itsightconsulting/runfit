package com.itsight.service;

import com.itsight.domain.UbPeru;
import com.itsight.domain.dto.UbPeruLimDto;
import com.itsight.generic.BaseService;

public interface UbPeruService extends BaseService<UbPeru> {

    UbPeru findById(String id);

    UbPeruLimDto findPeLimUbigeo();

    UbPeruLimDto findPeProvByDep(String depId);

    UbPeruLimDto findPeDistByDepAndProv(String depId, String provId);
}
