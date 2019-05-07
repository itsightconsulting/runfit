package com.itsight.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RefUploadIds {

    private Integer trainerId;

    private String nombresImgsGaleria;

    private String nombreImgPerfil;


    public RefUploadIds(){}

    public RefUploadIds(Integer trainerId, String nombresImgsGaleria, String nombreImgPerfil) {
        this.trainerId = trainerId;
        this.nombresImgsGaleria = nombresImgsGaleria;
        this.nombreImgPerfil = nombreImgPerfil;
    }

    public void setNombreImgPerfil(String extImgPerfil) {
        System.out.println(extImgPerfil);
        this.nombreImgPerfil = UUID.randomUUID().toString()+"."+extImgPerfil.substring(extImgPerfil.indexOf("extImgPerfil:"));
    }

    public void setNombresImgsGaleria(String extensiones) {
        String[] exts = extensiones.substring(extensiones.indexOf("extsImgsGaleria:")).split("\\|");
        String nombres = "";
        String sep = "";
        for(int i=0; i<exts.length;i++){
            nombres += sep+UUID.randomUUID().toString()+"."+exts[i];
            sep="|";
        }
        this.nombresImgsGaleria = nombres;
    }
}
