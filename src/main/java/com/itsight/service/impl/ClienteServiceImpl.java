package com.itsight.service.impl;

import com.itsight.domain.*;
import com.itsight.domain.dto.ClienteDTO;
import com.itsight.domain.jsonb.Rol;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ClienteRepository;
import com.itsight.repository.SecurityRoleRepository;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.MailContents;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.itsight.util.Enums.Mail.NUEVO_CLIENTE;
import static com.itsight.util.Enums.ResponseCode;

@Service
@Transactional
public class ClienteServiceImpl extends BaseServiceImpl<ClienteRepository> implements ClienteService {

    private static final Logger LOGGER = LogManager.getLogger(ClienteServiceImpl.class);

    private SecurityUserRepository securityUserRepository;

    private SecurityRoleRepository securityRoleRepository;

    private RolService rolService;

    private EmailService emailService;

    private ConfiguracionClienteService configuracionClienteService;

    private ConfiguracionGeneralService configuracionGeneralService;

    private RedFitnessService redFitnessService;

    private CorreoService correoService;

    @Autowired
    private ClienteService clienteService;

    @Value("${domain.name}")
    private String domainName;

    public ClienteServiceImpl(
            ClienteRepository repository,
            SecurityUserRepository securityUserRepository,
            SecurityRoleRepository securityRoleRepository,
            RolService rolService,
            EmailService emailService,
            ConfiguracionClienteService configuracionClienteService,
            ConfiguracionGeneralService configuracionGeneralService,
            RedFitnessService redFitnessService,
            CorreoService correoService) {
        super(repository);
        // TODO Auto-generated constructor stub
        this.securityRoleRepository = securityRoleRepository;
        this.securityUserRepository = securityUserRepository;
        this.rolService = rolService;
        this.emailService = emailService;
        this.configuracionClienteService = configuracionClienteService;
        this.configuracionGeneralService = configuracionGeneralService;
        this.redFitnessService = redFitnessService;
        this.correoService = correoService;
    }

    @Override
    public List<Cliente> findAll() {
        // TODO Auto-generated method stub
        return repository.findAllByOrderByIdDesc();
    }

