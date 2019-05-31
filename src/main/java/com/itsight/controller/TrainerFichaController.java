package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.advice.CustomValidationException;
import com.itsight.advice.SecCustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.PostulanteTrainer;
import com.itsight.domain.dto.*;
import com.itsight.domain.jsonb.CuentaPago;
import com.itsight.domain.pojo.ServicioPOJO;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import com.itsight.util.Validador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.itsight.util.Enums.Msg.*;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;
import static com.itsight.util.Enums.ResponseCode.REGISTRO;
import static com.itsight.util.Enums.TipoTrainer.*;
import static com.itsight.util.Utilitarios.jsonResponse;

@Controller
@RequestMapping("/p/trainer")
public class TrainerFichaController extends BaseController {

    private TrainerFichaService trainerFichaService;

    private PostulanteTrainerService postulanteTrainerService;

    private TrainerService trainerService;

    private SecurityUserRepository securityUserRepository;

    private DisciplinaService disciplinaService;

    private TrainerProcedureInvoker trainerProcedureInvoker;

    private ServicioService servicioService;

    private UbPeruService ubPeruService;

    @Autowired
    public TrainerFichaController(TrainerFichaService trainerFichaService,
                                  PostulanteTrainerService postulanteTrainerService,
                                  TrainerService trainerService,
                                  SecurityUserRepository securityUserRepository,
                                  DisciplinaService disciplinaService,
                                  TrainerProcedureInvoker trainerProcedureInvoker,
                                  ServicioService servicioService,
                                  UbPeruService ubPeruService) {
        this.trainerFichaService = trainerFichaService;
        this.postulanteTrainerService = postulanteTrainerService;
        this.trainerService = trainerService;
        this.securityUserRepository = securityUserRepository;
        this.disciplinaService = disciplinaService;
        this.trainerProcedureInvoker = trainerProcedureInvoker;
        this.servicioService = servicioService;
        this.ubPeruService = ubPeruService;
    }

    @GetMapping("/find/all")
    public @ResponseBody List<TrainerFichaPOJO> listarTodos(){
        return trainerFichaService.findAllWithFgEnt();
    }

    @GetMapping("/find/dinamico")
    public @ResponseBody List<TrainerFichaPOJO> listarDinamico(
        @ModelAttribute @Valid TrainerQueryDTO query){
        return trainerProcedureInvoker.findAllByDynamic(query);
    }

