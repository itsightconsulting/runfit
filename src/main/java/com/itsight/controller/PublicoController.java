package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.advice.SecCustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.BandejaTemporal;
import com.itsight.domain.PostulanteTrainer;
import com.itsight.domain.SecurityUser;
import com.itsight.domain.dto.ClienteDTO;
import com.itsight.domain.dto.CondicionMejoraDTO;
import com.itsight.domain.dto.PostulanteTrainerDTO;
import com.itsight.domain.dto.VideoQueryDTO;
import com.itsight.domain.pojo.VideoPOJO;
import com.itsight.repository.BandejaTemporalRepository;
import com.itsight.repository.IdiomaRepository;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Enums.Msg;
import com.itsight.util.Parseador;
import com.itsight.util.RSA_Encryption;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.itsight.util.Utilitarios.jsonResponse;

@Controller
@RequestMapping("/p")
public class PublicoController extends BaseController {

    private CondicionMejoraService condicionMejoraService;

    private DisciplinaService disciplinaService;

    private PostulanteTrainerService postulanteTrainerService;

    private UbPeruService ubPeruService;

    private SecurityUserRepository securityUserRepository;

    private IdiomaRepository idiomaRepository;

    private ClienteService clienteService;

    private TrainerFichaService trainerFichaService;

    private SecurityUserService securityUserService;

    @Autowired
    private BandejaTemporalRepository bandejaTemporalRepository;

    @Autowired
    public PublicoController(CondicionMejoraService condicionMejoraService,
                             DisciplinaService disciplinaService,
                             PostulanteTrainerService postulanteTrainerService,
                             UbPeruService ubPeruService,
                             SecurityUserRepository securityUserRepository,
                             IdiomaRepository idiomaRepository,
                             ClienteService clienteService,
                             TrainerFichaService trainerFichaService,
                             SecurityUserService securityUserService) {
        this.condicionMejoraService = condicionMejoraService;
        this.disciplinaService = disciplinaService;
        this.postulanteTrainerService = postulanteTrainerService;
        this.ubPeruService = ubPeruService;
        this.securityUserRepository = securityUserRepository;
        this.idiomaRepository = idiomaRepository;
        this.clienteService = clienteService;
        this.trainerFichaService = trainerFichaService;
        this.securityUserService = securityUserService;
    }

    @GetMapping("/inicio")
    public ModelAndView index(){
        return new ModelAndView(ViewConstant.MAIN_INICIO);
    }

    @GetMapping("/contigo")
    public ModelAndView contigo(){
        return new ModelAndView(ViewConstant.MAIN_CONTIGO);
    }

    @GetMapping("/asesores")
    public ModelAndView asesores(){
        return new ModelAndView(ViewConstant.MAIN_ASESORES);
    }

    @GetMapping("/quienes-somos")
    public ModelAndView quienesSomos(){
        return new ModelAndView(ViewConstant.MAIN_QUIENES_SOMOS);
    }

    @GetMapping("/busqueda/trainers")
    public ModelAndView busquedaTrainer(Model model)
    {
        model.addAttribute("idiomas", idiomaRepository.findAll());
        model.addAttribute("peLiDistritos", ubPeruService.findPeDistByDepAndProv("15", "01"));
        return new ModelAndView(ViewConstant.MAIN_BUSQUEDA_TRAINER);
    }

    @GetMapping("/preguntas-frecuentes")
    public ModelAndView preguntasFrecuentes(){
        return new ModelAndView(ViewConstant.MAIN_PREGUNTAS_FRECUENTES);
    }

    @GetMapping("/terminos-y-condiciones")
    public ModelAndView terminosYcondiciones(){
        return new ModelAndView(ViewConstant.MAIN_TERMINOS_Y_CONDICIONES);
    }

    @GetMapping("/recurso-no-encontrado")
    public ModelAndView recursoNoEncontrado(){
        return new ModelAndView(ViewConstant.P_ERROR404);
    }

    @GetMapping("/ficha-inscripcion")
    public ModelAndView fichaInscripcionRunner(
            @RequestParam(name="key", required=false) String hshTrainerId,
            @RequestParam(name="ml", required=false) String trainerMailDecode,
            Model model) {
        return getFichaApropiada(model,
                                hshTrainerId,
                                trainerMailDecode,
                                ViewConstant.MAIN_FICHA_INSCRIPCION_RUNNING);
    }

