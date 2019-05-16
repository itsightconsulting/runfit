package com.itsight.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class TrainerFichaPOJO implements Serializable {

    private int id;
    private String nombreCompleto;
    private String fichaClienteIds;
    private String especialidad;
    private String disciplina;
    private String acerca;
    private String idiomas;
    private String estudios;
    private String metodoTrabajo;
    private String experiencias;
    private String resultados;
    private String niveles;
    private String centroTrabajo;
    private String especialidades;
    private String formasTrabajo;
    private String miniGaleria;
    private String adicionalInfo;
    private String correo;
    private String ubigeo;
    private int canPerValoracion;
    private Double totalValoracion;
    private String rutaWebImg;
    private String servicios;
    private String cuentas;
    private String mediosPago;
    private String nomPag;
    private String mapCoordenadas;
    private String mapCircleRadio;
    private String redes;

    public TrainerFichaPOJO() {
    }

    public TrainerFichaPOJO(int id, String nombreCompleto, String especialidad, String ubigeo, String acerca, int canPerValoracion, Double totalValoracion, String rutaWebImg, String servicios, String nomPag) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
        this.ubigeo = ubigeo;
        this.acerca = acerca;
        this.canPerValoracion = canPerValoracion;
        this.totalValoracion = totalValoracion;
        this.rutaWebImg = rutaWebImg;
        this.servicios = servicios;
        this.nomPag = nomPag;
    }

    public TrainerFichaPOJO(int id, String nombreCompleto, String fichaClienteIds, String especialidad, String disciplina, String acerca, String idiomas, String estudios, String metodoTrabajo, String experiencias, String resultados, String niveles, String centroTrabajo, String especialidades, String formasTrabajo, String miniGaleria, String adicionalInfo, String correo, String ubigeo, int canPerValoracion, Double totalValoracion, String rutaWebImg, String servicios, String cuentas, String mediosPago, String mapCoordenadas, String mapCircleRadio, String redes) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.fichaClienteIds = fichaClienteIds;
        this.especialidad = especialidad;
        this.disciplina = disciplina;
        this.acerca = acerca;
        this.idiomas = idiomas;
        this.estudios = estudios;
        this.metodoTrabajo = metodoTrabajo;
        this.experiencias = experiencias;
        this.resultados = resultados;
        this.niveles = niveles;
        this.centroTrabajo = centroTrabajo;
        this.especialidades = especialidades;
        this.formasTrabajo = formasTrabajo;
        this.miniGaleria = miniGaleria;
        this.adicionalInfo = adicionalInfo;
        this.correo = correo;
        this.ubigeo = ubigeo;
        this.canPerValoracion = canPerValoracion;
        this.totalValoracion = totalValoracion;
        this.rutaWebImg = rutaWebImg;
        this.servicios = servicios;
        this.cuentas = cuentas;
        this.mediosPago = mediosPago;
        this.mapCoordenadas = mapCoordenadas;
        this.mapCircleRadio = mapCircleRadio;
        this.redes = redes;
    }
}
