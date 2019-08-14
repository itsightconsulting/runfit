
package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.jsonb.CompetenciaRunner;
import com.itsight.domain.jsonb.CondicionAnatomica;
import com.itsight.domain.jsonb.CondicionMejora;
import com.itsight.domain.jsonb.Salud;
import com.itsight.json.JsonMoneyDoubleSimpleSerializer;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteFitnessDTO implements Serializable {

    private String correoSecundario;
    private Integer estadoCivil;
    private Integer sexo;
    @JsonSerialize(using = JsonMoneyDoubleSimpleSerializer.class)
    private Double peso;
    private Integer talla;
    @Digits(integer = 3, fraction = 1)
    private Double imc;
    private Integer nivel;

    @Valid
    private List<CondicionMejora> mejoras;
    private String tiempoUnKilometro;
    @JsonSerialize(using = JsonMoneyDoubleSimpleSerializer.class)
    private Double kilometrajePromedioSemana;
    private Integer diasSemanaCorriendo;
    private Boolean flagCalentamiento;
    private Boolean flagEstiramiento;
    private String desgasteZapatilla;

    private String desgasteZapatillaOtro;

    private String desObjetivos;
    private String desTerPredom;
    private String desTerPredomOtro;

    private String tiempoDistancia;

    //JSONB
    private CondicionAnatomica condicionAnatomica;
    private Salud salud;
    @Valid
    private List<CompetenciaRunner> competencias;
    @Valid
    private String fitElementos;
    private Integer frecuenciaComunicacion;
    private Integer tipoCanalVentaId;
    private UsuarioDTO usuario;
    private String trainerId;
    private Object condicionAnatomicaObj;
    private String fichaId;

    private String ubigeo;
    private String fechaCreacion;



    public ClienteFitnessDTO(){}



}
