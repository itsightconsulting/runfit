package com.itsight.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itsight.domain.jsonb.Parametro;
import com.itsight.json.JsonDateSimpleDeserializer;
import com.itsight.json.JsonDateSimpleSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@NamedEntityGraphs({
    @NamedEntityGraph(name = "confCliente"),
    @NamedEntityGraph(name = "confCliente.cliente", attributeNodes = {
        @NamedAttributeNode("cliente")
    })
})
@Data
public class ConfiguracionCliente {

    @Id
    private Integer id;

    @Type(type = "jsonb")
    @Column(name="parametros", nullable = false, columnDefinition = "jsonb")
    private List<Parametro> lstParametro;

    @Column()
    private String modificadoPor;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSimpleSerializer.class)
    @JsonDeserialize(using = JsonDateSimpleDeserializer.class)
    private Date fechaModificacion;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "ClienteId", referencedColumnName = "SecurityUserId")
    private Cliente cliente;
}
