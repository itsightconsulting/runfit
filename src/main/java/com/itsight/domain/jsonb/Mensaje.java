package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Mensaje implements Serializable {

    private Integer id;
    private String msg;
    private Date fecha;
    private boolean esSalida;
}
