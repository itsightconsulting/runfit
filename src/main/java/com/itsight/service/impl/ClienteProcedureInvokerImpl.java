package com.itsight.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.domain.dto.ClienteDTO;
import com.itsight.domain.dto.ClienteFitnessDTO;
import com.itsight.service.ClienteProcedureInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.Date;
import java.util.List;

import static javax.persistence.ParameterMode.IN;

@Repository
@Transactional
public class ClienteProcedureInvokerImpl implements ClienteProcedureInvoker {

    @Autowired
    private EntityManager entityManager;

    private static final Logger LOGGER = LogManager.getLogger(ClienteProcedureInvokerImpl.class);

    @Override
    public List<ClienteDTO> getDistribucionDepartamentoCliente(Integer trainerId) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_distr_ubi_q_dynamic_where", "resultMappingClienteDistribucionDepartamento");
        storedProcedureQuery.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter(0, trainerId);
        return  storedProcedureQuery.getResultList();
    }

    @Override
    public boolean actualizarClienteById(ClienteDTO cliente, Integer clienteId) throws JsonProcessingException {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("func_update_cliente_by_id");
        ClienteFitnessDTO cliFit = cliente.getCliFit();

        try {
            //Entity: Cliente
            storedProcedureQuery
                    .registerStoredProcedureParameter(0, clienteId.getClass(), IN)
                    .registerStoredProcedureParameter(1, cliente.getClass().getDeclaredField("movil").getType(), IN)//_movil text,
                    .registerStoredProcedureParameter(2, cliente.getClass().getDeclaredField("movil").getType(), IN)//_nombres text,
                    .registerStoredProcedureParameter(3, cliente.getClass().getDeclaredField("apellidos").getType(), IN)//_apellidos text,
                    .registerStoredProcedureParameter(4, cliente.getClass().getDeclaredField("numeroDocumento").getType(), IN)//_numero_documento text,
                    .registerStoredProcedureParameter(5, cliente.getClass().getDeclaredField("paisId").getType(), IN)//_pais_id int,
                    .registerStoredProcedureParameter(6, cliente.getClass().getDeclaredField("fechaNacimiento").getType(), IN)//_fecha_modificacion timestamp,
                    .registerStoredProcedureParameter(7, cliente.getClass().getDeclaredField("fechaNacimiento").getType(), IN)//_fecha_nacimiento timestamp,
                    .registerStoredProcedureParameter(8, cliente.getClass().getDeclaredField("username").getType(), IN)//logged's username
                    .registerStoredProcedureParameter(9, cliente.getClass().getDeclaredField("tipoDocumentoId").getType(), IN)//_tipo_documento_id int,
                    .registerStoredProcedureParameter(10, cliente.getClass().getDeclaredField("ubigeo").getType(), IN);//,--END Cliente

            //Entity: ClienteFitness
            storedProcedureQuery
                    .registerStoredProcedureParameter(11, String.class, IN)//text,--jsonb
                    .registerStoredProcedureParameter(12, String.class, IN)//text,--jsonb
                    .registerStoredProcedureParameter(13, cliFit.getClass().getDeclaredField("desObjetivos").getType(), IN)//_des_objetivos text,
                    .registerStoredProcedureParameter(14, cliFit.getClass().getDeclaredField("desTerPredom").getType(), IN)//_des_ter_predom text,
                    .registerStoredProcedureParameter(15, cliFit.getClass().getDeclaredField("desTerPredomOtro").getType(), IN)//_des_ter_predom_otro text,
                    .registerStoredProcedureParameter(16, cliFit.getClass().getDeclaredField("desgasteZapatilla").getType(), IN)//_desgaste_zapatilla text,
                    .registerStoredProcedureParameter(17, cliFit.getClass().getDeclaredField("desgasteZapatillaOtro").getType(), IN)//_desgaste_zapatilla_otro text,
                    .registerStoredProcedureParameter(18, cliFit.getClass().getDeclaredField("diasSemanaCorriendo").getType(), IN)//_dias_semana_corriendo int,
                    .registerStoredProcedureParameter(19, cliFit.getClass().getDeclaredField("estadoCivil").getType(), IN)//_estado_civil int,
                    .registerStoredProcedureParameter(20, cliFit.getClass().getDeclaredField("fitElementos").getType(), IN)//_fit_elementos text,
                    .registerStoredProcedureParameter(21, cliFit.getClass().getDeclaredField("flagCalentamiento").getType(), IN)//_flag_calentamiento boolean,
                    .registerStoredProcedureParameter(22, cliFit.getClass().getDeclaredField("flagEstiramiento").getType(), IN)//_flag_estiramiento boolean,
                    .registerStoredProcedureParameter(23, cliFit.getClass().getDeclaredField("imc").getType(), IN)//double precision,
                    .registerStoredProcedureParameter(24, cliFit.getClass().getDeclaredField("kilometrajePromedioSemana").getType(), IN)//double precision,
                    .registerStoredProcedureParameter(25, String.class, IN)//text,--jsonb
                    .registerStoredProcedureParameter(26, cliFit.getClass().getDeclaredField("nivel").getType(), IN)//_nivel int,
                    .registerStoredProcedureParameter(27, cliFit.getClass().getDeclaredField("peso").getType(), IN)//double precision,
                    .registerStoredProcedureParameter(28, String.class, IN)//text,--jsonb
                    .registerStoredProcedureParameter(29, cliFit.getClass().getDeclaredField("sexo").getType(), IN)//_sexo int,
                    .registerStoredProcedureParameter(30, cliFit.getClass().getDeclaredField("talla").getType(), IN)//_talla int,
                    .registerStoredProcedureParameter(31, cliFit.getClass().getDeclaredField("tiempoDistancia").getType(), IN)//_tiempo_distancia text,
                    .registerStoredProcedureParameter(32, cliFit.getClass().getDeclaredField("tiempoUnKilometro").getType(), IN);////_tiempo_un_kilometro text


        ObjectMapper objectMapper = new ObjectMapper();

        //Entity: Cliente
        storedProcedureQuery.setParameter(0, clienteId);//
        storedProcedureQuery.setParameter(1, cliente.getMovil());//_movil text,
        storedProcedureQuery.setParameter(2, cliente.getNombres());//_nombres text,
        storedProcedureQuery.setParameter(3, cliente.getApellidos());//_apellidos text,
        storedProcedureQuery.setParameter(4, cliente.getNumeroDocumento());//_numero_documento text,
        storedProcedureQuery.setParameter(5, cliente.getPaisId());//_pais_id int,
        storedProcedureQuery.setParameter(6, new Date());//_fecha_modificacion timestamp,
        storedProcedureQuery.setParameter(7, cliente.getFechaNacimiento());//_fecha_nacimiento timestamp,
        storedProcedureQuery.setParameter(8, cliente.getUsername());//_modificado_por text, || Username equals to logged's username
        storedProcedureQuery.setParameter(9, cliente.getTipoDocumentoId());//_tipo_documento_id int,
        storedProcedureQuery.setParameter(10, cliente.getUbigeo());//_ubigeo text,--END Cliente
        //Entity: ClienteFitness
        storedProcedureQuery.setParameter(11, objectMapper.writeValueAsString(cliFit.getCompetencias()));//_competencias text,--jsonb|STARTS Cliente_Fitness
        storedProcedureQuery.setParameter(12, objectMapper.writeValueAsString(cliFit.getCondicionAnatomica()));//_condicion_anatomica text,--jsonb
        storedProcedureQuery.setParameter(13, cliFit.getDesObjetivos());//_des_objetivos text,
        storedProcedureQuery.setParameter(14, cliFit.getDesTerPredom());//_des_ter_predom text,
        storedProcedureQuery.setParameter(15, cliFit.getDesTerPredom());//_des_ter_predom_otro text,
        storedProcedureQuery.setParameter(16, cliFit.getDesgasteZapatilla());//_desgaste_zapatilla text,
        storedProcedureQuery.setParameter(17, cliFit.getDesgasteZapatilla());//_desgaste_zapatilla_otro text,
        storedProcedureQuery.setParameter(18, cliFit.getDiasSemanaCorriendo());//_dias_semana_corriendo int,
        storedProcedureQuery.setParameter(19, cliFit.getEstadoCivil());//_estado_civil int,
        storedProcedureQuery.setParameter(20, cliFit.getFitElementos());//_fit_elementos text,
        storedProcedureQuery.setParameter(21, cliFit.getFlagCalentamiento());//_flag_calentamiento boolean,
        storedProcedureQuery.setParameter(22, cliFit.getFlagEstiramiento());//_flag_estiramiento boolean,
        storedProcedureQuery.setParameter(23, cliFit.getImc());//_imc double precision,
        storedProcedureQuery.setParameter(24, cliFit.getKilometrajePromedioSemana());//_kilometraje_promedio_semana double precision,
        storedProcedureQuery.setParameter(25, objectMapper.writeValueAsString(cliFit.getMejoras()));//_mejoras text,--jsonb
        storedProcedureQuery.setParameter(26, cliFit.getNivel());//_nivel int,
        storedProcedureQuery.setParameter(27, cliFit.getPeso());//_peso double precision,
        storedProcedureQuery.setParameter(28, objectMapper.writeValueAsString(cliFit.getSalud()));//_salud text,--jsonb
        storedProcedureQuery.setParameter(29, cliFit.getSexo());//_sexo int,
        storedProcedureQuery.setParameter(30, cliFit.getTalla());//_talla int,
        storedProcedureQuery.setParameter(31, cliFit.getTiempoDistancia());//_tiempo_distancia text,
        storedProcedureQuery.setParameter(32, cliFit.getTiempoUnKilometro());//_tiempo_un_kilometro text
        return storedProcedureQuery.execute();
        }catch (NoSuchFieldException nsfexc){
            LOGGER.warn(nsfexc.getMessage());
        }catch (Exception ex){
            LOGGER.warn(ex.getMessage());
        }
        return false;
    }
}
