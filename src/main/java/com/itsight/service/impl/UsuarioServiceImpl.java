package com.itsight.service.impl;

import com.itsight.domain.*;
import com.itsight.domain.jsonb.PorcKiloTipo;
import com.itsight.domain.jsonb.PorcKiloTipoSema;
import com.itsight.domain.jsonb.Rol;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.EspecificacionSubCategoriaRepository;
import com.itsight.repository.SecurityRoleRepository;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.repository.UsuarioRepository;
import com.itsight.service.EmailService;
import com.itsight.service.PorcentajesKilometrajeService;
import com.itsight.service.RolService;
import com.itsight.service.UsuarioService;
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
public class UsuarioServiceImpl extends BaseServiceImpl<UsuarioRepository> implements UsuarioService {

    private SecurityUserRepository securityUserRepository;

    private SecurityRoleRepository securityRoleRepository;

    private EspecificacionSubCategoriaRepository especificacionSubCategoriaRepository;

    private RolService rolService;

    private EmailService emailService;

    private PorcentajesKilometrajeService porcentajesKilometrajeService;

    @Autowired
    private UsuarioService usuarioService;

    @Value("${domain.name}")
    private String domainName;

    public UsuarioServiceImpl(
            UsuarioRepository repository,
            SecurityUserRepository securityUserRepository,
            SecurityRoleRepository securityRoleRepository,
            EspecificacionSubCategoriaRepository especificacionSubCategoriaRepository,
            RolService rolService,
            EmailService emailService,
            PorcentajesKilometrajeService porcentajesKilometrajeService) {
        super(repository);
        // TODO Auto-generated constructor stub
        this.securityRoleRepository = securityRoleRepository;
        this.securityUserRepository = securityUserRepository;
        this.especificacionSubCategoriaRepository = especificacionSubCategoriaRepository;
        this.rolService = rolService;
        this.emailService = emailService;
        this.porcentajesKilometrajeService = porcentajesKilometrajeService;
    }

    @Override
    public List<Usuario> findAll() {
        // TODO Auto-generated method stub
        return repository.findAllByOrderByIdDesc();
    }

