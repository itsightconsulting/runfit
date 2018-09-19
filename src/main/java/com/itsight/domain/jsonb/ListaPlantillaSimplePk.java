package com.itsight.domain.jsonb;

import java.io.Serializable;
import java.util.UUID;

public class ListaPlantillaSimplePk implements Serializable {

    private int id;

    public ListaPlantillaSimplePk(){}

    public ListaPlantillaSimplePk(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