    @GetMapping("/ficha-inscripcion/general")
    public ModelAndView fichaInscripcionGeneral(
            @RequestParam(name="key", required=false) String hshTrainerId,
            @RequestParam(name="ml", required=false) String trainerMailDecode,
            Model model) {
        return getFichaApropiada(model,
                                hshTrainerId,
                                trainerMailDecode,
                                ViewConstant.MAIN_FICHA_INSCRIPCION_GENERAL);
    }

    //TRAINER PROCESS

    @GetMapping("/postulacion/trainer")
    public ModelAndView preRegistroTrainer(){
        return new ModelAndView(ViewConstant.MAIN_EMPRENDE_CON_NOSOTROS);
    }

    @GetMapping("/a")
    public ModelAndView a(){
        return new ModelAndView(ViewConstant.MAIN_INF_P);
    }

    @GetMapping("/postulacion/trainer/verificar-correo/{hashPreTrainerId}")
    public ModelAndView confirmarCorreoPostulacionTrainer(
            Model model,
            @PathVariable(name = "hashPreTrainerId") String hashPreTrainerId){

        Integer preTraId = getDecodeHashIdShorter("rf-request", hashPreTrainerId);

        if(preTraId == 0){
            return new ModelAndView(ViewConstant.P_ERROR404);
        }

        PostulanteTrainer post = postulanteTrainerService.findOne(preTraId);
        if(post == null){
            return new ModelAndView(ViewConstant.P_ERROR404);
        }

        if(post.isFlagCuentaConfirmada()){
            model.addAttribute("msg", Msg.CUENTA_YA_VERIFICADA.get());
            return new ModelAndView(ViewConstant.MAIN_INF_P);
        }

        postulanteTrainerService.updateFlagCuentaConfirmada(post, true);
        model.addAttribute("msg", Msg.CUENTA_VERIFICADA.get());
        return new ModelAndView(ViewConstant.MAIN_INF_P);
    }

    @PostMapping("/postulacion/trainer/registrar")
    public @ResponseBody String registrarSolicitudTrainer(
            @ModelAttribute @Valid PostulanteTrainerDTO postulanteTrainerDTO) throws CustomValidationException {
        PostulanteTrainer preTrainer = new PostulanteTrainer();
        BeanUtils.copyProperties(postulanteTrainerDTO, preTrainer);
        return jsonResponse(postulanteTrainerService.registrar(preTrainer, null));
    }

    @GetMapping("/formulario/trainer/{hashPreTrainerId}")
    public ModelAndView formularioRegistroTrainerParticular(Model model,
                @PathVariable(name = "hashPreTrainerId") String hashPreTrainerId){
        Integer preTraId = getDecodeHashIdShorter("rf-request", hashPreTrainerId);

        if(preTraId == 0){
            return new ModelAndView(ViewConstant.P_ERROR404);
        }

        PostulanteTrainer post = postulanteTrainerService.findOne(preTraId);
        if(post == null){
            return new ModelAndView(ViewConstant.P_ERROR404);
        }

        if(post.isFlagRechazado()|| !post.isFlagAceptado()){
            return new ModelAndView(ViewConstant.P_ERROR404);
        }

        if(post.isFlagRegistrado()){
            model.addAttribute("msg", Msg.POSTULANTE_YA_REG.get());
            return new ModelAndView(ViewConstant.MAIN_INF_P);
        }

        Date now = new Date();
        if(now.after(post.getFechaLimiteAccion())){
            model.addAttribute("msg", Msg.POST_LINK_EXP_PR.get());
            return new ModelAndView(ViewConstant.MAIN_INF_N);
        }

        model.addAttribute("postulante", post);
        model.addAttribute("distritos", ubPeruService.findPeDistByDepAndProv("15", "01"));
        return new ModelAndView(ViewConstant.MAIN_REGISTRO_TRAINER);
    }

    @GetMapping("/formulario/empresa/{hashPreTrainerId}")
    public ModelAndView formularioRegistroTrainerEmpresa(Model model,
                                                  @PathVariable(name = "hashPreTrainerId") String hashPreTrainerId){
        Integer preTraId = getDecodeHashIdShorter("rf-request", hashPreTrainerId);

        if(preTraId == 0){
            return new ModelAndView(ViewConstant.P_ERROR404);
        }

        PostulanteTrainer post = postulanteTrainerService.findOne(preTraId);
        if(post == null){
            return new ModelAndView(ViewConstant.P_ERROR404);
        }

        if(post.isFlagRechazado()|| !post.isFlagAceptado()){
            return new ModelAndView(ViewConstant.P_ERROR404);
        }

        if(post.isFlagRegistrado()){
            model.addAttribute("msg", Msg.POSTULANTE_YA_REG.get());
            return new ModelAndView(ViewConstant.MAIN_INF_P);
        }

        Date now = new Date();
        if(now.after(post.getFechaLimiteAccion())){
            model.addAttribute("msg", Msg.POST_LINK_EXP_PR.get());
            return new ModelAndView(ViewConstant.MAIN_INF_N);
        }

        model.addAttribute("postulante", post);
        model.addAttribute("distritos", ubPeruService.findPeDistByDepAndProv("15", "01"));
        return new ModelAndView(ViewConstant.MAIN_REGISTRO_TRAINER_EMPRESA);
    }

