package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.base.AuditingEntity;
import com.itsight.domain.jsonb.Rol;
import com.itsight.domain.pojo.UsuarioPOJO;
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
        @NamedEntityGraph(name = "usuario.tipoUsuario", attributeNodes = {
                @NamedAttributeNode(value = "tipoUsuario")}),
        @NamedEntityGraph(name = "usuario.all", attributeNodes = {
                @NamedAttributeNode(value = "tipoUsuario"),
                @NamedAttributeNode(value = "tipoDocumento")}),
        @NamedEntityGraph(name = "usuario"),
})
@Data
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@EqualsAndHashCode(callSuper = false)
@SqlResultSetMapping(
        name="usuarioPojo",
        classes={
                @ConstructorResult(
                        targetClass=UsuarioPOJO.class,
                        columns={
                                @ColumnResult(name="id"),
                                @ColumnResult(name="fechaCreacion"),
                                @ColumnResult(name="nombreCompleto"),
                                @ColumnResult(name="flagActivo"),
                                @ColumnResult(name="correo"),
                                @ColumnResult(name="username"),
                                @ColumnResult(name="fechaUltimoAcceso"),
                                @ColumnResult(name="tipoUsuario")
                        }
                )
        }
)
@NamedNativeQuery(name = "Usuario.getAllFromNativeQuery", query = "" +
        "select gen.id, gen.fechaCreacion, gen.nombreCompleto, gen.flagActivo, gen.correo, gen.username, gen.fechaUltimoAcceso, p.nombre tipoUsuario from (select t.security_user_id id, t.fecha_creacion fechaCreacion," +
        "concat(t.apellido_paterno,' ',t.apellido_materno,' ', t.nombres) nombreCompleto," +
        "t.flag_activo flagActivo, t.correo, t.username, t.tipo_usuario_id, t.fecha_ultimo_acceso fechaUltimoAcceso " +
        "from trainer t " +
        "union all " +
        "select u.security_user_id id, u.fecha_creacion fechaCreacion, " +
        "concat(u.apellido_paterno,' ',u.apellido_materno,' ', u.nombres) nombreCompleto," +
        "u.flag_activo flagActivo, u.correo, u.username, u.tipo_usuario_id, u.fecha_ultimo_acceso fechaUltimoAcceso " +
        "from usuario u " +
        "order by 1) gen inner join tipo_usuario p on gen.tipo_usuario_id=p.tipo_usuario_id where flagActivo=true", resultSetMapping = "usuarioPojo")
public class Usuario extends AuditingEntity implements Serializable {

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
    @JoinColumn(name = "TipoUsuarioId")
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "integrante")
    private List<RedFitness> lstRedIntegrante;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private List<UsuarioFitness> lstUsuarioFitness;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private List<UsuarioPlan> lstUsuarioPlan;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private List<VideoAudioFavorito> lstMiFavorito;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private List<MultimediaEntrenador> lstMultimediaentrenador;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private List<MultimediaDetalle> lstMultimediadetalle;

    @Transient
    @JsonSerialize
    private String nombreCompleto;

    @Transient
    private String password;

    public Usuario() {
        // TODO Auto-generated constructor stub
    }

    public Usuario(int id) {
        // TODO Auto-generated constructor stub
        this.id = id;
    }

    public Usuario(String codigoTrainer, String nombres, String apellidoPaterno, String apellidoMaterno) {
        // TODO Auto-generated constructor stub
        this.codigoTrainer = codigoTrainer;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public Usuario(int id, String codigoTrainer) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.codigoTrainer = codigoTrainer;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                '}';
    }
}
