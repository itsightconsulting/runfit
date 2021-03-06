package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.base.AuditingEntity;
import com.itsight.domain.pojo.VideoPOJO;
import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Video extends AuditingEntity implements Identifiable {
    
    public static EntityVisitor<Video, SubCategoriaVideo> ENTITY_VISITOR = new EntityVisitor<Video, SubCategoriaVideo>(Video.class) {
        @Override
        public SubCategoriaVideo getParent(Video visitingObject) {
            return visitingObject.getSubCatVideo();
        }

        @Override
        public List<Video> getChildren(SubCategoriaVideo parent) {
            return parent.getVideos();
        }

        @Override
        public void setChildren(SubCategoriaVideo parent) {
            parent.setVideos(new ArrayList<>());
        }
    };

    @Id
    @GeneratedValue(generator = "video_seq")
    @GenericGenerator(
            name = "video_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "video_seq", value = "video_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "VideoId")
    private Integer id;

    @Column()
    private String nombre;

    @Column(updatable = false)
    private String rutaWeb;

    @Column()
    private String peso;

    @Lazy()
    @Column()
    private String duracion;

    @Column(updatable = false)
    private UUID uuid;

    @Column(updatable = false)
    private UUID thumbnail;

    @Column(updatable = false)
    private String extFile;

    @Column
    private Integer version;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubCategoriaVideoId")
    private SubCategoriaVideo subCatVideo;

    @Transient
    @JsonSerialize
    @JsonInclude(Include.NON_DEFAULT)
    private Integer subCatVideoId;

    @Transient
    @JsonSerialize
    @JsonInclude(Include.NON_DEFAULT)
    private String nombreSubCat;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "video")
    private List<VideoAudioFavorito> lstFavoritos;

    @JsonBackReference
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "ClienteVideo",
            joinColumns =  @JoinColumn(name = "VideoId", referencedColumnName = "VideoId"),
            inverseJoinColumns = @JoinColumn(name = "ClienteId", referencedColumnName = "SecurityUserId"))
     private List<Cliente> clientes;


    public Video(){}

    public Video(Integer id) {
        this.id = id;
    }

    public Video(String nombre, String rutaWeb, String peso, String duracion, UUID uuid, int subCatVideoId, boolean flagActivo) {
        this.nombre = nombre;
        this.rutaWeb = rutaWeb;
        this.peso = peso;
        this.duracion = duracion;
        this.uuid = uuid;
        this.subCatVideo = new SubCategoriaVideo(subCatVideoId);
        this.setFlagActivo(flagActivo);
    }

    public Video(String nombre, String rutaWeb, String peso, String duracion, UUID uuid, int version, int subCatVideoId, boolean flagActivo) {
        this.nombre = nombre;
        this.rutaWeb = rutaWeb;
        this.peso = peso;
        this.duracion = duracion;
        this.uuid = uuid;
        this.version = version;
        this.subCatVideo = new SubCategoriaVideo(subCatVideoId);
        this.setFlagActivo(flagActivo);
    }

    public Video(String nombre, String rutaWeb, String peso, UUID uuid, Integer subCatVideoId, boolean flagActivo) {
        this.nombre = nombre;
        this.rutaWeb = rutaWeb;
        this.peso = peso;
        this.uuid = uuid;
        this.subCatVideo = new SubCategoriaVideo(subCatVideoId);
        this.setFlagActivo(flagActivo);
    }

    public Video(Integer id, String nombre, String rutaWeb, String peso, String duracion, UUID uuid, boolean flagActivo, int subCatVideoId, String nombreSubCat) {
        this.id = id;
        this.nombre = nombre;
        this.rutaWeb = rutaWeb;
        this.peso = peso;
        this.duracion = duracion;
        this.uuid = uuid;
        this.setFlagActivo(flagActivo);
        this.subCatVideoId = subCatVideoId;
        this.nombreSubCat = nombreSubCat;
    }

    public Video(Integer id, String nombre, String rutaWeb, String peso, String duracion, UUID uuid, UUID thumbnail, boolean flagActivo, int subCatVideoId, String nombreSubCat) {
        this.id = id;
        this.nombre = nombre;
        this.rutaWeb = rutaWeb;
        this.peso = peso;
        this.duracion = duracion;
        this.uuid = uuid;
        this.thumbnail = thumbnail;
        this.setFlagActivo(flagActivo);
        this.subCatVideoId = subCatVideoId;
        this.nombreSubCat = nombreSubCat;
    }

    public Video(String nombre, String rutaWeb, String peso, String duracion, UUID uuid, UUID thumbnail, int version, int subCatVideoId, boolean flagActivo) {
        this.nombre = nombre;
        this.rutaWeb = rutaWeb;
        this.peso = peso;
        this.duracion = duracion;
        this.uuid = uuid;
        this.thumbnail = thumbnail;
        this.version = version;
        this.subCatVideo = new SubCategoriaVideo(subCatVideoId);
        this.setFlagActivo(flagActivo);
    }

    public void setSubCatVideo(Integer subCatId){
        this.subCatVideo = new SubCategoriaVideo(subCatId);
    }

}
