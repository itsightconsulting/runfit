package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.MultimediaEntrenador;
import com.itsight.service.MultimediaEntrenadorService;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.UUID;

import static com.itsight.util.Enums.ResponseCode.EXITO_GENERICA;

@Controller
@RequestMapping("/gestion/multimedia")
public class MultimediaEntrenadorController {
    private static final Logger LOGGER = LogManager.getLogger(MultimediaEntrenadorController.class);


    @Value("${main.repository}")
    private String mainRoute;

    private MultimediaEntrenadorService multimediaEntrenadorService;

    @Autowired
    public MultimediaEntrenadorController(MultimediaEntrenadorService multimediaEntrenadorService) {
        // TODO Auto-generated constructor stub
        this.multimediaEntrenadorService = multimediaEntrenadorService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal(Model model) {
        model.addAttribute("lstTipoAudio", multimediaEntrenadorService.findAll());
        return new ModelAndView(ViewConstant.MAIN_AUDIO);
    }

    @RequestMapping(value = "/deleteaudiovideo", method = RequestMethod.POST)
    public @ResponseBody String deleteAudioVideo(@RequestParam Integer id,HttpSession session) {

        MultimediaEntrenador objencontrado = multimediaEntrenadorService.findOne(id);
        int idUser = Integer.parseInt(session.getAttribute("id").toString());

        String fullPath = mainRoute +"/Multimedia/"+idUser;
        fullPath += "/" + objencontrado.getNombreArchivoUnico() + objencontrado.getExtension();
        File file = new File(fullPath);
        if(file.exists()) {
            file.delete();
        }
        multimediaEntrenadorService.delete(id);
        return EXITO_GENERICA.get();
    }

    @GetMapping(value = "/obtenerListadoMultimedia")
    public @ResponseBody
    List<MultimediaEntrenador> listarMultimediaEntrenador(HttpSession session) {
        int idUser = Integer.parseInt(session.getAttribute("id").toString());
        List<MultimediaEntrenador> lista = multimediaEntrenadorService.findByTrainer(idUser);
        String fullPath = mainRoute +"/Multimedia/"+idUser+"/";
        for (MultimediaEntrenador obj : lista) {
            if (obj.getNombreArchivoUnico() != null) {
                String nuevaruta = fullPath + obj.getNombreArchivoUnico()+obj.getExtension();
                obj.setRutaWeb(nuevaruta);
            }

            int cantidad = multimediaEntrenadorService.findDetalleTopCantidad(obj.getId());
            obj.setCantidadLikes(cantidad);
        }

        return lista;
    }

    @RequestMapping(value = "/uploadtext", method = RequestMethod.POST)
    public @ResponseBody String guardarTexto(@RequestParam Integer id,@RequestParam String titulo,@RequestParam String descripcion,HttpSession session) {
        int idUser = Integer.parseInt(session.getAttribute("id").toString());

        if (id == 0) {
            MultimediaEntrenador qmul = new MultimediaEntrenador();
            qmul.setTrainer(idUser);
            qmul.setTitulo(titulo);
            qmul.setDescripcion(descripcion);
            qmul.setTipo(Enums.TipoMedia.TEXTO.get());
            qmul.setFlagActivo(true);
            multimediaEntrenadorService.save(qmul);
        } else {
            MultimediaEntrenador objencontrado = multimediaEntrenadorService.findOne(id);
            objencontrado.setTrainer(idUser);
            objencontrado.setTitulo(titulo);
            objencontrado.setDescripcion(descripcion);
            objencontrado.setTipo(Enums.TipoMedia.TEXTO.get());
            multimediaEntrenadorService.update(objencontrado);
        }
        return EXITO_GENERICA.get();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String guardarArchivo(@RequestPart MultipartFile multimedia,@RequestParam Integer id,@RequestParam String titulo,@RequestParam String descripcion,@RequestParam Integer tipo,@RequestParam String duracion, @RequestParam String peso,HttpSession session) {
        int idUser = Integer.parseInt(session.getAttribute("id").toString());

        if (multimedia != null) {
            guardarFile(multimedia, id, titulo, descripcion, tipo, idUser, duracion, peso);
        }
        return EXITO_GENERICA.get();
    }

    private void guardarFile(MultipartFile file, int id, String titulo, String descripcion, int tipo, int idUser, String duracion, String peso) {
        if (!file.isEmpty()) {
            try {
                UUID uuid = UUID.randomUUID();
                String[] splitNameFile = file.getOriginalFilename().split("\\.");
                String extension = "." + splitNameFile[splitNameFile.length - 1];
                String fullPath = mainRoute +"/Multimedia/"+idUser;
                String nombrefile = splitNameFile[0];
                if(id == 0) {
                    Utilitarios.createDirectory(fullPath);
                    fullPath += "/" + uuid + extension;

                    File nuevoFile = new File(fullPath);

                    // Agregando la ruta a la base de datos
                    MultimediaEntrenador qmul = new MultimediaEntrenador();
                    qmul.setTrainer(idUser);
                    qmul.setNombreArchivoUnico(uuid.toString());
                    qmul.setExtension(extension);
                    qmul.setTitulo(nombrefile);
                    qmul.setTipo(tipo);
                    qmul.setFlagActivo(true);
                    qmul.setDuracion(duracion);
                    qmul.setPeso(peso);

                    // Pasando la imagen  o archivo desde la web hacia el servidor en donde se guardará en la ruta especificada en la instacia nueva de File creada
                    file.transferTo(nuevoFile);

                    multimediaEntrenadorService.save(qmul);
                }else{
                    MultimediaEntrenador objencontrado = multimediaEntrenadorService.findOne(id);
                    Utilitarios.createDirectory(fullPath);
                    fullPath += "/" + uuid + extension;

                    File nuevoFile = new File(fullPath);

                    // Agregando la ruta a la base de datos

                    objencontrado.setTrainer(idUser);
                    objencontrado.setNombreArchivoUnico(uuid.toString());
                    objencontrado.setExtension(extension);
                    objencontrado.setTitulo(nombrefile);
                    objencontrado.setTipo(tipo);
                    objencontrado.setDuracion(duracion);
                    objencontrado.setPeso(peso);

                    // Pasando la imagen  o archivo desde la web hacia el servidor en donde se guardará en la ruta especificada en la instacia nueva de File creada
                    file.transferTo(nuevoFile);

                    multimediaEntrenadorService.update(objencontrado);
                }
                LOGGER.info("> ROUTE: " + fullPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("> Isn't a file");
        }
    }

    @GetMapping(value = "/obtenerListadoTop")
    public @ResponseBody
    List<MultimediaEntrenador> listarMultimediaTop(HttpSession session) {
        int idUser = Integer.parseInt(session.getAttribute("id").toString());
        List<MultimediaEntrenador> lista = multimediaEntrenadorService.findByTrainerTop(idUser);

        String fullPath = mainRoute +"/Multimedia/"+idUser+"/";
        for (MultimediaEntrenador obj : lista) {
            if (obj.getNombreArchivoUnico() != null) {
                String nuevaruta = fullPath + obj.getNombreArchivoUnico()+obj.getExtension();
                obj.setRutaWeb(nuevaruta);
            }
            int cantidad = multimediaEntrenadorService.findDetalleTopCantidad(obj.getId());
            obj.setCantidadLikes(cantidad);
        }
        return lista;
    }
}
