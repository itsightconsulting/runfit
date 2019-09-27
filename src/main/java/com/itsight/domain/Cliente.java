package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.base.AuditingEntity;
import com.itsight.domain.jsonb.Rol;
import com.itsight.domain.pojo.ClientePOJO;
import com.itsight.domain.pojo.TycClientePOJO;
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
import java.util.UUID;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "cliente.all", attributeNodes = {
                @NamedAttributeNode(value = "tipoDocumento")}),
        @NamedEntityGraph(name = "cliente")
})
@Data
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@NamedNativeQueries({
        @NamedNativeQuery(query = "SELECT U.security_user_id id, U.nombres, U.apellidos, U.tipo_documento_id tipoDocumento, U.numero_documento numeroDocumento, U.correo, U.telefono, U.movil, U.username, U.ubigeo, U.flag_activo flagActivo FROM cliente U WHERE U.security_user_id = ?1",
                name = "Cliente.getById",
                resultSetMapping = "findById"),
        @NamedNativeQuery(query = "select nombres, apellidos, coalesce(uuid_fp, '') uuidFp, ext_fp extFp from cliente c where c.security_user_id = ?1",
                name = "Cliente.getForCookieById",
                resultSetMapping = "findForCookieById")
})

@SqlResultSetMappings({
        @SqlResultSetMapping(name = "resultMappingClienteDistribucionDepartamento",
                classes = {
                        @ConstructorResult(
                                targetClass = ClientePOJO.class,
                                columns = {
                                        @ColumnResult(name = "departamentoUb"),
                                        @ColumnResult(name = "qtyClientesByDepartamento")
                                }
                        )
                }),
        @SqlResultSetMapping(name = "resultMappingClienteDistribucionDistrito",
                classes = {
                        @ConstructorResult(
                                targetClass = ClientePOJO.class,
                                columns = {
                                        @ColumnResult(name = "distritoUb"),
                                        @ColumnResult(name = "distritoNombre"),
                                        @ColumnResult(name = "qtyClientesByDistrito")
                                }
                        )
                }),
        @SqlResultSetMapping(name = "resultMappingClienteDistribucionProvincia", // pendiente de utilización en el sistema
                classes = {
                        @ConstructorResult(
                                targetClass = ClientePOJO.class,
                                columns = {
                                        @ColumnResult(name = "provinciaUb"),
                                        @ColumnResult(name = "provinciaNombre"),
                                        @ColumnResult(name = "qtyClientesByProvincia")
                                }
                        )
                }),
        @SqlResultSetMapping(name = "resultMappingGetTycServiciosById",
                classes = {
                        @ConstructorResult(
                                targetClass = TycClientePOJO.class,
                                columns = {
                                        @ColumnResult(name = "trainer"),
                                        @ColumnResult(name = "nombreServicio"),
                                        @ColumnResult(name = "tycUrl")
                                }
                        )
                })
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

    @Column(nullable = false)
    private String correo;

    @Column(length = 16)
    private String telefono;

    @Column(nullable = false, length = 16)
    private String movil;

    @Column(nullable = false, length = 1)
    private Integer sexo;

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
    @JoinColumn(name = "TipoDocumentoId")
    private TipoDocumento tipoDocumento;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PaisId", nullable = false, updatable = false)
    private Pais pais;

    @JsonBackReference
    @OneToMany(
            mappedBy = "cliente",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ClienteServicio> lstCliSer = new ArrayList<>();

    @Column
    private String ubigeo;

    @Column()
    private String uuidFp;

    @Column()
    private String extFp;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "SecurityUserId")
    private SecurityUser securityUser;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<RedFitness> lstRedIntegrante;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
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

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cliente")
    private AnuncioReceptor lstAnuncioReceptor;

    /*@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "ClienteServicio",
        joinColumns = {
            @JoinColumn(name = "ClienteId", referencedColumnName = "SecurityUserId")
        },inverseJoinColumns = {
            @JoinColumn(name = "ServicioId")
        }
    )
    private List<Servicio> servicios = new ArrayList<>();*/

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "ClienteVideo",
        joinColumns =
             @JoinColumn(name = "ClienteId", referencedColumnName = "SecurityUserId"),
        inverseJoinColumns =
             @JoinColumn(name = "VideoId", referencedColumnName = "VideoId"))
    private List<Video> videos = new ArrayList<>();

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
                   String numeroDocumento, Integer tipoDocumentId, boolean flagActivo) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.movil = movil;
        this.username = username;
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = new TipoDocumento(tipoDocumentId);
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

    public void setTipoDocumento(Integer tipoDocumentoId) {
        this.tipoDocumento = new TipoDocumento(tipoDocumentoId);
    }

    public String getNombreCompleto() {
        return this.apellidos + ", " + this.nombres;
    }
}
