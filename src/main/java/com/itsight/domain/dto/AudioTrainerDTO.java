package com.itsight.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.TipoAudio;
import com.itsight.domain.Trainer;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Data
public class AudioTrainerDTO implements Serializable {

    @Positive
    @NotNull
    private Integer categoriaAudioId;

    @NotBlank
    @Size(max = 60)
    private String nombre;

    /*@Size(max = )*/
    private String duracion;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false)
    private UUID uuid;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoAudioId")
    private TipoAudio tipoAudio;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerId", referencedColumnName = "SecurityUserId")
    private Trainer trainer;
}
