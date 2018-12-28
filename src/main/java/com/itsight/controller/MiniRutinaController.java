package com.itsight.controller;


import com.itsight.constants.ViewConstant;
import com.itsight.domain.Dia;
import com.itsight.domain.MiMiniRutina;
import com.itsight.domain.MiniRutina;
import com.itsight.domain.dto.ResponseDto;
import com.itsight.domain.jsonb.MiRutinaPk;
import com.itsight.service.CategoriaService;
import com.itsight.service.DiaService;
import com.itsight.service.MiMiniRutinaService;
import com.itsight.service.MiniRutinaService;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.itsight.util.Enums.ResponseCode.REGISTRO;

@Controller
@RequestMapping("/gestion/mini-rutina")
@PreAuthorize("hasRole('TRAINER') OR hasRole('ADMIN')")
public class MiniRutinaController {

    private MiniRutinaService miniRutinaService;
    
    private MiMiniRutinaService miMiniRutinaService;

    private DiaService diaService;

    private CategoriaService categoriaService;

    @Autowired
    public MiniRutinaController(MiniRutinaService miniRutinaService, MiMiniRutinaService miMiniRutinaService, DiaService diaService, CategoriaService categoriaService) {
        this.miniRutinaService = miniRutinaService;
        this.miMiniRutinaService = miMiniRutinaService;
        this.diaService = diaService;
        this.categoriaService = categoriaService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal(Model model) {
        model.addAttribute("lstCategorias", categoriaService.findAll());
        return new ModelAndView(ViewConstant.MAIN_MIS_RUTINAS_TRAINER);
    }

    @PostMapping(value = "/agregar/dia-rutinario")
    public @ResponseBody
    String nuevo(
                    @RequestParam String numeroSemana,
                    @RequestParam String diaIndice,
                    @RequestParam String categoriaId,
                    @RequestParam String nombre,
                    @RequestParam String nivel,
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
        miMiniRutina.setNivel(Integer.parseInt(nivel));
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
        String hashId = Parseador.getEncodeHash32Id("wk-mis-rutinas",miMiniRutina.getId());
        miMiniRutinaPks.add(new MiRutinaPk(miMiniRutina.getId(), nombre, hashId, Integer.parseInt(nivel)));
        //Agregando la lista jsonb con la nueva lista agregada
        miniRutina.setMiRutinaIds(miMiniRutinaPks);
        miniRutinaService.save(miniRutina);
        return REGISTRO.get();
    }

    @GetMapping("/obtenerCategoriasId/ByUsuario")
    public @ResponseBody List<Integer> obtenerCategoriasMiniRutina(HttpSession session){
        int usuarioId = Integer.parseInt(session.getAttribute("id").toString());
        return miniRutinaService.findAllCategoriaIdByUsuarioId(usuarioId);
    }

    @GetMapping("/obtener-rutinas/by/categoria/{catId}")
    public @ResponseBody ResponseDto obtenerMisMiniRutinas(@PathVariable(value = "catId") String catId, HttpSession session){
        if(Utilitarios.isInteger(catId)){
            int usuarioId = Integer.parseInt(session.getAttribute("id").toString());
            Optional<MiniRutina> optionalLst = Optional.ofNullable(miniRutinaService.findByCategoriaIdAndUsuarioId(Integer.parseInt(catId), usuarioId));
            if(optionalLst.isPresent()){
                optionalLst.get().getMiRutinaIds().forEach(v->v.setId(0));
                return new ResponseDto(Integer.parseInt(Enums.ResponseCode.SUCCESS_QUERY.get()),optionalLst.get().getMiRutinaIds());
            }
        }
        return new ResponseDto(Integer.parseInt(Enums.ResponseCode.NOT_FOUND_MATCHES.get()), null);
    }

    @GetMapping("/obtener/by/hash/{hashId}")
    public @ResponseBody
    ResponseDto obtenerMiniRutinaByHashId(@PathVariable(value = "hashId") String hashId){
        Optional<MiMiniRutina> mini = Optional.ofNullable(miMiniRutinaService.findOne(Parseador.getDecodeHash32Id("wk-mis-rutinas", hashId)));
        if(mini.isPresent())
            return new ResponseDto(Integer.parseInt(Enums.ResponseCode.SUCCESS_QUERY.get()), mini.get());
        else
            return new ResponseDto(Integer.parseInt(Enums.ResponseCode.NOT_FOUND_MATCHES.get()), null);
    }
}
