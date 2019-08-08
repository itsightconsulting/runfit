package com.itsight.domain.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data

public class ClienteFitnessPOJO {

    private Integer id;

    private String competencias;

    private String condicionAnatomica;

    private String desObjetivos;

    private String desTerPredom;

    private String desTerPredomOtro;

    private String desgasteZapatilla;

    private String desgasteZapatillaOtro;

    private Integer diasSemanaCorriendo;

    private Integer estadoCivil;

    private String fitElementos;

    private Boolean flagCalentamiento;

    private Boolean flagEstiramiento;

    private Integer frecuenciaComunicacion;

    private Double imc;

    private BigDecimal kilometrajePromedioSemana;

    private String mejoras;

    private Integer nivel;

    private BigDecimal peso;

    private String salud;

    private Integer sexo;

    private Integer talla;

    private String tiempoDistancia;

    private String tiempoUnKilometro;

    private Integer tipoCanalVentaId;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaCreacion;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaModificacion;

    private boolean flagActivo;

    private String correo;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    private Date fechaNacimiento;

    private Date fechaUltimoAcceso;

    private String movil;

    private String nombres;

    private String apellidos;

    private String numeroDocumento;

    private Integer paisId;

    private Integer tipoDocumentoId;

    private String ubigeo;

    private Integer predeterminadaFichaId;

    public ClienteFitnessPOJO(
            Integer id,
            String competencias,
            String condicionAnatomica,
            String desObjetivos,
            String desTerPredom,
            String desTerPredomOtro,
            String desgasteZapatilla,
            String desgasteZapatillaOtro,
            Integer diasSemanaCorriendo,
            Integer estadoCivil,
            String fitElementos,
            Boolean flagCalentamiento,
            Boolean flagEstiramiento,
            Integer frecuenciaComunicacion,
            Double imc,
            BigDecimal kilometrajePromedioSemana,
            String mejoras,
            Integer nivel,
            BigDecimal peso,
            String salud,
            Integer sexo,
            Integer talla,
            String tiempoDistancia,
            String tiempoUnKilometro,
            Integer tipoCanalVentaId,
            Date fechaCreacion,
            Date fechaModificacion,
            boolean flagActivo,
            String correo,
            Date fechaNacimiento,
            Date fechaUltimoAcceso,
            String movil,
            String nombres,
            String apellidos,
            String numeroDocumento,
            Integer paisId,
            Integer tipoDocumentoId,
            String ubigeo
    ){

                this.id = id;

                this.competencias = competencias;

                this.condicionAnatomica = condicionAnatomica;

                this.desObjetivos = desObjetivos;

                this.desTerPredom = desTerPredom;

                 this.desTerPredomOtro = desTerPredomOtro;

                this.desgasteZapatilla = desgasteZapatilla;

                this.desgasteZapatillaOtro = desgasteZapatillaOtro;

                this.diasSemanaCorriendo = diasSemanaCorriendo;

                this.estadoCivil = estadoCivil;

                this.fitElementos = fitElementos;

                this.flagCalentamiento = flagCalentamiento;

                this.flagEstiramiento = flagEstiramiento;

                this.frecuenciaComunicacion = frecuenciaComunicacion;

                this.imc = imc;

                this.kilometrajePromedioSemana = kilometrajePromedioSemana;

                this.mejoras = mejoras;

                this.nivel = nivel;

                this.peso = peso;

                this.salud = salud;

                this.sexo = sexo;

                this.talla = talla;

                this.tiempoDistancia = tiempoDistancia;

                this.tiempoUnKilometro = tiempoUnKilometro;

                this.tipoCanalVentaId = tipoCanalVentaId;

                this.fechaCreacion = fechaCreacion;

                this.fechaModificacion = fechaModificacion;

                this.flagActivo = flagActivo;

                this.correo = correo;

                this.fechaNacimiento = fechaNacimiento;

                this.fechaUltimoAcceso = fechaUltimoAcceso;

                this.movil = movil;

                this.nombres = nombres;

                this.apellidos = apellidos;

                this.numeroDocumento = numeroDocumento;

                this.paisId = paisId;

                this.tipoDocumentoId = tipoDocumentoId;

                this.ubigeo = ubigeo;
    }


    public ClienteFitnessPOJO(Integer id, Integer tipoCanalVentaId, String condicionAnatomica, Date fechaNacimiento, String ubigeo,  Integer sexo, Date fechaCreacion, Integer predeterminadaFichaId /*, */) {
                this.id = id;
                this.tipoCanalVentaId = tipoCanalVentaId;
                this.condicionAnatomica = condicionAnatomica;
                this.fechaNacimiento = fechaNacimiento;
                this.ubigeo = ubigeo;
                this.sexo = sexo;
                this.fechaCreacion = fechaCreacion;
                this.predeterminadaFichaId = predeterminadaFichaId;

    }
}
