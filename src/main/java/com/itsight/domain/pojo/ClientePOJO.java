package com.itsight.domain.pojo;

import com.itsight.domain.dto.ClienteFitnessDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Past;
import java.math.BigInteger;
import java.util.Date;


@Data
public class ClientePOJO {
    private Integer trainerId;
    private String nombres;
    private String apellidos;
    private String numeroDocumento;
    private String correo;
    private String correoTrainer;
    private String movil;
    @Past
    private Date fechaNacimiento;
    private String username;
    /*FK's*/
    private Integer tipoDocumentoId;
    private Integer paisId;
    private String ubigeo;
    private String password;

    private Integer servicioId;
    private Integer predetFichaId;
    @Valid
    private ClienteFitnessDTO cliFit;

    private String departamentoUb;
    private BigInteger qtyClientesByDepartamento;

    private String distritoUb;
    private String distritoNombre;
    private BigInteger qtyClientesByDistrito;


    //asociados a procedimiento almacenado. pendiente de utilización en el sistema (sin constructor)
    private String provinciaUb;
    private String provinciaNombre;
    private BigInteger qtyClientesByProvincia;

    public ClientePOJO(String departamentoUb, BigInteger qtyClientesByDepartamento) {
        this.departamentoUb = departamentoUb;
        this.qtyClientesByDepartamento = qtyClientesByDepartamento;
    }

    public ClientePOJO(String distritoUb,String distritoNombre, BigInteger qtyClientesByDistrito) {
        this.distritoUb = distritoUb;
        this.distritoNombre = distritoNombre;
        this.qtyClientesByDistrito = qtyClientesByDistrito;
    }
}
