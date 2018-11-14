package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.*;
import com.itsight.domain.dto.*;
import com.itsight.domain.jsonb.Elemento;
import com.itsight.domain.jsonb.RutinaControl;
import com.itsight.domain.jsonb.SubElemento;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import groovy.lang.Tuple2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.itsight.util.Enums.ResponseCode;

@Controller
@RequestMapping("/gestion/rutina")
public class RutinaController {

    private UsuarioService usuarioService;
    private RutinaService rutinaService;

    private DiaService diaService;

    private TipoAudioService tipoAudioService;

    private CategoriaEjercicioService categoriaEjercicioService;

    private RutinaPlantillaService rutinaPlantillaService;

    private RedFitnessService redFitnessService;

    private SemanaService semanaService;

    private EmailService emailService;

    private VideoService videoService;

    private CategoriaService categoriaService;

    private VideoAudioFavoritoService videoAudioFavoritoService;

    private RuConsolidadoService ruConsolidadoService;

    @Value("${domain.name}")
    private String domainName;

    @Autowired
    public RutinaController(RutinaPlantillaService rutinaPlantillaService, RutinaService rutinaService, DiaService diaService, TipoAudioService tipoAudioService, CategoriaEjercicioService categoriaEjercicioService, RedFitnessService redFitnessService, EmailService emailService, SemanaService semanaService, VideoService videoService, CategoriaService categoriaService,UsuarioService usuarioService,VideoAudioFavoritoService videoAudioFavoritoService, RuConsolidadoService ruConsolidadoService) {
        this.rutinaPlantillaService = rutinaPlantillaService;
        this.rutinaService = rutinaService;
        this.diaService = diaService;
        this.tipoAudioService = tipoAudioService;
        this.categoriaEjercicioService = categoriaEjercicioService;
        this.redFitnessService = redFitnessService;
        this.emailService = emailService;
        this.semanaService = semanaService;
        this.videoService = videoService;
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
        this.videoAudioFavoritoService = videoAudioFavoritoService;
        this.ruConsolidadoService = ruConsolidadoService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView principal(Model model) {
        return new ModelAndView(ViewConstant.MAIN_RUTINA_PLANTILLA);
    }

    @GetMapping(value = "/trainer")
    public ModelAndView principalTrainer(Model model) {
        return new ModelAndView(ViewConstant.MAIN_TRAINER_RUTINA_PLANTILLA);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{perfil}")
    public @ResponseBody
    List<Rutina> listarConFiltro(
            @PathVariable("comodin") String comodin) {
        return rutinaService.listarPorFiltro(comodin, "", "");
    }

    //PT: Para trainer
    @GetMapping(value = "/trainer/obtenerListado")
    public @ResponseBody
    List<Rutina> listadoConFiltroPT(HttpSession session) {
        //trainerId == usuarioId
        int trainerId = Integer.parseInt(session.getAttribute("id").toString());
        //PT: Por trainer
        return rutinaService.listarPorFiltroPT(trainerId);
    }

    @GetMapping(value = "/trainer/red/obtenerListado")
    public @ResponseBody
    List<Rutina> listadoRutinaRed(@RequestParam int usuarioId, HttpSession session) {
        //trainerId == usuarioId
        session.setAttribute("clienteId", usuarioId);
        int trainerId = Integer.parseInt(session.getAttribute("id").toString());
        //PT: Por trainer
        return rutinaService.listarPorFiltroPT(trainerId);
    }

    @GetMapping(value = "/primera-semana/edicion")
    public @ResponseBody Semana obtenerPrimeraSemanaRutina(HttpSession session) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaRutinaId"));
        if(sessionValor.isPresent()){
            return semanaService.findOneWithDaysById((int) session.getAttribute("semanaRutinaId"));
        }else{//En caso no se encuentre el id de la semana, significa que el usuario ha intentado ingresar directamente
              // desde un simple peticion get y no desde su vista de red fitness
            return new Semana();
        }
    }

    @GetMapping(value = "/semana/obtener/{semanaIndice}")
    public @ResponseBody Semana obtenerEspecificaSemana(@PathVariable String semanaIndice, HttpSession session) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()){
            int[] sIds = (int[]) session.getAttribute("semanaIds");
            int sIx = Integer.parseInt(semanaIndice);
            if(sIx <sIds.length)
                return semanaService.findOneWithDaysById(sIds[sIx]);
            else
                return new Semana();
        }else{//En caso no se encuentre el id de la semana, significa que el usuario ha intentado ingresar directamente
            // desde un simple peticion get y no desde su vista de red fitness
            return new Semana();
        }
    }

    @PostMapping(value = "/agregar/semana")
    public @ResponseBody String agregarSemanaARutina(@RequestBody SemanaPlantillaDto nuevaSemana, HttpSession session) {
        Semana semana = new Semana();
        BeanUtils.copyProperties(nuevaSemana, semana);
        List<Dia> dias = new ArrayList<>();
        for(DiaPlantillaDto diaDto: nuevaSemana.getDias()){
            Dia dia = new Dia();
            BeanUtils.copyProperties(diaDto, dia);
            dias.add(dia);
            //Pasando el objeto de semana al dia para al momento de su registro, el ID que se genere(De la SemPlan)
            //se referencie en el registro del día
            dia.setSemana(semana);
        }
        int rutinaId = (int) session.getAttribute("edicionRutinaId");
        semana.setRutina(rutinaId);
        semana.setLstDia(dias);
        semanaService.update(semana);
        Rutina qRutina = rutinaService.findOne(rutinaId);
        qRutina.setTotalSemanas(nuevaSemana.getTotalSemanas());
        qRutina.setFechaFin(nuevaSemana.getFechaFin());
        int[] qSemanaIds = Utilitarios.agregarElementoArray(qRutina.getSemanaIds(), semana.getId());
        qRutina.setSemanaIds(qSemanaIds);
        qRutina.setDias(qRutina.getDias()+7);
        rutinaService.update(qRutina);
        session.setAttribute("semanaIds", qSemanaIds);
        //Actualizar última fecha de planificación
        int redFitnessId = rutinaService.obtenerRedFitnessIdById(rutinaId);
        redFitnessService.actualizarUltimaFechaPlanificacionById(redFitnessId, nuevaSemana.getFechaFin());
        return ResponseCode.REGISTRO.get();
    }

    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @PostMapping(value = "/agregar")
    public @ResponseBody
    String nuevo(@RequestParam int rutinaPlantillaId, @RequestParam String correo, HttpSession session) {
        int clienteId = Integer.parseInt(session.getAttribute("clienteId").toString());
        int redFitnessId = Integer.parseInt(session.getAttribute("redFitnessId").toString());
        RutinaPlantilla rp = rutinaPlantillaService.findOne(rutinaPlantillaId);
        Rutina rutina = new Rutina();
        BeanUtils.copyProperties(rp, rutina);
        //Asignandole al usuario==clienteId la rutina
        rutina.setUsuario(clienteId);
        rutinaService.save(rutina);
        //Actualizandole el flag a la redfitness del cliente
        redFitnessService.updateFlagEnActividadById(redFitnessId, true);
        //Enviando correo
        StringBuilder sb = new StringBuilder(1000);
        sb.append("<h4>Estimado cliente,</h4>");
        sb.append("<p>Su entrenador acaba de asignarle una rutina que se encuentra disponible en nuestra plataforma, esperemos que obtenga los mejores resultados de esta.<br> Que siga teniendo un buen día.</p><br>");
        sb.append("<div class='' style='text-align: center;'><a style='text-decoration:none;width: 115px;height: 25px;background: #28a745;padding: 10px;text-align: center;border-radius: 5px;color: white;font-weight: bold;' class='' href=\""+domainName+"/login/\" target=\"_blanket\">Plataforma Workout</a></div>");
        sb.append("<h4>Dennys Workout, <br> Los Eucaliptos, Los Olivos 15008, Perú.</h4>");
        emailService.enviarCorreoInformativo("Dennys Workout Notification", correo, sb.toString());
        return ResponseCode.REGISTRO.get();
    }

    @GetMapping(value = "/obtenerSemana")
    public @ResponseBody String obtenerSemanaRutina(@RequestParam String ixSem, HttpSession session){
        int semanaId = ((Integer[]) session.getAttribute("rpSemanaIds"))[Integer.parseInt(ixSem)];
        return ResponseCode.REGISTRO.get();
    }

    /*@PutMapping(value = "/elemento/actualizar")
    public @ResponseBody String actualizarElemento(
            @RequestParam String nombre,
            @RequestParam String numeroSemana,
            @RequestParam String diaIndice,
            @RequestParam String listaIndice,
            @RequestParam String elementoIndice, HttpSession session){
        int semanaId = ((Integer[]) session.getAttribute("rpSemanaIds"))[Integer.parseInt(numeroSemana)];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(Integer.parseInt(diaIndice));
        diaService.actualizarNombreElementoByListaIndexAndElementoIndexAndId(diaId, Integer.parseInt(listaIndice), Integer.parseInt(elementoIndice), nombre);
        return ResponseCode.ACTUALIZACION.get();
    }*/

    @PostMapping(value = "/elemento/agregar")
    public @ResponseBody String agregarNuevoElemento(
            @RequestBody Elemento elemento, HttpSession session) throws JsonProcessingException {
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[elemento.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(elemento.getDiaIndice());
        //Seteamos las variables primitivas int con su valor por defecto para que no sean tomadas en consideracion en la serializacion jackson
        elemento.setNumeroSemana(0);
        elemento.setDiaIndice(0);
        diaService.insertarNuevoElemento(diaId, new ObjectMapper().writeValueAsString(elemento));
        return ResponseCode.REGISTRO.get();
    }

    @PostMapping(value = "/elemento/agregar/pos-especifica")
    public @ResponseBody String agregarNuevoElementoPosicionEspecifica(
            @RequestBody ElementoEspecifico elemento, HttpSession session) throws JsonProcessingException {
        //System.out.println("INSERTAR DESPUES: "+elemento.getInsertarDespues());
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[elemento.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(elemento.getDiaIndice());
        int posElementoReferencial = elemento.getRefElementoIndice();
        boolean insertarDespues = elemento.getInsertarDespues();
        //Seteamos las variables primitivas/no-pri con su valor por defecto para que no sean tomadas en consideracion en la serializacion jackson
        elemento.setNumeroSemana(0);
        elemento.setDiaIndice(0);
        elemento.setInsertarDespues(null);
        elemento.setRefElementoIndice(0);
        diaService.insertarNuevoElementoPosEspecifica(diaId, posElementoReferencial, insertarDespues, new ObjectMapper().writeValueAsString(elemento));
        return ResponseCode.REGISTRO.get();
    }

    @PostMapping(value = "/sub-elemento/agregar/pos-especifica")
    public @ResponseBody String agregarNuevoSubElementoPosicionEspecifica(
            @RequestBody SubElementoEspecifico subElemento, HttpSession session) throws JsonProcessingException {
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[subElemento.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(subElemento.getDiaIndice());
        int elementoIndice = subElemento.getElementoIndice();
        int posSubElementoReferencial = subElemento.getRefSubElementoIndice();
        boolean insertarDespues = subElemento.getInsertarDespues();
        //Seteamos las variables primitivas/no-pri con su valor por defecto para que no sean tomadas en consideracion en la serializacion jackson
        subElemento.setNumeroSemana(0);
        subElemento.setDiaIndice(0);
        subElemento.setInsertarDespues(null);
        subElemento.setElementoIndice(0);
        subElemento.setRefSubElementoIndice(0);
        diaService.insertarNuevoSubElementoPosEspecifica(diaId, elementoIndice ,posSubElementoReferencial, insertarDespues, new ObjectMapper().writeValueAsString(subElemento));
        return ResponseCode.REGISTRO.get();
    }

    @PutMapping(value = "/elemento/eliminar")
    public @ResponseBody String eliminarElemento(
            @RequestParam String numeroSemana,
            @RequestParam String diaIndice,
            @RequestParam String elementoIndice,
            @RequestParam int minutos,
            @RequestParam double distancia,
            @RequestParam double calorias, HttpSession session){
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[Integer.parseInt(numeroSemana)];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(Integer.parseInt(diaIndice));
        diaService.eliminarElementoById(diaId, Integer.parseInt(elementoIndice), minutos, distancia, calorias);
        return ResponseCode.ELIMINACION.get();
    }

    @PutMapping(value = "/sub-elemento/eliminar")
    public @ResponseBody String eliminarElemento(
            @RequestParam String numeroSemana,
            @RequestParam String diaIndice,
            @RequestParam String elementoIndice,
            @RequestParam String subElementoIndice,
            @RequestParam double calorias,
            @RequestParam double distancia,
             HttpSession session){
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[Integer.parseInt(numeroSemana)];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(Integer.parseInt(diaIndice));
        diaService.eliminarSubElementoById(diaId, Integer.parseInt(elementoIndice), Integer.parseInt(subElementoIndice), distancia, calorias);
        return ResponseCode.ELIMINACION.get();
    }

    @PutMapping(value = "/elemento/actualizar")
    public @ResponseBody String actualizarElementoDia(@RequestBody Elemento elemento, HttpSession session){
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[elemento.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(elemento.getDiaIndice());
        diaService.actualizarNombreElementoByListaIndexAndId(elemento.getNombre(), elemento.getElementoIndice(), diaId);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/elemento/estilos/actualizar")
    public @ResponseBody String actualizarEstilosElementoDia(
            @RequestBody Elemento elemento, HttpSession session) throws JsonProcessingException {
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[elemento.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(elemento.getDiaIndice());
        diaService.actualizarElementosEstilosFull(new ObjectMapper().writeValueAsString(elemento.getEstilos()), elemento.getElementoIndice(), diaId);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/elemento/actualizar/2")
    public @ResponseBody String actualizarElementoNomAndTipoDia(
            @RequestBody ElementoDto elemento, HttpSession session) throws JsonProcessingException {
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[elemento.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(elemento.getDiaIndice());
        int eleIndice = elemento.getElementoIndice();
        elemento.setElementoIndice(0);
        elemento.setDiaIndice(0);
        elemento.setNumeroSemana(0);
        diaService.actualizarElementoByListaIndexAndId(new ObjectMapper().writeValueAsString(elemento), eleIndice, diaId);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/elemento/tiempo/actualizar")
    public @ResponseBody String actualizarTiempoElemento(
            @RequestBody Elemento elemento, HttpSession session){
        int minutosDia = elemento.getMinutosDia();
        elemento.setMinutosDia(0);//Para no ser tomado en cuenta en la serializacion
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[elemento.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(elemento.getDiaIndice());
        diaService.actualizarTiempoElementoByListaIndexAndId(elemento.getMinutos(), elemento.getElementoIndice(), diaId, minutosDia);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/dia/actualizar")
    public @ResponseBody String actualizarDia(
            @RequestBody Elemento elemento, HttpSession session){
        double distanciaDia = elemento.getDistanciaDia();
        elemento.setDistanciaDia(0);//Para no ser tomado en cuenta en la serializacion
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[elemento.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(elemento.getDiaIndice());
        diaService.actualizarDiaAndElementoById(diaId, elemento.getCalorias(), distanciaDia, elemento.getNombre(), elemento.getDistancia(), elemento.getElementoIndice());
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/dia/actualizar2")
    public @ResponseBody String actualizarDiaPlusTiempo(
            @RequestBody Elemento elemento, HttpSession session){
        double distanciaDia = elemento.getDistanciaDia();
        elemento.setDistanciaDia(0);//Para no ser tomado en cuenta en la serializacion
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[elemento.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(elemento.getDiaIndice());
        diaService.actualizarDiaAndElemento2ById(diaId, elemento.getCalorias(), distanciaDia, elemento.getMinutosDia(), elemento.getNombre(), elemento.getDistancia(), elemento.getMinutos(), elemento.getElementoIndice());
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/dia/actualizar3")
    public @ResponseBody String actualizarDiaFromSubEle(
            @RequestBody SubElemento subElemento, HttpSession session) throws JsonProcessingException{
        double distanciaDia = subElemento.getDistanciaDia();
        subElemento.setDistanciaDia(0);//Para no ser tomado en cuenta en la serializacion
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[subElemento.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(subElemento.getDiaIndice());
        diaService.actualizarDiaAndSubElementoById(diaId, subElemento.getCalorias(), distanciaDia, subElemento.getDistancia(),  subElemento.getElementoIndice(), subElemento.getSubElementoIndice(), new ObjectMapper().writeValueAsString(new SubElemento(subElemento.getNombre(), subElemento.getMediaAudio(),subElemento.getMediaVideo(), subElemento.getTipo())));
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/elemento/nota/actualizar")
    public @ResponseBody String actualizarNotaElemento(
            @RequestBody Elemento elemento, HttpSession session){
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[elemento.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(elemento.getDiaIndice());
        diaService.actualizarNotaElementoByListaIndexAndId(elemento.getNota(), elemento.getElementoIndice(), diaId);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/elemento/media/actualizar")
    public @ResponseBody String actualizarMediaElemento(
            @ModelAttribute ElementoMediaDto elemento, HttpSession session){
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[elemento.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(elemento.getDiaIndice());
        diaService.actualizarMediaElemento(elemento, diaId);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/elemento/media/agregar")
    public @ResponseBody String agregarMediaElemento(
            @RequestBody ElementoDto elemento, HttpSession session){
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[elemento.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(elemento.getDiaIndice());
        diaService.actualizarMediaElemento2(elemento, diaId);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/sub-elemento/media/agregar")
    public @ResponseBody String agregarMediaSubElemento(
            @RequestBody SubElementoMediaDto subEle, HttpSession session){
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[subEle.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(subEle.getDiaIndice());
        diaService.actualizarMediaSubElemento2(subEle, diaId);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PostMapping(value = "/sub-elemento/agregar")
    public @ResponseBody String obtenerListaElementoNueva(
            @RequestBody SubElemento subElemento, HttpSession session) throws JsonProcessingException {
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[subElemento.getNumeroSemana()];
        int diaPlantillaId = diaService.encontrarIdPorSemanaId(semanaId).get(subElemento.getDiaIndice());
        diaService.insertarSubElementoById(diaPlantillaId, subElemento.getElementoIndice(), subElemento.getSubElementoIndice(), new ObjectMapper().writeValueAsString(new SubElemento(subElemento.getNombre(), subElemento.getMediaAudio(),subElemento.getMediaVideo(), subElemento.getTipo())));
        return ResponseCode.REGISTRO.get();
    }

    @PutMapping(value = "/sub-elemento/multiple/agregar")
    public @ResponseBody String agregarMultipleSubElementos(
            @RequestBody ElementoDto elemento, HttpSession session) throws JsonProcessingException {
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[elemento.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(elemento.getDiaIndice());
        diaService.actualizarSubElementos(diaId, elemento.getElementoIndice(), new ObjectMapper().writeValueAsString(elemento.getSubElementos()));
        return ResponseCode.REGISTRO.get();
    }

    @PutMapping(value = "/sub-elemento/actualizar")
    public @ResponseBody String actualizarSubElementoNombre(
            @RequestParam String nombre,
            @RequestParam String numeroSemana,
            @RequestParam String diaIndice,
            @RequestParam String elementoIndice,
            @RequestParam String subElementoIndice, HttpSession session){
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[Integer.parseInt(numeroSemana)];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(Integer.parseInt(diaIndice));
        diaService.actualizarNombreSubElementoByElementoIndexAndSubElementoIndexAndId(diaId, Integer.parseInt(elementoIndice), Integer.parseInt(subElementoIndice), nombre);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/sub-elemento/nota/actualizar")
    public @ResponseBody String actualizarSubElementoNota(
            @RequestParam String nota,
            @RequestParam String numeroSemana,
            @RequestParam String diaIndice,
            @RequestParam String elementoIndice,
            @RequestParam String subElementoIndice, HttpSession session){
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[Integer.parseInt(numeroSemana)];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(Integer.parseInt(diaIndice));
        diaService.actualizarNotaSubElementoByElementoIndexAndSubElementoIndexAndId(diaId, Integer.parseInt(elementoIndice), Integer.parseInt(subElementoIndice), nota);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/sub-elemento/media/actualizar")
    public @ResponseBody String actualizarSubElementoMedia(@RequestBody SubElementoMediaDto subElemento, HttpSession session){
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[subElemento.getNumeroSemana()];
        int id = diaService.encontrarIdPorSemanaId(semanaId).get(subElemento.getDiaIndice());
        diaService.actualizarMediaSubElemento2(subElemento, id);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/dia/actualizar/flag-descanso")
    public @ResponseBody String actualizarFlagDescanso(
            @RequestParam String numeroSemana,
            @RequestParam String diaIndice,
            @RequestParam String flagDescanso, HttpSession session){
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[Integer.parseInt(numeroSemana)];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(Integer.parseInt(diaIndice));
        boolean flag = Boolean.valueOf(flagDescanso);
        //Incluye vaciar las listas del dia que se hayan creado
        diaService.actualizarFlagDescanso(diaId, flag);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PutMapping(value = "/dia/from-plantilla/actualizar")
    public @ResponseBody String actualizarDiaDesdePlantillaDia(
            @RequestBody DiaDto diaDto, HttpSession session) throws JsonProcessingException {
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[diaDto.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(diaDto.getDiaIndice());
        diaDto.setNumeroSemana(0);
        diaDto.setDiaIndice(0);
        diaService.actualizarDiaFromPlantilla(diaId, diaDto.getCalorias(), diaDto.getDistancia(), diaDto.getMinutos(), new ObjectMapper().writeValueAsString(diaDto.getElementos()));
        return ResponseCode.REGISTRO.get();
    }

    @PutMapping(value = "/dia/from-plantilla/actualizar/full")
    public @ResponseBody String actualizarDiaDesdePlantillaDia2(
            @RequestBody DiaDto diaDto, HttpSession session) throws JsonProcessingException {
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[diaDto.getNumeroSemana()];
        int diaId = diaService.encontrarIdPorSemanaId(semanaId).get(diaDto.getDiaIndice());
        diaDto.setNumeroSemana(0);
        diaDto.setDiaIndice(0);
        //Actualiza de raiz, los elementos. No tomando en consideracion los anteriores(si es que existian)
        diaService.actualizarDiaRaizDesdePlantilla(diaId, diaDto.getCalorias(), diaDto.getDistancia(), diaDto.getMinutos(), new ObjectMapper().writeValueAsString(diaDto.getElementos()));
        return ResponseCode.REGISTRO.get();
    }

    @PutMapping(value = "/objetivo/dia/actualizar")
    public @ResponseBody String actualizarObjetivoDia(@RequestParam String objetivos, @RequestParam String numSem, HttpSession session){
        //Actualiza de raiz, los elementos. No tomando en consideracion los anteriores(si es que existian)
        int semanaId = ((int[]) session.getAttribute("semanaIds"))[Integer.parseInt(numSem)];
        semanaService.actualizarObjetivos(semanaId, objetivos);
        return ResponseCode.REGISTRO.get();
    }

    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @PostMapping(value = "/nueva")
    public @ResponseBody
    String nuevo(@RequestParam(name = "key") String redFitnessId, @RequestParam(name = "rn") String runnerId, @RequestBody RutinaDto rutinaDto, HttpSession session) {
        int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
        int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);
        String codTrainer = session.getAttribute("codTrainer").toString();
        String qCodTrainer = redFitnessService.findCodTrainerByIdAndRunnerId(redFitId, runneId);
        if(codTrainer.equals(qCodTrainer)){
            Rutina objR = new Rutina();
            //Pasando del Dto al objeto
            RutinaControl rc = new RutinaControl();
            BeanUtils.copyProperties(rutinaDto.getControl(), rc);
            BeanUtils.copyProperties(rutinaDto, objR);
            objR.setUsuario(runneId);
            objR.setRedFitness(redFitId);
            objR.setControl(rc);
            //Instanciando lista de semanas
            List<Semana> semanas = new ArrayList<>();
            for (SemanaPlantillaDto semana: rutinaDto.getSemanas()) {
                Semana semana1 = new Semana();
                BeanUtils.copyProperties(semana, semana1);
                semanas.add(semana1);
                //Pasando el objeto de rutina a la semana para al momento de su registro, el ID que se genere(De la RutPlan)
                //se referencie en el registro de la semana
                semana1.setRutina(objR);
                //Insertando dias de la semana a su respectiva lista de dias
                List<Dia> dias = new ArrayList<>();
                for(DiaPlantillaDto dia: semana.getDias()){
                    Dia objDia = new Dia();
                    BeanUtils.copyProperties(dia, objDia);
                    dias.add(objDia);
                    //Pasando el objeto de semana al dia para al momento de su registro, el ID que se genere(De la SemPlan)
                    //se referencie en el registro del día
                    objDia.setSemana(semana1);
                }
                //Agregando la lista de dias a la semana
                semana1.setLstDia(dias);
                semana1.setObjetivos("0,0,0,0,0,0,0");//Se refiere al id del objetivo del día, en este caso inician como 0 o mejor dicho sin objetivo específico
            }
            //Agregando las semanas a la instancia de rutina que hará que se inserten mediante cascade strategy
            objR.setLstSemana(semanas);
            objR.setFlagActivo(false);
            objR.setTipoRutina(Enums.TipoRutina.RUNNING.get());
            //rutinaService.save(objR);
            RuConsolidado nR = new RuConsolidado();
            nR.setGeneral(rutinaDto.getGeneral());
            nR.setRutina(objR);
            nR.setStats(rutinaDto.getStats());
            nR.setMejoras(rutinaDto.getMejoras());
            nR.setDtGrafico(rutinaDto.getDtGrafico());
            ruConsolidadoService.save(nR);
            int[] qSemanaIds = new int[objR.getLstSemana().size()];
            for(int i=0; i<qSemanaIds.length;i++){
                qSemanaIds[i] = objR.getLstSemana().get(i).getId();
            }
            rutinaService.updateSemanaIds(objR.getId(), qSemanaIds);
            redFitnessService.updatePlanStatusAndUltimoDiaPlanificacionAndContadorRutinas(redFitId, 2, rutinaDto.getFechaFin(), 1);
            return ResponseCode.REGISTRO.get();
        }
        return ResponseCode.EX_GENERIC.get();
    }

    @PutMapping(value = "/semana-completa/actualizar/{semIxDesde}/{semIxPara}")
    public @ResponseBody String actualizarSemanaCompletaDesdeOtra(@PathVariable int semIxDesde, @PathVariable int semIxPara, HttpSession session){
        semIxDesde = ((int[]) session.getAttribute("semanaIds"))[semIxDesde];
        semIxPara = ((int[]) session.getAttribute("semanaIds"))[semIxPara];
        diaService.actualizarSemanaCompletaDesdeOtra(semIxDesde, semIxPara);
        return ResponseCode.ACTUALIZACION.get();
    }

    @PostMapping(value = "/elemento/adddeletefavorito")
    public @ResponseBody String agregarEliminarFavorito(@RequestParam int videoid,@RequestParam int audioid,@RequestParam int addedit) {
        String nameuser = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario objuse = usuarioService.findByUsername(nameuser);
        VideoAudioFavorito videoaudio = new VideoAudioFavorito();
        VideoAudioFavorito videoaudioexiste = null;
        if (videoid != 0) {
            videoaudioexiste = videoAudioFavoritoService.getVideoAudioFavoritoVideo(objuse.getId(), videoid);
        }
        if (audioid != 0) {
            videoaudioexiste = videoAudioFavoritoService.getVideoAudioFavoritoAudio(objuse.getId(), audioid);
        }

        if (addedit == 1 && videoaudioexiste == null) {
            if (videoid != 0) {
                videoaudio.setTipo(Enums.TipoMedia.VIDEO.get());
                videoaudio.setVideo(videoid);
            }
            if (audioid != 0) {
                videoaudio.setTipo(Enums.TipoMedia.AUDIO.get());
                videoaudio.setAudio(audioid);

            }
            videoaudio.setFlagActivo(true);
            videoaudio.setUsuario(objuse.getId());
            videoAudioFavoritoService.save(videoaudio);
        }

        if (addedit == 0 && videoaudioexiste != null) {
            videoAudioFavoritoService.delete(videoaudioexiste.getId());
        }
        return ResponseCode.ACTUALIZACION.get();
    }

    @GetMapping(value = "/elemento/obtenermisfavoritos")
    public @ResponseBody List<VideoAudioFavorito> obtenerMisFavoritos(){
        String nameuser = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario objuse = usuarioService.findByUsername(nameuser);
        return videoAudioFavoritoService.findByUsuarioId(objuse.getId());
    }

    @PostMapping(value = "/elemento/adddeletetexto")
    public @ResponseBody String adddeletetexto(@RequestParam String titulo,@RequestParam String descripcion,@RequestParam int addedit) {
        String nameuser = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario objuse = usuarioService.findByUsername(nameuser);
        VideoAudioFavorito videoaudiotexto = new VideoAudioFavorito();
        if (addedit == 0) {
            videoaudiotexto.setUsuario(objuse.getId());
            videoaudiotexto.setTitulo(titulo);
            videoaudiotexto.setDescripcion(descripcion);
            videoaudiotexto.setTipo(Enums.TipoMedia.TEXTO.get());
            videoaudiotexto.setFlagActivo(true);
            videoAudioFavoritoService.save(videoaudiotexto);
        } else {
            videoAudioFavoritoService.delete(addedit);
        }

        return ResponseCode.ACTUALIZACION.get();
    }

    @PostMapping(value = "/elemento/updateAvance")
    public @ResponseBody String actualizarAvance(@RequestParam int idrutina, @RequestParam int indexsemana, @RequestParam String stravance,@RequestParam String porcentaje)
    {
        rutinaService.updateAvance(idrutina,indexsemana,stravance,porcentaje);
        return "Ok";
    }

    @PostMapping(value = "/elemento/updateDiasSeleccionados")
    public @ResponseBody String actualizarDiasSeleccionados(@RequestParam String listjson, HttpSession session)
    {
        int idrutina = Integer.parseInt(session.getAttribute("edicionRutinaId").toString());
        int[] sIds = (int[]) session.getAttribute("semanaIds");
        List<DiaSemanaDto> listdias = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            listdias = mapper.readValue(listjson, new TypeReference<List<DiaSemanaDto>>(){});
            List<Integer> intList = new ArrayList<Integer>();
            for (int i : sIds)
            {
                intList.add(i);
            }

            rutinaService.updateResetDiasFlagEnvio(intList);

            for (int i = 0; i < listdias.size() ; i++) {
                int indexsemana = listdias.get(i).getSemana();
                int indexdia = listdias.get(i).getDia();
                rutinaService.updateDiasFlagEnvio(sIds[indexsemana],indexdia);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseCode.ACTUALIZACION.get();
    }


}
