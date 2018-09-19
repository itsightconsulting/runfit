package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Producto;
import com.itsight.domain.ProductoPresentacion;
import com.itsight.domain.Video;
import com.itsight.repository.TipoDescuentoRepository;
import com.itsight.service.CategoriaService;
import com.itsight.service.ProductoPresentacionService;
import com.itsight.service.ProductoService;
import com.itsight.service.VideoService;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

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
        //model.addAttribute("productos", productoService.listarPorFiltro("0","-1","0"));
        return new ModelAndView(ViewConstant.MAIN_PRODUCTO);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{categoria}")
    public @ResponseBody
    List<Producto> listarConFiltro(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @PathVariable("categoria") String categoria) throws JsonProcessingException {
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
    String nuevo(@ModelAttribute Producto producto, @ModelAttribute ProductoPresentacion presentacion, String categoriaId) {
        producto.setProductoPresentacion(presentacion);
        if (producto.getId() == 0)
            return Utilitarios.customResponse(Enums.ResponseCode.REGISTRO.get(), productoService.registrar(producto, categoriaId));
        return productoService.actualizar(producto, categoriaId);
    }

    @PutMapping(value = "/desactivar")
    public @ResponseBody
    String desactivar(@RequestParam(value = "id") int id, @RequestParam boolean flagActivo) {
        try {
            productoService.actualizarFlagActivoById(id, flagActivo);
            return "1";
        } catch (Exception e) {
            return "-9";
        }
    }

    @RequestMapping(value = "/upload/presentacion", method = RequestMethod.POST)
    public @ResponseBody
    String subirArchivo(
            @RequestPart(required = true) MultipartFile imagenPresentacion,
            @RequestPart(required = false) MultipartFile videoPresentacion,
            @RequestParam(value = "productoId", required = true) Integer id, HttpServletRequest request) {

        int tipo = 0;
        if (imagenPresentacion != null) {
            tipo = 1;
            guardarFile(imagenPresentacion, id, tipo);//1|imagen
        }

        if (videoPresentacion != null) {
            tipo = 2;
            guardarFile(videoPresentacion, id, tipo);//2|video
        }

        return "1";
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
                    video.setRutaReal(fullPath);
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
