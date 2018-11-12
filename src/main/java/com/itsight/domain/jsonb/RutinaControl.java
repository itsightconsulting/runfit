package com.itsight.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RutinaControl implements Serializable {

    private double kilometrajeTotal;
    private double kilometrajeActual;
    private List<String> avanceSemanas;
<<<<<<< HEAD
    private String actualizarAvance;
=======
    private List<String> intensidades;

>>>>>>> 2bf8be21cc88ae0729bc8b12591c5ca30c5a8422

}
