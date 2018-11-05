package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RuDataGrafico implements Serializable {

    private double kms;
    private String color;
    private int percInts;
    private String imgIcon;
}
