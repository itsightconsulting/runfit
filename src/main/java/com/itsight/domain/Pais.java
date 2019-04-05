package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
/*@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})*/
public class Pais {

    @Id
    @Column(name = "PaisId")
    private Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String nombre;

    @Size(min = 2, max = 2)
    @Column(nullable = false, updatable = false)
    private String sc;//short code

    @Size(min = 3, max = 3)
    @Column(nullable = false, updatable = false)
    private String lc;//large code

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pais")
    private List<Cliente> lstCliente;

    /*@Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private PaiDet paisDetalle;*/

    public Pais() {
    }

    public Pais(int id) {
        this.id = id;
    }

    public Pais(int id, String nombre, String sc, String lc) {
        this.id = id;
        this.nombre = nombre;
        this.sc = sc;
        this.lc = lc;
    }
}

