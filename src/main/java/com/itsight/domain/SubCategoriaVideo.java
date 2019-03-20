package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class SubCategoriaVideo implements Identifiable {

    public static EntityVisitor<SubCategoriaVideo, CategoriaVideo> ENTITY_VISITOR = new EntityVisitor<SubCategoriaVideo, CategoriaVideo>(SubCategoriaVideo.class) {
        @Override
        public CategoriaVideo getParent(SubCategoriaVideo visitingObject) {
            return visitingObject.getCategoriaVideo();
        }

        @Override
        public List<SubCategoriaVideo> getChildren(CategoriaVideo parent) {
            return parent.getSubCategoriasVideo();
        }

        @Override
        public void setChildren(CategoriaVideo parent) {
            parent.setSubCategoriasVideo(new ArrayList<>());
        }
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubCategoriaVideoId")
    private Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private boolean flagActivo;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoriaVideoId")
    private CategoriaVideo categoriaVideo;

    @JsonSerialize
    @Transient
    private Integer categoriaId;

    @JsonSerialize
    @Transient
    private String nombreCategoria;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subCatVideo")
    private List<Video> videos;

    public SubCategoriaVideo() {}

    public SubCategoriaVideo(Integer id) {
        this.id = id;
    }

    public SubCategoriaVideo(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public SubCategoriaVideo(String nombre) {
        this.nombre = nombre;
    }

    public SubCategoriaVideo(String nombre, Integer categoriaVideoId) {
        this.nombre = nombre;
        this.categoriaVideo = new CategoriaVideo(categoriaVideoId);
    }

    public SubCategoriaVideo(String nombre, Integer categoriaVideoId, boolean flagActivo) {
        this.nombre = nombre;
        this.categoriaVideo = new CategoriaVideo(categoriaVideoId);
        this.flagActivo = flagActivo;
    }

    public SubCategoriaVideo(Integer id, String nombre, boolean flagActivo, Integer categoriaId) {
        this.id = id;
        this.nombre = nombre;
        this.flagActivo = flagActivo;
        this.categoriaId = categoriaId;
    }

    public SubCategoriaVideo(Integer id, String nombre, boolean flagActivo, Integer categoriaId, String nombreCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.flagActivo = flagActivo;
        this.categoriaId = categoriaId;
        this.nombreCategoria = nombreCategoria;
    }

}
