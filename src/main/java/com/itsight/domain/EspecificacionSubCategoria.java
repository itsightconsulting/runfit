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
public class EspecificacionSubCategoria implements Identifiable {

    public static EntityVisitor<EspecificacionSubCategoria, SubCategoriaEjercicio> ENTITY_VISITOR = new EntityVisitor<EspecificacionSubCategoria, SubCategoriaEjercicio>(EspecificacionSubCategoria.class) {
        @Override
        public SubCategoriaEjercicio getParent(EspecificacionSubCategoria visitingObject) {
            return visitingObject.getSubCategoriaEjercicio();
        }

        @Override
        public List<EspecificacionSubCategoria> getChildren(SubCategoriaEjercicio parent) {
            return parent.getEspecificacionSubCategorias();
        }

        @Override
        public void setChildren(SubCategoriaEjercicio parent) {
            parent.setEspecificacionSubCategorias(new ArrayList<EspecificacionSubCategoria>());
        }
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EspecificacionSubCategoriaId")
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private boolean flagActivo;

    @Column
    private int nivel;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubCategoriaEjercicioId")
    private SubCategoriaEjercicio subCategoriaEjercicio;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "especificacionSubCategoria")
    private List<MiniPlantilla> lstMiniPlantilla;

    @JsonSerialize
    @Transient
    private int subCategoriaId;

    @JsonSerialize
    @Transient
    private String nombreSubCategoria;

    public EspecificacionSubCategoria() {
    }

    public EspecificacionSubCategoria(int id) {
        this.id = id;
    }

    public EspecificacionSubCategoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public EspecificacionSubCategoria(int id, String nombre, int nivel) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
    }

    public EspecificacionSubCategoria(String nombre) {
        this.nombre = nombre;
    }

    public EspecificacionSubCategoria(String nombre, int subCategoriaEjercicioId) {
        this.nombre = nombre;
        this.subCategoriaEjercicio = new SubCategoriaEjercicio(subCategoriaEjercicioId);
    }

    public EspecificacionSubCategoria(String nombre, int subCategoriaEjercicioId, int nivel) {
        this.nombre = nombre;
        this.subCategoriaEjercicio = new SubCategoriaEjercicio(subCategoriaEjercicioId);
        this.nivel = nivel;
    }

    public EspecificacionSubCategoria(String nombre, int subCategoriaEjercicioId, int nivel, boolean flagActivo) {
        this.nombre = nombre;
        this.subCategoriaEjercicio = new SubCategoriaEjercicio(subCategoriaEjercicioId);
        this.nivel = nivel;
        this.flagActivo = flagActivo;
    }

    public EspecificacionSubCategoria(int id, String nombre, int nivel, boolean flagActivo, int subCategoriaId , String nombreSubCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
        this.flagActivo = flagActivo;
        this.subCategoriaId = subCategoriaId;
        this.nombreSubCategoria = nombreSubCategoria;
    }

}
