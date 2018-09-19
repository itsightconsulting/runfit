package com.itsight.controller;

import com.itsight.constants.ViewConstant;
import com.itsight.domain.Dia;
import com.itsight.domain.RedFitness;
import com.itsight.domain.Rutina;
import com.itsight.domain.Semana;
import com.itsight.domain.dto.DiaPlantillaDto;
import com.itsight.domain.dto.RutinaDto;
import com.itsight.domain.dto.RutinaPlantillaDto;
import com.itsight.domain.dto.SemanaPlantillaDto;
import com.itsight.service.RedFitnessService;
import com.itsight.service.RutinaService;
import com.itsight.util.Enums;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/gestion/trainer/red")
public class RedFitnessController {

    private RedFitnessService redFitnessService;

    private RutinaService rutinaService;

    public RedFitnessController(RedFitnessService redFitnessService, RutinaService rutinaService){
        this.redFitnessService = redFitnessService;
        this.rutinaService = rutinaService;
    }

    @GetMapping("")
    public ModelAndView principal(){
        return new ModelAndView(ViewConstant.MAIN_TRAINER_RED);
    }

    @GetMapping(value = "/obtenerListado")
    public @ResponseBody
    List<RedFitness> listarConFiltro(HttpSession session) {
        if (session.getAttribute("codTrainer") != null) {
            String codigoTrainer = session.getAttribute("codTrainer").toString();
            return redFitnessService.listarSegunRedTrainer(codigoTrainer);
        }
        return new ArrayList<>();
    }

    @PutMapping(value = "/anadir-nota")
    public @ResponseBody
    String actualizarNotaAIntegrante(@RequestParam String id, @RequestParam String nota) {
        redFitnessService.actualizarNotaAIntegrante(Integer.parseInt(id), nota);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @PostMapping(value = "/pre-asignar-rutina")
    public @ResponseBody String preAsignarRutina(@RequestBody RutinaDto rutinaDto){
        Rutina rutina = new Rutina();
        //Copiando las propiedades del dto 
        BeanUtils.copyProperties(rutinaDto, rutina);
        Date ultimoDiaPlanificado = null;
        //RutinaDto es un arreglo multidimensional que se diferencia del original en la propiedad lista de semanas
        //RutinaDto -> semanas | Rutina -> lstSemana
        //Esto con el fin de cortar la copia de propiedades mediante BeanUtils en el primer nivel
        //Pasando del Dto al objeto
        //Instanciando lista de semanas
        List<Semana> semanas = new ArrayList<>();
        for (SemanaPlantillaDto semDto: rutinaDto.getSemanas()) {
            Semana semana = new Semana();
            BeanUtils.copyProperties(semDto, semana);
            semanas.add(semana);
            //Pasando el objeto de rutina a la semana para al momento de su registro, el ID que se genere(De la RutPlan)
            //se referencie en el registro de la semana
            semana.setRutina(rutina);
            //Insertando dias de la semana a su respectiva lista de dias
            List<Dia> dias = new ArrayList<>();
            for(DiaPlantillaDto diaDto: semDto.getDias()){
                Dia dia = new Dia();
                BeanUtils.copyProperties(diaDto, dia);
                dias.add(dia);
                //Pasando el objeto de semana al dia para al momento de su registro, el ID que se genere(De la SemPlan)
                //se referencie en el registro del día
                dia.setSemana(semana);
            }
            //Obteniendo el ultimoDiaPlanificado
            ultimoDiaPlanificado = dias.get(dias.size()-1).getFecha();
            //Agregando la lista de dias a la semana
            semana.setLstDia(dias);
        }
        //Agregando las semanas a la instancia de rutina que hará que se inserten mediante cascade strategy
        rutina.setId(0);
        rutina.setLstSemana(semanas);
        rutina.setTipoRutina(Enums.TipoRutina.RUNNING.get());
        rutina.setFlagActivo(false);
        rutina.setUsuario(rutinaDto.getClienteId());
        rutina.setRedFitness(rutinaDto.getRedFitnessId());
        rutinaService.save(rutina);
        //Actualizando el status del integrante de la red y último día de la planificación
        redFitnessService.updatePlanStatusAndUltimoDiaPlanificacionAndContadorRutinas(rutinaDto.getRedFitnessId(), Enums.EstadoPlan.EN_REVISION.get(), ultimoDiaPlanificado, rutinaDto.getContadorRutinas());
        //After registro, guardamos los ids de las semanas y los insertamos en la rutina
        int[] semanaIds = new int[semanas.size()];
        int i = 0;
        for (Semana s1:semanas) {
            semanaIds[i] = s1.getId();i++;
        }
        rutinaService.updateSemanaIds(rutina.getId(), semanaIds);
        return Enums.ResponseCode.REGISTRO.get() + ","+rutinaDto.getRedFitnessId();
    }
}
