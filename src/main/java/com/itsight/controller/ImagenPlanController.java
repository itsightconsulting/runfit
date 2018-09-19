package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.ImagenPlan;
import com.itsight.service.ImagenPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Controller
@RequestMapping("/gestion/imagen-plan")
public class ImagenPlanController {

    private ServletContext context;

    private ImagenPlanService imagenPlanService;

    @Autowired
    public ImagenPlanController(ServletContext context, ImagenPlanService imagenPlanService) {
        // TODO Auto-generated constructor stub
        this.imagenPlanService = imagenPlanService;
        this.context = context;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principalImagenPlan() {
        return new ModelAndView(ViewConstant.MAIN_IMAGEN_PLAN);
    }

    @GetMapping(value = "/listarTodos")
    public @ResponseBody
    String listAll(@RequestParam(value = "id") int planId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Hibernate5Module());

        List<ImagenPlan> lstImagenPlan = imagenPlanService.findByPlanId(planId);
        if (lstImagenPlan.isEmpty()) {
            return "-1";
        }
        return objectMapper.writeValueAsString(lstImagenPlan);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String addImagenPlan(@ModelAttribute ImagenPlan imagenPlan) {

        if (imagenPlan.getId() == 0) {

            imagenPlanService.add(imagenPlan);
            return "1";
        } else {
            imagenPlanService.update(imagenPlan);
            return "2";
        }
    }

    @RequestMapping(value = "/cargarImagen", method = RequestMethod.POST)
    public @ResponseBody
    String guardarArchivo(
            @RequestPart(value = "fileImagenPlan", required = true) MultipartFile fileImagenPlan,
            @RequestParam(value = "planId", required = true) Integer planId,
            @RequestParam(value = "tipoImagenId", required = true) Integer tipoImagenId, HttpServletRequest request) {

//		logger.debug("PARAMS| planId: " + planId);

        if (fileImagenPlan != null) {
            guardarFile(fileImagenPlan, planId, tipoImagenId);
        }

        return "1";

    }

    private void guardarFile(MultipartFile file, int planId, int tipoImagenId) {
        if (!file.isEmpty()) {
            try {

                String[] splitNameFile = file.getOriginalFilename().split("\\.");
                String extension = "." + splitNameFile[splitNameFile.length - 1];
                String fullPath = "";

                String rutaBase = String.valueOf(context.getAttribute("MAIN_ROUTE"));

                fullPath = rutaBase + "/Planes/" + "Imagen_" + planId + "_" + tipoImagenId + extension;
                File nuevoFile = new File(fullPath);

                // Agregando la ruta a la base de datos

                ImagenPlan qImagenPlan = new ImagenPlan();
                qImagenPlan.setPlan(planId);
                qImagenPlan.setTipoImagen(tipoImagenId);
                qImagenPlan.setRutaMedia(fullPath);
                qImagenPlan.setNombreMedia(nuevoFile.getName());

                // Pasando la imagen de la vista a un arreglo de bytes para luego convertirlo y
                // transferirlo a un nuevo file con un nombre, ruta generado con anterioridad
                byte[] bytes = file.getBytes();

                // Create the file on server
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(nuevoFile));
                stream.write(bytes);
                stream.close();

                imagenPlanService.add(qImagenPlan);


                System.out.println("> ROUTE: " + fullPath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("> Isn't a file");
        }
    }
}
