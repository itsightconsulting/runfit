package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Categoria {

    @Id
    @GeneratedValue(generator = "categoria_seq")
    @GenericGenerator(
            name = "categoria_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "categoria_seq", value = "categoria_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "CategoriaId")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = true)
    private String rutaWeb;

    @Column(nullable = true)
    private String rutaReal;

    @Column(nullable = true)
    private UUID uuid;

    @Column(nullable = false)
    private boolean flagActivo;

    @JsonBackReference
    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    private List<Producto> lstProducto;

    public Categoria() {
    }

    public Categoria(Integer id) {
        this.id = id;
    }

    public Categoria(Integer id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Categoria(Integer id, String nombre, String descripcion, boolean flagActivo, String rutaWeb, String rutaReal, UUID uuid) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.flagActivo = flagActivo;
        this.rutaWeb = rutaWeb;
        this.rutaReal = rutaReal;
        this.uuid = uuid;
    }

}
