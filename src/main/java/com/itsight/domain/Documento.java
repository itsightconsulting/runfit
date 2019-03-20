package com.itsight.domain;

import com.itsight.domain.base.AuditingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "documento"),
})
@Data
@EqualsAndHashCode(callSuper = false)
public class Documento extends AuditingEntity {

    @Id
    @GeneratedValue(generator = "documento_seq")
    @GenericGenerator(
            name = "documento_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "documento_seq", value = "documento_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "DocumentoId")
    private Integer id;

    @Column(nullable = true)
    private String nombre;

    @Column(nullable = true)
    private String rutaWeb;

    @Column(nullable = true)
    private String rutaReal;

    @Column(nullable = true)
    private String peso;

    @Column(nullable = true)
    private UUID uuid;

    @ManyToMany(mappedBy = "lstDocumento")
    private Set<Producto> lstProducto;

    public Documento() {
    }

}
