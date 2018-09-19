package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.json.JsonMoneySimpleSerializer;

import javax.persistence.*;
import java.math.BigDecimal;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "presentacion",
                attributeNodes = {}),
        @NamedEntityGraph(name = "presentacion.producto",
                attributeNodes = {
                        @NamedAttributeNode(value = "producto")
                })
})
@Entity
public class ProductoPresentacion {

    @Id
    private int id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @JsonSerialize(using = JsonMoneySimpleSerializer.class)
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal precioInicial;

    @JsonSerialize(using = JsonMoneySimpleSerializer.class)
    @Column(nullable = true, precision = 8, scale = 2)
    private BigDecimal descuento;

    @Column(nullable = true)
    private String porcentaje;

    @Column(precision = 8, scale = 2)
    private BigDecimal precioFinal;

    @Column(precision = 5, scale = 4)
    private BigDecimal calificacion;

    @Column(nullable = false)
    private String url;

    @Column(nullable = true)
    private String nombreArchivo;

    @Column(nullable = true)
    private String rutaWebArchivo;

    @Column(nullable = true)
    private String rutaRealArchivo;

    @Column(nullable = true)
    private boolean flagActivo;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "ProductoId")
    private Producto producto;

    public ProductoPresentacion() {
    }

    public ProductoPresentacion(int id) {
        // TODO Auto-generated constructor stub
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecioInicial() {
        return precioInicial;
    }

    public void setPrecioInicial(BigDecimal precioInicial) {
        this.precioInicial = precioInicial;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(BigDecimal precioFinal) {
        this.precioFinal = precioFinal;
    }

    public BigDecimal getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(BigDecimal calificacion) {
        this.calificacion = calificacion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getRutaWebArchivo() {
        return rutaWebArchivo;
    }

    public void setRutaWebArchivo(String rutaWebArchivo) {
        this.rutaWebArchivo = rutaWebArchivo;
    }

    public String getRutaRealArchivo() {
        return rutaRealArchivo;
    }

    public void setRutaRealArchivo(String rutaRealArchivo) {
        this.rutaRealArchivo = rutaRealArchivo;
    }

    public boolean isFlagActivo() {
        return flagActivo;
    }

    public void setFlagActivo(boolean flagActivo) {
        this.flagActivo = flagActivo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }


}
