package com.itsight.domain.dto;

import com.itsight.util.Parseador;
import lombok.Data;

@Data
public class AprobacionDTO {
    private String hshId;
    private String nm;//base64username
    private String ml;//base64correo
    private Integer ttId;//base64tipoTrainerId

    public void decode(){
        this.nm = Parseador.getDecodeBase64(nm);
        this.ml = Parseador.getDecodeBase64(ml);
    }
}
