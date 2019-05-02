package com.itsight.controller;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.BandejaTemporal;
import com.itsight.domain.PostulanteTrainer;
import com.itsight.domain.dto.PostulanteTrainerDTO;
import com.itsight.domain.dto.TrainerFichaDTO;
import com.itsight.domain.pojo.TrainerFichaPOJO;
import com.itsight.repository.BandejaTemporalRepository;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Enums.Msg;
import com.itsight.util.Parseador;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;
import static com.itsight.util.Enums.ResponseCode.REGISTRO;
import static com.itsight.util.Utilitarios.jsonResponse;

@Controller
@RequestMapping("/p")
@PropertySource("${aws.auths.file}")
public class PublicoController {

    private CondicionMejoraService condicionMejoraService;

    private DisciplinaService disciplinaService;

    private PostulanteTrainerService postulanteTrainerService;

    private UbPeruService ubPeruService;

    private TrainerService trainerService;

    private TrainerFichaService trainerFichaService;

    @Value("${amazon.aws.s3.access.key}")
    private String aws3accessKey;

    @Value("${amazon.aws.s3.secret.key}")
    private String aws3secretKey;

    @Value("${amazon.aws.s3.region}")
    private String aws3region;

    @Value("${amazon.aws.s3.bucket}")
    private String aws3bucket;

    @Autowired
    private BandejaTemporalRepository bandejaTemporalRepository;

    @Autowired
    public PublicoController(CondicionMejoraService condicionMejoraService,
                             DisciplinaService disciplinaService,
                             PostulanteTrainerService postulanteTrainerService,
                             UbPeruService ubPeruService,
                             TrainerService trainerService,
                             TrainerFichaService trainerFichaService) {
        this.condicionMejoraService = condicionMejoraService;
        this.disciplinaService = disciplinaService;
        this.postulanteTrainerService = postulanteTrainerService;
        this.ubPeruService = ubPeruService;
        this.trainerService = trainerService;
        this.trainerFichaService = trainerFichaService;
    }

    @GetMapping("/fi/2")
    public ModelAndView fiDos(){
        return new ModelAndView("portal/ficha_inscripcion");
    }

    @GetMapping("/inicio")
    public ModelAndView index(){
        return new ModelAndView(ViewConstant.MAIN_INICIO);
    }

    @GetMapping("/contigo")
    public ModelAndView contigo(){
        return new ModelAndView(ViewConstant.MAIN_CONTIGO);
    }

    @GetMapping("/quienes-somos")
    public ModelAndView quienesSomos(){
        return new ModelAndView(ViewConstant.MAIN_QUIENES_SOMOS);
    }

