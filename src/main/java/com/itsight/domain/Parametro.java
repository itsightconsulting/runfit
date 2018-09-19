package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Parametro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ParametroId")
    private int id;

    @Column(nullable = false, unique = true)
    private String clave;

    @Column(nullable = false)
    private String valor;

    @Column(nullable = true)
    private String modificadoPor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Temporal(TemporalType.TIMESTAMP)
//	@Column(nullable = true, updatable = true, columnDefinition="DATETIME")//MYSQL
    @Column(nullable = true, updatable = true)//PGSQL
    private Date fechaModificacion;

    public Parametro() {
        // TODO Auto-generated constructor stub
    }

    public Parametro(String clave, String valor) {
        this.clave = clave;
        this.valor = valor;
    }


}
