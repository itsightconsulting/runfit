package com.itsight.controller.media;

import com.itsight.annotation.NoLogging;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/workout/multimedia")
@NoLogging
public class MedioVideoController {
    @Value("${main.repository}")
    private String mainRoute;

    @Autowired
    public MedioVideoController() {
    }

    @RequestMapping(value = "/audio/{id}/{audio:.+}", method = RequestMethod.GET)
    public @ResponseBody
    byte[] getAudioFromAudio(@PathVariable(value = "id", required = true) String id, @PathVariable(value = "audio", required = true) String audioName) {
        try {
            File serverFile = new File(mainRoute + "/Multimedia/" + id + "/" + audioName);
            return Files.readAllBytes(serverFile.toPath());
        } catch (IOException e) {
            // TODO: handle exception
            return "No se ha encontrado el audio, comuníquese con el administrador. Gracias por su compresión.".getBytes();
        }
    }


    @RequestMapping(value = "/video/{id}/{video:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> obtenerVideo(
            @PathVariable(value = "id", required = true) String id,
            @PathVariable(value = "video", required = true) String videoName) throws IOException {
        File serverFile = new File(mainRoute + "/Multimedia/"+ id +"/"+ videoName);

        if (!serverFile.exists() || serverFile.length() <= 0) {
            return new ResponseEntity<>("No se ha encontrado el archivo, comuníquese con el administrador. Gracias por su compresión.".getBytes(), HttpStatus.NO_CONTENT);
        }
        byte[] contents = Files.readAllBytes(serverFile.toPath());
        HttpHeaders headers = new HttpHeaders();

        String uuid = serverFile.getName().split("\\.")[0];
        String extension = "." + serverFile.getName().split("\\.")[1];

        headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
        headers.setContentDispositionFormData("Video", uuid  + extension);

        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }

}
