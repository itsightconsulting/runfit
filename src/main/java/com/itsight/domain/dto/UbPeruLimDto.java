package com.itsight.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UbPeruLimDto implements Serializable {


    private List<UbPeruDTO> lstDep;

    private List<UbPeruDTO> lstPro;

    private List<UbPeruDTO> lstDis;

    public UbPeruLimDto(){}

    public UbPeruLimDto(List<UbPeruDTO> lstDep, List<UbPeruDTO> lstPro, List<UbPeruDTO> lstDis) {
        this.lstDep = lstDep;
        this.lstPro = lstPro;
        this.lstDis = lstDis;
    }
}
