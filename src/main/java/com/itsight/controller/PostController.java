package com.itsight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.domain.Post;
import com.itsight.domain.dto.PostDTO;
import com.itsight.service.PostService;
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

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
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
            return guardarFile(multimedia, id, post);
        }
        return EX_GENERIC.get();
    }

    private String guardarFile(MultipartFile file, int id, Post post) {
        if (!file.isEmpty()) {
            try {
                int trainerId = post.getTrainer().getId();
                UUID uuid = UUID.randomUUID();
                String[] splitNameFile = file.getOriginalFilename().split("\\.");
                String extension = "." + splitNameFile[splitNameFile.length - 1];
                String fullPath = mainRoute +"/Multimedia/"+trainerId;
                String nombrefile = splitNameFile[0];
                if(id == 0) {
                    Utilitarios.createDirectory(fullPath);
                    fullPath += "/" + uuid + extension;

                    File nuevoFile = new File(fullPath);

                    // Agregando la ruta a la base de datos
                    post.setRutaWeb("/" + trainerId + "/" + uuid + extension);
                    post.setUuid(uuid);
                    post.setFlagActivo(true);
                    post.setLstDetalle(new ArrayList<>());
                    // Pasando la imagen  o archivo desde la web hacia el servidor en donde se guardará en la ruta especificada en la instacia nueva de File creada
                    file.transferTo(nuevoFile);
                    postService.save(post);
                } else {
                    Post qPost = postService.findOne(id);
                    Utilitarios.createDirectory(fullPath);
                    fullPath += "/" + uuid + extension;

                    File nuevoFile = new File(fullPath);

                    // Agregando la ruta a la base de datos
                    qPost.setRutaWeb("/" + trainerId + "/" + uuid + extension);
                    qPost.setTitulo(nombrefile);
                    qPost.setTipo(post.getTipo());
                    qPost.setDuracion(post.getDuracion());
                    qPost.setPeso(post.getPeso());

                    // Pasando la imagen  o archivo desde la web hacia el servidor en donde se guardará en la ruta especificada en la instacia nueva de File creada
                    file.transferTo(nuevoFile);
                    postService.update(qPost);
                }
                LOGGER.info("> ROUTE: " + fullPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("> Isn't a file");
        }

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
