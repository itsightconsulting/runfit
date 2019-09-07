package com.itsight.domain.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TycClientePOJO implements Serializable {

    private String trainer;

    private String nombreServicio;

    private String tycUrl;

    public TycClientePOJO() {
    }

    public TycClientePOJO(String trainer, String nombreServicio, String tycUrl) {
        this.trainer = trainer;
        this.nombreServicio = nombreServicio;
        this.tycUrl = tycUrl;
    }
}
