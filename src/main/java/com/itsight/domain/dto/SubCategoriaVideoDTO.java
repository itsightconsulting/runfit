package com.itsight.domain.dto;

import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubCategoriaVideoDTO implements Identifiable {

    public static EntityVisitor<SubCategoriaVideoDTO, CategoriaVideoDTO> ENTITY_VISITOR = new EntityVisitor<SubCategoriaVideoDTO, CategoriaVideoDTO>(SubCategoriaVideoDTO.class) {
        @Override
        public CategoriaVideoDTO getParent(SubCategoriaVideoDTO visitingObject) {
            return visitingObject.getCategoriaVideo();
        }

        @Override
        public List<SubCategoriaVideoDTO> getChildren(CategoriaVideoDTO parent) {
            return parent.getSubCategoriasVideo();
        }

        @Override
        public void setChildren(CategoriaVideoDTO parent) {
            parent.setSubCategoriasVideo(new ArrayList<>());
        }
    };

    private Integer id;

    private String nombre;

    private CategoriaVideoDTO categoriaVideo;

    private List<VideoDTO> videos;

    public SubCategoriaVideoDTO() {}

    public SubCategoriaVideoDTO(Integer id) {
        this.id = id;
    }

    public SubCategoriaVideoDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public SubCategoriaVideoDTO(String nombre) {
        this.nombre = nombre;
    }

    public SubCategoriaVideoDTO(String nombre, Integer categoriaVideoId) {
        this.nombre = nombre;
        this.categoriaVideo = new CategoriaVideoDTO(categoriaVideoId);
    }

    @Override
    public String toString() {
        return "SubCategoriaVideoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", videos=" + videos +
                '}';
    }
}
