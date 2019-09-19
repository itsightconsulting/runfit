package com.itsight.domain.dto;

import lombok.Data;

@Data
public class ChatDTO {

    private Integer cliId;
    private String correo;
    private String cuerpo;
    private String fpTrainer;
    private String nomTrainer;
    private Integer redFitId;

}
