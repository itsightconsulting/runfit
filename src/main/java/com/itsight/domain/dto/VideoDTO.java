package com.itsight.domain.dto;

import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class VideoDTO implements Identifiable {

    public static EntityVisitor<VideoDTO, SubCategoriaVideoDTO> ENTITY_VISITOR = new EntityVisitor<VideoDTO, SubCategoriaVideoDTO>(VideoDTO.class) {
        @Override
        public SubCategoriaVideoDTO getParent(VideoDTO visitingObject) {
            return visitingObject.getSubCatVideo();
        }

        @Override
        public List<VideoDTO> getChildren(SubCategoriaVideoDTO parent) {
            return parent.getVideos();
        }

        @Override
        public void setChildren(SubCategoriaVideoDTO parent) {
            parent.setVideos(new ArrayList<>());
        }
    };

    private Integer id;

    private String nombre;

    private String rutaWeb;

    private String peso;

    private String duracion;

    private UUID uuid;

    private SubCategoriaVideoDTO subCatVideo;


    public VideoDTO(){ }

    public VideoDTO(Integer id) {
        this.id = id;
    }

    public VideoDTO(String nombre, String rutaWeb, String peso, String duracion, UUID uuid, int subCatVideoId) {
        this.nombre = nombre;
        this.rutaWeb = rutaWeb;
        this.peso = peso;
        this.duracion = duracion;
        this.uuid = uuid;
        this.subCatVideo = new SubCategoriaVideoDTO(subCatVideoId);
    }

    public void setSubCatVideo(Integer subCatId){
        this.subCatVideo = new SubCategoriaVideoDTO(subCatId);
    }

    public void setSubCatVideo(SubCategoriaVideoDTO subCategoriaVideoDTO){
        this.subCatVideo = subCategoriaVideoDTO;
    }

    @Override
    public String toString() {
        return "VideoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", rutaWeb='" + rutaWeb + '\'' +
                ", peso='" + peso + '\'' +
                ", duracion='" + duracion + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}
