package com.itsight.service;

import com.itsight.domain.dto.VideoQueryDTO;
import com.itsight.domain.pojo.VideoPOJO;

import java.util.List;

public interface VideoProcedureInvoker {

    List<VideoPOJO> findAllByDynamic(VideoQueryDTO queryDTO);
}
