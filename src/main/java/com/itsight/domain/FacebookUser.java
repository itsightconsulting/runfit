package com.itsight.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.base.AuditingEntity;
import com.itsight.json.JsonDateSimpleSerializer;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class FacebookUser extends AuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FacebookUserId")
    Integer id;

    @NaturalId
    @Column(nullable = false, unique = true)
    Integer facebookId;

    @Column(nullable = false)
    String primerNombre;

    @Column(nullable = false)
    String segundoNombre;


    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    Date fechaNacimiento;

    @Column(nullable = false)
    Integer genero;

    @Column(nullable = false)
    String locacion;

    @Column(nullable = false)
    String email;

    public FacebookUser() {
    }





}
