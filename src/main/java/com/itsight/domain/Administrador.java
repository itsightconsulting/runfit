package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.base.AuditingEntity;
import com.itsight.domain.dto.SecurityUserDTO;
import com.itsight.domain.dto.UsuGenDTO;
import com.itsight.domain.jsonb.Rol;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.json.JsonDateSimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "administrador.all", attributeNodes = {
                @NamedAttributeNode(value = "tipoDocumento")}),
        @NamedEntityGraph(name = "administrador"),
})
@Data
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@NamedNativeQueries({
        @NamedNativeQuery(query = "SELECT U.security_user_id id, U.nombres, U.apellidos, U.tipo_documento_id tipoDocumento, U.numero_documento numeroDocumento, U.correo, U.telefono, U.movil, U.username, U.ubigeo, U.flag_activo flagActivo FROM administrador U WHERE U.security_user_id = ?1",
                name = "Administrador.getById",
                resultSetMapping = "findById")
})
@NamedNativeQuery(query = "select nombres, apellidos, CAST('' as text) uuidFp, CAST('' as text) extFp from administrador a where a.security_user_id = ?1",
        name = "Administrador.getForCookieById",
        resultSetMapping = "findForCookieById")
@EqualsAndHashCode(callSuper = false)
public class Administrador extends AuditingEntity implements Serializable {

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
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Column(nullable = false, updatable = false)
    private String username;
    @Column
    private String ubigeo;
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
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "SecurityUserId")
    private SecurityUser securityUser;

    @Transient
    @JsonSerialize
    private String nombreCompleto;

    @Transient
    private String password;

    public Administrador() {
        // TODO Auto-generated constructor stub
    }

    public Administrador(Integer id) {
        // TODO Auto-generated constructor stub
        this.id = id;
    }

    public Administrador(String nombres, String apellidos) {
        // TODO Auto-generated constructor stub
        this.nombres = nombres;
        this.apellidos = apellidos;
    }


    public void setTipoDocumento(Integer id) {
        this.tipoDocumento = new TipoDocumento(id);
    }

    public String getNombreCompleto() {
        return this.apellidos + ", " + this.nombres;
    }
}