    @GetMapping("/{nomPag:.+}")
    public @ResponseBody ModelAndView getTrainerByUsername(){
        return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER);
    }


    @GetMapping("/get/revision/{hshTrainerId}")
    public @ResponseBody ModelAndView getTrainerByIdRevision(Model model,
                @PathVariable(name = "hshTrainerId") String hshTrainerId) throws SecCustomValidationException {
        Integer trainerId = getDecodeHashIdSecCustom("rf-aprobacion", hshTrainerId);
        Boolean isActived = securityUserRepository.findEnabledById(trainerId);
        if(isActived == null){
            return new ModelAndView(ViewConstant.P_ERROR404);
        }
        if(isActived){
            return new ModelAndView(ViewConstant.P_ERROR404);
        } else {
            model.addAttribute("hshTrainerId", hshTrainerId);
            model.addAttribute("disciplinas", disciplinaService.obtenerDisciplinasByTrainerId(trainerId));
            return new ModelAndView(ViewConstant.MAIN_REVISION_TRAINER);
        }
    }

    @PutMapping("/subsanar/observaciones/perfil")
    public @ResponseBody String subsanarObservacionesPerfil(
            @RequestBody @Valid TrainerFichaDTO trainerFicha) throws CustomValidationException, JsonProcessingException {
        Integer trainerId = getDecodeHashId("rf-aprobacion", trainerFicha.getTrainerId());
        return jsonResponse(trainerFichaService.actualizarObservacionesPerfil(trainerFicha, trainerId));
    }

    @GetMapping("/ver/{hshTrainerId}")
    public @ResponseBody ModelAndView getTrainerById(Model model, @PathVariable(name = "hshTrainerId") String hshTrainerId) throws SecCustomValidationException {
        model.addAttribute("porAprobar", true);
        model.addAttribute("hshTrainerId", hshTrainerId);
        Integer trainerId = getDecodeHashIdSecCustom("rf-aprobacion", hshTrainerId);
        Boolean isActived = securityUserRepository.findEnabledById(trainerId);
        if(isActived == null){
            return new ModelAndView(ViewConstant.P_ERROR404);
        }
        if(isActived){
            model.addAttribute("msg", PERFIL_APROBADO_ANTERIORMENTE.get());
            return new ModelAndView(ViewConstant.MAIN_INF_N);
        } else {
            Boolean flag = trainerFichaService.getFlagFichaAceptadaByTrainerId(trainerId);
            if(flag == null){
                model.addAttribute("disciplinas", disciplinaService.obtenerDisciplinasByTrainerId(trainerId));
                return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER);
            }//Si entra acá es porque ya ha sido observado, nunca entrará aca cuando haya sido aprobado debido a la primera
            //validación antes de esta
            model.addAttribute("msg", PERFIL_EN_REVISION.get());
            return new ModelAndView(ViewConstant.MAIN_INF_N);
        }
    }

    @GetMapping("/checkout/{hshPostTrainerId}/{hshTrainerId}")
    public @ResponseBody ModelAndView getTrainerEmpresa(
            Model model,
            @PathVariable(name = "hshPostTrainerId") String hshPostTrainerId,
            @PathVariable(name = "hshTrainerId") String hshTrainerId) throws SecCustomValidationException {
        Integer trainerId = getDecodeHashIdSecCustom("rf-load-media", hshTrainerId);
        Boolean isActived = securityUserRepository.findEnabledById(trainerId);
        if(isActived == null){
            return new ModelAndView(ViewConstant.P_ERROR404);
        }
        if(isActived){
            model.addAttribute("msg", CHECK_PERFIL_EMPRESA_EN_VISTA_BUSQUEDA.get());
            return new ModelAndView(ViewConstant.MAIN_INF_P);
        } else {
            Boolean flag = trainerFichaService.getFlagFichaAceptadaByTrainerId(trainerId);
            if(flag == null){
                model.addAttribute("disciplinas", disciplinaService.obtenerDisciplinasByTrainerId(trainerId));
                model.addAttribute("hshTrainerId", Parseador.getEncodeHash32Id("rf-aprobacion", trainerId));
                return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER_EMP);
            }//Si entra acá es porque ya ha sido observado, nunca entrará aca cuando haya sido aprobado debido a la primera
            //validación antes de esta
            model.addAttribute("msg", PERFIL_EN_REVISION.get());
            return new ModelAndView(ViewConstant.MAIN_INF_N);
        }
    }

    @GetMapping("/get/disciplinas/{trainerId}")
    public @ResponseBody List<String> obtenerDisciplinas(@PathVariable() String trainerId){
        return disciplinaService.obtenerDisciplinasByTrainerId(Integer.parseInt(trainerId));
    }

    @GetMapping("/get/servicios/{trainerId}")
    public @ResponseBody List<ServicioPOJO> obtenerServicios(
            @PathVariable() String trainerId,
            @RequestParam() String tipoTrainerId){
        if(Integer.parseInt(tipoTrainerId) == PARA_EMPRESA.get()){
            Integer empTraId = trainerFichaService.getTrEmpIdById(Integer.parseInt(trainerId));
            return servicioService.findAllByTrainerId(empTraId);
        }
        return servicioService.findAllByTrainerId(Integer.parseInt(trainerId));
    }

    @GetMapping("/perfil/observaciones")
    public @ResponseBody String enviarObservacionesPerfil(@ModelAttribute @Valid PerfilObsDTO perfilObs) throws CustomValidationException {
        Integer trainerId = getDecodeHashId("rf-aprobacion", perfilObs.getHshTrainerId());
        return jsonResponse(trainerFichaService.enviarCorreoPerfilObs(perfilObs, trainerId));
    }

    @GetMapping("/get/{nomPag:.+}")
    public @ResponseBody
    ResponseEntity<TrainerFichaPOJO> getTrainerByUsername(@PathVariable(name = "nomPag") String nomPag) {
        TrainerFichaPOJO t = trainerFichaService.findByNomPagPar(nomPag);
        t.setHshTrainerId(Parseador.getEncodeHash32Id("rf-public", t.getId()));
        if(t != null){
            return new ResponseEntity<>(t, HttpStatus.OK);
        }
        return new ResponseEntity<>(t, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get/sec/{hshTrainerId}")
    public @ResponseBody
    ResponseEntity<TrainerFichaPOJO> getTrainerByIdParaAprobacionFinal(@PathVariable(name = "hshTrainerId") String hshTrainerId) throws CustomValidationException {
        Integer trainerId = getDecodeHashId("rf-aprobacion", hshTrainerId);
        TrainerFichaPOJO t = trainerFichaService.findByTrainerId(trainerId);
        if(t != null){
            return new ResponseEntity<>(t, HttpStatus.OK);
        }
        return new ResponseEntity<>(t, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/ultima-aprobacion/{hshTrainerId}/{correo}")
    public @ResponseBody String ultimaAprobacionTrainer(
            @PathVariable(name = "hshTrainerId") String hshTrainerId,
            @PathVariable(name = "correo") String correo) throws CustomValidationException {
        if(Validador.validarCorreo(correo)){
            Integer trainerId = getDecodeHashId("rf-aprobacion", hshTrainerId);
            trainerService.actualizarFlagActivoByIdAndNotificacion(trainerId, true, correo);
            return jsonResponse(Enums.Msg.APROBACION_FINAL_PERFIL_TRAINER.get());
        }
        throw new CustomValidationException(VALIDACION_FALLIDA.get(), EX_VALIDATION_FAILED.get());
    }

    @PostMapping("/registro/{hashPreTrainerId}")
    public @ResponseBody String registroTrainer(
            @PathVariable(name = "hashPreTrainerId") String hashPreTrainerId,
            @RequestBody @Valid TrainerDTO trainerFicha) throws CustomValidationException {

        Integer postTraId = 0;

        if(hashPreTrainerId.length() == 32){
            postTraId = Parseador.getDecodeHash32Id("rf-request", hashPreTrainerId);
        }

        if(postTraId == 0){
            throw new CustomValidationException(VALIDACION_FALLIDA.get(), EX_VALIDATION_FAILED.get());
        }

        PostulanteTrainer postulante = postulanteTrainerService.findOne(postTraId);
        if(postulante == null){
            throw new CustomValidationException(VALIDACION_FALLIDA.get(), EX_VALIDATION_FAILED.get());
        }

        if(!postulante.isFlagAceptado()){
            return Enums.ResponseCode.EX_VALIDATION_FAILED.get();
        }

        if(postulante.isFlagAceptado() && !postulante.isFlagRegistrado()){
            //Nos aseguramos que el correo sea el mismo que se encuentra en BD
            //y no el que enviaron en la petición post
            trainerFicha.setCorreo(postulante.getCorreo());
            trainerFicha.setPostulanteTrainerId(postTraId);
            RefUploadIds refsUpload = trainerService.registrarPostulante(trainerFicha, trainerFicha.getTipoTrainerId() == 1 ? PARTICULAR.get() : EMPRESA.get(), null);
            String gal = refsUpload.getNombresImgsGaleria().equals("") ? "" : "@"+refsUpload.getNombresImgsGaleria();
            String svcsFiles = refsUpload.getNombresCondSvcs().equals("") ? "" : "@"+refsUpload.getNombresCondSvcs();
            String staffFiles = refsUpload.getStaffGaleria().equals("") ? "" : "@"+refsUpload.getStaffGaleria();

            String finalUploadNames = refsUpload.getUuidFp()+refsUpload.getExtFp() +  gal + svcsFiles + staffFiles;
            return jsonResponse(
                        Parseador.getEncodeHash32Id("rf-load-media", refsUpload.getTrainerId()),
                        finalUploadNames);
        }
        return jsonResponse(REGISTRO.get());
    }

    @PutMapping("/subir/foto/perfil/{trainerHshId}/{rdmUUID}")
    public @ResponseBody String subirImagenPerfil(
            @RequestPart(name = "file") MultipartFile imgPerfil,
            @RequestPart(name = "fileExtension", required = false) String extFile,
            @PathVariable(name = "trainerHshId") String hshTrainerId,
            @PathVariable(name = "rdmUUID") String uuid) throws CustomValidationException {
        return jsonResponse(trainerService.subirFile(imgPerfil, getDecodeHashId("rf-load-media", hshTrainerId), uuid, extFile));
    }

    @PutMapping("/subir/fotos/perfil/{trainerHshId}/{rdmUUID}")
    public @ResponseBody String subirImagenesPerfil(
            @RequestPart(name = "files") MultipartFile[] imgs,
            @RequestPart(name = "fileExtension", required = false) String extFile,
            @PathVariable(name = "trainerHshId") String hshTrainerId,
            @PathVariable(name = "rdmUUID") String uuid) throws CustomValidationException {
        return jsonResponse(trainerService.subirFiles(imgs, getDecodeHashId("rf-load-media", hshTrainerId), uuid, extFile));
    }

    @GetMapping("/empresa/agregar/sub/{hshEmpTraId}/{hshPostTrainerId}")
    public ModelAndView vistaAgregarTrainerAEmpresa(
            @PathVariable String hshEmpTraId,
            @PathVariable String hshPostTrainerId,
            Model model) throws SecCustomValidationException {
        Integer empTraId = getDecodeHashIdSecCustom("rf-load-media", hshEmpTraId);
        Integer postId = getDecodeHashIdSecCustom("rf-request", hshPostTrainerId);
        Integer tipoTrainerId = postulanteTrainerService.getTipoTrainerIdById(postId);
        if(tipoTrainerId != null && tipoTrainerId == 2){
            String nuevoHshEmpTraId = Parseador.getEncodeHash32Id("rf-emp-trainer", empTraId);
            String hshVerEmpTrainerId = Parseador.getEncodeHash32Id("rf-aprobacion", empTraId);
            model.addAttribute("hshVerEmpTrainerId", hshVerEmpTrainerId);
            model.addAttribute("hshEmpTrainerId", nuevoHshEmpTraId);
            model.addAttribute("disciplinas", disciplinaService.findAll());
            model.addAttribute("distritos", ubPeruService.findPeDistByDepAndProv("15", "01"));
            return new ModelAndView(ViewConstant.MAIN_REGISTRO_TRAINER_DE_EMPRESA);
        }
            return new ModelAndView(ViewConstant.P_ERROR404);

    }

    @PostMapping("/empresa/agregar/sub/{hshEmpTraId}")
    public @ResponseBody String registroTrainerParaEmpresa(
            @PathVariable String hshEmpTraId,
            @RequestBody @Valid TrainerDTO trainerFicha) throws CustomValidationException, IOException {
        Integer empTraId = getDecodeHashId("rf-emp-trainer", hshEmpTraId);
        trainerFicha.setCorreo(trainerFicha.getCorreo());
        Integer ttId = trainerService.getTipoTrainerIdById(empTraId);
        if(ttId != null && ttId == 2){
            String[] ccsAndMediosPago = trainerFichaService.obtenerCcsAndMediosPagoById(empTraId).split("@");
            if(ccsAndMediosPago.length == 2){
                trainerFicha.setMediosPago(ccsAndMediosPago[1]);
                trainerFicha.setCuentas(new ObjectMapper().readValue(ccsAndMediosPago[0], new TypeReference<List<CuentaPago>>(){}));
            }
            RefUploadIds refsUpload = trainerService.registrarPostulante(trainerFicha, PARA_EMPRESA.get(), empTraId);
            String imgPerfil = refsUpload.getTrainerId()+ "/" + refsUpload.getUuidFp()+refsUpload.getExtFp();
            String nomFull =  trainerFicha.getNombres()+" "+trainerFicha.getApellidos();
            String staffGaleria = imgPerfil  + "," + nomFull + "," + trainerFicha.getNomPag();
            //Obteniendo el id
            trainerFichaService.actualizarStaffGaleriaByTrainerId(staffGaleria, empTraId);
            String finalUploadNames = refsUpload.getUuidFp()+refsUpload.getExtFp();
            return jsonResponse(
                    Parseador.getEncodeHash32Id("rf-load-media", refsUpload.getTrainerId()),
                    finalUploadNames);
        }
        throw new CustomValidationException(VALIDACION_FALLIDA.get(), EX_VALIDATION_FAILED.get());
    }
}