    @GetMapping("/busqueda/trainer/{codTrainer}")
    public ModelAndView perfilTrainer(@PathVariable(value = "codTrainer") String codTrainer){
        return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER);
    }

    //END TRAINER PROCESS

    @GetMapping("/bandeja")
    public @ResponseBody List<BandejaTemporal> getBandejaGeneral(){
        return bandejaTemporalRepository.findAllByOrderByIdDesc();
    }

    @GetMapping(value = "/condicion-mejora/listar/todos")
    public @ResponseBody
    List<CondicionMejoraDTO> listarCondicionesMejora(){
        return condicionMejoraService.getAll();
    }

    @PostMapping(value = "/cliente/fitness/agregar")
    public @ResponseBody String nuevo(@RequestBody @Valid ClienteDTO cliente) {
        return clienteService.registroFull(cliente);
    }

    @PostMapping(value = "/encryptar")
    public String encryptar(@RequestBody String p){
        String encryptRSA;
        try {
            RSA_Encryption rsa = new RSA_Encryption();
            encryptRSA = rsa.encrypted_(p);
            return jsonResponse(encryptRSA);
        } catch (Exception e2) {
            e2.printStackTrace();
            // TODO: handle exception
        }
        return Enums.ResponseCode.EX_GENERIC.get();
    }

    @PostMapping(value = "/desencryptar", consumes = "application/json")
    public @ResponseBody String desencryptar(@RequestBody String p){
        try {
            String decrypted;
            RSA_Encryption rsa = new RSA_Encryption();
            decrypted = rsa.decrypted_(p);
            return jsonResponse(decrypted);
        } catch (Exception e2) {
            e2.printStackTrace();
            // TODO: handle exception
        }
        return Enums.ResponseCode.EX_GENERIC.get();
    }

    @GetMapping(value = "/validacion-correo")
    public @ResponseBody Boolean validarCorreoUnico(@RequestParam String valor){
        return securityUserRepository.findCorreoExist(valor);
    }

    @GetMapping(value = "/validacion-username")
    public @ResponseBody Boolean validarUsernameUnico(@RequestParam String valor){
        return Optional.ofNullable(securityUserRepository.findIdByUsername(valor)).isPresent();
    }

    @GetMapping(value = "/validacion-nompag")
    public @ResponseBody Boolean validarNomPag(@RequestParam String valor){
        return trainerFichaService.checkNomPagExiste(valor);
    }

    @GetMapping("/visitante/registro/verificar-correo/{hashVisitanteId}")
    public ModelAndView confirmarCorreoRegistroVisitante (
            Model model,
            @PathVariable(name = "hashVisitanteId") String hashVisitanteId, @RequestParam String sc ) throws SecCustomValidationException{

        Integer ViId = getDecodeHashIdSecCustom(new String(Base64.getDecoder().decode(sc.getBytes())), hashVisitanteId);

        if(ViId == 0){
            return new ModelAndView(ViewConstant.P_ERROR404);
        }

        SecurityUser securityUser = securityUserRepository.findById(ViId).orElse(null);

        if(securityUser == null){
            return new ModelAndView(ViewConstant.P_ERROR404);
        }

        if(securityUser.isEnabled()){
            model.addAttribute("msg", Msg.CUENTA_YA_VERIFICADA.get());
            return new ModelAndView(ViewConstant.MAIN_INF_P);
        }

        securityUserService.updateFlagEnabled(securityUser.getId(), securityUser.isEnabled());
        model.addAttribute("msg", Msg.CUENTA_VISITANTE_VERIFICADA.get());
        return new ModelAndView(ViewConstant.MAIN_INF_P);
    }

    @GetMapping("/informativo")
    public ModelAndView informativo(
            @RequestParam String g,
            Model model){
        model.addAttribute("msg", Parseador.getDecodeBase64(g));
        return new ModelAndView(ViewConstant.MAIN_INF_P);
    }
}
