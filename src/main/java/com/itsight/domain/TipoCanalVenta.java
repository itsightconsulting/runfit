package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class TipoCanalVenta {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "TipoCanalVentaId")
        private Integer id;

        @NotNull
        @Size(min = 1, max = 255)
        @Column(nullable = false)
        private String nombre;

        @JsonBackReference
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "TipoCanalVenta")
        private List<ClienteFitness> lstClienteFitness;

        public TipoCanalVenta() {}

        public TipoCanalVenta(Integer id) {
            this.id = id;
        }

        public TipoCanalVenta(String nombre) {
            this.nombre = nombre;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public List<ClienteFitness> getLstCliente() {
            return lstClienteFitness;
        }

        public void setLstCliente(List<ClienteFitness> lstClienteFitness) {
            this.lstClienteFitness = lstClienteFitness;
        }

}
