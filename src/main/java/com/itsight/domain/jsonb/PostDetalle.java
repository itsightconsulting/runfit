package com.itsight.domain.jsonb;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PostDetalle implements Serializable {

    private int clienteId;

    private String nombreCompleto;

    private boolean flagLiked;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaCreacion;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaModificacion;

}
