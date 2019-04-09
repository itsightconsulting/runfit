package com.itsight.controller;


import com.itsight.constants.ViewConstant;
import com.itsight.domain.*;
import com.itsight.domain.jsonb.DiaRutinarioPk;
import com.itsight.domain.jsonb.ListaPlantillaSimplePk;
import com.itsight.service.DiaRutinarioService;
import com.itsight.service.DiaService;
import com.itsight.service.MiniPlantillaService;
import com.itsight.service.ListaPlantillaSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.itsight.util.Enums.ResponseCode.REGISTRO;

@Controller
@RequestMapping("/gestion/mini-plantilla")
public class MiniPlantillaController {

    private MiniPlantillaService miniPlantillaService;

    private DiaRutinarioService diaRutinarioService;

    private DiaService diaService;

    @Autowired
    public MiniPlantillaController(MiniPlantillaService miniPlantillaService, DiaRutinarioService diaRutinarioService, DiaService diaService) {
        this.miniPlantillaService = miniPlantillaService;
        this.diaRutinarioService = diaRutinarioService;
        this.diaService = diaService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal() {
        return new ModelAndView(ViewConstant.MAIN_MINI_PLANTILLA);
    }

    @GetMapping(value = "/obtener")
    public @ResponseBody
    MiniPlantilla obtenerPorId(@RequestParam(value = "id") int objetivoId) {
        return miniPlantillaService.findOne(objetivoId);
    }

    @GetMapping(value = "/obtener/dia-rutinario")
    public @ResponseBody
    List<DiaRutinario> rutinariosz(@RequestParam String subCatId, @RequestParam String index, HttpSession session) {
        String[] evaluacion = subCatId.split("\\|");
        int subCategoriaId = Integer.parseInt(evaluacion[0]);
        int adicionales = Integer.parseInt(evaluacion[1]);

        //La diferencia la hace el usuarioId que esta en session
        MiniPlantilla miniPlantilla = miniPlantillaService.findByTrainerIdAndEspecificacionSubCategoriaId(Integer.parseInt(session.getAttribute("id").toString()), subCategoriaId);

        List<Integer> diaIds = new ArrayList<>();
        for (int i=0; i<adicionales+1;i++){
            diaIds.add(miniPlantilla.getDiaRutinarioIds().get(Integer.parseInt(index)+i).getId());
        }
        //(plantillaListaId): Posteriormente será usado para insertar esa misma listaPlantilla en una rutina que se este generando
        session.setAttribute("diaRutinarioId", miniPlantilla.getDiaRutinarioIds().get(Integer.parseInt(index)).getId());
        return diaRutinarioService.findByIdsIn(diaIds);

    }

    @GetMapping(value = "/obtener/referencias")
    public @ResponseBody String obtenerReferenciasDeMiniPlantilla(@RequestParam String espSubCatId, HttpSession session) {
        int esSubCatId = Integer.parseInt(espSubCatId);
        int trainerId = Integer.parseInt(session.getAttribute("id").toString());
        int totalMiniPlantillas = miniPlantillaService.findPlantillaIdsByTrainerIdAndEspecificacionSubCategoriaId(trainerId, esSubCatId);
        return String.valueOf(totalMiniPlantillas);
    }

    @PostMapping(value = "/agregar/dia-rutinario")
    public @ResponseBody
    String nuevo(@RequestParam String numeroSemana, @RequestParam String diaIndice, @RequestParam String especificacionSubCategoriaId, HttpSession session) {
        //Obteniendo el diaId
        Integer semanaId = ((Integer[]) session.getAttribute("semanaIds"))[Integer.parseInt(numeroSemana)];
        int diaPlantillaId = diaService.encontrarIdPorSemanaId(semanaId).get(Integer.parseInt(diaIndice));
        DiaRutinario diaRutinario = new DiaRutinario();
        Dia qDia = diaService.findOne(diaPlantillaId);
        diaRutinario.setElementos(qDia.getElementos());
        diaRutinario.setMinutos(qDia.getMinutos());
        diaRutinario.setDistancia(qDia.getDistancia());
        diaRutinario.setCalorias(qDia.getCalorias());

        MiniPlantilla miniPlantilla = miniPlantillaService.findByTrainerIdAndEspecificacionSubCategoriaId(Integer.parseInt(session.getAttribute("id").toString()), Integer.parseInt(especificacionSubCategoriaId));
        List<DiaRutinarioPk> diaRutinarioPks;
        //Verificamos que aún no se haya insertado alguna mini plantilla a lstMiniPlantilla
        if(miniPlantilla.getDiaRutinarioIds() != null && miniPlantilla.getDiaRutinarioIds().size()> 0){
            diaRutinarioPks = miniPlantilla.getDiaRutinarioIds();
        }else{
            diaRutinarioPks = new ArrayList<>();
        }
        //Registrando primero el dia rutinario

        diaRutinario.setFechaCreacion();
        diaRutinarioService.save(diaRutinario);
        //Agregando el id de la nueva listaPlantilla generada en la lista de ids
        diaRutinarioPks.add(new DiaRutinarioPk(diaRutinario.getId()));
        //Agregando la lista jsonb con la nueva lista agregada
        miniPlantilla.setDiaRutinarioIds(diaRutinarioPks);
        miniPlantillaService.save(miniPlantilla);
        return REGISTRO.get();
    }
}
