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
        @NamedEntityGraph(name = "trainer.tipoUsuario", attributeNodes = {
                @NamedAttributeNode(value = "tipoUsuario")}),
        @NamedEntityGraph(name = "trainer.all", attributeNodes = {
                @NamedAttributeNode(value = "tipoUsuario"),
                @NamedAttributeNode(value = "tipoDocumento")}),
        @NamedEntityGraph(name = "trainer"),
})
@Data
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@EqualsAndHashCode(callSuper = false)
public class Trainer extends AuditingEntity implements Serializable {

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
    @Column(length = 16)
    private String telefonoFijo;
    @Column(nullable = false, length = 16)
    private String movil;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Column(nullable = false, updatable = false)
    private String username;
    @Column(updatable = false)
    private String codigoTrainer;
    @Column(nullable = false)
    private boolean flagRutinarioCe;
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainer")
    private List<RedFitness> lstRedIntegrante;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainer")
    private List<PorcentajesKilometraje> lstPorcentajesKilo;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainer")
    private List<Post> lstPost;

    @Transient
    private String password;

    public Trainer() {
        // TODO Auto-generated constructor stub
    }

    public Trainer(Integer id) {
        // TODO Auto-generated constructor stub
        this.id = id;
    }

    public Trainer(String codigoTrainer, String nombres, String apellidos) {
        // TODO Auto-generated constructor stub
        this.codigoTrainer = codigoTrainer;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Trainer(Integer id, String codigoTrainer) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.codigoTrainer = codigoTrainer;
    }

    public Trainer(String nombres, String apellidos, String correo, String telefonoFijo, String movil, String username,
                String numeroDocumento, boolean flagRutinarioCe, int tipoDocumentId, int tipoUsuarioId, String codigoTrainer, boolean flagActivo) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefonoFijo = telefonoFijo;
        this.movil = movil;
        this.username = username;
        this.numeroDocumento = numeroDocumento;
        this.flagRutinarioCe = flagRutinarioCe;
        this.tipoDocumento = new TipoDocumento(tipoDocumentId);
        this.tipoUsuario = new TipoUsuario(tipoUsuarioId);
        this.codigoTrainer = codigoTrainer;
        this.setFlagActivo(flagActivo);
    }

    public void setTipoUsuario(int id) {
        this.tipoUsuario = new TipoUsuario(id);
    }
    public void setTipoDocumento(int id) {
        this.tipoDocumento = new TipoDocumento(id);
    }

    public String getNombreCompleto() {
        return this.apellidos + ", " + this.nombres;
    }
}
