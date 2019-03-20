package com.itsight.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonAudioSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@NamedEntityGraphs({
    @NamedEntityGraph(name = "tipoAudio")
})
@Entity
public class TipoAudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TipoAudioId")
    private Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String nombre;

    @Size(max = 255)
    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private boolean flagActivo;

    @JsonSerialize(using = JsonAudioSerializer.class)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoAudio")
    private List<Audio> lstAudio;

    public TipoAudio() {
    }

    public TipoAudio(int id) {
        this.id = id;
    }

    public TipoAudio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoAudio(int id, String nombre, String descripcion, boolean flagActivo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.flagActivo = flagActivo;
    }

    public TipoAudio(String nombre, String descripcion, boolean flagActivo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.flagActivo = flagActivo;
    }

    public TipoAudio(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isFlagActivo() {
        return flagActivo;
    }

    public void setFlagActivo(boolean flagActivo) {
        this.flagActivo = flagActivo;
    }

    public List<Audio> getLstAudio() {
        return lstAudio;
    }

    public void setLstAudio(List<Audio> lstAudio) {
        this.lstAudio = lstAudio;
    }

    @Override
    public String toString() {
        return "TipoAudio [id=" + id + ", nombre=" + nombre + ", flagActivo=" + flagActivo + "]";
    }

}
