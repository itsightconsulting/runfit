package com.itsight.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class ServicioPOJO implements Serializable {

    private Integer id;
    private String nombre;
    private String descripcion;
    private String incluye;
    private String infoAdicional;
    private String tarifarios;
    private String tycFile;

    private BigInteger qtyClientes;
    private BigInteger qtyHombre;
    private BigInteger qtyMujer;
    private Integer trainerId;
    private String trainerNombres;

    public ServicioPOJO(Integer id, String nombre, String descripcion, String incluye, String infoAdicional, String tarifarios, String tycFile) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.incluye = incluye;
        this.infoAdicional = infoAdicional;
        this.tarifarios = tarifarios;
        this.tycFile = tycFile;
    }

    public ServicioPOJO(int trainerId, String trainerNombres, String servicioNombre, int servicioId, BigInteger qtyClientes,
                        BigInteger qtyHombre, BigInteger qtyMujer){
        this.trainerId = trainerId;
        this.trainerNombres = trainerNombres;
        this.nombre = servicioNombre;
        this.id = servicioId;
        this.qtyClientes = qtyClientes;
        this.qtyHombre = qtyHombre;
        this.qtyMujer = qtyMujer;
    }

    public ServicioPOJO(int id, String nombre, BigInteger qtyClientes, BigInteger qtyHombre, BigInteger qtyMujer){
        this.id = id;
        this.nombre = nombre;
        this.qtyClientes = qtyClientes;
        this.qtyHombre = qtyHombre;
        this.qtyMujer = qtyMujer;
    }
}
