package com.itsight.service.impl;

import com.itsight.domain.Administrador;
import com.itsight.domain.SecurityRole;
import com.itsight.domain.SecurityUser;
import com.itsight.domain.dto.UsuGenDTO;
import com.itsight.domain.jsonb.Rol;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.AdministradorRepository;
import com.itsight.repository.SecurityRoleRepository;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.service.AdministradorService;
import com.itsight.service.EmailService;
import com.itsight.service.RolService;
import com.itsight.util.Enums;
import com.itsight.util.MailContents;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.itsight.util.Enums.ResponseCode;
import static com.itsight.util.Enums.TipoUsuario.ADMINISTRADOR;

@Service
@Transactional
public class AdministradorServiceImpl extends BaseServiceImpl<AdministradorRepository> implements AdministradorService {

    private SecurityUserRepository securityUserRepository;

    private SecurityRoleRepository securityRoleRepository;

    private RolService rolService;

    private EmailService emailService;

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
        return null;
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
        return repository.findByNombreCompleto(comodin.equals("0") ? "": comodin);
    }

    @Override
    public String registrar(Administrador administrador, String rols) {
        // TODO Auto-generated method stub
        administrador.setUsername(administrador.getUsername().toLowerCase());
        if (securityUserRepository.findByUsername(administrador.getUsername()) == null) {
            try{
                    String originalPassword = administrador.getPassword();
                    Integer[] rolAdmin = new Integer[]{1};//ROLE_ADMIN
                    List<com.itsight.domain.Rol> lstRoles = rolService.findByIdsIn(Arrays.asList(rolAdmin));
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
                    secUser.setAdministrador(administrador);
                    //Validamos si presenta un rol de entrenador
                    int flagTrainer = administrador.getRoles().stream().filter(r -> r.getId() == 2).findFirst().isPresent()?1:0;
                    //Guardando administrador de autenticacion
                    administrador.setId(null);
                    administrador.setSecurityUser(secUser);
                    save(administrador);

                    //Enviando correo al nuevo administrador
                    StringBuilder sb = MailContents.contenidoNuevoUsuario(administrador.getUsername(), originalPassword, ADMINISTRADOR.ordinal(), domainName);
                    emailService.enviarCorreoInformativo("Bienvenido a la familia", administrador.getCorreo(), sb.toString());
                    return ResponseCode.REGISTRO.get()+','+administrador.getId()+','+flagTrainer;
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
            Integer[] rolAdmin = new Integer[]{1};//ROLE_ADMIN
            List<com.itsight.domain.Rol> lstRoles = rolService.findByIdsIn(Arrays.asList(rolAdmin));
            List<Rol> lstJsonRoles = lstRoles.stream()
                    .map(rol -> new Rol(rol.getId(), rol.getRol()))
                    .collect(Collectors.toList());

            Administrador qAdministrador = repository.findById(administrador.getId()).orElse(null);
            administrador.setFechaUltimoAcceso(qAdministrador.getFechaUltimoAcceso());
            administrador.setCreador(qAdministrador.getCreador());
            administrador.setRowVersion(qAdministrador.getRowVersion());
            administrador.setRoles(lstJsonRoles);
            administrador.setCorreo(qAdministrador.getCorreo());
            administrador.setFlagEliminado(qAdministrador.isFlagEliminado());

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
        Date fechaModificacion = new Date();
        String modificadoPor = SecurityContextHolder.getContext().getAuthentication().getName();
        repository.updateFlagActivoById(id, flagActivo, fechaModificacion, modificadoPor);
    }

    @Override
    public void actualizarFechaUltimoAcceso(Date date, Integer id) {
        // TODO Auto-generated method stub
        repository.updateFechaUltimoAcceso(date, id);
    }

    @Override
    public String validarCorreo(String correo) {
        return repository.findAllByCorreo(correo).isEmpty()?"1":"0";
    }

    @Override
    public String validarUsername(String username) {
        return securityUserRepository.findUsernameExists(username)==null?"1":"0";
    }

    @Override
    public Administrador findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public UsuGenDTO obtenerById(Integer id) {
        return repository.getById(id);
    }

    @Override
    public String getUsernameById(int id) {
        return repository.getUsernameById(id);
    }

    @Override
    public UsuGenDTO getForCookieById(Integer id) {
        return repository.getForCookieById(id);
    }
}
