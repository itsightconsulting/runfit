package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import java.util.Date;

@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiaPlantillaDTO {
    private Integer id;
    private int dia;
    private String literal;
    private boolean flagDescanso;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fecha;

    //private List<Lista> listas;

    public DiaPlantillaDTO(){}

}
