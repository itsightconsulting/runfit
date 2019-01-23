package com.itsight.domain;

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

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "videoaudiofavorito"),
        @NamedEntityGraph(name = "videoaudiofavorito.all", attributeNodes = {
                @NamedAttributeNode(value = "cliente"),
                @NamedAttributeNode(value = "video"),
                @NamedAttributeNode(value = "audio")}),
        @NamedEntityGraph(name = "videoaudio_favorito", attributeNodes = {
                @NamedAttributeNode(value = "video"),
                @NamedAttributeNode(value = "audio")}),
})
@Data
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@EqualsAndHashCode(callSuper = false)
public class VideoAudioFavorito extends AuditingEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "video_audio_favorito_seq")
    @GenericGenerator(
            name = "video_audio_favorito_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "video_audio_favorito_seq", value = "video_audio_favorito_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "VideoAudioFavoritoId")
    private int id;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClienteId", referencedColumnName = "SecurityUserId", updatable = false)
    private Cliente cliente;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VideoId", updatable = false)
    private Video video;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AudioId", updatable = false)
    private Audio audio;

    @Column
    private int Tipo;

    @Column
    private String Titulo;
    @Column
    private String Descripcion;

    public VideoAudioFavorito(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(int id) {
        this.cliente = new Cliente(id);
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(int id) {
        this.video = new Video(id);
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(int id) {
        this.audio = new Audio(id);
    }

    public int getTipo() {
        return Tipo;
    }

    public void setTipo(int tipo) {
        Tipo = tipo;
    }
}
