package com.itsight;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.*;
import com.itsight.repository.AudioRepository;
import com.itsight.repository.UsuarioRepository;
import com.itsight.repository.specifications.UsuarioSpecificationBuilder;
import com.itsight.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/rest")
public class RestDemoController {

    @Autowired
    private ProductoPresentacionService proPresentacionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private TipoAudioService tipoAudioService;

    @Autowired
    private AudioRepository audioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioFitnessService usuarioFitnessService;

    @Autowired
    private RutinaService rutinaService;

    @Autowired
    private SemanaService semanaService;

    @Autowired
    private DiaService diaService;

    @Autowired
    private RedFitnessService redFitnessService;

    @GetMapping("/presentacion")
    public @ResponseBody
    List<ProductoPresentacion> listaPP() {
        return proPresentacionService.findAll();
    }

    @GetMapping("/mtom")
    public @ResponseBody
    String listaPX() {
        try {
            Producto producto = productoService.findOne(1);
            Set<Video> lstVideo = new HashSet<Video>();
            lstVideo.add(videoService.findOneWithFT(2));
            //producto.setLstVideo(lstVideo);
            productoService.save(producto);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            return "FAIL";
        }
        return "OK";
    }

    @GetMapping("/padre-hijo")
    public List<TipoAudio> getTA() throws JsonProcessingException {
        //return audioRepository.findAll();
       /* UsuarioSpecification spec =
                new UsuarioSpecification(new SearchCriteria("nombres", ":", "Peter"));*/

        UsuarioSpecificationBuilder builder = new UsuarioSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\"([^\"]+)\")");
        Matcher matcher = pattern.matcher("apellidoMaterno:\"Carrasco Estrada\"" + ",");
        while (matcher.find()) {
            String d = matcher.group(3).substring(1,matcher.group(3).length()-1);
            System.out.println(">D: "+d);
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            builder.with(matcher.group(1), matcher.group(2), d);
        }

        builder.with("flagActivo","=", true);
        Specification<Usuario> spec = builder.build();
        usuarioRepository.findAll(spec).forEach(usuario-> System.out.println(usuario.getNombreCompleto()));
        return tipoAudioService.findAllWithChildrens();
    }

    @GetMapping("/fitness")
    public  @ResponseBody List<UsuarioFitness> listaAllUsuariosFitness(){
        return usuarioFitnessService.findAll();
    }

    @GetMapping("/audios")
    public @ResponseBody List<TipoAudio> listaAllTipoAudio(){
        return tipoAudioService.findAll();
    }

    @GetMapping("/audios/c")
    public @ResponseBody List<TipoAudio> listaAllTipoAudio2(){
        return tipoAudioService.findAllWithChildrens();
    }

    @GetMapping("/rutina/{id}")
    public Rutina obtenerRutinaConSoloUnaSemana(@PathVariable int id){
        Rutina r = rutinaService.findOne(id);
        r.setFechaFin(new Date());
        r.setTotalSemanas(200);
        rutinaService.update(r);
        return rutinaService.findOne(id);
    }

    @GetMapping("/semana/{id}")
    public Semana obtenerSemanaRutina(){
        return semanaService.findOneWithDaysById(1);
    }

    @GetMapping("/video/all")
    public List<Video> obtenerAllVideos(){
        return videoService.findAll();
    }
	
	@GetMapping("/video/chelmo")
    public List<Video> metodoChelmo(){
        return videoService.findAll();
    }

}
