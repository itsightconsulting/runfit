package com.itsight.domain.jsonb;

import lombok.Data;

import java.io.Serializable;

@Data
public class Objetivo implements Serializable {
    private int id;
    private String nombre;
}
