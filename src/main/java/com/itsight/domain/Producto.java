package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.base.AuditingEntity;
import com.itsight.json.JsonMoneySimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "producto",
                attributeNodes = {}),
        @NamedEntityGraph(name = "producto.categoria",
                attributeNodes = {
                        @NamedAttributeNode(value = "categoria")
                }),
        @NamedEntityGraph(name = "producto.all",
                attributeNodes = {
                        @NamedAttributeNode(value = "categoria"),
                        @NamedAttributeNode(value = "productoPresentacion"),
                }),
})
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
})
public class Producto extends AuditingEntity {

    @Id
    @GeneratedValue(generator = "producto_seq")
    @GenericGenerator(
            name = "producto_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "producto_seq", value = "producto_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "ProductoId")
    private int id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = true)
    private String informacion;

    @JsonSerialize(using = JsonMoneySimpleSerializer.class)
    @Column(precision = 8, scale = 2)
    private BigDecimal precioFinal;

    @Column(nullable = false)
    private boolean flagSimple;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoriaId")
    public Categoria categoria;

    /*// V I D E O
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "ProductoVideo",
            joinColumns = @JoinColumn(name = "ProductoId"),
            inverseJoinColumns = @JoinColumn(name = "VideoId")
    )
    private Set<Video> lstVideo;
    // A U D I O
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "ProductoAudio",
            joinColumns = {@JoinColumn(name = "ProductoId")},
            inverseJoinColumns = {@JoinColumn(name = "AudioId")}
    )
    private Set<Audio> lstAudio;*/
    // D O C U M E N T O  ( PDF | WORD | PPT )
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "ProductoDocumento",
            joinColumns = {@JoinColumn(name = "ProductoId")},
            inverseJoinColumns = {@JoinColumn(name = "DocumentoId")}
    )
    private Set<Audio> lstDocumento;
    // M O D U L O S
    @JsonBackReference
    @OneToMany(mappedBy = "producto", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<ProductoModulo> lstProductoModulo;
    // P R E S E N T A C I O N
    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY, optional = false, mappedBy = "producto", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private ProductoPresentacion productoPresentacion;

    public Producto() {
    }

    public Producto(Producto producto) {
        this.id = producto.getId();
        this.codigo = producto.getCodigo();
        this.nombre = producto.getNombre();
        this.informacion = producto.getInformacion();
        this.precioFinal = producto.getPrecioFinal();
        this.setFlagActivo(producto.isFlagActivo());
        this.setFlagEliminado(producto.isFlagEliminado());
        this.flagSimple = producto.isFlagSimple();
        this.getProductoPresentacion().setFlagActivo(producto.isFlagActivo());
    }

    public void setCategoria(int id) {
        // TODO Auto-generated method stub
        this.categoria = new Categoria(id);
    }

    public void setCategoria(Categoria categoria) {
        // TODO Auto-generated method stub
        this.categoria = categoria;
    }

    public void addProductoPresentacion(ProductoPresentacion productoPresentacion) {
        productoPresentacion.setProducto(this);
        productoPresentacion.setNombre(this.nombre);
        productoPresentacion.setPrecioInicial(this.precioFinal);
        productoPresentacion.setPrecioFinal(this.precioFinal);
        productoPresentacion.setUrl("/store/" + this.nombre.toLowerCase().replace(" ", "-"));
        this.productoPresentacion = productoPresentacion;
    }
}
