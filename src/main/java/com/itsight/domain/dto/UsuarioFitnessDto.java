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
public class UsuarioFitnessDto implements Serializable {

    private String correoSecundario;
    private int estadoCivil;
    private int sexo;
    @JsonSerialize(using = JsonMoneySimpleSerializer.class)
    private BigDecimal peso;
    private int talla;
    private int imc;
    private int nivel;
    private List<CondicionMejora> mejoras;
    private String tiempoUnKilometro;
    @JsonSerialize(using = JsonMoneySimpleSerializer.class)
    private BigDecimal kilometrajePromedioSemana;
    private int diasSemanaCorriendo;
    private boolean flagHaceCalentamiento;
    private boolean flagHaceEstiramientos;
    private String desgasteZapatilla;
    private String objetivosDescripcion;
    private String terrenoPredominante;
    private int diaDescanso;

    //
    private List<TiempoDisponible> tiemposDisponibles;
    private List<Objetivo> objetivos;
    private CondicionAnatomica condicionAnatomica;
    private List<CompetenciaRunner> competencias;
    private int frecuenciaComunicacion;
    private String viaConexion;
    private UsuarioDto usuario;
    private String entrenadorId;
    private String trainerId;

}
