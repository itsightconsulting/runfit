package com.itsight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.advice.CustomValidationException;
import com.itsight.domain.Post;
import com.itsight.domain.dto.PostDTO;
import com.itsight.domain.dto.RefUpload;
import com.itsight.service.PostService;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static com.itsight.util.Enums.ResponseCode.EXITO_GENERICA;
import static com.itsight.util.Enums.ResponseCode.EX_GENERIC;
import static com.itsight.util.Utilitarios.jsonResponse;

@Controller
@RequestMapping("/gestion/consejo")
public class PostController {

    private static final Logger LOGGER = LogManager.getLogger(PostController.class);

    @Value("${main.repository}")
    private String mainRoute;

    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @RequestMapping(value = "/upload/audio", method = RequestMethod.POST)
    public @ResponseBody
    String guardarArchivo(@RequestPart MultipartFile multimedia,
                          @RequestParam Integer id,
                          @RequestParam String postString,
                          HttpSession session) throws IOException {

        int trainerId = Integer.parseInt(session.getAttribute("id").toString());
        PostDTO postDto = new ObjectMapper().readValue(postString, PostDTO.class);
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);

        post.setTrainer(trainerId);

        if (multimedia != null) {
            RefUpload refUpload = postService.guardarAudio(multimedia, id, post);

            return jsonResponse(String.valueOf(refUpload.getId()),
                    refUpload.getUuid().toString());
        }
        return EX_GENERIC.get();
    }

    @RequestMapping(value = "/upload/aws/{rdmUUID}", method = RequestMethod.POST)
    public @ResponseBody
    String subirAudioAws(
            @RequestPart(value = "audio", required = true) MultipartFile audio,
            @RequestParam(value = "audioId", required = true) Integer audioId,
            @PathVariable(name = "rdmUUID") String uuid) throws CustomValidationException {
        return Utilitarios.jsonResponse(postService.subirAudioAws(audio, audioId, uuid, null));
    }

    @RequestMapping(value = "/upload/text", method = RequestMethod.POST)
    public @ResponseBody String guardarTexto(@RequestParam Integer id,@RequestParam String titulo,@RequestParam String descripcion,HttpSession session) {
        int idUser = Integer.parseInt(session.getAttribute("id").toString());

        if (id == 0) {
            Post post = new Post();
            post.setTrainer(idUser);
            post.setTitulo(titulo);
            post.setDescripcion(descripcion);
            post.setTipo(Enums.TipoMedia.TEXTO.get());
            post.setFlagActivo(true);
            postService.save(post);
        } else {
            Post post = postService.findOne(id);
            post.setTitulo(titulo);
            post.setDescripcion(descripcion);
            postService.update(post);
        }
        return EXITO_GENERICA.get();
    }

    @RequestMapping(value = "/desactivar", method = RequestMethod.POST)
    public @ResponseBody String actualizarEstadoConsejo(@RequestParam Integer id,HttpSession session) {

        int idUser = Integer.parseInt(session.getAttribute("id").toString());
        postService.actualizarEstadoPost(id);

        return EXITO_GENERICA.get();
    }

    @GetMapping("/get/{id}")
    public @ResponseBody ResponseEntity<Post> obtenerPostById(@PathVariable(value = "id") int id){
        if(postService.findOne(id) == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postService.findOne(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/get")
    public @ResponseBody ResponseEntity<Post> obtenerPostById2(@RequestParam(value = "id") Integer id){
        if(postService.findOne(id) == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postService.findOne(id), HttpStatus.ACCEPTED);
    }
}
