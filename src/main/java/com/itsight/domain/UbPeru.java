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
public class UbPeru {

    @Id
    @Column(name = "UbPeruId")
    private String id;

    @Column(nullable = false, updatable = false)
    private String dep;

    @Column(nullable = false, updatable = false)
    private String pro;//short code

    @Column(nullable = false, updatable = false)
    private String dis;//large code

    @Column(nullable = false, updatable = false)
    private String ub;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pais")
    private List<Cliente> lstCliente;

    /*@Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private PaiDet paisDetalle;*/

    public UbPeru() {
    }

    public UbPeru(String id) {
        this.id = id;
    }

    public UbPeru(String id, String dep, String pro, String dis, String ub) {
        this.id = id;
        this.dep = dep;
        this.pro = pro;
        this.dis = dis;
        this.ub = ub;
    }
}

