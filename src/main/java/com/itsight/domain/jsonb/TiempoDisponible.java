package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;

@Data
public class TiempoDisponible implements Serializable {

    private int dia;
    private int minutos;
}
