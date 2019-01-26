package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.base.AuditingEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "multimedia_entrenador"),
        @NamedEntityGraph(name = "multimedia_entrenador.all", attributeNodes = {
                @NamedAttributeNode(value = "trainer"),
        })
})
@Data
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@EqualsAndHashCode(callSuper = false)
public class MultimediaEntrenador  extends AuditingEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "multimedia_entrenador_seq")
    @GenericGenerator(
            name = "multimedia_entrenador_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "multimedia_entrenador_seq", value = "multimedia_entrenador_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )

    @Column(name = "MultimediaEntrenadorId")
    private int id;

    @Column
    private int tipo;

    @Column
    private String titulo;

    @Column
    private String descripcion;

    @Column
    private String nombreArchivoUnico;

    @Column
    private String extension;

    @Column
    private String duracion;

    @Column
    private String peso;

    @Transient
    private String rutaWeb;

    @Transient
    private int cantidadLikes;

    @Transient
    private boolean mylike;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerId", updatable = false)
    private Trainer trainer;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "multimediaEntrenador")
    private List<MultimediaDetalle> lstMultimediaDetalle;

    public List<MultimediaDetalle> getLstMultimediaDetalle() {
        return lstMultimediaDetalle;
    }

    public void setLstMultimediaDetalle(List<MultimediaDetalle> lstMultimediadetalle) {
        this.lstMultimediaDetalle = lstMultimediadetalle;
    }

    public MultimediaEntrenador(int id){
        this.id = id;
    }
    public MultimediaEntrenador(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreArchivoUnico() {
        return nombreArchivoUnico;
    }

    public void setNombreArchivoUnico(String nombreArchivoUnico) {
        this.nombreArchivoUnico = nombreArchivoUnico;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getRutaWeb() {
        return rutaWeb;
    }

    public void setRutaWeb(String rutaWeb) {
        this.rutaWeb = rutaWeb;
    }

    public int getCantidadLikes() {
        return cantidadLikes;
    }

    public void setCantidadLikes(int cantidadLikes) {
        this.cantidadLikes = cantidadLikes;
    }

    public boolean isMylike() {
        return mylike;
    }

    public void setMylike(boolean mylike) {
        this.mylike = mylike;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(int trainerId) {
        this.trainer = new Trainer(trainerId);
    }
}
