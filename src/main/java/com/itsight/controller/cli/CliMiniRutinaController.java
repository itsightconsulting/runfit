package com.itsight.controller.cli;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.MiMiniRutina;
import com.itsight.domain.MiniRutina;
import com.itsight.domain.dto.ResponseDto;
import com.itsight.service.CategoriaService;
import com.itsight.service.MiMiniRutinaService;
import com.itsight.service.MiniRutinaService;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/{demo}/mini-rutinas")
@PreAuthorize("hasRole('ROLE_DG_CLI')")
public class CliMiniRutinaController {

    private MiniRutinaService miniRutinaService;

    private MiMiniRutinaService miMiniRutinaService;

    private CategoriaService categoriaService;

    @Autowired
    public CliMiniRutinaController(MiniRutinaService miniRutinaService, MiMiniRutinaService miMiniRutinaService, CategoriaService categoriaService) {
        this.miniRutinaService = miniRutinaService;
        this.miMiniRutinaService = miMiniRutinaService;
        this.categoriaService = categoriaService;
    }

    @GetMapping(value = "")
    public ModelAndView principal(Model model) {
        model.addAttribute("lstCategorias", categoriaService.findAll());
        return new ModelAndView(ViewConstant.MAIN_MIS_RUTINAS_CLIENTE);
    }

    @GetMapping("/obtener-categorias-id/by/trainer")
    public @ResponseBody List<Integer> obtenerCategoriasMiniRutina(){
        return miniRutinaService.findAllCategoriaIdByTrainerId(2);//2: Id de entrenador de DG
    }

    @GetMapping("/obtener-rutinas/by/categoria/{catId}")
    public @ResponseBody
    ResponseDto obtenerMisMiniRutinas(@PathVariable(value = "catId") String catId){
        if(Utilitarios.isInteger(catId)){
            int trainerId = 2;//2: Id de entrenador de DG
            Optional<MiniRutina> optionalLst = Optional.ofNullable(miniRutinaService.findByCategoriaIdAndTrainerId(Integer.parseInt(catId), trainerId));
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
