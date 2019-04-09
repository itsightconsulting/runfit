package com.itsight.domain.dto;

import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class GrupoVideoDTO implements Identifiable {

    public static EntityVisitor<GrupoVideoDTO, BagForestDTO> ENTITY_VISITOR = new EntityVisitor<GrupoVideoDTO, BagForestDTO>(GrupoVideoDTO.class) {

        @Override
        public BagForestDTO getParent(GrupoVideoDTO visitingObject) {
            return visitingObject.getForest();
        }

        @Override
        public List<GrupoVideoDTO> getChildren(BagForestDTO parent) {
            return parent.getTreesGb();
        }

        @Override
        public void setChildren(BagForestDTO parent) {
            parent.setTreesGb(new ArrayList<>());
        }
    };

    private Integer id;

    private String nombre;

    private String rutaWeb;

    private UUID uuid;

    private List<CategoriaVideoDTO> lstCategoriaVideo;

    public BagForestDTO forest;

    public GrupoVideoDTO() {
    }

    public GrupoVideoDTO(Integer id) {
        this.id = id;
    }

    public GrupoVideoDTO(String nombre) {
        this.nombre = nombre;
    }

    public GrupoVideoDTO(String nombre, Integer forestId) {
        this.nombre = nombre;
        this.forest = new BagForestDTO(forestId);
    }

    public GrupoVideoDTO(String nombre, Integer forestId, String rutaWeb, UUID uuid) {
        this.nombre = nombre;
        this.forest = new BagForestDTO(forestId);
        this.rutaWeb = rutaWeb;
        this.uuid = uuid;
    }

    public GrupoVideoDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public void setForest(BagForestDTO forest){
        this.forest = forest;
    }

    public void setForest(Integer bagForestId){
        this.forest = new BagForestDTO(bagForestId);
    }

    @Override
    public String toString() {
        return "GrupoVideoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", lstCategoriaVideo=" + lstCategoriaVideo +
                '}';
    }
}
