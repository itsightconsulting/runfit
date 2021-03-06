package com.itsight.domain.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioPOJO implements Serializable {

    private int id;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaCreacion;

    private String nombreCompleto;

    private boolean flagActivo;

    private String correo;

    private String username;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaUltimoAcceso;

    private String tipoUsuario;

    private int rows;

    public UsuarioPOJO(){}

    public UsuarioPOJO(int id, Date fechaCreacion, String nombreCompleto, boolean flagActivo, String correo, String username, Date fechaUltimoAcceso, String tipoUsuario) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.nombreCompleto = nombreCompleto;
        this.flagActivo = flagActivo;
        this.correo = correo;
        this.username = username;
        this.fechaUltimoAcceso = fechaUltimoAcceso;
        this.tipoUsuario = tipoUsuario;
    }

    public UsuarioPOJO(int id, Date fechaCreacion, String nombreCompleto, boolean flagActivo, String correo, String username, Date fechaUltimoAcceso, String tipoUsuario, int rows) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.nombreCompleto = nombreCompleto;
        this.flagActivo = flagActivo;
        this.correo = correo;
        this.username = username;
        this.fechaUltimoAcceso = fechaUltimoAcceso;
        this.tipoUsuario = tipoUsuario;
        this.rows = rows;
    }
}
