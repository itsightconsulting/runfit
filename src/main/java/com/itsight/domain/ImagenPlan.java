package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "imagenplan.tipoimagen",
                attributeNodes = {
                        @NamedAttributeNode(value = "tipoImagen"),
                }),
        @NamedEntityGraph(name = "imagenplan"),
})
@Data
public class ImagenPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ImagenPlanId")
    private Integer id;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PlanId")
    private Plan plan;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoImagenId")
    private TipoImagen tipoImagen;

    @Column(nullable = false)
    private String nombreMedia;

    @Column(nullable = false)
    private String rutaMedia;

    public void setTipoImagen(TipoImagen tipoImagen) {
        this.tipoImagen = tipoImagen;
    }

    public void setTipoImagen(int tipoImagenId) {
        this.tipoImagen = new TipoImagen(tipoImagenId);
    }

    public void setPlan(int planId) {
        this.plan = new Plan(planId);
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }


}
