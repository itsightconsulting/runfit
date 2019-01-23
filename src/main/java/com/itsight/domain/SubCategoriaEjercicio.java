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
public class SubCategoriaEjercicio implements Identifiable {

    public static EntityVisitor<SubCategoriaEjercicio, CategoriaEjercicio> ENTITY_VISITOR = new EntityVisitor<SubCategoriaEjercicio, CategoriaEjercicio>(SubCategoriaEjercicio.class) {
        @Override
        public CategoriaEjercicio getParent(SubCategoriaEjercicio visitingObject) {
            return visitingObject.getCategoriaEjercicio();
        }

        @Override
        public List<SubCategoriaEjercicio> getChildren(CategoriaEjercicio parent) {
            return parent.getLstSubCategoriaEjercicio();
        }

        @Override
        public void setChildren(CategoriaEjercicio parent) {
            parent.setLstSubCategoriaEjercicio(new ArrayList<>());
        }
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubCategoriaEjercicioId")
    private int id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private boolean flagActivo;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoriaEjercicioId")
    private CategoriaEjercicio categoriaEjercicio;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subCategoriaEjercicio")
    private List<EspecificacionSubCategoria> especificacionSubCategorias;

    //Estrategia para traer los campos de la ft ya que estos no se pueden serializar directamente por la estrategia @JsonBackReference/@JsonManagedReference
    @JsonSerialize
    @Transient
    private int categoriaId;

    @JsonSerialize
    @Transient
    private String nombreCategoria;

    public SubCategoriaEjercicio() { }

    public SubCategoriaEjercicio(int id) {
        this.id = id;
    }

    public SubCategoriaEjercicio(String nombre) {
        this.nombre = nombre;
    }

    public SubCategoriaEjercicio(String nombre, int categoriaId) {
        this.nombre = nombre;
        this.categoriaEjercicio = new CategoriaEjercicio(categoriaId);
    }

    public SubCategoriaEjercicio(String nombre, int categoriaId, boolean flagActivo) {
        this.nombre = nombre;
        this.categoriaEjercicio = new CategoriaEjercicio(categoriaId);
        this.flagActivo = flagActivo;
    }

    public SubCategoriaEjercicio(int id, String nombre, boolean flagActivo, int categoriaId ,String nombreCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.flagActivo = flagActivo;
        this.categoriaId = categoriaId;
        this.nombreCategoria = nombreCategoria;
    }
}
