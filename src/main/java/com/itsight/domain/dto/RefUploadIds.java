package com.itsight.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RefUploadIds {

    private Integer trainerId;
    private String nombreImgPerfil;
    private String nombresImgsGaleria;
    private String nombresCondSvcs;




    public RefUploadIds(){}

    public RefUploadIds(Integer trainerId, String nombresImgsGaleria, String nombreImgPerfil) {
        this.trainerId = trainerId;
        this.nombresImgsGaleria = nombresImgsGaleria;
        this.nombreImgPerfil = nombreImgPerfil;
    }

    public void setNombresCondSvcs() {
        this.nombresCondSvcs = "";
    }

    public void setNombresImgsGaleria() {
        this.nombresImgsGaleria = "";
    }

    public void setNombreImgPerfil(String extImgPerfil) {
        if(extImgPerfil.contains("extImgPerfil:")){
            this.nombreImgPerfil = UUID.randomUUID().toString()+"."+extImgPerfil.substring("extImgPerfil:".length());
        }
    }

    public void setNombresImgsGaleria(String extensiones) {
        if(extensiones.contains("extsImgsGaleria:")) {
            String[] exts = extensiones.substring("extsImgsGaleria:".length()).split("\\|");
            String nombres = "";
            String sep = "";
            for(int i=0; i<exts.length;i++){
                nombres += sep+UUID.randomUUID().toString()+"."+exts[i];
                sep="|";
            }
            this.nombresImgsGaleria = nombres;
        }
    }

    public void setNombresCondSvcs(String extensiones) {
        if(extensiones.contains("extsCondSvcs:")) {
            String[] exts = extensiones.substring("extsCondSvcs:".length()).split("\\|");
            String nombres = "";
            String sep = "";
            for(int i=0; i<exts.length;i++){
                if(!exts[i].equals("")){
                    nombres += sep+UUID.randomUUID().toString()+"."+exts[i];
                    sep="|";
                }else{
                    nombres +="|";
                }
            }
            this.nombresCondSvcs = nombres;
        }
    }
}
