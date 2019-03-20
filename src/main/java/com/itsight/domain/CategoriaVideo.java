package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class CategoriaVideo implements Identifiable {

    public static EntityVisitor<CategoriaVideo, GrupoVideo> ENTITY_VISITOR = new EntityVisitor<CategoriaVideo, GrupoVideo>(CategoriaVideo.class) {
        @Override
        public GrupoVideo getParent(CategoriaVideo visitingObject) {
            return visitingObject.getGrupoVideo();
        }

        @Override
        public List<CategoriaVideo> getChildren(GrupoVideo parent) {
            return parent.getLstCategoriaVideo();
        }

        @Override
        public void setChildren(GrupoVideo parent) {
            parent.setLstCategoriaVideo(new ArrayList<>());
        }
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoriaVideoId")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private boolean flagActivo;

    //Rutinario
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GrupoVideoId")
    private GrupoVideo grupoVideo;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoriaVideo")
    private List<SubCategoriaVideo> subCategoriasVideo;

    //Estrategia para traer los campos de la ft ya que estos no se pueden serializar directamente por la estrategia @JsonBackReference/@JsonManagedReference
    @JsonSerialize
    @Transient
    private Integer grupoVideoId;

    @JsonSerialize
    @Transient
    private String nombreGrupo;

    public CategoriaVideo() { }

    public CategoriaVideo(Integer id) {
        this.id = id;
    }

    public CategoriaVideo(String nombre) {
        this.nombre = nombre;
    }

    public CategoriaVideo(String nombre, Integer grupoVideoId) {
        this.nombre = nombre;
        this.grupoVideo = new GrupoVideo(grupoVideoId);
    }

    public CategoriaVideo(String nombre, Integer grupoVideoId, boolean flagActivo) {
        this.nombre = nombre;
        this.grupoVideo = new GrupoVideo(grupoVideoId);
        this.flagActivo = flagActivo;
    }

    public CategoriaVideo(Integer id, String nombre, boolean flagActivo, Integer grupoVideoId , String nombreGrupo) {
        this.id = id;
        this.nombre = nombre;
        this.flagActivo = flagActivo;
        this.grupoVideoId = grupoVideoId;
        this.nombreGrupo = nombreGrupo;
    }
}
