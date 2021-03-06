package com.itsight.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RutinaPlantillaDTO implements Serializable {

    private int id;
    private String nombre;
    private int anios;
    private int meses;
    private int totalSemanas;
    private int dias;
    private int subCategoriaPlantilla;

//  private List<SemanaPlantillaDTO> semanas;

    public RutinaPlantillaDTO(){}

    public RutinaPlantillaDTO(int id, String nombre, int totalSemanas, int dias) {
        this.id = id;
        this.nombre = nombre;
        this.totalSemanas = totalSemanas;
        this.dias = dias;
    }
}
