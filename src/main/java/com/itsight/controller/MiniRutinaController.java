package com.itsight.controller;


import com.itsight.domain.Dia;
import com.itsight.domain.MiMiniRutina;
import com.itsight.domain.MiniRutina;
import com.itsight.domain.jsonb.MiRutinaPk;
import com.itsight.service.MiMiniRutinaService;
import com.itsight.service.DiaService;
import com.itsight.service.MiniRutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.itsight.util.Enums.ResponseCode.REGISTRO;

@Controller
@RequestMapping("/gestion/mini-rutina")
public class MiniRutinaController {

    private MiniRutinaService miniRutinaService;
    
    private MiMiniRutinaService miMiniRutinaService;

    private DiaService diaService;

    @Autowired
    public MiniRutinaController(MiniRutinaService miniRutinaService, MiMiniRutinaService miMiniRutinaService, DiaService diaService) {
        this.miniRutinaService = miniRutinaService;
        this.miMiniRutinaService = miMiniRutinaService;
        this.diaService = diaService;
    }

    @PostMapping(value = "/agregar/dia-rutinario")
    public @ResponseBody
    String nuevo(
                    @RequestParam String numeroSemana,
                    @RequestParam String diaIndice,
                    @RequestParam String categoriaId,
                    HttpSession session) {
        //Obteniendo el diaId
        int usuarioId = Integer.parseInt(session.getAttribute("id").toString());
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[Integer.parseInt(numeroSemana)];
        int diaPlantillaId = diaService.encontrarIdPorSemanaId(semanaId).get(Integer.parseInt(diaIndice));
        MiMiniRutina miMiniRutina = new MiMiniRutina();
        Dia qDia = diaService.findOne(diaPlantillaId);
        miMiniRutina.setElementos(qDia.getElementos());
        miMiniRutina.setMinutos(qDia.getMinutos());
        miMiniRutina.setDistancia(qDia.getDistancia());
        miMiniRutina.setFechaCreacion();
        miMiniRutinaService.save(miMiniRutina);

        MiniRutina miniRutina = miniRutinaService.findByUsuarioIdAndCategoriaId(usuarioId, Integer.parseInt(categoriaId));
        List<MiRutinaPk> miMiniRutinaPks;
        //Verificamos que aún no se haya insertado alguna mini plantilla a lstMiniRutina
        if(miniRutina != null && miniRutina.getMiRutinaIds() != null && miniRutina.getMiRutinaIds().size()> 0){
            miMiniRutinaPks = miniRutina.getMiRutinaIds();
        }else{
            miniRutina = new MiniRutina();
            miniRutina.setCategoria(Integer.parseInt(categoriaId));
            miniRutina.setUsuario(usuarioId);
            miMiniRutinaPks = new ArrayList<>();
        }
        //Registrando primero el dia rutinario

        miMiniRutina.setFechaCreacion();
        /*miMiniRutinaService.save(miMiniRutina);*/
        //Agregando el id de la nueva listaPlantilla generada en la lista de ids
        miMiniRutinaPks.add(new MiRutinaPk(miMiniRutina.getId()));
        //Agregando la lista jsonb con la nueva lista agregada
        miniRutina.setMiRutinaIds(miMiniRutinaPks);
        miniRutinaService.save(miniRutina);
        return REGISTRO.get();
    }
}
