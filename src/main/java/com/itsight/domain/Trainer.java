package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.base.AuditingEntity;
import com.itsight.domain.jsonb.Rol;
import com.itsight.json.JsonDateSimpleSerializer;
import com.itsight.json.JsonMoneyDoubleSimpleSerializer;
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
        @NamedEntityGraph(name = "trainer.all", attributeNodes = {
                @NamedAttributeNode(value = "tipoDocumento")}),
        @NamedEntityGraph(name = "trainer"),
})
@Data
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@NamedNativeQueries({
        @NamedNativeQuery(query = "SELECT U.security_user_id id, U.nombres, U.apellidos, U.tipo_documento_id tipoDocumento, U.numero_documento numeroDocumento, U.correo, U.telefono, U.movil, U.username, U.ubigeo, U.flag_activo flagActivo FROM trainer U WHERE U.security_user_id = ?1",
                name = "Trainer.getById",
                resultSetMapping = "findById"),
        @NamedNativeQuery(query = "select nombres, apellidos, cast(tf.uuid_fp as text) uuidFp, tf.ext_fp extFp from trainer t inner join trainer_ficha tf on t.security_user_id = tf.trainer_id where t.security_user_id = ?1",
                name = "Trainer.getForCookieById",
                resultSetMapping = "findForCookieById")
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
    private String telefono;
    @Column(nullable = false, length = 16)
    private String movil;
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Column(nullable = false, updatable = false)
    private String username;
    @Column()
    private String codigoTrainer;
    @Column(nullable = true)
    private String fichaClienteIds;
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
    @JoinColumn(name = "TipoDocumentoId")
    private TipoDocumento tipoDocumento;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PaisId", nullable = false, updatable = false)
    private Pais pais;

    @Column
    private String ubigeo;

    @Column
    private String nomUbigeo;

    @Column(nullable = false)
    private Integer canPerValoracion;

    @JsonSerialize(using = JsonMoneyDoubleSimpleSerializer.class)
    @Column(precision = 6, scale = 2, nullable = false)
    private Double totalValoracion;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoTrainerId", nullable = false, updatable = false)
    private TipoTrainer tipoTrainer;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "SecurityUserId")
    private SecurityUser securityUser;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainer")
    private List<RedFitness> lstRedIntegrante;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainer", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<TrainerFicha> lstTrainerFicha;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainer")
    private List<PorcentajesKilometraje> lstPorcentajesKilo;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainer")
    private List<Post> lstPost;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainer", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Servicio> lstServicio;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainer", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<AnuncioTrainer> lstAnuncioTrainer;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainer")
    private List<ContactoTrainer> lstContactoTrainer;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainer")
    private List<AudioTrainer> lstAudioTrainer;

    @Transient
    private String password;

    @Transient
    @JsonSerialize
    private String nombreCompleto;

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

    public Trainer(String nombres, String apellidos, String correo, String telefono, String movil, String username,
                   String numeroDocumento, boolean flagRutinarioCe, int tipoDocumentId, boolean flagActivo) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.movil = movil;
        this.username = username;
        this.numeroDocumento = numeroDocumento;
        this.flagRutinarioCe = flagRutinarioCe;
        this.tipoDocumento = new TipoDocumento(tipoDocumentId);
        this.setFlagActivo(flagActivo);
    }

    public void setPais(Pais pais){
        this.pais = pais;
    }

    public void setPais(Integer paisId){
        this.pais = new Pais(paisId);
    }

    public void setTipoDocumento(int id) {
        this.tipoDocumento = new TipoDocumento(id);
    }

    public String getNombreCompleto() {
        return this.apellidos + ", " + this.nombres;
    }

    public void setTipoTrainer(Integer tipoTrainerId) {
        this.tipoTrainer = new TipoTrainer(tipoTrainerId);
    }

    public void setTipoTrainer(TipoTrainer tipoTrainer) {
        this.tipoTrainer = tipoTrainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trainer)) return false;
        return id != null && id.equals(((Trainer) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
