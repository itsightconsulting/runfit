package com.itsight.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@AllArgsConstructor
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
