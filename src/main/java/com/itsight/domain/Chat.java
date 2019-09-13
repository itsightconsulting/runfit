package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.jsonb.Mensaje;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Chat implements Serializable {

    @Id
    private Integer id;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Mensaje ultimo;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Mensaje> mensajes;

    @Column(nullable = false)
    private String fpTrainer;

    @Column(nullable = false)
    private String nomTrainer;

    @Column(nullable = true)
    private String fpCliente;

    @Column(nullable = true)
    private String nomCliente;

    @Column(nullable = false)
    private boolean flagLeido;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaCreacion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date fechaModificacion;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "ChatId", referencedColumnName = "RedFitnessId")
    private RedFitness redFitness;

}