    @Override
    public List<Cliente> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivoOrderByIdDesc(flagActivo);
    }

    @Override
    public Cliente findOne(Integer id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    public Cliente findOneWithFT(Integer id) {
        // TODO Auto-generated method stub
        return repository.getById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        // TODO Auto-generated method stub
        return repository.save(cliente);
    }

    @Override
    public Cliente update(Cliente cliente) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(cliente);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    public List<Cliente> findByNombreCompleto(String nombreCompleto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String findPasswordById(Integer id) {
        // TODO Auto-generated method stub
        return securityUserRepository.findPasswordById(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Cliente> findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Cliente> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Cliente> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Cliente> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Cliente> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Cliente> listarPorFiltro(String comodin, String estado, String fk) {//fk:perfil
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UsuarioPOJO> listarPorFiltroDto(String comodin, String estado, String fk) {
        return repository.findByNombreCompleto(comodin);
    }

    @Override
    public String registrar(Cliente cliente, String rols) {
        // TODO Auto-generated method stub
        cliente.setUsername(cliente.getUsername().toLowerCase());
        if (securityUserRepository.findByUsername(cliente.getUsername()) == null) {
            try {
                if(cliente.getTipoUsuario().getId() == 3){//3: Cliente
                    String originalPassword = cliente.getPassword();
                    String[] arrRoles = rols.split(",");
                    List<com.itsight.domain.Rol> lstRoles = rolService.findByIdsIn(Arrays.asList(Parseador.stringArrayToIntArray(arrRoles)));
                    List<Rol> lstJsonRoles = lstRoles.stream()
                            .map(rol -> new Rol(rol.getId(), rol.getRol()))
                            .collect(Collectors.toList());
                    cliente.setRoles(lstJsonRoles);

                    cliente.setPassword(Utilitarios.encoderPassword(cliente.getPassword()));
                    //Añadiendo las credenciales de ingreso
                    SecurityUser secUser = new SecurityUser(cliente.getUsername().toLowerCase(), cliente.getPassword(), cliente.isFlagActivo());
                    //Añadiendo el role de colaborador
                    Set<SecurityRole> listSr = new HashSet<>();
                    for (Rol rol : cliente.getRoles()){
                        listSr.add(new SecurityRole(rol.getNombre(), secUser));
                    }
                    //Adding to User
                    secUser.setRoles(listSr);
                    //secUser.addUsuario(cliente);
                    //Validamos si presenta un rol de entrenador
                    int flagTrainer = cliente.getRoles().stream().filter(r -> r.getId() == 2).findFirst().isPresent()?1:0;
                    //Guardando cliente de autenticacion
                    cliente.setSecurityUser(secUser);
                    clienteService.save(cliente);

                    //Enviando correo al nuevo cliente
                    StringBuilder sb = MailContents.contenidoNuevoUsuario(cliente.getUsername(), originalPassword, cliente.getTipoUsuario().getId(), domainName);
                    emailService.enviarCorreoInformativo("Bienvenido a la familia", cliente.getCorreo(), sb.toString());
                    return ResponseCode.REGISTRO.get()+','+cliente.getId()+','+flagTrainer;
                }
                return ResponseCode.EX_VALIDATION_FAILED.get();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return ResponseCode.VF_USUARIO_REPETIDO.get();
    }

    @Override
    public String registroFull(ClienteDTO cliente) {
        boolean pickTrainer = false;
        if(cliente.getTrainerId() != null && cliente.getTrainerId() > 0){
            Boolean suserActive = securityUserRepository.findEnabledById(cliente.getTrainerId());
            if(suserActive == null){
                return ResponseCode.NOT_FOUND_MATCHES.get();
            } else if(suserActive){
                pickTrainer = true;
            } else {
                return ResponseCode.NOT_FOUND_MATCHES.get();
            }
        }
        LOGGER.info(cliente.getUsername()+": "+cliente.getPassword());
        Cliente objCli = new Cliente();
        objCli.setFlagActivo(true);
        //Agregando roles

        objCli.setRoles(Enums.TipoUsuario.CLIENTE.ordinal());
        objCli.setTipoUsuario(Enums.TipoUsuario.CLIENTE.ordinal());
        objCli.setTipoDocumento(cliente.getTipoDocumentoId());
        objCli.setPais(cliente.getPaisId());

        SecurityUser secCliente = new SecurityUser(cliente.getUsername().toLowerCase(), cliente.getPassword());
        secCliente.setRoles(Enums.TipoUsuario.CLIENTE.ordinal());

        objCli.setSecurityUser(secCliente);

        BeanUtils.copyProperties(cliente, objCli);

        ClienteFitness objCliFit = new ClienteFitness();
        BeanUtils.copyProperties(cliente.getCliFit(), objCliFit);
        objCliFit.setCliente(objCli);

        List<ClienteFitness> clienteFitnesses = new ArrayList<>();
        clienteFitnesses.add(objCliFit);
        objCli.setLstClienteFitness(clienteFitnesses);

        ConfiguracionCliente cliConfg = new ConfiguracionCliente();
        cliConfg.setLstParametro(configuracionGeneralService.getAll().stream()
                .map(cg-> new com.itsight.domain.jsonb.Parametro(
                            cg.getNombre(),
                            cg.getValor()))
                            .collect(Collectors.toList()));
        cliConfg.setCliente(objCli);
        //Registrando en cascada SEC_USER->CLIENTE->CONFIG_CLIENTE (OneToOne relationships)
        configuracionClienteService.save(cliConfg);

        //Agregandolo a la red de su entrenador
        if(pickTrainer){
            redFitnessService.save(new RedFitness(cliente.getTrainerId(), objCli.getId(), Utilitarios.getPeticionParaTipoRutina(cliente.getCliFit().getFichaId())));
            emailService.enviarCorreoInformativo("Nuevo cliente Runfit","contoso.peru@gmail.com" /*cliente.getCorreoTrainer()*/, "<h1>Tienes un nuevo cliente</h1>");
        }
        //Enviando correo al nuevo cliente
        //Obtener cuerpo del correo
        Correo correo = correoService.findOne(NUEVO_CLIENTE.get());
        //Envio de correo
        String cuerpo = String.format(correo.getBody(), cliente.getUsername(), cliente.getPassword(), domainName);
        emailService.enviarCorreoInformativo("Bienvenido a la familia","contoso.peru@gmail.com" /*cliente.getCorreo()*/, cuerpo);
        return ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(Cliente cliente, String rols) {
        // TODO Auto-generated method stub
        try {
            Cliente qCliente = repository.getById(cliente.getId());
            cliente.setFechaUltimoAcceso(qCliente.getFechaUltimoAcceso());
            cliente.setCreador(qCliente.getCreador());
            cliente.setRowVersion(qCliente.getRowVersion());
            cliente.setRoles(qCliente.getRoles());
            cliente.setCorreo(qCliente.getCorreo());
            cliente.setFlagEliminado(qCliente.isFlagEliminado());

            // - Verificando si hubo cambio de roles -
            //Los roles antiguos
            Integer[] rolesIniciales = cliente.getRoles().stream().map(rol -> rol.getId()).toArray(Integer[]::new);
            //rols representa los roles que vienen de la vista como un string separados con ","
            //Los roles nuevos
            Integer[] rolesFinales = Parseador.stringArrayToIntArray(rols.split(","));

            List<Integer> noRequierenActualizacion = new ArrayList<>();
            List<Integer> debenEliminarse = new ArrayList<>();
            List<Integer> nuevosInsert = new ArrayList<>();

            //En esta parte verificamos la diferencia entre los roles iniciales(A) y los finales(B) separando la interseccion de estos en una lista y los sobrantes
            // de (A) en otra para su posterior eliminacion
            for (int i = 0; i < rolesIniciales.length; i++) {
                boolean coincide = false;
                for (int ii = 0; ii < rolesFinales.length; ii++) {
                    if (rolesIniciales[i] == rolesFinales[ii]) {
                        coincide = true;
                        noRequierenActualizacion.add(rolesIniciales[i]);
                    }
                }
                if (!coincide)
                    debenEliminarse.add(rolesIniciales[i]);
            }
            //Ahora ayudandonos de la interseccion(noRequierenActualizacion: C) obtenemos los sobrantes de (B) que serán los que al final se insertarán al ser estos ineditos
            for (int i = 0; i < rolesFinales.length; i++) {
                boolean coincide = false;
                for (Integer in: noRequierenActualizacion) {
                    if(in == rolesFinales[i])
                        coincide = true;
                }
                //Si no es interseccion de (B) y (C) = nuevos a insertar
                if(!coincide)
                    nuevosInsert.add(rolesFinales[i]);
            }

            List<Rol> lstRolesNueva = new ArrayList<>();
            if(nuevosInsert.size()>0)
                for (Integer n : nuevosInsert){
                com.itsight.domain.Rol rol = rolService.findOne(n);
                securityRoleRepository.save(new SecurityRole(rol.getRol(), cliente.getId()));
                lstRolesNueva.add(new Rol(n, rol.getRol()));
                }

            if(debenEliminarse.size()>0) {
                for (Integer n : debenEliminarse) {
                    com.itsight.domain.Rol rol = rolService.findOne(n);
                    Integer id = securityRoleRepository.findBySecurityUserIdAndRole(qCliente.getId(), rol.getRol()).getId();
                    securityRoleRepository.deleteById(id);
                }
                //Agregamos a la nueva lista creada solo los roles que se mantendrán de esta forma indirectamente
                //Ya estaremos borrando de la columna jsonb los que ya se hayan eliminado de la tabla
                for (Rol r: cliente.getRoles()) {
                    for (Integer x : noRequierenActualizacion){
                        if(r.getId() == x){
                            lstRolesNueva.add(r);
                        }
                    }
                }
            }else{//En caso no se elimine ningun roles transfiere todos los roles "antiguos" a una nueva lista
                //que posiblemente ya tenga algunos elementos en el caso se hayan agregado nuevos roles
                lstRolesNueva.addAll(cliente.getRoles());
            }
            //Finalmente volvemos a setear los roles con la nueva lista y guardamos
            cliente.setRoles(lstRolesNueva);
            if(qCliente.isFlagActivo() != cliente.isFlagActivo())
                securityUserRepository.updateEstadoByUsername(cliente.getUsername(), cliente.isFlagActivo());
            repository.saveAndFlush(cliente);
            return ResponseCode.ACTUALIZACION.get();
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseCode.EX_GENERIC.get();
        }
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

    @Override
    public void actualizarFechaUltimoAcceso(Date date, String id) {
        // TODO Auto-generated method stub
        repository.updateFechaUltimoAcceso(date, Integer.parseInt(id));
    }

    @Override
    public String validarCorreo(String correo) {
        return repository.findAllByCorreo(correo).isEmpty()?"1":"0";
    }

    @Override
    public String validarUsername(String username) {
        return securityUserRepository.findUsernameByUsername(username)==null?"1":"0";
    }

    @Override
    public Cliente findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public String findNombreCompletoById(Integer id) {
        return repository.findNombreCompletoById(id);
    }
}
