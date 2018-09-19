package com.itsight.controller.media;

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
@RequestMapping("/workout/media/audio")
public class MediaAudioController {

    @Value("${main.repository}")
    private String mainRoute;

    @Autowired
    public MediaAudioController() {
    }

    @RequestMapping(value = "/gt/{audio:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getAudioAsBytesArray(@PathVariable(value = "audio", required = true) String audioName) throws IOException {
        File serverFile = new File(mainRoute + "/Audios/" + audioName);

        if (!serverFile.exists()) {
            return new ResponseEntity<>("No se ha encontrado el audio, comuníquese con el administrador. Gracias por su compresión.".getBytes(), HttpStatus.NO_CONTENT);
        }
        byte[] contents = Files.readAllBytes(serverFile.toPath());
        String[] nameInTwo = serverFile.getName().split("\\.");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("audio/mp3"));
        headers.setContentDispositionFormData(nameInTwo[0], nameInTwo[0] + "." + serverFile.getName().split("\\.")[1]);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = "/{id}/{audio:.+}", method = RequestMethod.GET)
    public @ResponseBody
    byte[] getAudioFromAudio(@PathVariable(value = "id", required = true) String id, @PathVariable(value = "audio", required = true) String audioName) {
        try {
            File serverFile = new File(mainRoute + "/Audios/" + id + "/" + audioName);
            return Files.readAllBytes(serverFile.toPath());
        } catch (IOException e) {
            // TODO: handle exception
            return "No se ha encontrado el audio, comuníquese con el administrador. Gracias por su compresión.".getBytes();
        }
    }

}
