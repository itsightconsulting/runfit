package com.itsight.controller.media;

import com.itsight.annotation.NoLogging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequestMapping(value = "/workout/media/image")
public class MediaImageController {


    @Value("${main.repository}")
    private String mainRoute;

    @RequestMapping(value = "/plan/{image:.+}", method = RequestMethod.GET)
    public @ResponseBody
    byte[] getImageFromPlanImage(@PathVariable(value = "image", required = true) String imageName) {
        try {
            File serverFile = new File(
                    mainRoute + "/Planes/" + imageName);

            return Files.readAllBytes(serverFile.toPath());
        } catch (IOException e) {
            // TODO: handle exception
            return "No se ha encontrado la imagen, comuníquese con el administrador. Gracias por su compresión.".getBytes();
        }
    }

    @NoLogging
    @RequestMapping(value = "/categoria/gt/{strategy}/{id}/{imagen:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImagenCategoriaAsBytesArray(
            @PathVariable int strategy,
            @PathVariable int id,
            @PathVariable String imagen) throws IOException {

        File serverFile = new File(mainRoute + "/Categorias/" +id +"/"+ imagen);

        if (!serverFile.exists()) {
            return new ResponseEntity<>("No se ha encontrado la imagen de la categoria, comuníquese con el administrador. Gracias por su compresión.".getBytes(), HttpStatus.NO_CONTENT);
        }
        byte[] contents = Files.readAllBytes(serverFile.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        if (strategy == 0) {
            String[] nameInTwo = serverFile.getName().split("\\.");
            headers.setContentDispositionFormData(nameInTwo[0], nameInTwo[0] + "." + serverFile.getName().split("\\.")[1]);
        }
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }

    @NoLogging
    @RequestMapping(value = "/categoria-ejercicio/gt/{strategy}/{id}/{imagen:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImagenCategoriaEjercicioAsBytesArray(
            @PathVariable int strategy,
            @PathVariable int id,
            @PathVariable String imagen) throws IOException {

        File serverFile = new File(mainRoute + "/CategoriasEjercicio/" +id +"/"+ imagen);

        if (!serverFile.exists()) {
            return new ResponseEntity<>("No se ha encontrado la imagen de la categoria, comuníquese con el administrador. Gracias por su compresión.".getBytes(), HttpStatus.NO_CONTENT);
        }
        byte[] contents = Files.readAllBytes(serverFile.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        if (strategy == 0) {
            String[] nameInTwo = serverFile.getName().split("\\.");
            headers.setContentDispositionFormData(nameInTwo[0], nameInTwo[0] + "." + serverFile.getName().split("\\.")[1]);
        }
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }

    @NoLogging
    @RequestMapping(value = "/grupo-video/gt/{strategy}/{id}/{imagen:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImagenGruposVideoAsBytesArray(
            @PathVariable int strategy,
            @PathVariable int id,
            @PathVariable String imagen) throws IOException {

        File serverFile = new File(mainRoute + "/GruposVideo/" +id +"/"+ imagen);

        if (!serverFile.exists()) {
            return new ResponseEntity<>("No se ha encontrado la imagen de la categoria, comuníquese con el administrador. Gracias por su compresión.".getBytes(), HttpStatus.NO_CONTENT);
        }
        byte[] contents = Files.readAllBytes(serverFile.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        if (strategy == 0) {
            String[] nameInTwo = serverFile.getName().split("\\.");
            headers.setContentDispositionFormData(nameInTwo[0], nameInTwo[0] + "." + serverFile.getName().split("\\.")[1]);
        }
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }
}
