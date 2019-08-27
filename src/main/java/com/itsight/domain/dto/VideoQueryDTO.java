package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoQueryDTO implements Serializable {

    @Size(max = 30)
    private String nombre;

    @Positive
    @Max(value = 29999)
    private Integer grupoVideoId;

    @Positive
    @Max(value = 29999)
    private Integer catVideoId;
    @Positive
    @Max(value = 29999)
    private Integer subCatVideoId;

    private Boolean flag;

    @Positive
    @Max(100)
    private Integer limit;
    @PositiveOrZero
    private Integer offset;


}
