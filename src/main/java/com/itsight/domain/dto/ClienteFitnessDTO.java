package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.jsonb.*;
import com.itsight.json.JsonMoneySimpleSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteFitnessDTO implements Serializable {

    private String correoSecundario;
    private Integer estadoCivil;
    private Integer sexo;
    @JsonSerialize(using = JsonMoneySimpleSerializer.class)
    private BigDecimal peso;
    private Integer talla;
    private Integer imc;
    private Integer nivel;

    private List<CondicionMejora> mejoras;
    private String tiempoUnKilometro;
    @JsonSerialize(using = JsonMoneySimpleSerializer.class)
    private BigDecimal kilometrajePromedioSemana;
    private Integer diasSemanaCorriendo;
    private Boolean flagHaceCalentamiento;
    private Boolean flagHaceEstiramientos;
    private String desgasteZapatilla;
    private String desObjetivos;
    private String desTerPredom;
    private String tiempoDistancia;

    //JSONB
    private CondicionAnatomica condicionAnatomica;
    private Salud salud;
    private List<CompetenciaRunner> competencias;
    private List<FitElemento> fitElementos;
    private Integer frecuenciaComunicacion;
    private Integer viaConexion;
    private UsuarioDTO usuario;
    private String trainerId;
    private Object condicionAnatomicaObj;
    private String fichaId;

    public ClienteFitnessDTO(){}
}
