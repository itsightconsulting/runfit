package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import com.itsight.json.JsonMoneySimpleSerializer;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "descuento", attributeNodes = {}),
        @NamedEntityGraph(name = "descuento.tipo", attributeNodes = {
                @NamedAttributeNode(value = "tipoDescuento")
        }),
        @NamedEntityGraph(name = "descuento.all", attributeNodes = {
                @NamedAttributeNode(value = "tipoDescuento"),
                @NamedAttributeNode(value = "productoPresentacion")
        }),
})
@Data
public class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DescuentoId")
    private int id;

    @JsonSerialize(using = JsonMoneySimpleSerializer.class)
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal precioInicial;

    @Column(precision = 8, scale = 2)
    private BigDecimal dscto;

    @Column(nullable = true)
    private String porcentaje;

    @Column(precision = 8, scale = 2)
    private BigDecimal precioFinal;

    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaInicio;

    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaFin;

    @Column(nullable = false)
    private boolean flagActivo;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoDescuentoId")
    private TipoDescuento tipoDescuento;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductoId")
    private ProductoPresentacion productoPresentacion;

    public Descuento() {
    }

    public Descuento(int id, String nombre) {
        this.id = id;
    }

    public void setTipoDescuento(TipoDescuento tipoDescuento) {
        // TODO Auto-generated method stub
        this.tipoDescuento = tipoDescuento;
    }

    public void setTipoDescuento(int tipoDescuentoId) {
        // TODO Auto-generated method stub
        this.tipoDescuento = new TipoDescuento(tipoDescuentoId);
    }

    public void setProductoPresentacion(ProductoPresentacion productoPresentacion) {
        // TODO Auto-generated method stub
        this.productoPresentacion = productoPresentacion;
    }

    public void setProductoPresentacion(int productoPresentacionId) {
        // TODO Auto-generated method stub
        this.productoPresentacion = new ProductoPresentacion(productoPresentacionId);
    }

}
