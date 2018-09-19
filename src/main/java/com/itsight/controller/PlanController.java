package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.Plan;
import com.itsight.service.PlanService;
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
@RequestMapping("/gestion/plan")
public class PlanController {

    private ServletContext context;

    private PlanService planService;

    @Autowired
    public PlanController(ServletContext context, PlanService planService) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.planService = planService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principalPlan() {
        return new ModelAndView(ViewConstant.MAIN_PLAN);
    }

    @GetMapping(value = "/listarTodos")
    public @ResponseBody
    List<Plan> listAllPlanWithoutFilters() throws JsonProcessingException {
        return planService.readAll();
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}")
    public @ResponseBody
    String listAllPlan(
            @PathVariable("comodin") String comodin,
            @PathVariable("estado") String estado) throws JsonProcessingException {

        List<Plan> lstPlan = new ArrayList<Plan>();
        ObjectMapper objMapper = (new ObjectMapper()).registerModule(new Hibernate5Module());

        if (comodin.equals("0") && estado.equals("-1")) {
            lstPlan = planService.listAll();
        } else {
            if (comodin.equals("0")) {
                lstPlan = planService.findAllByFlagActivo(Boolean.valueOf(estado));
            } else {

                lstPlan = planService.findAllByNombre(comodin);

                if (estado.equals("-1")) {
                } else {
                    lstPlan = lstPlan.stream()
                            .filter(x -> Boolean.valueOf(estado).equals(x.isFlagActivo()))
                            .collect(Collectors.toList());
                }
            }
        }

        String result = "{"
                + "\"total\":" + lstPlan.size() + ","
                + "\"rows\":" + objMapper.writeValueAsString(lstPlan)
                + "}";
        System.out.println("> JSON: " + result);
        return result;
    }

    @GetMapping(value = "/obtenerPlan")
    public @ResponseBody
    Plan getPlanById(@RequestParam(value = "id") int planId) {
        return planService.getPlanById(planId);
    }

    @PostMapping(value = "/agregar")
    public @ResponseBody
    String addPlan(@ModelAttribute Plan plan) {

        if (plan.getId() == 0) {

            planService.add(plan);
            return "1";
        } else {
            planService.update(plan);
            return "2";
        }
    }

    @PostMapping(value = "/desactivarPlan")
    public @ResponseBody
    String disabledPlan(@RequestParam(value = "id") int planId) {
        Plan plan = planService.getPlanById(planId);

        if (plan.isFlagActivo()) {
            plan.setFlagActivo(false);
        } else {
            plan.setFlagActivo(true);
        }
        planService.update(plan);

        return "1";
    }

    @RequestMapping(value = "/cargarImagen", method = RequestMethod.POST)
    public @ResponseBody
    String registrarFileVigenciaPoder(
            @RequestParam(value = "imagen", required = true) MultipartFile imagen,
            @RequestParam(value = "planId", required = true) Integer planId,
            @RequestParam(value = "tipoImagenId", required = true) Integer tipoImagenId, HttpServletRequest request) {

        if (imagen != null) {

            switch (tipoImagenId) {
                case 1:
                    guardarFile(imagen, planId, "ABC");
                case 2:
                    guardarFile(imagen, planId, "DEF");
                case 3:
                    guardarFile(imagen, planId, "GHI");
                case 4:
                    guardarFile(imagen, planId, "JKL");
                case 5:
                    guardarFile(imagen, planId, "MNO");
                case 6:
                    guardarFile(imagen, planId, "PQR");
                default:
                    return "-1";
            }
        }

        return "1";

    }

    private void guardarFile(MultipartFile file, Integer planId, String tipoImagen) {
        if (!file.isEmpty()) {
            try {

                String[] splitNameFile = file.getOriginalFilename().split("\\.");
                String extension = "." + splitNameFile[splitNameFile.length - 1];
                String fullPath = "";

                String rutaBase = String.valueOf(context.getAttribute("MAIN_ROUTE"));

                fullPath = rutaBase + "/Planes/Planes_" + tipoImagen + "_" + planId + extension;
                File nuevoFile = new File(fullPath);

                // Agregando la ruta a la base de datos

                Plan qPlan = planService.getPlanById(planId);
                planService.update(qPlan);

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
