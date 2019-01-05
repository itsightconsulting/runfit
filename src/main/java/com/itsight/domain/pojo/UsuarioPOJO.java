package com.itsight.domain.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
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

    private int tipoUsuarioId;

    private String tipoUsuario;
}
