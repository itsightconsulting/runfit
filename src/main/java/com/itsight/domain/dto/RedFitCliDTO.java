package com.itsight.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RedFitCliDTO implements Serializable {

    private Integer id;

    private String nota;

    private String msgCliente;

    private int contadorRutinas;

    private int estadoPlan;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaFinalPlanificacion;

    private String cliNombreCompleto;

    private String cliMovil;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date cliFechaUltimoAcceso;

    private Integer cliId;

    private String cliCorreo;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date cliFechaNacimiento;

    public RedFitCliDTO(Integer id, String nota, String msgCliente, int contadorRutinas, int estadoPlan, Date fechaFinalPlanificacion, String cliNombreCompleto, String cliMovil, Date cliFechaUltimoAcceso, Integer cliId, String cliCorreo, Date cliFechaNacimiento) {
        this.id = id;
        this.nota = nota;
        this.msgCliente = msgCliente;
        this.contadorRutinas = contadorRutinas;
        this.estadoPlan = estadoPlan;
        this.fechaFinalPlanificacion = fechaFinalPlanificacion;
        this.cliNombreCompleto = cliNombreCompleto;
        this.cliMovil = cliMovil;
        this.cliFechaUltimoAcceso = cliFechaUltimoAcceso;
        this.cliId = cliId;
        this.cliCorreo = cliCorreo;
        this.cliFechaNacimiento = cliFechaNacimiento;
    }
}
