package com.itsight.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class RedFitCliDTO implements Serializable {

    private Integer id;

    private String nota;

    private String msgCliente;

    private int contadorRutinas;

    private int estadoPlan;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaInicialPlanificacion;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaFinalPlanificacion;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaCreacion;


    private String cliNombreCompleto;

    private String cliMovil;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date cliFechaUltimoAcceso;

    private Integer cliId;

    private Integer predeterminadaFichaId;

    private String cliCorreo;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date cliFechaNacimiento;

    private int rows;

    private List<String> mesesCliSuspendidos;

    public RedFitCliDTO(Integer id, String nota, String msgCliente, int contadorRutinas, int estadoPlan, Date fechaInicialPlanificacion, Date fechaFinalPlanificacion, String cliNombreCompleto, String cliMovil, Date cliFechaUltimoAcceso, Date cliFechaNacimiento ,Integer cliId, String cliCorreo, Integer predeterminadaFichaId, int rows) {
        this.id = id;
        this.nota = nota;
        this.msgCliente = msgCliente;
        this.contadorRutinas = contadorRutinas;
        this.estadoPlan = estadoPlan;
        this.fechaInicialPlanificacion = fechaInicialPlanificacion;
        this.fechaFinalPlanificacion = fechaFinalPlanificacion;
        this.cliNombreCompleto = cliNombreCompleto;
        this.cliMovil = cliMovil;
        this.cliFechaUltimoAcceso = cliFechaUltimoAcceso;
        this.cliFechaNacimiento = cliFechaNacimiento;
        this.cliId = cliId;
        this.cliCorreo = cliCorreo;
        this.predeterminadaFichaId = predeterminadaFichaId;
        this.rows = rows;
    }

    public RedFitCliDTO(Integer id, String cliNombreCompleto, Date fechaCreacion,Integer cliId) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.cliNombreCompleto = cliNombreCompleto;
        this.cliId = cliId;
    }

    public RedFitCliDTO(List<String> mesesCliSuspendidos) {
        this.mesesCliSuspendidos = mesesCliSuspendidos;
    }
}
