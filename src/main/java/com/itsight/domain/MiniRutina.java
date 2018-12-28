package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.itsight.domain.jsonb.MiRutinaPk;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
})
@NamedEntityGraphs({
        @NamedEntityGraph(name = "miniRutina")
})
public class MiniRutina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MiniRutinaId")
    private int id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UsuarioId", referencedColumnName = "SecurityUserId", updatable = false)
    private Usuario usuario;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoriaId", updatable = false)
    private Categoria categoria;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<MiRutinaPk> miRutinaIds;

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public void setUsuario(int usuarioId){
        this.usuario = new Usuario(usuarioId);
    }

    public void setCategoria(Categoria categoria){
        this.categoria= categoria;
    }

    public void setCategoria(int categoriaId){
        this.categoria= new Categoria(categoriaId);
    }

}
