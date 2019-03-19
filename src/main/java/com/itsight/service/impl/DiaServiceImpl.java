package com.itsight.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.domain.Dia;
import com.itsight.domain.dto.*;
import com.itsight.domain.jsonb.Elemento;
import com.itsight.domain.jsonb.SubElemento;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.DiaRepository;
import com.itsight.service.DiaService;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static com.itsight.util.Enums.ResponseCode.*;
import static com.itsight.util.Enums.TipoMedia.AUDIO;

@Service
@Transactional
public class DiaServiceImpl extends BaseServiceImpl<DiaRepository> implements DiaService {

    private HttpSession session;

    @Autowired
    public DiaServiceImpl(DiaRepository repository, HttpSession session) {
        super(repository);
        this.session = session;
    }

    @Override
    public Dia update(Dia dia) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(dia);
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.delete(id);
    }

    @Override
    public Dia save(Dia entity) {
        return repository.save(entity);
    }

    @Override
    public Dia findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    public Dia findOneWithFT(Long id) {
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Dia> findAll() {
        return null;
    }

    @Override
    public List<Dia> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Dia> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Dia> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Dia> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Dia> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Dia> findByIdsIn(List<Long> ids) {
        return null;
    }

    @Override
    public List<Dia> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Dia entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(Dia entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Long id, boolean flagActivo) {

    }

    @Override
    public List<Integer> encontrarIdPorSemanaId(Integer semanaId) {
        return repository.findIdBySemanaId(semanaId);
    }

    @Override
    public String actualizarFlagDescanso(int numSem, int diaIndice, boolean flagDescanso) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()){
            int semanaId = ((int[]) sessionValor.get())[numSem];
            Long id = repository.findIdBySemanaId(semanaId).get(diaIndice);
            repository.updateFlagDescanso(id, flagDescanso);
            return ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String eliminarElementoById(ElementoDel elementoDel) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()){
            int semanaId = ((int[]) sessionValor.get())[elementoDel.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elementoDel.getDiaIndice());
            repository.deleteElementoById(id, elementoDel.getElementoIndice(), elementoDel.getMinutos(), elementoDel.getDistancia(), elementoDel.getCalorias());
            return ELIMINACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String insertarNuevoElemento(Elemento elemento) throws JsonProcessingException {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()){
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            //Seteamos las variables primitivas int con su valor por defecto para que no sean tomadas en consideracion en la serializacion jackson
            elemento.setNumeroSemana(0);
            elemento.setDiaIndice(0);
            repository.saveElemento(id, new ObjectMapper().writeValueAsString(elemento));
            return REGISTRO.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();

    }

    @Override
    public String actualizarNombreElementoByListaIndexAndId(ElementoUpd elemento) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            String texto = "{"+elemento.getElementoIndice()+",\"nombre\"}";
            repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(id, elemento.getNombre(), texto);
            return ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String insertarSubElementoById(SubElemento subElemento) throws JsonProcessingException {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[subElemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(subElemento.getDiaIndice());
            String texto = "{"+subElemento.getElementoIndice()+",\"subElementos\""+","+subElemento.getSubElementoIndice()+"}";
            repository.saveSubElemento(id, texto, new ObjectMapper().writeValueAsString(new SubElemento(subElemento.getNombre(), subElemento.getMediaAudio(),subElemento.getMediaVideo(), subElemento.getTipo())));
            return REGISTRO.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String actualizarNombreSubElementoByElementoIndexAndSubElementoIndexAndId(ElementoUpd subElemento) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[subElemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(subElemento.getDiaIndice());
            String texto = "{"+subElemento.getElementoIndice()+",\"subElementos\""+","+subElemento.getSubElementoIndice()+",\"nombre\"}";
            repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(id, subElemento.getNombre(), texto);
            return ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String insertarNuevoElementoPosEspecifica(ElementoEspecifico elemento) throws JsonProcessingException {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()){
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            int posElementoReferencial = elemento.getRefElementoIndice();
            boolean insertarDespues = elemento.getInsertarDespues();
            //Seteamos las variables primitivas/no-pri con su valor por defecto para que no sean tomadas en consideracion en la serializacion jackson
            elemento.setNumeroSemana(0);
            elemento.setDiaIndice(0);
            elemento.setInsertarDespues(null);
            elemento.setRefElementoIndice(0);
            String texto = "{"+posElementoReferencial+"}";
            repository.saveElementoPosEspecificaGeneric(id, texto, insertarDespues, new ObjectMapper().writeValueAsString(elemento));
            return REGISTRO.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String actualizarTiempoElementoByListaIndexAndId(ElementoUpd elemento, int minutosDia) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            String texto = "{"+elemento.getElementoIndice()+",\"minutos\""+"}";
            repository.updateTiemposDia(id, String.valueOf(elemento.getMinutos()), texto, minutosDia);
            return REGISTRO.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String actualizarNotaElementoByListaIndexAndId(ElementoUpd elemento) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            String texto = "{" + elemento.getElementoIndice() + ",\"nota\"" + "}";
            repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(id, elemento.getNota(), texto);
            return ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String actualizarDiaAndElementoById(ElementoUpd elemento, double distanciaDia) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            int elementoIndice = elemento.getElementoIndice();
            String txtNombre = "{"+elementoIndice+",\"nombre\""+"}";
            String txtDistancia = "{"+elementoIndice+",\"distancia\""+"}";
            repository.updateDiaAndElemento(id, elemento.getCalorias(),distanciaDia, txtNombre, elemento.getNombre() , txtDistancia, String.valueOf(elemento.getDistancia()));
            return ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String actualizarDiaAndElemento2ById(ElementoUpd elemento, double distanciaDia, int minutosDia) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()){
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            int elementoIndice = elemento.getElementoIndice();
            String txtNombre = "{"+elementoIndice+",\"nombre\""+"}";
            String txtDistancia = "{"+elementoIndice+",\"distancia\""+"}";
            String txtMinutos = "{"+elementoIndice+",\"minutos\""+"}";
            repository.updateDiaAndElemento2(id, elemento.getCalorias(), distanciaDia, minutosDia, txtNombre, elemento.getNombre(), txtDistancia, String.valueOf(elemento.getDistancia()), txtMinutos, String.valueOf(elemento.getMinutos()));
            return ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String actualizarDiaAndSubElementoById(SubElemento subElemento) throws JsonProcessingException {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[subElemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(subElemento.getDiaIndice());
            int elementoIndice = subElemento.getElementoIndice();
            String txtDistancia = "{"+elementoIndice+",\"distancia\""+"}";
            String txtNewSubEle = "{"+elementoIndice+",\"subElementos\""+","+subElemento.getSubElementoIndice()+"}";
            repository.updateDiaAndSubEleEle(id, subElemento.getCalorias(), subElemento.getDistanciaDia(), txtDistancia, String.valueOf(subElemento.getDistancia()), txtNewSubEle, new ObjectMapper().writeValueAsString(new SubElemento(subElemento.getNombre(), subElemento.getMediaAudio(),subElemento.getMediaVideo(), subElemento.getTipo())));
            return ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();

    }

    @Override
    public String insertarNuevoSubElementoPosEspecifica(SubElementoEspecifico subElemento) throws JsonProcessingException {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[subElemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(subElemento.getDiaIndice());
            int elementoIndice = subElemento.getElementoIndice();
            int posSubElementoReferencial = subElemento.getRefSubElementoIndice();
            boolean insertarDespues = subElemento.getInsertarDespues();
            //Seteamos las variables primitivas/no-pri con su valor por defecto para que no sean tomadas en consideracion en la serializacion jackson
            subElemento.setNumeroSemana(0);
            subElemento.setDiaIndice(0);
            subElemento.setInsertarDespues(null);
            subElemento.setElementoIndice(0);
            subElemento.setRefSubElementoIndice(0);
            String texto = "{"+elementoIndice+",\"subElementos\""+","+posSubElementoReferencial+"}";
            repository.saveElementoPosEspecificaGeneric(id, texto, insertarDespues, new ObjectMapper().writeValueAsString(subElemento));
            return REGISTRO.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String eliminarSubElementoById(ElementoDel elementoDel) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[elementoDel.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elementoDel.getDiaIndice());
            String texto = "{"+elementoDel.getElementoIndice()+",subElementos,"+elementoDel.getSubElementoIndice()+"}";
            repository.deleteSubElementoById(id, texto, elementoDel.getDistancia(), elementoDel.getCalorias());
            return ELIMINACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String eliminarMediaElemento(ElementoMediaDTO elemento) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            String[] tipoAndMedia = validarMedia(elemento.getTipoMedia(), elemento.getMediaAudio(), elemento.getMediaVideo());
            String texto = "{"+elemento.getElementoIndice()+","+tipoAndMedia[0]+"}";
            repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(id, tipoAndMedia[1], texto);
            return Enums.ResponseCode.ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String actualizarMediaElemento(ElementoMediaDTO elemento) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            String[] tipoAndMedia = validarMedia(elemento.getTipoMedia(), elemento.getMediaAudio(), elemento.getMediaVideo());
            String textoMedia = "{"+elemento.getElementoIndice()+","+tipoAndMedia[0]+"}";
            String textoNombre = "{"+elemento.getElementoIndice()+",\"nombre\"}";
            repository.updateEspecificasColumnasJsonBGenericoByQueriesTextAndId2(id, textoNombre, elemento.getNombre(), textoMedia, tipoAndMedia[1]);
            return Enums.ResponseCode.ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String actualizarDiaFromPlantilla(DiaDTO dia) throws JsonProcessingException {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[dia.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(dia.getDiaIndice());
            repository.updateDiaFromTemplate(id, dia.getCalorias(), dia.getDistancia(), dia.getMinutos(), new ObjectMapper().writeValueAsString(dia.getElementos()));
            return Enums.ResponseCode.ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();

    }

    @Override
    public String actualizarNotaSubElementoByElementoIndexAndSubElementoIndexAndId(ElementoUpd subElemento) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[subElemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(subElemento.getDiaIndice());
            String texto = "{"+subElemento.getElementoIndice()+",\"subElementos\""+","+subElemento.getSubElementoIndice()+",\"nota\"}";
            repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(id, subElemento.getNota(), texto);
            return ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String actualizarElementoByListaIndexAndId(ElementoDTO elemento) throws JsonProcessingException {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            int tempEleIndice = elemento.getElementoIndice();//Trick
            elemento.setElementoIndice(0);
            elemento.setDiaIndice(0);
            elemento.setNumeroSemana(0);
            String texto = "{"+tempEleIndice+"}";
            repository.updateAllJsonBGenericoByQueryTextAndId(id, new ObjectMapper().writeValueAsString(elemento), texto);
            return ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String agregarMediaElemento(ElementoDTO elemento) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            String[] tipoAndMedia = validarMedia(elemento.getTipoMedia(), elemento.getMediaAudio(), elemento.getMediaVideo());
            int elementoIndice = elemento.getElementoIndice();
            String textoNombre = "{"+ elementoIndice +",\"nombre\"}";
            String textoMedia = "{"+ elementoIndice +",\""+tipoAndMedia[0]+"\"}";
            String textoTipo = "{"+ elementoIndice +",\"tipo\"}";
            repository.updateEspecificasColumnasJsonBGenericoByQueriesTextAndId(id, textoNombre, elemento.getNombre(), textoMedia, tipoAndMedia[1], textoTipo, String.valueOf(elemento.getTipo()));
            return Enums.ResponseCode.ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String actualizarMediaSubElemento(SubElementoMediaDTO subEle) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[subEle.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(subEle.getDiaIndice());
            String[] tipoAndMedia = validarMedia(subEle.getTipoMedia(), subEle.getMediaAudio(), subEle.getMediaVideo());
            String textoNombre = "{"+subEle.getElementoIndice()+",\"subElementos\""+","+subEle.getSubElementoIndice()+",\"nombre\"}";
            String textoMedia = "{"+subEle.getElementoIndice()+",\"subElementos\""+","+subEle.getSubElementoIndice()+",\""+tipoAndMedia[0]+"\"}";
            String textoTipo = "{"+subEle.getElementoIndice()+",\"subElementos\""+","+subEle.getSubElementoIndice()+",\"tipo\"}";
            repository.updateEspecificasColumnasJsonBGenericoByQueriesTextAndId(id, textoNombre, subEle.getNombre(), textoMedia, tipoAndMedia[1], textoTipo, String.valueOf(subEle.getTipo()));
            return ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String eliminarMediaSubElemento(SubElementoMediaDTO subEle) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[subEle.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(subEle.getDiaIndice());
            String[] tipoAndMedia = validarMedia(subEle.getTipoMedia(), subEle.getMediaAudio(), subEle.getMediaVideo());
            String textoMedia = "{"+subEle.getElementoIndice()+",\"subElementos\""+","+subEle.getSubElementoIndice()+",\""+tipoAndMedia[0]+"\"}";
            String textoTipo = "{"+subEle.getElementoIndice()+",\"subElementos\""+","+subEle.getSubElementoIndice()+",\"tipo\"}";
            repository.updateEspecificasColumnasJsonBGenericoByQueriesTextAndId2(id, textoMedia, tipoAndMedia[1], textoTipo, String.valueOf(subEle.getTipo()));
            return ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String actualizarSubElementos(ElementoDTO elemento) throws JsonProcessingException  {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            String texto = "{"+elemento.getElementoIndice()+",\"subElementos\"}";
            repository.updateSubElementos(id, texto, new ObjectMapper().writeValueAsString(elemento.getSubElementos()));
            return ACTUALIZACION.get();

        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String actualizarDiaRaizDesdePlantilla(DiaDTO dia) throws JsonProcessingException {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[dia.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(dia.getDiaIndice());
            repository.updateDiaRootFromTemplate(id, dia.getCalorias(), dia.getDistancia(), dia.getMinutos(), new ObjectMapper().writeValueAsString(dia.getElementos()));
            return ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String actualizarElementosEstilosFull(EleEstiloUpd elemento) throws JsonProcessingException {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            Long id = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            String texto = "{"+elemento.getElementoIndice()+",\"estilos\""+"}";
            repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndIdInt(id, new ObjectMapper().writeValueAsString(elemento.getEstilos()), texto);
            return Enums.ResponseCode.ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String actualizarSemanaCompletaDesdeOtra(int semIxDesde, int semIxPara) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semIdDesde = ((int[]) sessionValor.get())[semIxDesde];
            int semIdPara = ((int[]) sessionValor.get())[semIxPara];
            repository.updateSemanaFromAnother(semIdDesde, semIdPara);
            return Enums.ResponseCode.ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    private String[] validarMedia(int tipoMedia, String inMedia, String inVideo){
        String[] tipoAndMedia = new String[2];
        if(tipoMedia == AUDIO.get()){
            tipoAndMedia[0] = "mediaAudio";
            tipoAndMedia[1] = inMedia;
        }else{//TipoMedia.VIDEO
            tipoAndMedia[0] = "mediaVideo";
            tipoAndMedia[1] = inVideo;
        }
        return tipoAndMedia;
    }
}
