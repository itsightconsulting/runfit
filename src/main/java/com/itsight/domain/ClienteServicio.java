package com.itsight.domain;

import com.itsight.domain.id.ClienteServicioId;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "ClienteServicio")
public class ClienteServicio {

    @EmbeddedId
    private ClienteServicioId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("SecurityUserId")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ServicioId")
    private Servicio servicio;

    @Column(name = "FechaCreacion")
    private Date fechaCreacion = new Date();

    public ClienteServicio() {
    }

    public ClienteServicio(Cliente cliente, Servicio servicio) {
        this.cliente = cliente;
        this.servicio = servicio;
        this.id = new ClienteServicioId(cliente.getId(), servicio.getId());
    }

    public ClienteServicioId getId() {
        return id;
    }

    public void setId(ClienteServicioId id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ClienteServicio that = (ClienteServicio) o;
        return Objects.equals(cliente, that.cliente) &&
                Objects.equals(servicio, that.servicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, servicio);
    }
}
