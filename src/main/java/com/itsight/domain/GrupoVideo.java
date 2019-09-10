package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.util.EntityVisitor;
import com.itsight.util.Identifiable;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "grupoVideo"),
})
@Data
public class GrupoVideo implements Identifiable {

    public static EntityVisitor<GrupoVideo, BagForest> ENTITY_VISITOR = new EntityVisitor<GrupoVideo, BagForest>(GrupoVideo.class) {

        @Override
        public BagForest getParent(GrupoVideo visitingObject) {
            return visitingObject.getForest();
        }

        @Override
        public List<GrupoVideo> getChildren(BagForest parent) {
            return parent.getTreesGb();
        }

        @Override
        public void setChildren(BagForest parent) {
            parent.setTreesGb(new ArrayList<>());
        }
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GrupoVideoId")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column()
    private String rutaWeb;

    @Column()
    private UUID uuid;

    @Column()
    private  String extImg;
    
    @Column(nullable = false)
    private boolean flagActivo;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "grupoVideo")
    private List<CategoriaVideo> lstCategoriaVideo;

    @JsonBackReference
    @ManyToOne
    public BagForest forest;

    public GrupoVideo() {
    }

    public GrupoVideo(Integer id) {
        this.id = id;
    }

    public GrupoVideo(String nombre) {
        this.nombre = nombre;
    }

    public GrupoVideo(String nombre, Integer forestId) {
        this.nombre = nombre;
        this.forest = new BagForest(forestId);
    }

    public GrupoVideo(String nombre, Integer forestId, boolean flagActivo, String rutaWeb, UUID uuid, String extImg) {
        this.nombre = nombre;
        this.forest = new BagForest(forestId);
        this.flagActivo = flagActivo;
        this.rutaWeb = rutaWeb;
        this.uuid = uuid;
        this.extImg = extImg;
    }

    public GrupoVideo(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public GrupoVideo(String nombre, boolean flagActivo) {
        this.nombre = nombre;
        this.flagActivo = flagActivo;
    }

    public void setForest(BagForest forest){
        this.forest = forest;
    }

    public void setForest(Integer bagForestId){
        this.forest = new BagForest(bagForestId);
    }

}
