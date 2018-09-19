package com.itsight.controller.media;

import com.itsight.service.DocumentoService;
import com.itsight.service.VideoService;
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
@RequestMapping("/workout/media/file")
public class MediaFileController {

    private VideoService videoService;

    private DocumentoService documentoService;

    @Value("${main.repository}")
    private String mainRoute;

    @Autowired
    public MediaFileController(VideoService videoService, DocumentoService documentoService) {
        this.videoService = videoService;
        this.documentoService = documentoService;
    }

    @RequestMapping(value = "/paquete/gt/{strategy}/{contrato:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getAudioAsBytesArray(@PathVariable int strategy, @PathVariable(value = "contrato", required = true) String contratoName) throws IOException {
        File serverFile = new File(mainRoute + "/ContratosPaquete/" + contratoName);

        if (!serverFile.exists()) {
            return new ResponseEntity<>("No se ha encontrado el contrato, comuníquese con el administrador. Gracias por su compresión.".getBytes(), HttpStatus.NO_CONTENT);
        }
        byte[] contents = Files.readAllBytes(serverFile.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        if (strategy == 0) {
            String[] nameInTwo = serverFile.getName().split("\\.");
            headers.setContentDispositionFormData(nameInTwo[0], nameInTwo[0] + "." + serverFile.getName().split("\\.")[1]);
        }
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = "paquete/{contrato:.+}", method = RequestMethod.GET)
    public @ResponseBody
    byte[] getAudioFromAudio(@PathVariable(value = "contrato", required = true) String contratoName) {
        try {
            File serverFile = new File(
                    String.valueOf(mainRoute + "/ContratosPaquete/" + contratoName));

            return Files.readAllBytes(serverFile.toPath());
        } catch (IOException e) {
            // TODO: handle exception
            return "No se ha encontrado el contrato, comuníquese con el administrador. Gracias por su compresión.".getBytes();
        }
    }

    @RequestMapping(value = "/video/gt/{strategy}/{videoId}/{uuidName:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> obtenerVideo(
            @PathVariable int strategy,
            @PathVariable String videoId,
            @PathVariable String uuidName) throws IOException {
        File serverFile = new File(mainRoute + "/Videos/" + videoId + "/" + uuidName);

        if (!serverFile.exists() || serverFile.length() <= 0) {
            return new ResponseEntity<>("No se ha encontrado el archivo, comuníquese con el administrador. Gracias por su compresión.".getBytes(), HttpStatus.NO_CONTENT);
        }
        byte[] contents = Files.readAllBytes(serverFile.toPath());
        HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.parseMediaType("application/pdf;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;application/vnd.ms-excel"));
        if (strategy == 0) {
            String uuid = serverFile.getName().split("\\.")[0];
            String extension = "." + serverFile.getName().split("\\.")[1];
            String documentName = videoService.obtenerNombrePorId(Integer.parseInt(videoId), uuid);
            headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
            headers.setContentDispositionFormData("Video", documentName + extension);
        }
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = "/documento/gt/{strategy}/{documentoId}/{uuidName:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> obtenerDocumento(
            @PathVariable int strategy,
            @PathVariable String documentoId,
            @PathVariable String uuidName) throws IOException {
        File serverFile = new File(mainRoute + "/Documentos/" + documentoId + "/" + uuidName);

        if (!serverFile.exists() || serverFile.length() <= 0) {
            return new ResponseEntity<>("No se ha encontrado el archivo, comuníquese con el administrador. Gracias por su compresión.".getBytes(), HttpStatus.NO_CONTENT);
        }
        byte[] contents = Files.readAllBytes(serverFile.toPath());
        HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.parseMediaType("application/pdf;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;application/vnd.ms-excel"));
        if (strategy == 0) {
            String uuid = serverFile.getName().split("\\.")[0];
            String extension = "." + serverFile.getName().split("\\.")[1];
            String documentName = documentoService.obtenerNombrePorId(Integer.parseInt(documentoId), uuid);
            headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
            headers.setContentDispositionFormData("Video", documentName + extension);
        }
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }

}
