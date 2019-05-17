package com.itsight.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

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
    private String horario;
    private String miniGaleria;
    private String nota;
    private String correo;
    private String ubigeo;
    private int canPerValoracion;
    private Double totalValoracion;
    private String nomImgPerfil;
    private String servicios;
    private String cuentas;
    private String mediosPago;
    private String nomPag;
    private String mapCoordenadas;
    private Double mapCircleRadio;
    private String redes;

    public TrainerFichaPOJO() {
    }

    public TrainerFichaPOJO(int id, String nombreCompleto, String especialidad, String ubigeo, String acerca, int canPerValoracion, Double totalValoracion, String nomImgPerfil, String servicios, String nomPag) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
        this.ubigeo = ubigeo;
        this.acerca = acerca;
        this.canPerValoracion = canPerValoracion;
        this.totalValoracion = totalValoracion;
        this.nomImgPerfil = nomImgPerfil;
        this.servicios = servicios;
        this.nomPag = nomPag;
    }

    public TrainerFichaPOJO(int id, String nombreCompleto, String fichaClienteIds, String especialidad, String disciplina, String acerca, String idiomas, String estudios, String metodoTrabajo, String experiencias, String resultados, String niveles, String centroTrabajo, String especialidades, String formasTrabajo, String horario, String miniGaleria, String nota, String correo, String ubigeo, int canPerValoracion, Double totalValoracion, String nomImgPerfil, String servicios, String cuentas, String mediosPago, String mapCoordenadas, Double mapCircleRadio, String redes) {
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
        this.horario = horario;
        this.miniGaleria = miniGaleria;
        this.nota = nota;
        this.correo = correo;
        this.ubigeo = ubigeo;
        this.canPerValoracion = canPerValoracion;
        this.totalValoracion = totalValoracion;
        this.nomImgPerfil = nomImgPerfil;
        this.servicios = servicios;
        this.cuentas = cuentas;
        this.mediosPago = mediosPago;
        this.mapCoordenadas = mapCoordenadas;
        this.mapCircleRadio = mapCircleRadio;
        this.redes = redes;
    }
}
