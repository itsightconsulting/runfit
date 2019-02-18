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
    private int id;
    @Column(nullable = false)
    private String nombres;
    @Column(nullable = false)
    private String apellidoPaterno;
    @Column(nullable = false)
    private String apellidoMaterno;
    @Column(nullable = false)
    private String numeroDocumento;
    @Column(nullable = false)
    private String correo;
    @Column(length = 16)
    private String telefonoFijo;
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
    @JoinColumn(name = "TipoUsuarioId", updatable = false)
    private TipoUsuario tipoUsuario;
    
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoDocumentoId")
    private TipoDocumento tipoDocumento;

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
    private List<ClienteFitness> lstClienteFitnesses;

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

    public Cliente(int id) {
        // TODO Auto-generated constructor stub
        this.id = id;
    }

    public Cliente(String nombres, String apellidoPaterno, String apellidoMaterno) {
        // TODO Auto-generated constructor stub
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }
    public Cliente(String nombres, String apellidoPaterno, String apellidoMaterno, String correo, String telefonoFijo, String movil, String username,
                   String numeroDocumento, int tipoDocumentId, int tipoUsuarioId, boolean flagActivo) {
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.telefonoFijo = telefonoFijo;
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


    public void setTipoUsuario(int id) {
        this.tipoUsuario = new TipoUsuario(id);
    }
    public void setTipoDocumento(int id) {
        this.tipoDocumento = new TipoDocumento(id);
    }

    public String getNombreCompleto() {
        return this.apellidoPaterno + " " + this.apellidoMaterno + ", " + this.nombres;
    }
}