package com.itsight.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class TrainerFichaPOJO implements Serializable {

    private int id;
    private String nombreCompleto;
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
    private String ubigeo;
    private int canPerValoracion;
    private Double totalValoracion;
    private String rutaWebImg;
    private String servicios;
    private String cuentas;
    private String nomPag;

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

    public TrainerFichaPOJO(int id, String nombreCompleto, String especialidad, String disciplina, String acerca, String idiomas, String estudios, String metodoTrabajo, String experiencias, String resultados, String niveles, String centroTrabajo, String especialidades, String formasTrabajo, String miniGaleria, String adicionalInfo, String ubigeo, int canPerValoracion, Double totalValoracion, String rutaWebImg, String servicios, String cuentas) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
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
        this.ubigeo = ubigeo;
        this.canPerValoracion = canPerValoracion;
        this.totalValoracion = totalValoracion;
        this.rutaWebImg = rutaWebImg;
        this.servicios = servicios;
        this.cuentas = cuentas;
    }
}
