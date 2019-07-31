package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.base.AuditingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "audio.tipoaudio",
                attributeNodes = {
                        @NamedAttributeNode(value = "tipoAudio"),
                }),
        @NamedEntityGraph(name = "audio")
})
@Data
@EqualsAndHashCode(callSuper = false)
public class Audio extends AuditingEntity {

    @Id
    @GeneratedValue(generator = "audio_seq")
    @GenericGenerator(
            name = "audio_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "audio_seq", value = "audio_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "AudioId")
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false)
    private String nombre;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column
    private String descripcion;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column
    private String rutaWeb;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column
    private String peso;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column
    private String duracion;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column
    private UUID uuid;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoAudioId")
    private TipoAudio tipoAudio;

    /*@JsonBackReference
    @ManyToMany(mappedBy = "lstAudio")
    private Set<Producto> lstProducto;*/

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "audio")
    private List<VideoAudioFavorito> lstFavoritos;

    @Transient
    private int seleccionado;

    public void setSeleccionado(int seleccionado) {
        this.seleccionado = seleccionado;
    }


    public Audio() {
    }

    public Audio(Integer id) {
        this.id = id;
    }

    public Audio(String nombre, String descripcion, String peso, String duracion, boolean flagActivo,UUID uuid, String rutaWeb,int tpAudioId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.peso = peso;
        this.duracion = duracion;
        this.setFlagActivo(flagActivo);
        this.uuid = uuid;
        this.rutaWeb = rutaWeb;
        this.tipoAudio = new TipoAudio(tpAudioId);
    }

    public Audio(Integer id, String nombre, String descripcion, String duracion, boolean flagActivo, String rutaWeb, String nombreTA) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.setFlagActivo(flagActivo);
        this.rutaWeb = rutaWeb;
        this.tipoAudio = new TipoAudio(nombreTA);
    }

    public void setTipoAudio(TipoAudio tipoAudio) {
        this.tipoAudio = tipoAudio;
    }

    public void setTipoAudio(int tipoAudioId) {
        this.tipoAudio = new TipoAudio(tipoAudioId);
    }

}
