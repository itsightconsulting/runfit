package com.itsight.controller;

import com.itsight.domain.MultimediaEntrenador;
import com.itsight.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller("/gestion/consejo")
public class PostController {

    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }


    @GetMapping(value = "/obtenerListadoMultimedia")
    public @ResponseBody
    List<MultimediaEntrenador> listarMultimediaEntrenador(HttpSession session) {
        /*int idUser = Integer.parseInt(session.getAttribute("id").toString());
        List<MultimediaEntrenador> lista = multimediaEntrenadorService.findByTrainer(idUser);
        String fullPath = mainRoute +"/Multimedia/"+idUser+"/";
        for (MultimediaEntrenador obj : lista) {
            if (obj.getNombreArchivoUnico() != null) {
                String nuevaruta = fullPath + obj.getNombreArchivoUnico()+obj.getExtension();
                obj.setRutaWeb(nuevaruta);
            }

            int cantidad = multimediaEntrenadorService.findDetalleTopCantidad(obj.getId());
            obj.setCantidadLikes(cantidad);
        }*/
        return null;
    }
}
