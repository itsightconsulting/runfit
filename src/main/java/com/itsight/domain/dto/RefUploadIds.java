package com.itsight.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RefUploadIds {

    private Integer trainerId;
    private UUID uuidFp;
    private String extFp;
    private String nombresImgsGaleria;
    private String nombresCondSvcs;
    private String staffGaleria;

    public RefUploadIds(){}

    public RefUploadIds(Integer trainerId, String nombresImgsGaleria, UUID uuidFp) {
        this.trainerId = trainerId;
        this.nombresImgsGaleria = nombresImgsGaleria;
        this.uuidFp = uuidFp;
    }

    public void setNombresCondSvcs() {
        this.nombresCondSvcs = "";
    }

    public void setNombresImgsGaleria() {
        this.nombresImgsGaleria = "";
    }

    public void setUuidFp(String extImgPerfil) {
        if(extImgPerfil.contains("extImgPerfil:")){
            this.uuidFp = UUID.randomUUID();
            this.extFp = "."+extImgPerfil.substring("extImgPerfil:".length());
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
    public void setStaffGaleria() {
        this.staffGaleria = "";
    }

    public void setStaffGaleria(String extensionesAndNombres) {
        if(extensionesAndNombres.contains("")) {
            String[] extsAndNombres = extensionesAndNombres.split("\\|");
            String nombres = "";
            String sep = "";
            for(int i=0; i<extsAndNombres.length;i++){
                String[] last = extsAndNombres[i].split(",");
                if(last.length == 2){
                    String ext = last[0];
                    String nom = last[1];
                    nombres += sep+UUID.randomUUID().toString()+"."+ext + ","+ nom;
                    sep="|";
                }
            }
            this.staffGaleria = nombres;
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
