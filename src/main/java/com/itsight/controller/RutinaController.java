package com.itsight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.constants.ViewConstant;
import com.itsight.domain.*;
import com.itsight.domain.dto.*;
import com.itsight.domain.jsonb.Elemento;
import com.itsight.domain.jsonb.SubElemento;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.itsight.util.Enums.ResponseCode;

@Controller
@PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
@RequestMapping("/gestion/rutina")
public class RutinaController {

    private ClienteService clienteService;

    private RutinaService rutinaService;

    private DiaService diaService;

    private RedFitnessService redFitnessService;

    private SemanaService semanaService;

    private VideoAudioFavoritoService videoAudioFavoritoService;

    @Value("${domain.name}")
    private String domainName;

    @Autowired
    public RutinaController(RutinaService rutinaService, DiaService diaService, RedFitnessService redFitnessService, SemanaService semanaService, ClienteService clienteService, VideoAudioFavoritoService videoAudioFavoritoService, RuConsolidadoService ruConsolidadoService) {
        this.rutinaService = rutinaService;
        this.diaService = diaService;
        this.redFitnessService = redFitnessService;
        this.semanaService = semanaService;
        this.clienteService = clienteService;
        this.videoAudioFavoritoService = videoAudioFavoritoService;
    }

    @GetMapping(value = "")
    public ModelAndView principal() {
        return new ModelAndView(ViewConstant.MAIN_RUTINA_PLANTILLA);
    }

    @GetMapping(value = "/trainer")
    public ModelAndView principalTrainer() {
        return new ModelAndView(ViewConstant.MAIN_TRAINER_RUTINA_PLANTILLA);
    }

    @GetMapping(value = "/obtenerListado/{comodin}/{estado}/{perfil}")
    public @ResponseBody
    List<Rutina> listarConFiltro(
            @PathVariable("comodin") String comodin) {
        return rutinaService.listarPorFiltro(comodin, "", "");
    }

