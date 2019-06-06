package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.AudioTrainer;
import com.itsight.service.AudioTrainerService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import static com.itsight.util.Enums.FileExt.WEBM;
import static com.itsight.util.Utilitarios.jsonResponse;

@Controller
@RequestMapping("/audio-trainer")
public class AudioTrainerController{

    private AudioTrainerService audioTrainerService;

    public AudioTrainerController(AudioTrainerService audioTrainerService) {
        this.audioTrainerService = audioTrainerService;
    }

    @PostMapping("/nuevo")
    public @ResponseBody String nuevo(
            @ModelAttribute AudioTrainer d, HttpSession session){
        int tId = (Integer) session.getAttribute("id");
        return Enums.ResponseCode.EXITO_GENERICA.get();
    }

    @PutMapping("/subir/foto/perfil")
    public @ResponseBody String subirAudio(
            @RequestPart(name = "file") MultipartFile file,
            @PathVariable(name = "rdmUUID") String uuid,
            HttpSession session) throws CustomValidationException {
        int tId = (Integer) session.getAttribute("id");
        return jsonResponse(audioTrainerService.subirFile(file, tId,uuid, WEBM.get().substring(1)));
    }
}
