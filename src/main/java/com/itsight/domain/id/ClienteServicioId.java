package com.itsight.domain.id;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClienteServicioId implements Serializable {

    @Column(name = "ClienteId")
    private Integer clienteId;

    @Column(name = "ServicioId")
    private Integer servicioId;

    private ClienteServicioId() {}

    public ClienteServicioId(
            Integer clienteId,
            Integer servicioId) {
        this.clienteId = clienteId;
        this.servicioId = servicioId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getServicioId() {
        return servicioId;
    }

    public void setServicioId(Integer servicioId) {
        this.servicioId = servicioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteServicioId that = (ClienteServicioId) o;
        return getClienteId().equals(that.getClienteId()) &&
                getServicioId().equals(that.getServicioId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClienteId(), getServicioId());
    }
}
