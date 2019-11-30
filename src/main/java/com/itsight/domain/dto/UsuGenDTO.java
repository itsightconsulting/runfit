package com.itsight.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsuGenDTO implements Serializable {

    private Integer id;
    private String nombres;
    private String apellidos;
    private Integer tipoDocumento;
    private String numeroDocumento;
    private String correo;
    private String telefono;
    private String movil;
    private String username;
    private String ubigeo;
    private boolean flagActivo;
    private String uuidFp;
    private String extFp;

    public UsuGenDTO(){}

    public UsuGenDTO(Integer id, String nombres, String apellidos, Integer tipoDocumento, String numeroDocumento, String correo, String telefono, String movil, String username, String ubigeo, boolean flagActivo) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.correo = correo;
        this.telefono = telefono;
        this.movil = movil;
        this.username = username;
        this.ubigeo = ubigeo;
        this.flagActivo = flagActivo;
    }

    public UsuGenDTO(String nombres, String apellidos, String uuidFp, String extFp
                    ) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.uuidFp = uuidFp;
        this.extFp = extFp;
    }
}
