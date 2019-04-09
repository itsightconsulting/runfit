package com.itsight.domain.dto;

import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoriaVideoDTO implements Identifiable {

    public static EntityVisitor<CategoriaVideoDTO, GrupoVideoDTO> ENTITY_VISITOR = new EntityVisitor<CategoriaVideoDTO, GrupoVideoDTO>(CategoriaVideoDTO.class) {
        @Override
        public GrupoVideoDTO getParent(CategoriaVideoDTO visitingObject) {
            return visitingObject.getGrupoVideo();
        }

        @Override
        public List<CategoriaVideoDTO> getChildren(GrupoVideoDTO parent) {
            return parent.getLstCategoriaVideo();
        }

        @Override
        public void setChildren(GrupoVideoDTO parent) {
            parent.setLstCategoriaVideo(new ArrayList<>());
        }
    };

    private Integer id;

    private String nombre;

    private GrupoVideoDTO grupoVideo;

    private List<SubCategoriaVideoDTO> subCategoriasVideo;

    public CategoriaVideoDTO() { }

    public CategoriaVideoDTO(Integer id) {
        this.id = id;
    }

    public CategoriaVideoDTO(String nombre) {
        this.nombre = nombre;
    }

    public CategoriaVideoDTO(String nombre, Integer grupoVideoId) {
        this.nombre = nombre;
        this.grupoVideo = new GrupoVideoDTO(grupoVideoId);
    }

    @Override
    public String toString() {
        return "CategoriaVideoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", subCategoriasVideo=" + subCategoriasVideo +
                '}';
    }
}
