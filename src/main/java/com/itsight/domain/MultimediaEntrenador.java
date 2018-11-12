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
                @NamedAttributeNode(value = "usuario"),
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

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UsuarioId", updatable = false)
    private Usuario usuario;

    @Column
    private int Tipo;

    @Column
    private String Titulo;

    @Column
    private String Descripcion;

    @Column
    private String NombreArchivoUnico;

    @Column
    private String Extension;

    @Column
    private String Duracion;

    @Column
    private String Peso;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "multimediaentrenador")
    private List<MultimediaDetalle> lstMultimediadetalle;

    @Transient
    private String RutaWeb;

    @Transient
    private int CantidadLikes;

    @Transient
    private boolean Mylike;

    public int getCantidadLikes() {
        return CantidadLikes;
    }

    public void setCantidadLikes(int cantidadLikes) {
        CantidadLikes = cantidadLikes;
    }

    public boolean isMylike() {
        return Mylike;
    }

    public void setMylike(boolean mylike) {
        Mylike = mylike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(int id) {
        this.usuario = new Usuario(id);
    }

    public int getTipo() {
        return Tipo;
    }

    public void setTipo(int tipo) {
        Tipo = tipo;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getNombreArchivoUnico() {
        return NombreArchivoUnico;
    }

    public void setNombreArchivoUnico(String nombreArchivoUnico) {
        NombreArchivoUnico = nombreArchivoUnico;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }

    public String getRutaWeb() {
        return RutaWeb;
    }

    public void setRutaWeb(String rutaWeb) {
        RutaWeb = rutaWeb;
    }

    public String getDuracion() {
        return Duracion;
    }

    public void setDuracion(String duracion) {
        Duracion = duracion;
    }

    public String getPeso() {
        return Peso;
    }

    public void setPeso(String peso) {
        Peso = peso;
    }

    public List<MultimediaDetalle> getLstMultimediadetalle() {
        return lstMultimediadetalle;
    }

    public void setLstMultimediadetalle(List<MultimediaDetalle> lstMultimediadetalle) {
        this.lstMultimediadetalle = lstMultimediadetalle;
    }
    public MultimediaEntrenador(int id){
        this.id = id;
    }
    public MultimediaEntrenador(){

    }
}
