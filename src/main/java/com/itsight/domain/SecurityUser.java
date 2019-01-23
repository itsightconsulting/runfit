package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itsight.domain.pojo.UsuarioPOJO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@NamedEntityGraphs({@NamedEntityGraph(name = "securityUser"),
                    @NamedEntityGraph(name = "securityUser.roles", attributeNodes = {
                        @NamedAttributeNode(value = "roles")
                        })
})
@Entity
@SqlResultSetMappings({
    @SqlResultSetMapping(
        name="allUsers",
        classes = {
                @ConstructorResult(
                        targetClass = UsuarioPOJO.class,
                        columns = {
                                @ColumnResult(name = "id"),
                                @ColumnResult(name = "fechaCreacion"),
                                @ColumnResult(name = "nombreCompleto"),
                                @ColumnResult(name = "flagActivo"),
                                @ColumnResult(name = "correo"),
                                @ColumnResult(name = "username"),
                                @ColumnResult(name = "fechaUltimoAcceso"),
                                @ColumnResult(name = "tipoUsuarioId"),
                                @ColumnResult(name = "tipoUsuario")
                        }
                )
        }
    ),
})
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "fn_validacion_correo",
        procedureName = "check_correo_existe",
        parameters = {
            @StoredProcedureParameter(name = "_correo", mode = ParameterMode.IN, type = String.class),
            @StoredProcedureParameter(name = "result", mode = ParameterMode.OUT, type = Boolean.class)
        })
})
public class SecurityUser{

    @Id
    @GeneratedValue(generator = "sec_user_seq")
    @GenericGenerator(
            name = "sec_user_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "sec_user_seq", value = "sec_user_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "SecurityUserId")
    private int id;
    @Column(name = "Username", unique = true, updatable = false)
    private String username;
    @Column(name = "Password", nullable = false)
    private String password;
    @Column(name = "Enabled", nullable = false)
    private boolean enabled;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "securityUser", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<SecurityRole> roles;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "securityUser", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, optional = false)
    private Cliente cliente;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "securityUser", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, optional = false)
    private Trainer trainer;

    public SecurityUser() {
    }

    public SecurityUser(int id) {
        this.id = id;
    }

    public SecurityUser(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public SecurityUser(String username, String password, Set<SecurityRole> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public SecurityUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Set<SecurityRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SecurityRole> roles) {
        for (SecurityRole x : roles) {
            x.setSecurityUser(this);
        }
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
