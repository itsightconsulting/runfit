package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.base.AuditingEntity;
import com.itsight.domain.jsonb.Rol;
import com.itsight.json.JsonDateSimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "cliente.tipoUsuario", attributeNodes = {
                @NamedAttributeNode(value = "tipoUsuario")}),
        @NamedEntityGraph(name = "cliente.all", attributeNodes = {
                @NamedAttributeNode(value = "tipoUsuario"),
                @NamedAttributeNode(value = "tipoDocumento")}),
        @NamedEntityGraph(name = "cliente")
})
@Data
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@EqualsAndHashCode(callSuper = false)
public class Cliente extends AuditingEntity implements Serializable {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private String numeroDocumento;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false, length = 16)
    private String movil;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(nullable = false, updatable = false)
    private String username;

    @Type(type = "jsonb")
    @Column(nullable = false, columnDefinition = "jsonb")
    private List<Rol> roles;

    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimoAcceso;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoUsuarioId", nullable = false, updatable = false)
    private TipoUsuario tipoUsuario;
    
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoDocumentoId")
    private TipoDocumento tipoDocumento;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PaisId", nullable = false, updatable = false)
    private Pais pais;

    @Column
    private String ubigeo;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "SecurityUserId")
    private SecurityUser securityUser;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<RedFitness> lstRedIntegrante;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<ClienteFitness> lstClienteFitness;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<ClientePlan> lstClientePlan;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<VideoAudioFavorito> lstMiFavorito;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, optional = false)
    private ConfiguracionCliente confCliente;

    @Transient
    @JsonSerialize
    private String nombreCompleto;

    @Transient
    private String password;

    public Cliente() {
        // TODO Auto-generated constructor stub
    }

    public Cliente(Integer id) {
        // TODO Auto-generated constructor stub
        this.id = id;
    }

    public Cliente(String nombres, String apellidos) {
        // TODO Auto-generated constructor stub
        this.nombres = nombres;
        this.apellidos = apellidos;
    }
    public Cliente(String nombres, String apellidos, String correo, String movil, String username,
                   String numeroDocumento, Integer tipoDocumentId, Integer tipoUsuarioId, boolean flagActivo) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.movil = movil;
        this.username = username;
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = new TipoDocumento(tipoDocumentId);
        this.tipoUsuario = new TipoUsuario(tipoUsuarioId);
        this.setFlagActivo(flagActivo);
    }

    public void setConfCliente(ConfiguracionCliente confCliente) {
        confCliente.setCliente(this);
        this.confCliente = confCliente;
    }

    public void setRoles(Integer tipoUsuario){
        List<Rol> rols = new ArrayList<>();
        switch(tipoUsuario){
            case 1:
                rols.add(new Rol(1, "ROLE_ADMIN"));
            case 2:
                rols.add(new Rol(2, "ROLE_TRAINER"));
            default:
                rols.add(new Rol(3, "ROLE_RUNNER"));
        }
        this.roles = rols;
    }

    public void setRoles(List<com.itsight.domain.jsonb.Rol> roles){
        this.roles = roles;
    }

    public void setPais(Pais pais){
        this.pais = pais;
    }

    public void setPais(Integer paisId){
        this.pais = new Pais(paisId);
    }


    public void setTipoUsuario(Integer tipoUsuarioId) {
        this.tipoUsuario = new TipoUsuario(tipoUsuarioId);
    }
    public void setTipoDocumento(Integer tipoDocumentoId) {
        this.tipoDocumento = new TipoDocumento(tipoDocumentoId);
    }

    public String getNombreCompleto() {
        return this.apellidos + ", " + this.nombres;
    }
}
