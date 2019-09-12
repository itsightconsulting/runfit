package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.*;
import com.itsight.domain.dto.*;
import com.itsight.domain.jsonb.ElementoPlantilla;
import com.itsight.domain.jsonb.ListaPlantilla;
import com.itsight.service.*;
import com.itsight.util.ClassId;
import com.itsight.util.EntityGraphBuilder;
import com.itsight.util.EntityVisitor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.itsight.util.Enums.ResponseCode;

@Controller
@RequestMapping("/gestion/rutina-plantilla")
public class RutinaPlantillaController {

    private RutinaPlantillaService rutinaPlantillaService;

    private RutinaService rutinaService;

    private DiaPlantillaService diaPlantillaService;

    private ListaPlantillaSimpleService listaPlantillaSimpleService;

    private CategoriaEjercicioService categoriaEjercicioService;

    private TipoAudioService tipoAudioService;

    private RutinaPlantillaProcedureInvoker rutinaPlantillaProcedureInvoker;

    private EntityManager entityManager;


    @Autowired
    public RutinaPlantillaController(
            RutinaPlantillaService rutinaPlantillaService,
            DiaPlantillaService diaPlantillaService,
            ListaPlantillaSimpleService listaPlantillaSimpleService,
            CategoriaEjercicioService categoriaEjercicioService,
            TipoAudioService tipoAudioService,
            RutinaService rutinaService,
            EntityManager entityManager,
            RutinaPlantillaProcedureInvoker rutinaPlantillaProcedureInvoker) {
        this.rutinaPlantillaService = rutinaPlantillaService;
        this.diaPlantillaService = diaPlantillaService;
        this.listaPlantillaSimpleService = listaPlantillaSimpleService;
        this.categoriaEjercicioService = categoriaEjercicioService;
        this.tipoAudioService = tipoAudioService;
        this.rutinaService = rutinaService;
        this.entityManager = entityManager;
        this.rutinaPlantillaProcedureInvoker = rutinaPlantillaProcedureInvoker;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal() {
        return new ModelAndView(ViewConstant.MAIN_RUTINA_PLANTILLA);
    }

    @GetMapping(value = "/trainer")
    public ModelAndView principalTrainer() {
        return new ModelAndView(ViewConstant.MAIN_TRAINER_RUTINA_PLANTILLA);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{perfil}")
    public @ResponseBody
    List<RutinaPlantilla> listarConFiltro(
            @PathVariable("comodin") String comodin) {
        return rutinaPlantillaService.listarPorFiltro(comodin, "", "");
    }

    //PT: Para trainer
    /*
    @GetMapping(value = "/trainer/obtenerListado")
    public @ResponseBody
    List<RutinaPlantilla> listadoConFiltroPT(HttpSession session) {
        //trainerId == usuarioId
        int trainerId = Integer.parseInt(session.getAttribute("id").toString());
        //PT: Por trainer
        return rutinaPlantillaService.listarPorFiltroPT(trainerId);
    }
*/
/*
    @GetMapping(value = "/trainer/red/obtenerListado")
    public @ResponseBody
    List<RutinaPlantilla> listadoRutinaPlantillaRed(@RequestParam int redFitnessId, @RequestParam int clienteId, HttpSession session) {
        //trainerId == usuarioId
        session.setAttribute("clienteId", clienteId);
        session.setAttribute("redFitnessId", redFitnessId);
        int trainerId = Integer.parseInt(session.getAttribute("id").toString());
        //PT: Por trainer
        return rutinaPlantillaService.listarPorFiltroPT(trainerId);
    }
*/
    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@RequestBody RutinaPlantillaDTO rutinaPlantilla) {

        //RutinaPlantillaDTO es un arreglo multidimensional que se diferencia del original en la propiedad lista de semanas
        //RutinaPlantillaDTO -> semanas | RutinaPlantilla > lstSemana
        //Esto con el fin de cortar la copia de propiedades mediante BeanUtils en el primer nivel

    //   objRp.setTrainer(userId);
        //Instanciando lista de semanas

        rutinaPlantillaService.agregarRutinaPrediseñada(rutinaPlantilla);

        return ResponseCode.REGISTRO.get();
    }

    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @PutMapping(value = "/actualizar")
    public @ResponseBody
    String actualizar(@RequestBody RutinaPlantillaDTO rutinaPlantilla) {

        rutinaPlantillaService.actualizarRutinaPrediseñada(rutinaPlantilla);

        return ResponseCode.ACTUALIZACION.get();
    }

    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @DeleteMapping(value = "/eliminar")
    public @ResponseBody
    String eliminar(@RequestParam Integer id) throws JsonProcessingException {
        rutinaPlantillaProcedureInvoker.eliminarRutinaPlantilla(id);
        return ResponseCode.ELIMINACION.get();
    }

  @PostMapping(value = "/lista/agregar")
    public @ResponseBody String obtenerListaNueva(
            @RequestBody ListaPlantilla listaRutinaPlantilla, HttpSession session) throws JsonProcessingException {
        //System.out.println(listaRutinaPlantilla);
        int semanaId = ((Integer[]) session.getAttribute("rpSemanaIds"))[listaRutinaPlantilla.getNumeroSemana()];
        int diaPlantillaId = diaPlantillaService.encontrarIdPorSemanaId(semanaId).get(listaRutinaPlantilla.getDiaIndice());
        //Seteamos las variables primitivas int con su valor por defecto para que no sean tomadas en consideracion en la serializacion jackson
        listaRutinaPlantilla.setNumeroSemana(0);
        listaRutinaPlantilla.setDiaIndice(0);
        diaPlantillaService.insertarNuevaListaById(diaPlantillaId, new ObjectMapper().writeValueAsString(listaRutinaPlantilla));
        return ResponseCode.REGISTRO.get();
    }

    @PostMapping(value = "/lista/elemento/agregar")
    public @ResponseBody String obtenerListaElementoNueva(
            @RequestBody ElementoPlantilla elementoPlantilla, HttpSession session) throws JsonProcessingException {
        //System.out.println(elementoPlantilla);
        int semanaId = ((Integer[]) session.getAttribute("rpSemanaIds"))[elementoPlantilla.getNumeroSemana()];
        int diaPlantillaId = diaPlantillaService.encontrarIdPorSemanaId(semanaId).get(elementoPlantilla.getDiaIndice());
        diaPlantillaService.insertarNuevoElementoById(diaPlantillaId, elementoPlantilla.getListaIndice(), elementoPlantilla.getElementoIndice(), new ObjectMapper().writeValueAsString(new ElementoPlantilla(elementoPlantilla.getNombre(), elementoPlantilla.getDuracion(), elementoPlantilla.getMedia(), elementoPlantilla.getTipo())));
        return ResponseCode.REGISTRO.get();
    }

    @PostMapping(value = "/lista/elemento/agregar/v2")
    public @ResponseBody String obtenerListaVariosElementosNuevos(
            @RequestBody List<ElementoPlantilla> elementosPlantilla, HttpSession session) throws JsonProcessingException {
        //System.out.println(elementosPlantilla);
        int numSemana = elementosPlantilla.get(0).getNumeroSemana();
        int diaIndice = elementosPlantilla.get(0).getDiaIndice();
        int listaIndice = elementosPlantilla.get(0).getListaIndice();
        int semanaId = ((Integer[]) session.getAttribute("rpSemanaIds"))[numSemana];
        int diaPlantillaId = diaPlantillaService.encontrarIdPorSemanaId(semanaId).get(diaIndice);
        for (int i=0; i< elementosPlantilla.size(); i++) {
            elementosPlantilla.set(i, new ElementoPlantilla(elementosPlantilla.get(i).getNombre(), elementosPlantilla.get(i).getDuracion(), elementosPlantilla.get(i).getMedia(), elementosPlantilla.get(i).getTipo()));
        }
        diaPlantillaService.insertarNuevosElementosById(diaPlantillaId, listaIndice, new ObjectMapper().writeValueAsString(elementosPlantilla));
        return ResponseCode.REGISTRO.get();
    }

    @GetMapping(value = "/obtenerSemana")
    public @ResponseBody String obtenerSemanaRutina(@RequestParam String ixSem, HttpSession session){
        int semanaId = ((Integer[]) session.getAttribute("rpSemanaIds"))[Integer.parseInt(ixSem)];
        return ResponseCode.REGISTRO.get();
    }

    @PutMapping(value = "/lista-elementos/agregar")
    public @ResponseBody String obtenerListaConElementosNueva(
                @RequestParam String numeroSemana,
                @RequestParam String diaIndice, HttpSession session) throws JsonProcessingException {
        //Obtenemos la plantillaListaId que guardamos al copiar una miniplantilla
        int listaPlantillaSimpleId = Integer.parseInt(session.getAttribute("listaPlantillaSimpleId").toString());
        int semanaId = ((Integer[]) session.getAttribute("rpSemanaIds"))[Integer.parseInt(numeroSemana)];
        int diaPlantillaId = diaPlantillaService.encontrarIdPorSemanaId(semanaId).get(Integer.parseInt(diaIndice));
        ListaPlantillaSimple plantillaLista = listaPlantillaSimpleService.findOne(listaPlantillaSimpleId);
        //Pasamos el objeto de la lista simple a la lista que sirve como columna json b para evitar insertar columnas innecesarias de LisstaPlantillaSimple
        ListaPlantilla listaPlantilla = new ListaPlantilla();
        listaPlantilla.setNombre(plantillaLista.getNombre());
        listaPlantilla.setElementos(plantillaLista.getElementos());
        diaPlantillaService.insertarNuevaListaById(diaPlantillaId, new ObjectMapper().writeValueAsString(listaPlantilla));
        return ResponseCode.REGISTRO.get();
    }

    @PutMapping(value = "/lista/actualizar")
    public @ResponseBody String actualizarListaPlantilla(
                @RequestBody ListaPlantilla lista, HttpSession session){
        int semanaId = ((Integer[]) session.getAttribute("rpSemanaIds"))[lista.getNumeroSemana()];
        int diaPlantillaId = diaPlantillaService.encontrarIdPorSemanaId(semanaId).get(lista.getDiaIndice());
        diaPlantillaService.actualizarNombreListaByListaIndexAndId(lista.getNombre(), lista.getListaIndice(), diaPlantillaId);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/elemento/actualizar")
    public @ResponseBody String actualizarElementoPlantilla(
                @RequestParam String nombre,
                @RequestParam String numeroSemana,
                @RequestParam String diaIndice,
                @RequestParam String listaIndice,
                @RequestParam String elementoIndice, HttpSession session){
        int semanaId = ((Integer[]) session.getAttribute("rpSemanaIds"))[Integer.parseInt(numeroSemana)];
        int diaPlantillaId = diaPlantillaService.encontrarIdPorSemanaId(semanaId).get(Integer.parseInt(diaIndice));
        diaPlantillaService.actualizarNombreElementoByListaIndexAndElementoIndexAndId(diaPlantillaId, Integer.parseInt(listaIndice), Integer.parseInt(elementoIndice), nombre);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/dia-plantilla/actualizarFlagDescanso")
    public @ResponseBody String actualizarFlagDescanso(
                @RequestParam String numeroSemana,
                @RequestParam String diaIndice,
                @RequestParam String flagDescanso, HttpSession session){
        int semanaId = ((Integer[]) session.getAttribute("rpSemanaIds"))[Integer.parseInt(numeroSemana)];
        int diaPlantillaId = diaPlantillaService.encontrarIdPorSemanaId(semanaId).get(Integer.parseInt(diaIndice));
        boolean flag = Boolean.valueOf(flagDescanso);
        //Incluye vaciar las listas del dia que se hayan creado
        diaPlantillaService.actualizarFlagDescanso(diaPlantillaId, flag);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/lista/eliminar")
    public @ResponseBody String eliminarListaPlantilla(
                            @RequestParam String numeroSemana,
                            @RequestParam String diaIndice,
                            @RequestParam String listaIndice, HttpSession session){
        int semanaId = ((Integer[]) session.getAttribute("rpSemanaIds"))[Integer.parseInt(numeroSemana)];
        int diaPlantillaId = diaPlantillaService.encontrarIdPorSemanaId(semanaId).get(Integer.parseInt(diaIndice));
        diaPlantillaService.eliminarListaById(diaPlantillaId, Integer.parseInt(listaIndice));
        return ResponseCode.ELIMINACION.get();
    }

    @PutMapping(value = "/elemento/eliminar")
    public @ResponseBody String eliminarElementoPlantilla(
            @RequestParam String numeroSemana,
            @RequestParam String diaIndice,
            @RequestParam String listaIndice,
            @RequestParam String elementoIndice, HttpSession session){
        int semanaId = ((Integer[]) session.getAttribute("rpSemanaIds"))[Integer.parseInt(numeroSemana)];
        int diaPlantillaId = diaPlantillaService.encontrarIdPorSemanaId(semanaId).get(Integer.parseInt(diaIndice));
        diaPlantillaService.eliminarElementoById(diaPlantillaId, Integer.parseInt(listaIndice), Integer.parseInt(elementoIndice));
        return ResponseCode.ELIMINACION.get();
    }

    private BagForest reconstructForest(List<EspecificacionSubCategoria> leaves, int forestId) {
        EntityGraphBuilder entityGraphBuilder = new EntityGraphBuilder(new EntityVisitor[]{
                CategoriaEjercicio.ENTITY_VISITOR, SubCategoriaEjercicio.ENTITY_VISITOR, EspecificacionSubCategoria.ENTITY_VISITOR, BagForest.ENTITY_VISITOR
        }).build(leaves);
        ClassId<BagForest> forestClassId = new ClassId<BagForest>(BagForest.class, forestId);
        return entityGraphBuilder.getEntityContext().getObject(forestClassId);
    }

    @GetMapping(value = "/listar")
    public  @ResponseBody List<RutinaPlantillaDTO>  obtenerRutinaPlantillaByCatId (@RequestParam Integer catId){

        return rutinaPlantillaService.listarRutinasPredByCatId(catId);
    }


    @PostMapping(value = {"/generar-rutina"})
    @Transactional
    public @ResponseBody
    String obtenerArbolDia(@RequestParam String fi, @RequestParam String ff, @RequestParam String rId, HttpSession session) throws JsonProcessingException {
        List<DiaPlantilla> dias = diaPlantillaService.obtenerTodosConJerarquia(Integer.parseInt(rId));
        Integer rutinaId = Integer.parseInt(rId);

        if(dias.isEmpty()){
            return "-9";
        }

        BagForest forest = reconstructForestDia(dias, 3);
        List<RutinaPlantilla> lstRutinaPlantilla = forest.getTreesRp();

        RutinaPlantilla rPlantilla = new RutinaPlantilla();
        rPlantilla = lstRutinaPlantilla.get(0);
//
        Integer redFitId = (Integer) session.getAttribute("plantillaRutRedFitId");
        Integer clienteId = (Integer) session.getAttribute("plantillaRutRunnerId");
        Integer tipoRutina = Integer.parseInt(session.getAttribute("plantillaRutTipo").toString());

        rutinaPlantillaService.agregarRutinadesdePlantilla(rPlantilla,fi,ff,redFitId,clienteId, tipoRutina);

        return "1";
    }

    private BagForest reconstructForestDia(List<DiaPlantilla> leaves, int forestId) {
        EntityGraphBuilder entityGraphBuilder = new EntityGraphBuilder(new EntityVisitor[]{
                RutinaPlantilla.ENTITY_VISITOR, SemanaPlantilla.ENTITY_VISITOR, DiaPlantilla.ENTITY_VISITOR, BagForest.ENTITY_VISITOR
        }).build(leaves);
        ClassId<BagForest> forestClassId = new ClassId<BagForest>(BagForest.class, forestId);
        return entityGraphBuilder.getEntityContext().getObject(forestClassId);
    }



}
