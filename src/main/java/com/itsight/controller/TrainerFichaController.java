package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.PostulanteTrainer;
import com.itsight.domain.dto.RefUploadIds;
import com.itsight.domain.dto.TrainerFichaDTO;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.service.PostulanteTrainerService;
import com.itsight.service.TrainerFichaService;
import com.itsight.service.TrainerService;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

import static com.itsight.util.Enums.Msg.VALIDACION_FALLIDA;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;
import static com.itsight.util.Enums.ResponseCode.REGISTRO;
import static com.itsight.util.Utilitarios.jsonResponse;

@Controller
@RequestMapping("/p/trainer")
public class TrainerFichaController extends BaseController {

    private TrainerFichaService trainerFichaService;

    private PostulanteTrainerService postulanteTrainerService;

    private TrainerService trainerService;

    @Autowired
    public TrainerFichaController(TrainerFichaService trainerFichaService,
                                  PostulanteTrainerService postulanteTrainerService,
                                  TrainerService trainerService) {
        this.trainerFichaService = trainerFichaService;
        this.postulanteTrainerService = postulanteTrainerService;
        this.trainerService = trainerService;
    }

    @GetMapping("/find/all")
    public @ResponseBody List<TrainerFichaPOJO> listarTodos(){
        return trainerFichaService.findAllWithFgEnt();
    }

    /*@GetMapping("/{nomPag:.+}")
    public ModelAndView getTrainerByUsername(Model model, @PathVariable(name = "nomPag") String nomPag){
        TrainerFicha trainerFicha = trainerFichaService.findByNomPag(nomPag);
        if(trainerFicha != null){
            model.addAttribute("trainer", trainerFicha);
            return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER);
        }
        return new ModelAndView(ViewConstant.ERROR404);
    }*/

    @GetMapping("/{nomPag:.+}")
    public @ResponseBody ModelAndView getTrainerByUsername(){
        return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER);
    }

    @GetMapping("/get/{nomPag:.+}")
    public @ResponseBody TrainerFichaPOJO getTrainerByUsername(@PathVariable(name = "nomPag") String nomPag){
        TrainerFichaPOJO  trainerFicha = trainerFichaService.findByNomPagPar(nomPag);
        if(trainerFicha != null){
            return trainerFicha;
        }
        return new TrainerFichaPOJO();
    }

    @PostMapping("/registro/{hashPreTrainerId}")
    public @ResponseBody String registroTrainer(
            @PathVariable(name = "hashPreTrainerId") String hashPreTrainerId,
            @RequestBody @Valid TrainerFichaDTO trainerFicha) throws CustomValidationException {

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
            String finalUploadNames = refsUpload.getNombreImgPerfil() +  gal;
            return jsonResponse(
                        Parseador.getEncodeHash32Id("rf-load-media", refsUpload.getTrainerId()),
                        finalUploadNames);
        }
        return jsonResponse(REGISTRO.get());
    }

    @PutMapping("/subir/foto/perfil/{trainerHshId}/{rdmUUID}")
    public @ResponseBody String subirImagenPerfil(
            @RequestPart(name = "file") MultipartFile imgPerfil,
            @PathVariable(name = "trainerHshId") String hshTrainerId,
            @PathVariable(name = "rdmUUID") String uuid) throws CustomValidationException {
        return jsonResponse(trainerService.subirImagen(imgPerfil, getDecodeHashId("rf-load-media", hshTrainerId), uuid));
    }
}
