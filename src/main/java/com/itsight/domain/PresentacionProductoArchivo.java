package com.itsight.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class PresentacionProductoArchivo {

    @Id
    @GeneratedValue(generator = "presentacion_producto_archivo_seq")
    @GenericGenerator(
            name = "presentacion_producto_archivo_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "presentacion_producto_archivo_seq", value = "presentacion_producto_archivo_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )

    @Column(name = "PresentacionProductoArchivoId")
    private int id;

    private String nombreArchivo;

    private String rutaWebArchivo;

    private String rutaRealArchivo;

//	private TipoArchivo tipoArchivo;

    private UUID uuid;

}
