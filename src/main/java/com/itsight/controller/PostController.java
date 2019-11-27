package com.itsight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.advice.CustomValidationException;
import com.itsight.domain.Post;
import com.itsight.domain.dto.PostDTO;
import com.itsight.domain.dto.RefUpload;
import com.itsight.service.PostService;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.itsight.util.Enums.ResponseCode.EXITO_GENERICA;
import static com.itsight.util.Enums.ResponseCode.EX_NULL_POINTER;
import static com.itsight.util.Utilitarios.jsonResponse;

@Controller
@RequestMapping("/gestion/consejo")
public class PostController {

    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @RequestMapping(value = "/upload/audio", method = RequestMethod.POST)
    public @ResponseBody
    String guardarArchivo(@RequestParam Integer id,
                          @RequestParam String postString,
                          @RequestParam String extensionAudio,
                          HttpSession session) throws IOException, CustomValidationException {

        int trainerId = Integer.parseInt(session.getAttribute("id").toString());
        PostDTO postDto = new ObjectMapper().readValue(postString, PostDTO.class);
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);

        post.setTrainer(trainerId);

        RefUpload refUpload = postService.guardarAudio(id, post, extensionAudio);
        if(refUpload != null){
            return jsonResponse(String.valueOf(refUpload.getId()),
                    refUpload.getUuid().toString());
        }
        throw new CustomValidationException("Algo ha salido mal, intentelo nuevamente", EX_NULL_POINTER.get());
    }

    @RequestMapping(value = "/upload/aws/{rdmUUID}", method = RequestMethod.POST)
    public @ResponseBody
    String subirAudioAws(
            @RequestPart(value = "audio", required = true) MultipartFile audio,
            @RequestParam(value = "audioId", required = true) Integer audioId,
            @PathVariable(name = "rdmUUID") String uuid) throws CustomValidationException {
        return Utilitarios.jsonResponse(postService.subirFile(audio, audioId, uuid, null));
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

    @RequestMapping(value = "/desactivar", method = RequestMethod.PUT)
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