    @Override
    public List<Usuario> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivoOrderByIdDesc(flagActivo);
    }

    @Override
    public Usuario findOne(int id) {
        // TODO Auto-generated method stub
        return repository.findOne(new Integer(id));
    }

    @Override
    public Usuario findOneWithFT(int id) {
        // TODO Auto-generated method stub
        return repository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        // TODO Auto-generated method stub
        return repository.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(usuario);
    }

    @Override
    public void delete(int usuarioId) {
        // TODO Auto-generated method stub
        repository.delete(new Integer(usuarioId));
    }

    @Override
    public List<Usuario> findByNombreCompleto(String nombreCompleto) {
        // TODO Auto-generated method stub
        return repository.findByNombreCompleto(nombreCompleto);
    }

    @Override
    public String findPasswordById(int id) {
        // TODO Auto-generated method stub
        return securityUserRepository.findPasswordById(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Usuario> findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Usuario> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Usuario> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Usuario> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Usuario> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Usuario> listarPorFiltro(String comodin, String estado, String fk) {//fk:perfil
        // TODO Auto-generated method stub

        List<Usuario> lstUsuario;

        if (comodin.equals("0") && estado.equals("-1") && fk.equals("0")) {
            lstUsuario = repository.findAllByOrderByIdDesc();
        } else {
            if (comodin.equals("0") && fk.equals("0")) {
                lstUsuario = repository.findAllByFlagActivoOrderByIdDesc(Boolean.valueOf(estado));
            } else {
                comodin = comodin.equals("0") ? "" : comodin;//Necesario para que la url de la request no viaje // y viaje /0/(otro parametro)

                lstUsuario = repository.findByNombreCompleto(comodin);

                if (!estado.equals("-1")) {
                    lstUsuario = lstUsuario.stream()
                            .filter(x -> Boolean.valueOf(estado).equals(x.isFlagActivo()))
                            .collect(Collectors.toList());
                }

                if (!fk.equals("0")) {
                    lstUsuario = lstUsuario.stream()
                            .filter(x -> fk.equals(String.valueOf(x.getTipoUsuario().getId())))
                            .collect(Collectors.toList());
                }
            }
        }
        return lstUsuario;
    }

    @Override
    public String registrar(Usuario usuario, String rols) {
        // TODO Auto-generated method stub
        if (securityUserRepository.findByUsername(usuario.getUsername()) == null) {
            try{
            String originalPassword = usuario.getPassword();
            String[] arrRoles = rols.split(",");
            List<com.itsight.domain.Rol> lstRoles = rolService.findByIdsIn(Arrays.asList(Parseador.stringArrayToIntArray(arrRoles)));
            List<Rol> lstJsonRoles = lstRoles.stream()
                    .map(rol -> new Rol(rol.getId(), rol.getRol()))
                    .collect(Collectors.toList());
            usuario.setRoles(lstJsonRoles);

            usuario.setPassword(Utilitarios.encoderPassword(usuario.getPassword()));
            //Añadiendo las credenciales de ingreso
            SecurityUser secUser = new SecurityUser(usuario.getUsername(), usuario.getPassword(), usuario.isFlagActivo());
            //Añadiendo el role de colaborador
            Set<SecurityRole> listSr = new HashSet<>();
            for (Rol rol : usuario.getRoles()){
                listSr.add(new SecurityRole(rol.getNombre(), secUser));
            }
            //Adding to User
            secUser.setRoles(listSr);
            //secUser.addUsuario(usuario);
            //Validamos si presenta un rol de entrenador
            int flagTrainer = usuario.getRoles().stream().filter(r -> r.getId() == 2).findFirst().isPresent()?1:0;
            //Generando codigo de entrenador en caso tenga el rol de entrenador
            if(flagTrainer == 1){
                usuario.setCodigoTrainer(secUser.getUsername());
            }
            //Guardando usuario de autenticacion
            usuario.setSecurityUser(secUser);
            usuarioService.save(usuario);
            //securityUserRepository.save(secUser);
            //Generando las mini_plantillas al entrenador en caso lo sea
            if(flagTrainer == 1){
                cargarRutinarioCe(usuario.getId());
                //Generando los porcentajes kilometricos para macro-ciclo
                //agregandoPorcentajesK(usuario);
            }

            //Enviando correo al nuevo usuario
            StringBuilder sb = MailContents.contenidoNuevoUsuario(usuario.getUsername(), originalPassword, usuario.getTipoUsuario().getId(), domainName);
            emailService.enviarCorreoInformativo("Dennys Workout Platform", usuario.getCorreo(), sb.toString());
            return ResponseCode.REGISTRO.get()+','+String.valueOf(usuario.getId())+','+flagTrainer;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ResponseCode.VF_USUARIO_REPETIDO.get();
    }

    @Override
    public String actualizar(Usuario usuario, String rols) {
        // TODO Auto-generated method stub
        try {
            Usuario qUsuario = repository.findOne(usuario.getId());
            usuario.setFechaUltimoAcceso(qUsuario.getFechaUltimoAcceso());
            usuario.setCreador(qUsuario.getCreador());
            usuario.setRowVersion(qUsuario.getRowVersion());
            usuario.setRoles(qUsuario.getRoles());
            usuario.setCorreo(qUsuario.getCorreo());
            usuario.setFlagRutinarioCe(qUsuario.isFlagRutinarioCe());
            usuario.setFlagEliminado(qUsuario.isFlagEliminado());

            // - Verificando si hubo cambio de roles -
            //Los roles antiguos
            Integer[] rolesIniciales = usuario.getRoles().stream().map(rol -> rol.getId()).toArray(Integer[]::new);
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
                securityRoleRepository.save(new SecurityRole(rol.getRol(), usuario.getId()));
                lstRolesNueva.add(new Rol(n, rol.getRol()));
                }

            if(debenEliminarse.size()>0) {
                for (Integer n : debenEliminarse) {
                    com.itsight.domain.Rol rol = rolService.findOne(n);
                    int id = securityRoleRepository.findBySecurityUserIdAndRole(qUsuario.getId(), rol.getRol()).getId();
                    securityRoleRepository.deleteById(id);
                }
                //Agregamos a la nueva lista creada solo los roles que se mantendrán de esta forma indirectamente
                //Ya estaremos borrando de la columna jsonb los que ya se hayan eliminado de la tabla
                for (Rol r: usuario.getRoles()) {
                    for (Integer x : noRequierenActualizacion){
                        if(r.getId() == x){
                            lstRolesNueva.add(r);
                        }
                    }
                }
            }else{//En caso no se elimine ningun roles transfiere todos los roles "antiguos" a una nueva lista
                //que posiblemente ya tenga algunos elementos en el caso se hayan agregado nuevos roles
                lstRolesNueva.addAll(usuario.getRoles());
            }

            //En caso entre sus permisos se encuentre el rol de entrenador y tenga el flagRutinarioCe en false
            //Le agregamos los registros para su personal rutinario-ce || ID = 2 : ROL ENTRENADOR
            if(!qUsuario.isFlagRutinarioCe() && lstRolesNueva.stream().filter(r -> r.getId() == 2).findFirst().isPresent()) {
                especificacionSubCategoriaRepository.registrarEspecificacionNuevoEntrenador(usuario.getId());
                usuario.setFlagRutinarioCe(true);
            }
            //Finalmente volvemos a setear los roles con la nueva lista y guardamos
            usuario.setRoles(lstRolesNueva);
            repository.saveAndFlush(usuario);

            return ResponseCode.ACTUALIZACION.get();
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseCode.EX_GENERIC.get();
        }
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

    @Override
    public void actualizarFechaUltimoAcceso(Date date, String id) {
        // TODO Auto-generated method stub
        repository.updateFechaUltimoAcceso(date, Integer.parseInt(id));
    }

    @Override
    public String cargarRutinarioCe(int usuarioId) {
        repository.updateFlagRutinarioCeById(usuarioId,true);
        especificacionSubCategoriaRepository.registrarEspecificacionNuevoEntrenador(usuarioId);
        return ResponseCode.REGISTRO.get();
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
    public String findCodigoTrainerById(int id) {
        return repository.findCodigoTrainerById(id);
    }

    @Override
    public List<Usuario> listarEntrenadores() {
        return repository.findAllTrainers();
    }

    @Override
    public Usuario findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public void agregandoPorcentajesK(Trainer trainer){
        PorcentajesKilometraje porcentajes;
        Integer[] maratonDistancias = {10, 21, 42};

        for(int i=0; i<3; i++){
            porcentajes = new PorcentajesKilometraje();
            porcentajes.setDistancia(maratonDistancias[i]);
            porcentajes.setTrainer(trainer);
            List<PorcKiloTipo> lstPorcKiloTipo = new ArrayList<>();
            for(int k=1; k<4; k++){
                PorcKiloTipo porcKiloTipo = new PorcKiloTipo();
                porcKiloTipo.setTipo(k);
                List<PorcKiloTipoSema> lstPorcKiloTipoSema = new ArrayList<>();
                for(int y=2; y<29; y++) {
                    PorcKiloTipoSema porcKiloTipoSema = new PorcKiloTipoSema();
                    porcKiloTipoSema.setTs(y);
                    List<Integer> porcents = new ArrayList<>(y);
                    for(int s=0; s<y;s++){
                        porcents.add(70-s);
                    }
                    porcKiloTipoSema.setPorcentajes(porcents);
                    lstPorcKiloTipoSema.add(porcKiloTipoSema);
                }
                porcKiloTipo.setSemanas(lstPorcKiloTipoSema);
                lstPorcKiloTipo.add(porcKiloTipo);
            }
            porcentajes.setPorcKiloTipos(lstPorcKiloTipo);
            porcentajesKilometrajeService.save(porcentajes);
        }
    }

    @Override
    public List<UsuarioPOJO> chelmo() {
        return repository.getAllFromNativeQuery();
    }
}
