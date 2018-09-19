package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Paquete;
import com.itsight.service.PaqueteService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/gestion/paquete")
public class PaqueteController {

    private ServletContext context;

    private PaqueteService paqueteService;

    @Autowired
    public PaqueteController(PaqueteService paqueteService, ServletContext context) {
        // TODO Auto-generated constructor stub
        this.paqueteService = paqueteService;
        this.context = context;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principalPaquete(HttpServletRequest request) {
        return new ModelAndView(ViewConstant.MAIN_PAQUETE);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{perfil}")
    public @ResponseBody
    String listAllPackages(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado,
            @PathVariable("perfil") String perfil) throws JsonProcessingException {

        List<Paquete> lstPaquete = new ArrayList<Paquete>();
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.registerModule(new Hibernate5Module());

        if (comodin.equals("0") && estado.equals("-1") && perfil.equals("0")) {
            lstPaquete = paqueteService.listAll();
        } else {
            if (comodin.equals("0") && perfil.equals("0")) {
                lstPaquete = paqueteService.findAllByFlagActivo(Boolean.valueOf(estado));
            } else {
                comodin = comodin.equals("0") ? "" : comodin;

                lstPaquete = paqueteService.findAllByNombreContainingOrDescripcionContaining(comodin, comodin);

                if (!estado.equals("-1")) {
                    lstPaquete = lstPaquete.stream()
                            .filter(x -> Boolean.valueOf(estado).equals(x.isFlagActivo()))
                            .collect(Collectors.toList());
                }

                if (!perfil.equals("0")) {
                    lstPaquete = lstPaquete.stream()
                            .filter(x -> perfil.equals(String.valueOf(x.getPlan().getId())))
                            .collect(Collectors.toList());
                }
            }
        }

        String result = "{"
                + "\"total\":" + lstPaquete.size() + ","
                + "\"rows\":" + objMapper.writeValueAsString(lstPaquete)
                + "}";
        System.out.println("> JSON: " + result);
        return result;
    }

    @GetMapping(value = "/obtenerPaquete")
    public @ResponseBody
    Paquete getPackageById(@RequestParam(value = "id") int paqueteId) {
        return paqueteService.getPaqueteById(paqueteId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String addPackage(@ModelAttribute Paquete paquete) {
        paquete.setPlan(paquete.getFkPlan());

        if (paquete.getId() == 0) {
            paquete.setNombreContrato("");
            paquete.setRutaContrato("");
            paqueteService.add(paquete);
            return String.valueOf(paquete.getId());
        } else {
            Paquete qPaquete = paqueteService.findRouteNamesById(paquete.getId());
            paquete.setNombreContrato(qPaquete.getNombreContrato());
            paquete.setRutaContrato(qPaquete.getRutaContrato());
            paqueteService.update(paquete);
            return String.valueOf(paquete.getId());
        }
    }

    @PostMapping(value = "/desactivarPaquete")
    public @ResponseBody
    String disabledPackage(@RequestParam(value = "id") int paqueteId) {
        Paquete paquete = paqueteService.getPaqueteById(paqueteId);

        if (paquete.isFlagActivo()) {
            paquete.setFlagActivo(false);
        } else {
            paquete.setFlagActivo(true);
        }

        paqueteService.update(paquete);

        return "1";
    }

    @RequestMapping(value = "/cargarContrato", method = RequestMethod.POST)
    public @ResponseBody
    String guardarArchivo(
            @RequestPart(value = "fileContrato", required = false) MultipartFile fileContrato,
            @RequestParam(value = "paqueteId", required = true) Integer paqueteId,
            @ModelAttribute Paquete paquete, HttpServletRequest request) {

//		logger.debug("PARAMS| paqueteId: " + paqueteId);
        System.out.println("> PAQ: " + paquete.toString());
        if (fileContrato != null) {
            guardarFile(fileContrato, paqueteId);
        } else {
            System.out.println("FILE IS NULL");
        }

        return "1";

    }

    private void guardarFile(MultipartFile file, int paqueteId) {
        if (!file.isEmpty()) {
            try {

                String[] splitNameFile = file.getOriginalFilename().split("\\.");
                String extension = "." + splitNameFile[splitNameFile.length - 1];
                String fullPath = "";

                String rutaBase = String.valueOf(context.getAttribute("MAIN_ROUTE"));

                fullPath = rutaBase + "/ContratosPaquete/" + "PaqueteContrato_" + paqueteId + extension;
                File nuevoFile = new File(fullPath);

                // Agregando la ruta a la base de datos

                Paquete qPaquete = paqueteService.getPaqueteById(paqueteId);
                qPaquete.setRutaContrato(fullPath);
                qPaquete.setNombreContrato(nuevoFile.getName());
                paqueteService.update(qPaquete);

                // Pasando la imagen de la vista a un arreglo de bytes para luego convertirlo y
                // transferirlo a un nuevo file con un nombre, ruta generado con anterioridad
                byte[] bytes = file.getBytes();

                // Create the file on server
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(nuevoFile));
                stream.write(bytes);
                stream.close();

                System.out.println("> ROUTE: " + fullPath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("> Isn't a file");
        }
    }
}
