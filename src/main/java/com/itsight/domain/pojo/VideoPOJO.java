package com.itsight.domain.pojo;

import lombok.Data;


@Data
public class VideoPOJO {

    private Integer id;

    private String nombre;

    private String rutaWeb;

    private String peso;

    private String duracion;

    private String uuid;

    private String thumbnail;

    private Boolean flagActivo;

    private Integer subCatVideoId;

    private String nombreSubCat;

    private Integer catVideoId;

    private String nomCatVid;

    private Integer rows;

    public VideoPOJO(){}

    public VideoPOJO(Integer id, String nombre, String rutaWeb, String peso, String duracion, String uuid, Boolean flagActivo, Integer subCatVideoId, String nombreSubCat, Integer catVideoId) {
        this.id = id;
        this.nombre = nombre;
        this.rutaWeb = rutaWeb;
        this.peso = peso;
        this.duracion = duracion;
        this.uuid = uuid;
        this.flagActivo = flagActivo;
        this.subCatVideoId = subCatVideoId;
        this.nombreSubCat = nombreSubCat;
        this.catVideoId = catVideoId;
    }

    public VideoPOJO(Integer id, String nombre, String rutaWeb, String peso, String duracion, String uuid, String thumbnail, Boolean flagActivo, Integer subCatVideoId, String nombreSubCat, Integer catVideoId, String nomCatVid, Integer rows) {
        this.id = id;
        this.nombre = nombre;
        this.rutaWeb = rutaWeb;
        this.peso = peso;
        this.duracion = duracion;
        this.uuid = uuid;
        this.thumbnail = thumbnail;
        this.flagActivo = flagActivo;
        this.subCatVideoId = subCatVideoId;
        this.nombreSubCat = nombreSubCat;
        this.catVideoId = catVideoId;
        this.nomCatVid = nomCatVid;
        this.rows = rows;
    }
}
