package com.itsight.domain.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TycClientePOJO implements Serializable {

    private String trainer;

    private String nomServicio;

    private String tycUrl;

    public TycClientePOJO() {
    }

    public TycClientePOJO(String trainer, String nomServicio, String tycUrl) {
        this.trainer = trainer;
        this.nomServicio = nomServicio;
        this.tycUrl = tycUrl;
    }
}
