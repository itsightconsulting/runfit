package com.itsight.domain.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class ChatPOJO {

    private String ultimo;

    private String mensajes;

    private boolean flagLeido;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaCreacion;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaModificacion;

    private String fpTrainer;

    private String fpCliente;

    private String nomTrainer;

    private String nomCliente;

    public ChatPOJO() {}

    public ChatPOJO(String ultimo, String mensajes, boolean flagLeido, Date fechaCreacion, Date fechaModificacion, String fpTrainer, String nomTrainer) {
        this.ultimo = ultimo;
        this.mensajes = mensajes;
        this.flagLeido = flagLeido;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.fpTrainer = fpTrainer;
        this.nomTrainer = nomTrainer;
    }
}