    @GetMapping("/busqueda/trainers")
    public ModelAndView busquedaTrainer(){
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

    @GetMapping({"/ficha-inscripcion", "/ficha-inscripcion"})
    public ModelAndView fichaInscripcion(
        @RequestParam(name="key", required=false) String hshTrainerId,
        @RequestParam(name="ml", required=false) String trainerMailDecode,
        Model model) {
        if(hshTrainerId != null){
            Integer trainerId = Parseador.getDecodeHash32Id("rf-public", hshTrainerId);
            if(trainerId == 0){
                return new ModelAndView(ViewConstant.ERROR404);
            }else{
                model.addAttribute("trainerId", trainerId);
                model.addAttribute("correoTrainer", trainerMailDecode);
            }
        }
        model.addAttribute("lstCondMejoras", condicionMejoraService.findAll());
        return new ModelAndView(ViewConstant.MAIN_FICHA_INSCRIPCION);
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
        Integer preTraId = 0;

        if(hashPreTrainerId.length() == 32){
            preTraId = Parseador.getDecodeHash32Id("rf-request", hashPreTrainerId);
        }

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

        postulanteTrainerService.updateFlagCuentaConfirmada(post.getId(), true, post.getCorreo());
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
    public ModelAndView formularioRegistroTrainer(Model model,
                @PathVariable(name = "hashPreTrainerId") String hashPreTrainerId){
        Integer preTraId = 0;

        if(hashPreTrainerId.length() == 32){
            preTraId = Parseador.getDecodeHash32Id("rf-request", hashPreTrainerId);
        }

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

        model.addAttribute("disciplinas", disciplinaService.findAll());
        model.addAttribute("postulante", post);
        model.addAttribute("distritos", ubPeruService.findPeDistByDepAndProv("15", "01"));
        return new ModelAndView(ViewConstant.MAIN_REGISTRO_TRAINER);
    }

    @PostMapping("/registro/trainer/{hashPreTrainerId}")
    public @ResponseBody String registroTrainer(
                @PathVariable(name = "hashPreTrainerId") String hashPreTrainerId,
                @RequestBody @Valid TrainerFichaDTO trainerFicha, HttpSession session) throws CustomValidationException {
        String uriImgPerfil = "";
        if(session.getAttribute("uri_img") != null){
            uriImgPerfil = session.getAttribute("uri_img").toString();
        }
        Integer postTraId = 0;

        if(hashPreTrainerId.length() == 32){
            postTraId = Parseador.getDecodeHash32Id("rf-request", hashPreTrainerId);
        }

        if(postTraId == 0){
            throw new CustomValidationException("La validación ha fallado", EX_VALIDATION_FAILED.get());
        }

        PostulanteTrainer postulante = postulanteTrainerService.findOne(postTraId);
        if(postulante == null){
            throw new CustomValidationException("La validación ha fallado", EX_VALIDATION_FAILED.get());
        }

        if(!postulante.isFlagAceptado()){
            return Enums.ResponseCode.EX_VALIDATION_FAILED.get();
        }

        if(postulante.isFlagAceptado() && !postulante.isFlagRegistrado()){
            //Nos aseguramos que el correo sea el mismo que se encuentra en BD
            //y no el que enviaron en la petición post
            trainerFicha.setCorreo(postulante.getCorreo());
            trainerFicha.setPostulanteTrainerId(postTraId);
            trainerFicha.setFotoPerfil(uriImgPerfil);
            return trainerService.registrarPostulante(trainerFicha);
        }


        return jsonResponse(REGISTRO.get());
    }

    @GetMapping("/busqueda/trainer/{codTrainer}")
    public ModelAndView perfilTrainer(@PathVariable(value = "codTrainer") String codTrainer){
        return new ModelAndView(ViewConstant.MAIN_PERFIL_TRAINER);
    }

    //END TRAINER PROCESS

    //TestQueries
    @GetMapping("/get/all/trainer-ficha")
    public @ResponseBody List<TrainerFichaPOJO> findAllTrainerResponseBody(){
        return trainerFichaService.findAllWithFgEnt();
    }

    @GetMapping("/bandeja")
    public @ResponseBody List<BandejaTemporal> getBandejaGeneral(){
        return bandejaTemporalRepository.findAllByOrderByIdDesc();
    }

    @PostMapping(value = "/simple-imagen")
    public @ResponseBody String subirImagenAmazonS3(
            @RequestPart(value = "file", required = false) MultipartFile file, HttpSession session) throws IOException {

        if (!file.isEmpty()) {

            String[] splitNameFile = file.getOriginalFilename().split("\\.");
            String extension = "." + splitNameFile[splitNameFile.length - 1];
            UUID uuid = UUID.randomUUID();

            // Pasando la imagen de la vista a un arreglo de bytes para luego convertirlo y
            // transferirlo a un nuevo file con un nombre, ruta generado con anterioridad


            try {
                String keyName = uuid+extension;
                BasicAWSCredentials awsCreds = new BasicAWSCredentials(aws3accessKey, aws3secretKey);

                AmazonS3 amazonS3 = AmazonS3ClientBuilder
                        .standard()
                        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                        .withRegion(aws3region)
                        .build();

                int maxUploadThreads = 5;

                TransferManager tm = TransferManagerBuilder
                        .standard()
                        .withS3Client(amazonS3)
                        .withMultipartUploadThreshold((long) (5 * 1024 * 1024))
                        .withExecutorFactory(() -> Executors.newFixedThreadPool(maxUploadThreads))
                        .build();
                System.out.println(file.getSize());

                ProgressListener progressListener =
                        progressEvent -> System.out.println("Transferred bytes: " + progressEvent.getBytesTransferred());

                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                PutObjectRequest request = new PutObjectRequest(aws3bucket, "perfil/"+keyName, file.getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead);
                List<Tag> tags = new ArrayList<>();
                tags.add(new Tag("one", "rf-test"));
                tags.add(new Tag("two", "undesired"));
                request.setTagging(new ObjectTagging(tags));

                request.setGeneralProgressListener(progressListener);

                Upload upload = tm.upload(request);
                session.setAttribute("uri_img", keyName);
                try {
                    upload.waitForCompletion();
                    System.out.println("Upload complete.");
                } catch (AmazonClientException e) {
                    System.out.println("Error occurred while uploading file");
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } /*catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
            return "1";
        }
        return "-1";
    }

    @GetMapping("/api/file/{keyname}")
    public ResponseEntity<byte[]> descargarFile(@PathVariable String keyname) {
        ByteArrayOutputStream downloadInputStream = downloadFile(keyname);

        return ResponseEntity.ok()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                //.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + keyname + "\"")
                .body(downloadInputStream.toByteArray());
    }

    public ByteArrayOutputStream downloadFile(String keyName) {
        try {
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(aws3accessKey, aws3secretKey);

            AmazonS3 amazonS3 = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .withRegion(aws3region)
                    .build();

            S3Object s3object = amazonS3.getObject(new GetObjectRequest(aws3bucket, keyName));

            InputStream is = s3object.getObjectContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }

            return baos;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (AmazonServiceException ase) {
            throw ase;
        } catch (AmazonClientException ace) {
            throw ace;
        }

        return null;
    }
}
