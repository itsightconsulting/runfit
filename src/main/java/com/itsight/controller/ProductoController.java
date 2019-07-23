package com.itsight.controller;

import com.itsight.advice.CustomValidationException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Producto;
import com.itsight.domain.ProductoPresentacion;
import com.itsight.domain.Video;
import com.itsight.repository.TipoDescuentoRepository;
import com.itsight.service.CategoriaService;
import com.itsight.service.ProductoPresentacionService;
import com.itsight.service.ProductoService;
import com.itsight.service.VideoService;
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

import java.io.File;
import java.util.List;
import java.util.UUID;

import static com.itsight.util.Enums.ResponseCode.*;

@Controller
@RequestMapping("/gestion/producto")
public class ProductoController {

    private static final Logger LOGGER = LogManager.getLogger(ProductoController.class);

    private ProductoService productoService;

    private CategoriaService categoriaService;

    private VideoService videoService;

    private ProductoPresentacionService productoPresentacionService;

    private TipoDescuentoRepository tipoDescuentoRepository;

    @Value("${main.repository}")
    private String mainRoute;

    @Autowired
    public ProductoController(ProductoService productoService,
                              CategoriaService categoriaService,
                              ProductoPresentacionService productoPresentacionService,
                              VideoService videoService,
                              TipoDescuentoRepository tipoDescuentoRepository) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.productoPresentacionService = productoPresentacionService;
        this.videoService = videoService;
        this.tipoDescuentoRepository = tipoDescuentoRepository;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal(Model model) {
        model.addAttribute("lstCategoria", categoriaService.findAll());
        model.addAttribute("lstTipoDescuento", tipoDescuentoRepository.findAll());
        return new ModelAndView(ViewConstant.MAIN_PRODUCTO);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{categoria}")
    public @ResponseBody
    List<Producto> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @PathVariable("categoria") String categoria) {
        return productoService.listarPorFiltro(comodin, estado, categoria);
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    Producto obtenerPorId(@RequestParam(value = "id") int id) {
        return productoService.findProductoById(id);
    }

    @GetMapping(value = "/presentacion/video/{id}")
    public @ResponseBody
    String obtenerRutaVideoPresentacion(@PathVariable int id) {
        return "99";
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@ModelAttribute Producto producto, @ModelAttribute ProductoPresentacion presentacion, String categoriaId) throws CustomValidationException {
        producto.setProductoPresentacion(presentacion);
        if (producto.getId() == 0)
            return productoService.registrar(producto, categoriaId);
        return productoService.actualizar(producto, categoriaId);
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            productoService.actualizarFlagActivoById(id, flagActivo);
            return EXITO_GENERICA.get();
        } catch (Exception e) {
            return EX_GENERIC.get();
        }
    }

    @RequestMapping(value = "/upload/presentacion", method = RequestMethod.POST)
    public @ResponseBody
    String subirArchivo(
            @RequestPart MultipartFile imagenPresentacion,
            @RequestPart(required = false) MultipartFile videoPresentacion,
            @RequestParam(value = "productoId") Integer id) {

        int tipo;
        if (imagenPresentacion != null) {
            tipo = 1;
            guardarFile(imagenPresentacion, id, tipo);//1|imagen
        }

        if (videoPresentacion != null) {
            tipo = 2;
            guardarFile(videoPresentacion, id, tipo);//2|video
        }

        return EXITO_GENERICA.get();
    }

    private void guardarFile(MultipartFile file, int id, int tipo) {
        if (!file.isEmpty()) {
            try {
                String fullPath = mainRoute + "/Productos/Presentacion/" + id;
                Utilitarios.createDirectory(fullPath);

                String[] splitNameFile = file.getOriginalFilename().split("\\.");
                String extension = "." + splitNameFile[splitNameFile.length - 1];

                UUID uuid = UUID.randomUUID();
                fullPath += "/" + uuid + extension;
                File nuevoFile = new File(fullPath);
                // Pasando la imagen  o archivo desde la web hacia el servidor en donde se guardará en la ruta especificada en la instacia nueva de File creada
                file.transferTo(nuevoFile);

                if (tipo == 1) {
                    // Agregando la ruta a la base de datos
                    ProductoPresentacion qPPresentacion = productoPresentacionService.findOne(id);
                    qPPresentacion.setNombreArchivo(nuevoFile.getName());
                    qPPresentacion.setRutaWebArchivo("/" + id + "/" + uuid + extension);
                    /*qPPresentacion.setRutaRealArchivo(fullPath);*/
                    //Pusheando la actualizacion a la base de datos(Save and flush)
                    productoPresentacionService.update(qPPresentacion);
                } else {
                    Video video = new Video();
                    video.setNombre(file.getOriginalFilename());
                    video.setRutaWeb("/" + id + "/" + uuid + extension);
                    video.setUuid(uuid);
                    //video.setTipoVideo(1);//DE TIPO PRESENTACION
                    videoService.save(video);
                }
                LOGGER.info("> ROUTE: " + fullPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("> Isn't a file");
        }
    }
}