    @GetMapping(value = "/primera-semana/edicion")
    public @ResponseBody Semana obtenerPrimeraSemanaRutina(HttpSession session) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaRutinaId"));
        if(sessionValor.isPresent())
            return semanaService.findOneWithDaysById((int) sessionValor.get());
        else//En caso no se encuentre el id de la semana, significa que el cliente ha intentado ingresar directamente
              // desde un simple peticion get y no desde su vista de red fitness
            return new Semana();
    }

    @GetMapping(value = "/semana/obtener/{semanaIndice}")
    public @ResponseBody Semana obtenerEspecificaSemana(@PathVariable String semanaIndice, HttpSession session) throws InterruptedException{
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()){
            int[] sIds = (int[]) sessionValor.get();
            int sIx = Integer.parseInt(semanaIndice);
            if(sIx <sIds.length)
                return semanaService.findOneWithDaysById(sIds[sIx]);
            else
                return new Semana();
        }
        //En caso no se encuentre el id de la semana, significa que el cliente ha intentado ingresar directamente
        // desde un simple peticion get y no desde su vista de red fitness
        return null;
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
        Optional<Object> sessionValor = Optional.of(session.getAttribute("edicionRutinaId"));
        if(sessionValor.isPresent()){
            int rutinaId = (int) sessionValor.get();
            return semanaService.agregarSemana(semana, dias, rutinaId, nuevaSemana.getTotalSemanas(), nuevaSemana.getFechaFin());
        }
        return ResponseCode.EX_GENERIC.get();

    }

    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @PostMapping(value = "/agregar")
    public @ResponseBody String nuevo(@RequestParam int rutinaPlantillaId, @RequestParam String correo, HttpSession session) {
        /*int clienteId = Integer.parseInt(session.getAttribute("clienteId").toString());
        int redFitnessId = Integer.parseInt(session.getAttribute("redFitnessId").toString());
        RutinaPlantilla rp = rutinaPlantillaService.findOne(rutinaPlantillaId);
        Rutina rutina = new Rutina();
        BeanUtils.copyProperties(rp, rutina);
        //Asignandole al cliente==clienteId la rutina
        rutina.setCliente(clienteId);
        rutinaService.save(rutina);
        //Actualizandole el flag a la redfitness del cliente
        redFitnessService.updateFlagEnActividadById(redFitnessId, true);
        //Enviando correo
        StringBuilder sb = new StringBuilder(1000);
        sb.append("<h4>Estimado cliente,</h4>");
        sb.append("<p>Su entrenador acaba de asignarle una rutina que se encuentra disponible en nuestra plataforma, esperemos que obtenga los mejores resultados de esta.<br> Que siga teniendo un buen día.</p><br>");
        sb.append("<div class='' style='text-align: center;'><a style='text-decoration:none;width: 115px;height: 25px;background: #28a745;padding: 10px;text-align: center;border-radius: 5px;color: white;font-weight: bold;' class='' href=\""+domainName+"/login/\" target=\"_blanket\">Plataforma Workout</a></div>");
        sb.append("<h4>Dennys Workout, <br> Los Eucaliptos, Los Olivos 15008, Perú.</h4>");
        emailService.enviarCorreoInformativo("Dennys Workout Notification", correo, sb.toString());*/
        return ResponseCode.REGISTRO.get();
    }

    @PostMapping(value = "/elemento/agregar")
    public @ResponseBody String agregarNuevoElemento(
            @RequestBody @Valid Elemento elemento, BindingResult bindingResult) throws JsonProcessingException {
            if(!bindingResult.hasErrors())
                return diaService.insertarNuevoElemento(elemento);
            return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PostMapping(value = "/elemento/agregar/pos-especifica")
    public @ResponseBody String agregarNuevoElementoPosicionEspecifica(
            @RequestBody @Valid ElementoEspecifico elemento, BindingResult bindingResult) throws JsonProcessingException {
            if(!bindingResult.hasErrors())
                return diaService.insertarNuevoElementoPosEspecifica(elemento);
            return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PostMapping(value = "/sub-elemento/agregar/pos-especifica")
    public @ResponseBody String agregarNuevoSubElementoPosicionEspecifica(
            @RequestBody @Valid SubElementoEspecifico subElemento, BindingResult bindingResult) throws JsonProcessingException {
            if(!bindingResult.hasErrors())
                return diaService.insertarNuevoSubElementoPosEspecifica(subElemento);
            return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/elemento/eliminar")
    public @ResponseBody String eliminarElemento(@ModelAttribute @Valid ElementoDel elementoDel, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            return diaService.eliminarElementoById(elementoDel);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/sub-elemento/eliminar")
    public @ResponseBody String eliminarSubElemento(@ModelAttribute @Valid ElementoDel elementoDel, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            return diaService.eliminarSubElementoById(elementoDel);
        }
        bindingResult.getModel().forEach((key, value)-> System.out.println("Key : " + key + " Value : " + value));
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/elemento/actualizar")
    public @ResponseBody String actualizarElementoDia(@RequestBody @Valid ElementoUpd elemento, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            return diaService.actualizarNombreElementoByListaIndexAndId(elemento);
        bindingResult.getModel().forEach((key, value)-> System.out.println("Key : " + key + " Value : " + value));
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/elemento/estilos/actualizar")
    public @ResponseBody String actualizarEstilosElementoDia(
            @RequestBody @Valid EleEstiloUpd elemento, BindingResult bindingResult) throws JsonProcessingException {
        if(!bindingResult.hasErrors())
            return diaService.actualizarElementosEstilosFull(elemento);
        bindingResult.getModel().forEach((key, value)-> System.out.println("Key : " + key + " Value : " + value));
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/elemento/actualizar/2")
    public @ResponseBody String actualizarElementoNomAndTipoDia(
            @RequestBody @Valid ElementoDto elemento, BindingResult bindingResult) throws JsonProcessingException {
            if(!bindingResult.hasErrors())
                return diaService.actualizarElementoByListaIndexAndId(elemento);
            return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/elemento/tiempo/actualizar")
    public @ResponseBody String actualizarTiempoElemento(
            @ModelAttribute @Valid ElementoUpd elemento, @RequestParam int minutosDia, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            return diaService.actualizarTiempoElementoByListaIndexAndId(elemento, minutosDia);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/dia/actualizar")
    public @ResponseBody String actualizarDia(
            @ModelAttribute @Valid ElementoUpd elemento, @RequestParam double distanciaDia, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            return diaService.actualizarDiaAndElementoById(elemento, distanciaDia);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/dia/actualizar2")
    public @ResponseBody String actualizarDiaPlusTiempo(
            @ModelAttribute @Valid ElementoUpd elemento, double distanciaDia, int minutosDia, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            return diaService.actualizarDiaAndElemento2ById(elemento, distanciaDia, minutosDia);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/dia/actualizar3")
    public @ResponseBody String actualizarDiaFromSubEle(
            @RequestBody @Valid SubElemento subElemento, BindingResult bindingResult) throws JsonProcessingException{
        if(!bindingResult.hasErrors())
            return diaService.actualizarDiaAndSubElementoById(subElemento);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/elemento/nota/actualizar")
    public @ResponseBody String actualizarNotaElemento(
            @ModelAttribute @Valid ElementoUpd elemento, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            return diaService.actualizarNotaElementoByListaIndexAndId(elemento);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/elemento/media/eliminar")
    public @ResponseBody String eliminarMediaElemento(
            @ModelAttribute @Valid ElementoMediaDto elemento, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            return diaService.eliminarMediaElemento(elemento);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/elemento/media/actualizar")
    public @ResponseBody String actualizarMediaElemento(
            @ModelAttribute @Valid ElementoMediaDto elemento, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            return diaService.actualizarMediaElemento(elemento);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/elemento/media/agregar")
    public @ResponseBody String agregarMediaElemento(
            @RequestBody @Valid ElementoDto elemento, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            return diaService.agregarMediaElemento(elemento);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/sub-elemento/media/actualizar")
    public @ResponseBody String actualizarSubElementoMedia(
            @ModelAttribute @Valid SubElementoMediaDto subEle, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            return diaService.actualizarMediaSubElemento(subEle);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/sub-elemento/media/eliminar")
    public @ResponseBody String eliminarMediaSubElemento(
            @RequestBody @Valid SubElementoMediaDto subEle, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            return diaService.eliminarMediaSubElemento(subEle);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PostMapping(value = "/sub-elemento/agregar")
    public @ResponseBody String agregarSubElemento(
            @RequestBody @Valid SubElemento subElemento, BindingResult bindingResult) throws JsonProcessingException {
            if(!bindingResult.hasErrors())
                return diaService.insertarSubElementoById(subElemento);
            return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/sub-elemento/multiple/agregar")
    public @ResponseBody String agregarMultipleSubElementos(
            @RequestBody @Valid ElementoDto elemento, BindingResult bindingResult) throws JsonProcessingException {
        if(!bindingResult.hasErrors())
            return diaService.actualizarSubElementos(elemento);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/sub-elemento/actualizar")
    public @ResponseBody String actualizarSubElementoNombre(
            @ModelAttribute @Valid ElementoUpd subElemento, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            return diaService.actualizarNombreSubElementoByElementoIndexAndSubElementoIndexAndId(subElemento);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/sub-elemento/nota/actualizar")
    public @ResponseBody String actualizarSubElementoNota(
            @ModelAttribute @Valid ElementoUpd subElemento, BindingResult bindingResult){
        if(!bindingResult.hasErrors())
            return diaService.actualizarNotaSubElementoByElementoIndexAndSubElementoIndexAndId(subElemento);
        bindingResult.getModel().forEach((key, value)-> System.out.println("Key : " + key + " Value : " + value));
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/dia/actualizar/flag-descanso")
    public @ResponseBody String actualizarDiaFlagDescanso(
            @RequestParam String numeroSemana,
            @RequestParam String diaIndice,
            @RequestParam String flagDescanso) {
        boolean validation = numeroSemana.length()>0 && diaIndice.length()>0 && Utilitarios.parseInt(numeroSemana).isPresent() && Utilitarios.parseInt(diaIndice).isPresent() ? true : false;
        boolean flag = Boolean.valueOf(flagDescanso);
        if(validation)
            return diaService.actualizarFlagDescanso(Integer.parseInt(numeroSemana), Integer.parseInt(diaIndice), flag);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/dia/from-plantilla/actualizar")
    public @ResponseBody String actualizarDiaDesdePlantillaDia(
            @RequestBody @Valid DiaDto diaDto, BindingResult bindingResult) throws JsonProcessingException {
        if(!bindingResult.hasErrors())
            return diaService.actualizarDiaFromPlantilla(diaDto);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/dia/from-plantilla/actualizar/full")
    public @ResponseBody String actualizarDiaDesdePlantillaDia2(
            @RequestBody DiaDto diaDto, BindingResult bindingResult) throws JsonProcessingException {
        if(!bindingResult.hasErrors())
            return diaService.actualizarDiaRaizDesdePlantilla(diaDto);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/objetivo/dia/actualizar")
    public @ResponseBody String actualizarObjetivoDia(
        @RequestParam String objetivos, @RequestParam String numSem){
        boolean validation = numSem.length()>0 && Utilitarios.onlyIntegers(objetivos) ? true : false;
        if(validation)
            return semanaService.actualizarObjetivos(Integer.parseInt(numSem), objetivos);
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @PostMapping(value = "/nueva")
    public @ResponseBody
    String nueva(@RequestBody @Valid RutinaDto rutinaDto,
                 @RequestParam(name = "key") String redFitnessId,
                 @RequestParam(name = "rn") String runnerId,
                HttpSession session, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
            int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);
            String codTrainer = session.getAttribute("codTrainer").toString();
            String qCodTrainer = redFitnessService.findCodTrainerByIdAndRunnerId(redFitId, runneId);
            if(codTrainer.equals(qCodTrainer)){
                return rutinaService.registrarByCascada(rutinaDto, redFitId, runneId);
            }
        }
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/metricas/velocidad/actualizar")
    public @ResponseBody String actualizarMetricasVelocidad(
                @RequestParam(name = "key") String redFitnessId,
                @RequestParam(name = "rn") String runnerId,
                @RequestParam(name = "mVz") String mVz, HttpSession session) throws IOException{
        int redFitId = Parseador.getDecodeHash32Id("rf-rutina", redFitnessId);
        int runneId = Parseador.getDecodeHash16Id("rf-rutina", runnerId);
        String codTrainer = session.getAttribute("codTrainer").toString();
        String qCodTrainer = redFitnessService.findCodTrainerByIdAndRunnerId(redFitId, runneId);
        if(codTrainer.equals(qCodTrainer)) {
            return semanaService.actualizarFullMetricasVelocidad(mVz);
        }
        return ResponseCode.EX_VALIDATION_FAILED.get();
    }

    @PutMapping(value = "/semana-completa/actualizar/{semIxDesde}/{semIxPara}")
    public @ResponseBody String actualizarSemanaCompletaDesdeOtra(@PathVariable int semIxDesde, @PathVariable int semIxPara){
        return diaService.actualizarSemanaCompletaDesdeOtra(semIxDesde, semIxPara);
    }

    @PostMapping(value = "/elemento/adddeletefavorito")
    public @ResponseBody String agregarEliminarFavorito(@RequestParam int videoid,@RequestParam int audioid,@RequestParam int addedit) {
        String nameuser = SecurityContextHolder.getContext().getAuthentication().getName();
        Cliente objuse = clienteService.findByUsername(nameuser);
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
            videoaudio.setCliente(objuse.getId());
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
        Cliente objuse = clienteService.findByUsername(nameuser);
        return videoAudioFavoritoService.findByClienteId(objuse.getId());
    }

    @PostMapping(value = "/elemento/adddeletetexto")
    public @ResponseBody String adddeletetexto(@RequestParam String titulo,@RequestParam String descripcion,@RequestParam int addedit) {
        String nameuser = SecurityContextHolder.getContext().getAuthentication().getName();
        Cliente objuse = clienteService.findByUsername(nameuser);
        VideoAudioFavorito videoaudiotexto = new VideoAudioFavorito();
        if (addedit == 0) {
            videoaudiotexto.setCliente(objuse.getId());
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

    @PreAuthorize("hasRole('RUNNER')")
    @PostMapping(value = "/elemento/updateAvance")
    public @ResponseBody String actualizarAvance(
            @RequestParam int idrutina,
            @RequestParam int indexsemana,
            @RequestParam String stravance,
            @RequestParam String porcentaje)
    {
        rutinaService.updateAvance(idrutina,indexsemana,stravance,porcentaje);
        return "Ok";
    }

    @PostMapping(value = "/elemento/updateDiasSeleccionados")
    public @ResponseBody String actualizarDiasSeleccionados(@RequestParam String listjson, @RequestParam int anio, @RequestParam int mes , HttpSession session)
    {
        int[] sIds = (int[]) session.getAttribute("semanaIds");
        List<DiaSemanaDto> listdias;
        ObjectMapper mapper = new ObjectMapper();
        try {
            listdias = mapper.readValue(listjson, new TypeReference<List<DiaSemanaDto>>(){});
            List<Integer> intList = new ArrayList<>();
            for (int i : sIds)
            {
                intList.add(i);
            }

            rutinaService.updateResetDiasFlagEnvio(anio,mes);

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

    @GetMapping(value = "/get/obtenerSemanasPorRutina")
    public @ResponseBody List<Semana> obtenerSemanasRutina(HttpSession session) {
        int idrutina = Integer.parseInt(session.getAttribute("edicionRutinaId").toString());
        return semanaService.findByRutinaIdOrderByIdDesc(idrutina);
    }
}
