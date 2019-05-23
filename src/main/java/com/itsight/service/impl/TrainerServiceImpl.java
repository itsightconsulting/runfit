package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.*;
import com.itsight.domain.dto.RefUploadIds;
import com.itsight.domain.dto.TrainerDTO;
import com.itsight.domain.jsonb.PorcKiloTipo;
import com.itsight.domain.jsonb.PorcKiloTipoSema;
import com.itsight.domain.jsonb.Rol;
import com.itsight.domain.pojo.AwsStresPOJO;
import com.itsight.domain.pojo.UsuarioPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.EspecificacionSubCategoriaRepository;
import com.itsight.repository.SecurityRoleRepository;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.repository.TrainerRepository;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Enums.Msg;
import com.itsight.util.Enums.ResponseCode;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static com.itsight.util.Enums.Mail.PERFIL_TRAINER_APROBADO;
import static com.itsight.util.Enums.Mail.ULTIMA_ETAPA_POSTULANTE;
import static com.itsight.util.Enums.Msg.FAIL_SUBIDA_IMG_PERFIL;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;

@Service
@Transactional
public class TrainerServiceImpl extends BaseServiceImpl<TrainerRepository> implements TrainerService {

    public static final Logger LOGGER = LogManager.getLogger(TrainerServiceImpl.class);

    private SecurityRoleRepository securityRoleRepository;

    private SecurityUserRepository securityUserRepository;

    private EspecificacionSubCategoriaRepository especificacionSubCategoriaRepository;

    private RolService rolService;

    private PorcentajesKilometrajeService porcentajesKilometrajeService;

    private EmailService emailService;

    private CorreoService correoService;

    private PostulanteTrainerService postulanteTrainerService;

    private ParametroService parametroService;

    private DisciplinaService disciplinaService;

    @Value("${domain.name}")
    private String domainName;

    @Autowired
    public TrainerServiceImpl(TrainerRepository repository,
            EspecificacionSubCategoriaRepository especificacionSubCategoriaRepository,
            RolService rolService, SecurityRoleRepository securityRoleRepository,
            SecurityUserRepository securityUserRepository,
            PorcentajesKilometrajeService porcentajesKilometrajeService,
            EmailService emailService,
            CorreoService correoService,
            PostulanteTrainerService postulanteTrainerService,
            ParametroService parametroService,
                              DisciplinaService disciplinaService) {
        super(repository);
        this.especificacionSubCategoriaRepository = especificacionSubCategoriaRepository;
        this.rolService = rolService;
        this.securityRoleRepository = securityRoleRepository;
        this.securityUserRepository = securityUserRepository;
        this.porcentajesKilometrajeService = porcentajesKilometrajeService;
        this.emailService = emailService;
        this.correoService = correoService;
        this.postulanteTrainerService = postulanteTrainerService;
        this.parametroService = parametroService;
        this.disciplinaService = disciplinaService;
    }

    @Override
    public Trainer save(Trainer entity) {
        return repository.save(entity);
    }

