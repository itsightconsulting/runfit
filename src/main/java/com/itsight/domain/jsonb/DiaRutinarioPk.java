package com.itsight.domain.jsonb;

import java.io.Serializable;

public class DiaRutinarioPk implements Serializable {

    private int id;

    public DiaRutinarioPk(){}

    public DiaRutinarioPk(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
