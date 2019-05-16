package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.advice.SecCustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.PostulanteTrainer;
import com.itsight.domain.dto.PerfilObsDTO;
import com.itsight.domain.dto.RefUploadIds;
import com.itsight.domain.dto.TrainerDTO;
import com.itsight.domain.dto.TrainerFichaDTO;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.service.PostulanteTrainerService;
import com.itsight.service.TrainerFichaService;
import com.itsight.service.TrainerService;
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
import java.io.File;
import java.util.List;

import static com.itsight.util.Enums.Msg.*;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;
import static com.itsight.util.Enums.ResponseCode.REGISTRO;
import static com.itsight.util.Utilitarios.jsonResponse;

@Controller
@RequestMapping("/p/trainer")
public class TrainerFichaController extends BaseController {

    private TrainerFichaService trainerFichaService;

    private PostulanteTrainerService postulanteTrainerService;

    private TrainerService trainerService;

    private SecurityUserRepository securityUserRepository;

    @Autowired
    public TrainerFichaController(TrainerFichaService trainerFichaService,
                                  PostulanteTrainerService postulanteTrainerService,
                                  TrainerService trainerService,
                                  SecurityUserRepository securityUserRepository) {
        this.trainerFichaService = trainerFichaService;
        this.postulanteTrainerService = postulanteTrainerService;
        this.trainerService = trainerService;
        this.securityUserRepository = securityUserRepository;
    }

    @GetMapping("/find/all")
    public @ResponseBody List<TrainerFichaPOJO> listarTodos(){
        return trainerFichaService.findAllWithFgEnt();
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
            return new ModelAndView(ViewConstant.MAIN_REVISION_TRAINER);
        }
    }

    @PutMapping("/subsanar/observaciones/perfil")
    public @ResponseBody String subsanarObservacionesPerfil(
            @RequestBody TrainerFichaDTO trainerFicha) throws CustomValidationException {
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
                return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER);
            }//Si entra acá es porque ya ha sido observado, nunca entrará aca cuando haya sido aprobado debido a la primera
            //validación antes de esta
            model.addAttribute("msg", PERFIL_EN_REVISION.get());
            return new ModelAndView(ViewConstant.MAIN_INF_N);
        }
    }

    @GetMapping("/perfil/observaciones")
    public @ResponseBody String enviarObservacionesPerfilATrainer(@ModelAttribute @Valid PerfilObsDTO perfilObs) throws CustomValidationException {
        Integer trainerId = getDecodeHashId("rf-aprobacion", perfilObs.getHshTrainerId());
        return jsonResponse(trainerFichaService.enviarCorreoPerfilObs(perfilObs, trainerId));
    }

    @GetMapping("/get/{nomPag:.+}")
    public @ResponseBody
    ResponseEntity<TrainerFichaPOJO> getTrainerByUsername(@PathVariable(name = "nomPag") String nomPag) {
        TrainerFichaPOJO t = trainerFichaService.findByNomPagPar(nomPag);
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
            RefUploadIds refsUpload = trainerService.registrarPostulante(trainerFicha);
            String gal = refsUpload.getNombresImgsGaleria().equals("") ? "" : "@"+refsUpload.getNombresImgsGaleria();
            String svcsFiles = refsUpload.getNombresCondSvcs().equals("") ? "" : "@"+refsUpload.getNombresCondSvcs();
            String finalUploadNames = refsUpload.getNombreImgPerfil() +  gal + svcsFiles;
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
        return jsonResponse(trainerService.subirImagen(imgPerfil, getDecodeHashId("rf-load-media", hshTrainerId), uuid, extFile));
    }

    @PutMapping("/subir/fotos/perfil/{trainerHshId}/{rdmUUID}")
    public @ResponseBody String subirImagenesPerfil(
            @RequestPart(name = "files") MultipartFile[] imgs,
            @RequestPart(name = "fileExtension", required = false) String extFile,
            @PathVariable(name = "trainerHshId") String hshTrainerId,
            @PathVariable(name = "rdmUUID") String uuid) throws CustomValidationException {
        return jsonResponse(trainerService.subirImagenes(imgs, getDecodeHashId("rf-load-media", hshTrainerId), uuid, extFile));
    }


}
