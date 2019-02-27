package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaiDet implements Serializable {

    private String paiIso;

    private String ciuIso;

    private int ciuId;

    private String disIso;

    private int disId;

}