    @Override
    public Trainer update(Trainer entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public Trainer findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Trainer findOneWithFT(Integer id) {
        return repository.getById(id);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Trainer> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Trainer> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Trainer> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Trainer> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Trainer> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Trainer> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Trainer> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Trainer> listarPorFiltro(String comodin, String estado, String fk) {//fk:perfil
        // TODO Auto-generated method stub

        List<Trainer> lstTrainer;

        if (comodin.equals("0") && estado.equals("-1") && fk.equals("0")) {
            lstTrainer = repository.findAllByOrderByIdDesc();
        } else {
            if (comodin.equals("0") && fk.equals("0")) {
                lstTrainer = repository.findAllByFlagActivoOrderByIdDesc(Boolean.valueOf(estado));
            } else {
                comodin = comodin.equals("0") ? "" : comodin;//Necesario para que la url de la request no viaje // y viaje /0/(otro parametro)
                List<UsuarioPOJO> lstPojos = repository.findByNombreCompleto(comodin);
                System.out.println("SIZE: "+lstPojos.size());
                System.out.println(lstPojos.get(0).toString());
                lstTrainer = new ArrayList<>();

                if (!estado.equals("-1")) {
                    lstTrainer = lstTrainer.stream()
                            .filter(x -> Boolean.valueOf(estado).equals(x.isFlagActivo()))
                            .collect(Collectors.toList());
                }

                if (!fk.equals("0")) {
                    lstTrainer = lstTrainer.stream()
                            .filter(x -> fk.equals(String.valueOf(x.getTipoUsuario().getId())))
                            .collect(Collectors.toList());
                }
            }
        }
        return lstTrainer;
    }

    @Override
    public List<UsuarioPOJO> listarPorFiltroDto(String comodin, String estado, String fk) {

        return repository.findByNombreCompleto(comodin.equalsIgnoreCase("0") ? "" : comodin);
    }

    @Override
    public String registrar(Trainer trainer, String rols) {
        // TODO Auto-generated method stub
        trainer.setUsername(trainer.getUsername().toLowerCase());
        if (securityUserRepository.findByUsername(trainer.getUsername()) == null) {
            try{
                if(trainer.getTipoUsuario().getId() == 2) {//2: Entrenador
                    String originalPassword = trainer.getPassword();
                    String[] arrRoles = rols.split(",");
                    List<com.itsight.domain.Rol> lstRoles = rolService.findByIdsIn(Arrays.asList(Parseador.stringArrayToIntArray(arrRoles)));
                    List<Rol> lstJsonRoles = lstRoles.stream()
                            .map(rol -> new Rol(rol.getId(), rol.getRol()))
                            .collect(Collectors.toList());
                    trainer.setRoles(lstJsonRoles);

                    trainer.setPassword(Utilitarios.encoderPassword(trainer.getPassword()));
                    //Añadiendo las credenciales de ingreso
                    SecurityUser secUser = new SecurityUser(trainer.getUsername().toLowerCase(), trainer.getPassword(), trainer.isFlagActivo());
                    //Añadiendo el role de colaborador
                    Set<SecurityRole> listSr = new HashSet<>();
                    for (Rol rol : trainer.getRoles()) {
                        listSr.add(new SecurityRole(rol.getNombre(), secUser));
                    }
                    //Adding to User
                    secUser.setRoles(listSr);
                    //secUser.addTrainer(trainer);
                    //Validamos si presenta un rol de entrenador
                    int flagTrainer = trainer.getRoles().stream().filter(r -> r.getId() == 2).findFirst().isPresent() ? 1 : 0;
                    //Generando codigo de entrenador en caso tenga el rol de entrenador
                    if (flagTrainer == 1) {
                        trainer.setCodigoTrainer(secUser.getUsername());
                    }
                    //Guardando trainer de autenticacion
                    trainer.setSecurityUser(secUser);
                    trainer.addDisciplina(disciplinaService.findOne(1));
                    trainer.addDisciplina(disciplinaService.findOne(2));
                    repository.save(trainer);
                    //securityUserRepository.save(secUser);
                    //Generando las mini_plantillas al entrenador en caso lo sea
                    if (flagTrainer == 1) {
                        cargarRutinarioCe(trainer.getId());
                        //Generando los porcentajes kilometricos para macro-ciclo
                        agregandoPorcentajesK(trainer);
                    }

                    //Enviando correo al nuevo trainer
                    StringBuilder sb = MailContents.contenidoNuevoUsuario(trainer.getUsername(), originalPassword, trainer.getTipoUsuario().getId(), domainName);
                    emailService.enviarCorreoInformativo("Bienvenido a la familia", trainer.getCorreo(), sb.toString());
                    return trainer.getId().toString();
                }
                return ResponseCode.EX_VALIDATION_FAILED.get();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ResponseCode.VF_USUARIO_REPETIDO.get();
    }

    @Override
    public RefUploadIds registrarPostulante(TrainerDTO trainerFicha, int tipoTrainerId){
        TrainerFicha obj = new TrainerFicha();
        BeanUtils.copyProperties(trainerFicha, obj);

        //No activamos el usuario en su registro ya que aún queda pasar por una última aprobación de toda la información que brindo para su perfil
        Trainer trainer = new Trainer(
                trainerFicha.getNombres(), trainerFicha.getApellidos(), trainerFicha.getCorreo(), trainerFicha.getTelefono(),
                trainerFicha.getMovil(), trainerFicha.getUsername().toLowerCase(), trainerFicha.getDocumento(), true, trainerFicha.getTipoDocumentoId(), Enums.TipoUsuario.ENTRENADOR.ordinal(),false);
        trainer.setPais(trainerFicha.getPaisId());
        trainer.setUbigeo(trainerFicha.getUbigeo());
        trainer.setCanPerValoracion(0);
        trainer.setTotalValoracion(0.0);
        trainer.setFichaClienteIds(String.valueOf(trainerFicha.getFichaClienteId()));//Tipo de fichas disponibles para trabajar(Running, general, boxeo, etc)

        trainer.setUsername(trainerFicha.getUsername().toLowerCase());
        trainer.setPassword(trainerFicha.getPassword());
        trainer.setTipoTrainer(tipoTrainerId);
        List<TrainerFicha> lstTf = new ArrayList<>();
        obj.setTrainer(trainer);
        lstTf.add(obj);
        //Autogenerando uuids para imagenes que se registrarán after esta request
        String[] extensiones = trainerFicha.getImgExt().split("@");
        int extsLen = extensiones.length;
        RefUploadIds refUpload = new RefUploadIds();
        refUpload.setUuidFp(extensiones[0]);//imgPerfil
        refUpload.setNombresImgsGaleria();
        refUpload.setNombresCondSvcs();
        if(extsLen == 2){
            refUpload.setNombresImgsGaleria(extensiones[1]);//imgsGaleria
            obj.setMiniGaleria(refUpload.getNombresImgsGaleria());
            refUpload.setNombresCondSvcs(extensiones[1]);//filesCondsSvcs|Se prueba si esta en la posición uno ya que puede que no se hayan subido fotos a la galeria pero si condiciones de servicio
            String[] noms = refUpload.getNombresCondSvcs().split("\\|");
            for(int i=0; i<noms.length; i++){
                if(!noms[i].equals("")){
                    obj.getServicios().get(i).setCondicionesServicioRuWeb(noms[i]);
                }
            }
        }else if(extsLen == 3){
            refUpload.setNombresImgsGaleria(extensiones[1]);//imgsGaleria
            obj.setMiniGaleria(refUpload.getNombresImgsGaleria());
            refUpload.setNombresCondSvcs(extensiones[2]);//filesCondsSvcs
            String[] noms = refUpload.getNombresCondSvcs().split("\\|");
            for(int i=0; i<noms.length; i++){
                if(!noms[i].equals("")){
                    obj.getServicios().get(i).setCondicionesServicioRuWeb(noms[i]);
                }
            }
        }
        //Img perfil
        obj.setUuidFp(refUpload.getUuidFp());
        obj.setExtFp(refUpload.getExtFp());

        trainer.setLstTrainerFicha(lstTf);
        //Registrando
        String trainerId = registrar(trainer, "2");
        //Actualizando flag de la postulación
        postulanteTrainerService.updateFlagRegistradoById(trainerFicha.getPostulanteTrainerId(), true);
        refUpload.setTrainerId(Integer.parseInt(trainerId));

        //Obtener cuerpo del correo
        Correo correo = correoService.findOne(ULTIMA_ETAPA_POSTULANTE.get());
        //Envio de correo
        String hashId = Parseador.getEncodeHash32Id("rf-aprobacion", trainer.getId());
        String cuerpo = String.format(correo.getBody(), domainName, hashId);
        String runfitCorreo = parametroService.getValorByClave("EMAIL_RECEPTOR_CONSULTAS");
        emailService.enviarCorreoInformativo(correo.getAsunto(), runfitCorreo, cuerpo);
        return refUpload;
    }

    @Override
    public String actualizar(Trainer trainer, String rols) {
        // TODO Auto-generated method stub
        try {
            Trainer qTrainer = repository.getById(trainer.getId());
            trainer.setFechaUltimoAcceso(qTrainer.getFechaUltimoAcceso());
            trainer.setCreador(qTrainer.getCreador());
            trainer.setRowVersion(qTrainer.getRowVersion());
            trainer.setRoles(qTrainer.getRoles());
            trainer.setCorreo(qTrainer.getCorreo());
            trainer.setFlagRutinarioCe(qTrainer.isFlagRutinarioCe());
            trainer.setFlagEliminado(qTrainer.isFlagEliminado());

            // - Verificando si hubo cambio de roles -
            //Los roles antiguos
            Integer[] rolesIniciales = trainer.getRoles().stream().map(rol -> rol.getId()).toArray(Integer[]::new);
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
                    securityRoleRepository.save(new SecurityRole(rol.getRol(), trainer.getId()));
                    lstRolesNueva.add(new Rol(n, rol.getRol()));
                }

            if(debenEliminarse.size()>0) {
                for (Integer n : debenEliminarse) {
                    com.itsight.domain.Rol rol = rolService.findOne(n);
                    Integer id = securityRoleRepository.findBySecurityUserIdAndRole(qTrainer.getId(), rol.getRol()).getId();
                    securityRoleRepository.deleteById(id);
                }
                //Agregamos a la nueva lista creada solo los roles que se mantendrán de esta forma indirectamente
                //Ya estaremos borrando de la columna jsonb los que ya se hayan eliminado de la tabla
                for (Rol r: trainer.getRoles()) {
                    for (Integer x : noRequierenActualizacion){
                        if(r.getId() == x){
                            lstRolesNueva.add(r);
                        }
                    }
                }
            }else{//En caso no se elimine ningun roles transfiere todos los roles "antiguos" a una nueva lista
                //que posiblemente ya tenga algunos elementos en el caso se hayan agregado nuevos roles
                lstRolesNueva.addAll(trainer.getRoles());
            }

            //Actualizando el estado del entrenador en caso este haya sido cambiado
            if(qTrainer.isFlagActivo() != trainer.isFlagActivo())
                securityUserRepository.updateEstadoByUsername(trainer.getUsername(), trainer.isFlagActivo());

            //Finalmente volvemos a setear los roles con la nueva lista y guardamos
            trainer.setRoles(lstRolesNueva);
            repository.saveAndFlush(trainer);
            return ResponseCode.ACTUALIZACION.get();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return ResponseCode.EX_GENERIC.get();
        }
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {
        repository.updateFlagActivoById(id, flagActivo);
        securityUserRepository.updateEstadoById(id, flagActivo);
    }

    @Override
    public void actualizarFlagActivoByIdAndNotificacion(Integer id, boolean flag, String destinatario) {
        repository.updateFlagActivoById(id, flag);
        securityUserRepository.updateEstadoById(id, flag);
        //Obtener cuerpo del correo
        Correo correo = correoService.findOne(PERFIL_TRAINER_APROBADO.get());
        //Envio de correo
        String cuerpo = String.format(correo.getBody(), domainName);
        emailService.enviarCorreoInformativo(correo.getAsunto(), destinatario, cuerpo);
    }

    @Override
    public void actualizarFechaUltimoAcceso(Date fechaUltimoAcceso, String id) {
        repository.updateFechaUltimoAcceso(fechaUltimoAcceso, Integer.parseInt(id));

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
    public String cargarRutinarioCe(Integer trainerId) {
        repository.updateFlagRutinarioCeById(trainerId,true);
        especificacionSubCategoriaRepository.registrarEspecificacionNuevoEntrenador(trainerId);
        return ResponseCode.REGISTRO.get();
    }

    @Override
    public String subirImagen(MultipartFile file, Integer id, String uuid, String extension) throws CustomValidationException {
        boolean success = uploadImageToAws3(file, new AwsStresPOJO(aws3accessKey, aws3secretKey, aws3region, aws3bucket, "trainer/"+id+"/", uuid, extension), LOGGER);
        if(success){
            return Msg.POSTULANTE_ULTIMA_ETAPA.get();
        }
        throw new CustomValidationException(FAIL_SUBIDA_IMG_PERFIL.get(), EX_VALIDATION_FAILED.get());
    }

    @Override
    public String subirImagenes(MultipartFile[] files, Integer id, String uuids, String extension) throws CustomValidationException {
        boolean success = uploadMultipleToAws3(files, new AwsStresPOJO(aws3accessKey, aws3secretKey, aws3region, aws3bucket, "trainer/"+id+"/", uuids, extension), LOGGER);
        if(success){
            return Msg.POSTULANTE_ULTIMA_ETAPA.get();
        }
        throw new CustomValidationException(FAIL_SUBIDA_IMG_PERFIL.get(), EX_VALIDATION_FAILED.get());
    }
}
