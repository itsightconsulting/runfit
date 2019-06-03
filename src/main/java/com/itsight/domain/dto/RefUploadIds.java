package com.itsight.domain.dto;

import com.itsight.domain.Servicio;
import lombok.Data;

import java.util.List;
import java.util.UUID;

import static com.itsight.util.Enums.FileExt.JPEG;
import static com.itsight.util.Enums.FileExt.PDF;

@Data
public class RefUploadIds {

    private Integer trainerId;
    private UUID uuidFp;
    private String nombresImgsGaleria;
    private String nombresCondSvcs;

    public RefUploadIds(){}

    public void setNombresImgsGaleria(int iteraciones) {
        if(iteraciones < 1) {
            return;
        }

        String nombres = "";
        String sep = "";
        for(int i=0; i<iteraciones;i++){
            nombres += sep+UUID.randomUUID()+ JPEG.get();
            sep="|";
        }
        this.nombresImgsGaleria = nombres;

    }

    public void setNombresCondSvcs(int iteraciones, List<Servicio> servicios, String ixsCondSvcFile) {
        if(iteraciones < 1) {
            return;
        }

        String nombres = "";
        String sep = "";
        String[] condSvcIxs = ixsCondSvcFile.split("\\|");
        for(int i=0; i<iteraciones;i++){
            Integer ix = Integer.parseInt(condSvcIxs[i]);
            servicios.get(ix).setTycUUID(UUID.randomUUID());
            servicios.get(ix).setTycExt(PDF.get());
            nombres += sep+servicios.get(ix).getTycUUID()+servicios.get(ix).getTycExt();
            sep="|";
        }
        this.nombresCondSvcs = nombres;
    }
}
