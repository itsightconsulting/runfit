package com.itsight.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RefUpload {

    private Integer id;
    private UUID uuid;
    private String extFile;
    private String aditionalUuid;

    public RefUpload(){}

    public RefUpload(Integer id, UUID uuid, String extFile) {
        this.id = id;
        this.uuid = uuid;
        this.extFile = extFile;
    }

    public RefUpload(Integer id, UUID uuid, String extFile, String aditionalUuid) {
        this.id = id;
        this.uuid = uuid;
        this.extFile = extFile;
        this.aditionalUuid = aditionalUuid;
    }

    public void setExtFile(String extFile) {
        this.uuid = UUID.randomUUID();
        this.extFile = extFile;
    }
}
