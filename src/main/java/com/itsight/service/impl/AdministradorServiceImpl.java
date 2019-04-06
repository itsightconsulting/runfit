package com.itsight.service.impl;

import com.itsight.domain.Administrador;
import com.itsight.domain.SecurityRole;
import com.itsight.domain.SecurityUser;
import com.itsight.domain.jsonb.Rol;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.AdministradorRepository;
import com.itsight.repository.SecurityRoleRepository;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.service.AdministradorService;
import com.itsight.service.EmailService;
import com.itsight.service.RolService;
import com.itsight.util.MailContents;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.itsight.util.Enums.ResponseCode;

@Service
@Transactional
public class AdministradorServiceImpl extends BaseServiceImpl<AdministradorRepository> implements AdministradorService {

    private SecurityUserRepository securityUserRepository;

    private SecurityRoleRepository securityRoleRepository;

    private RolService rolService;

    private EmailService emailService;

    @Autowired
    private AdministradorService administradorService;

    @Value("${domain.name}")
    private String domainName;

    public AdministradorServiceImpl(
            AdministradorRepository repository,
            SecurityUserRepository securityUserRepository,
            SecurityRoleRepository securityRoleRepository,
            RolService rolService,
            EmailService emailService) {
        super(repository);
        // TODO Auto-generated constructor stub
        this.securityRoleRepository = securityRoleRepository;
        this.securityUserRepository = securityUserRepository;
        this.rolService = rolService;
        this.emailService = emailService;
    }

    @Override
    public List<Administrador> findAll() {
        // TODO Auto-generated method stub
        return repository.findAllByOrderByIdDesc();
    }

    @Override
    public List<Administrador> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivoOrderByIdDesc(flagActivo);
    }

    @Override
    public Administrador findOne(Integer id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    public Administrador findOneWithFT(Integer id) {
        // TODO Auto-generated method stub
        return repository.getById(id);
    }

    @Override
    public Administrador save(Administrador administrador) {
        // TODO Auto-generated method stub
        return repository.save(administrador);
    }

    @Override
    public Administrador update(Administrador administrador) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(administrador);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    public List<Administrador> findByNombreCompleto(String nombreCompleto) {
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
    public List<Administrador> findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Administrador> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Administrador> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Administrador> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Administrador> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Administrador> listarPorFiltro(String comodin, String estado, String fk) {//fk:perfil
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public List<UsuarioPOJO> listarPorFiltroDto(String comodin, String estado, String fk) {
        return repository.findByNombreCompleto(comodin);
    }

    @Override
    public String registrar(Administrador administrador, String rols) {
        // TODO Auto-generated method stub
        if (securityUserRepository.findByUsername(administrador.getUsername()) == null) {
            try{
                if(administrador.getTipoUsuario().getId() == 1 ){//1: Administrador
                    String originalPassword = administrador.getPassword();
                    String[] arrRoles = rols.split(",");
                    List<com.itsight.domain.Rol> lstRoles = rolService.findByIdsIn(Arrays.asList(Parseador.stringArrayToIntArray(arrRoles)));
                    List<Rol> lstJsonRoles = lstRoles.stream()
                            .map(rol -> new Rol(rol.getId(), rol.getRol()))
                            .collect(Collectors.toList());
                    administrador.setRoles(lstJsonRoles);

                    administrador.setPassword(Utilitarios.encoderPassword(administrador.getPassword()));
                    //Añadiendo las credenciales de ingreso
                    SecurityUser secUser = new SecurityUser(administrador.getUsername(), administrador.getPassword(), administrador.isFlagActivo());
                    //Añadiendo el role de colaborador
                    Set<SecurityRole> listSr = new HashSet<>();
                    for (Rol rol : administrador.getRoles()){
                        listSr.add(new SecurityRole(rol.getNombre(), secUser));
                    }
                    //Adding to User
                    secUser.setRoles(listSr);
                    //secUser.addAdministrador(administrador);
                    //Validamos si presenta un rol de entrenador
                    int flagTrainer = administrador.getRoles().stream().filter(r -> r.getId() == 2).findFirst().isPresent()?1:0;
                    //Guardando administrador de autenticacion
                    administrador.setSecurityUser(secUser);
                    administradorService.save(administrador);

                    //Enviando correo al nuevo administrador
                    StringBuilder sb = MailContents.contenidoNuevoUsuario(administrador.getUsername(), originalPassword, administrador.getTipoUsuario().getId(), domainName);
                    emailService.enviarCorreoInformativo("Bienvenido a la familia", administrador.getCorreo(), sb.toString());
                    return ResponseCode.REGISTRO.get()+','+String.valueOf(administrador.getId())+','+flagTrainer;
                }
                return ResponseCode.EX_VALIDATION_FAILED.get();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ResponseCode.VF_USUARIO_REPETIDO.get();
    }

    @Override
    public String actualizar(Administrador administrador, String rols) {
        // TODO Auto-generated method stub
        try {
            Administrador qAdministrador = repository.findById(administrador.getId()).orElse(null);
            administrador.setFechaUltimoAcceso(qAdministrador.getFechaUltimoAcceso());
            administrador.setCreador(qAdministrador.getCreador());
            administrador.setRowVersion(qAdministrador.getRowVersion());
            administrador.setRoles(qAdministrador.getRoles());
            administrador.setCorreo(qAdministrador.getCorreo());
            administrador.setFlagEliminado(qAdministrador.isFlagEliminado());

            // - Verificando si hubo cambio de roles -
            //Los roles antiguos
            Integer[] rolesIniciales = administrador.getRoles().stream().map(rol -> rol.getId()).toArray(Integer[]::new);
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
                securityRoleRepository.save(new SecurityRole(rol.getRol(), administrador.getId()));
                lstRolesNueva.add(new Rol(n, rol.getRol()));
                }

            if(debenEliminarse.size()>0) {
                for (Integer n : debenEliminarse) {
                    com.itsight.domain.Rol rol = rolService.findOne(n);
                    Integer id = securityRoleRepository.findBySecurityUserIdAndRole(qAdministrador.getId(), rol.getRol()).getId();
                    securityRoleRepository.deleteById(id);
                }
                //Agregamos a la nueva lista creada solo los roles que se mantendrán de esta forma indirectamente
                //Ya estaremos borrando de la columna jsonb los que ya se hayan eliminado de la tabla
                for (Rol r: administrador.getRoles()) {
                    for (Integer x : noRequierenActualizacion){
                        if(r.getId() == x){
                            lstRolesNueva.add(r);
                        }
                    }
                }
            }else{//En caso no se elimine ningun roles transfiere todos los roles "antiguos" a una nueva lista
                //que posiblemente ya tenga algunos elementos en el caso se hayan agregado nuevos roles
                lstRolesNueva.addAll(administrador.getRoles());
            }
            //Finalmente volvemos a setear los roles con la nueva lista y guardamos
            administrador.setRoles(lstRolesNueva);
            if(qAdministrador.isFlagActivo() != administrador.isFlagActivo())
                securityUserRepository.updateEstadoByUsername(administrador.getUsername(), administrador.isFlagActivo());
            repository.saveAndFlush(administrador);
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
    public Administrador findByUsername(String username) {
        return repository.findByUsername(username);
    }

}
