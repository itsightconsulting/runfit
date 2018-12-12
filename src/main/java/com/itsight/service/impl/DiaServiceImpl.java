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
    public Dia update(Dia rutina) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(rutina);
    }

    @Override
    public void delete(int rutinaId) {
        // TODO Auto-generated method stub
        repository.delete(new Integer(rutinaId));
    }

    @Override
    public Dia save(Dia entity) {
        return repository.save(entity);
    }

    @Override
    public Dia findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public Dia findOneWithFT(int id) {
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
    public List<Dia> findByIdsIn(List<Integer> ids) {
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
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }

    @Override
    public List<Integer> encontrarIdPorSemanaId(int semanaId) {
        return repository.findIdBySemanaId(semanaId);
    }

    @Override
    public void actualizarNombreListaByListaIndexAndId(String nombre, int listaIndice, int diaId) {
        String texto = "{"+listaIndice+",\"nombre\"}";
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(diaId, nombre, texto);
    }

    @Override
    public void insertarNuevoElementoById(int diaId, int listaIndice, int elementoIndice, String elemento) {
        String texto = "{"+listaIndice+",\"elementos\""+","+elementoIndice+"}";
        repository.saveElemento(diaId, texto, elemento);
    }

    @Override
    public void insertarNuevosElementosById(int diaId, int listaIndice, String elemento) {
        String texto = "{"+listaIndice+",\"elementos\"}";
        repository.saveElementos(diaId, listaIndice, texto, elemento);
    }

    @Override
    public void actualizarFlagDescanso(int id, boolean flagDescanso) {
        repository.updateFlagDescanso(id, flagDescanso);
    }

    @Override
    public void actualizarNombreElementoByListaIndexAndElementoIndexAndId(int diaId, int listaIndice, int elementoIndice, String nombre) {
        String texto = "{"+listaIndice+",\"subelementos\""+","+elementoIndice+",\"nombre\"}";
        //{"avanceSemanas", 20}
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(diaId, nombre, texto);
    }

    @Override
    public String eliminarElementoById(ElementoDelDto elementoDel) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()){
            int semanaId = ((int[]) sessionValor.get())[Integer.parseInt(elementoDel.getNumeroSemana())];
            int diaId = repository.findIdBySemanaId(semanaId).get(Integer.parseInt(elementoDel.getDiaIndice()));
            repository.deleteElementoById(diaId, Integer.parseInt(elementoDel.getElementoIndice()), elementoDel.getMinutos(), elementoDel.getDistancia(), elementoDel.getCalorias());
            return ELIMINACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String insertarNuevoElemento(Elemento elemento) throws JsonProcessingException {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()){
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            int diaId = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            //Seteamos las variables primitivas int con su valor por defecto para que no sean tomadas en consideracion en la serializacion jackson
            elemento.setNumeroSemana(0);
            elemento.setDiaIndice(0);
            repository.saveElemento(diaId, new ObjectMapper().writeValueAsString(elemento));
            return REGISTRO.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();

    }

    @Override
    public String actualizarNombreElementoByListaIndexAndId(Elemento elemento) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            int diaId = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            String texto = "{"+elemento.getElementoIndice()+",\"nombre\"}";
            repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(diaId, elemento.getNombre(), texto);
            return Enums.ResponseCode.ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public void insertarSubElementoById(int id, int elementoIndice, int subElementoIndice, String elemento) {
        String texto = "{"+elementoIndice+",\"subElementos\""+","+subElementoIndice+"}";
        repository.saveSubElemento(id, texto, elemento);
    }

    @Override
    public void actualizarNombreSubElementoByElementoIndexAndSubElementoIndexAndId(int id, int elementoIndice, int subElementoIndice, String nombre) {
        String texto = "{"+elementoIndice+",\"subElementos\""+","+subElementoIndice+",\"nombre\"}";
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(id, nombre, texto);
    }

    @Override
    public String insertarNuevoElementoPosEspecifica(ElementoEspecifico elemento) throws JsonProcessingException {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()){
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            int diaId = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            int posElementoReferencial = elemento.getRefElementoIndice();
            boolean insertarDespues = elemento.getInsertarDespues();
            //Seteamos las variables primitivas/no-pri con su valor por defecto para que no sean tomadas en consideracion en la serializacion jackson
            elemento.setNumeroSemana(0);
            elemento.setDiaIndice(0);
            elemento.setInsertarDespues(null);
            elemento.setRefElementoIndice(0);
            String texto = "{"+posElementoReferencial+"}";
            repository.saveElementoPosEspecificaGeneric(diaId, texto, insertarDespues, new ObjectMapper().writeValueAsString(elemento));
            return REGISTRO.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public void actualizarTiempoElementoByListaIndexAndId(int tiempo, int elementoIndice, int id, int minutosTotales) {
        String texto = "{"+elementoIndice+",\"minutos\""+"}";
        repository.updateTiemposDia(id, String.valueOf(tiempo), texto, minutosTotales);
    }

    @Override
    public void actualizarNotaElementoByListaIndexAndId(String nota, int elementoIndice, int id) {
        String texto = "{"+elementoIndice+",\"nota\""+"}";
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(id, nota, texto);
    }

    @Override
    public void actualizarDiaAndElementoById(int id, double calorias, double distanciaTotal, String nombre, double distancia, int elementoIndice) {
        String txtNombre = "{"+elementoIndice+",\"nombre\""+"}";
        String txtDistancia = "{"+elementoIndice+",\"distancia\""+"}";
        repository.updateDiaAndElemento(id, calorias, distanciaTotal, txtNombre, nombre , txtDistancia, String.valueOf(distancia));
    }

    @Override
    public void actualizarDiaAndElemento2ById(int id, double calorias, double distanciaTotal, int minutosTotal, String nombre, double distancia, int minutos, int elementoIndice) {
        String txtNombre = "{"+elementoIndice+",\"nombre\""+"}";
        String txtDistancia = "{"+elementoIndice+",\"distancia\""+"}";
        String txtMinutos = "{"+elementoIndice+",\"minutos\""+"}";
        repository.updateDiaAndElemento2(id, calorias, distanciaTotal, minutosTotal, txtNombre, nombre , txtDistancia, String.valueOf(distancia), txtMinutos, String.valueOf(minutos));
    }

    @Override
    public void actualizarDiaAndSubElementoById(int id, double calorias, double distanciaDia, double distanciaEle, int elementoIndice, int subElementoIndice, String subEle) {
        String txtDistancia = "{"+elementoIndice+",\"distancia\""+"}";
        String txtNewSubEle = "{"+elementoIndice+",\"subElementos\""+","+subElementoIndice+"}";
        repository.updateDiaAndSubEleEle(id, calorias, distanciaDia, txtDistancia, String.valueOf(distanciaEle), txtNewSubEle, subEle);
    }

    @Override
    public String insertarNuevoSubElementoPosEspecifica(SubElementoEspecifico subElemento) throws JsonProcessingException {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[subElemento.getNumeroSemana()];
            int diaId = repository.findIdBySemanaId(semanaId).get(subElemento.getDiaIndice());
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
            repository.saveElementoPosEspecificaGeneric(diaId, texto, insertarDespues, new ObjectMapper().writeValueAsString(subElemento));
            return REGISTRO.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public void actualizarAudioElementoByListaIndexAndId(String mediaAudio, int elementoIndice, int id) {
        String texto = "{"+elementoIndice+",\"mediaAudio\""+"}";
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(id, mediaAudio, texto);
    }

    @Override
    public void actualizarVideoElementoByListaIndexAndId(String mediaVideo, int elementoIndice, int id) {
        String texto = "{"+elementoIndice+",\"mediaVideo\""+"}";
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(id, mediaVideo, texto);
    }

    @Override
    public String eliminarSubElementoById(ElementoDelDto elementoDel) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[Integer.parseInt(elementoDel.getNumeroSemana())];
            int diaId = repository.findIdBySemanaId(semanaId).get(Integer.parseInt(elementoDel.getDiaIndice()));
            String texto = "{"+elementoDel.getElementoIndice()+",subElementos,"+elementoDel.getSubElementoIndice()+"}";
            repository.deleteSubElementoById(diaId, texto, elementoDel.getDistancia(), elementoDel.getCalorias());
            return ELIMINACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public void actualizarMediaSubElemento(SubElementoMediaDto subElemento, int id) {
        String entryTipo;
        String media;
        if(subElemento.getTipo() == AUDIO.get()){
            entryTipo = "mediaAudio";
            media = subElemento.getMediaAudio();
        }else{//TipoMedia.VIDEO
            entryTipo = "mediaVideo";
            media = subElemento.getMediaVideo();
        }
        String texto = "{"+subElemento.getElementoIndice()+",subElementos,"+subElemento.getSubElementoIndice()+","+entryTipo+"}";
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(id, media, texto);
    }

    @Override
    public void actualizarMediaElemento(ElementoMediaDto elemento, int id) {
        String entryTipo;
        String media;
        if(elemento.getTipoMedia() == AUDIO.get()){
            entryTipo = "mediaAudio";
            media = elemento.getMediaAudio();
        }else{//TipoMedia.VIDEO
            entryTipo = "mediaVideo";
            media = elemento.getMediaVideo();
        }
        String texto = "{"+elemento.getElementoIndice()+","+entryTipo+"}";
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(id, media, texto);
    }

    @Override
    public void actualizarDiaFromPlantilla(int id, double calorias, double distanciaTot, int minutosTot, String elementos) {
        repository.updateDiaFromTemplate(id, calorias, distanciaTot, minutosTot, elementos);
    }

    @Override
    public void actualizarNotaSubElementoByElementoIndexAndSubElementoIndexAndId(int id, int elementoIndice, int subElementoIndice, String nota) {
        String texto = "{"+elementoIndice+",\"subElementos\""+","+subElementoIndice+",\"nota\"}";
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(id, nota, texto);
    }

    @Override
    public String actualizarElementoByListaIndexAndId(ElementoDto elemento) throws JsonProcessingException {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            int diaId = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            int eleIndice = elemento.getElementoIndice();
            elemento.setElementoIndice(0);
            elemento.setDiaIndice(0);
            elemento.setNumeroSemana(0);
            String texto = "{"+elemento.getElementoIndice()+"}";
            repository.updateAllJsonBGenericoByQueryTextAndId(diaId, new ObjectMapper().writeValueAsString(elemento), texto);
            return ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public void actualizarMediaElemento2(ElementoDto elemento, int id) {
        String entryTipo;
        String entryMedia;
        if(elemento.getTipoMedia() == AUDIO.get()){
            entryTipo = "mediaAudio";
            entryMedia = elemento.getMediaAudio();
        }else{//TipoMedia.VIDEO
            entryTipo = "mediaVideo";
            entryMedia = elemento.getMediaVideo();
        }

        String textoNombre = "{"+elemento.getElementoIndice()+",\"nombre\"}";
        String textoMedia = "{"+elemento.getElementoIndice()+",\""+entryTipo+"\"}";
        String textoTipo = "{"+elemento.getElementoIndice()+",\"tipo\"}";
        repository.updateEspecificasColumnasJsonBGenericoByQueriesTextAndId(id, textoNombre, elemento.getNombre(), textoMedia, entryMedia, textoTipo, String.valueOf(elemento.getTipo()));
    }

    @Override
    public void actualizarMediaElemento3(ElementoMediaDto elemento, int id) {
        String entryTipo;
        String entryMedia;
        if(elemento.getTipoMedia() == AUDIO.get()){
            entryTipo = "mediaAudio";
            entryMedia = elemento.getMediaAudio();
        }else{//TipoMedia.VIDEO
            entryTipo = "mediaVideo";
            entryMedia = elemento.getMediaVideo();
        }

        String textoNombre = "{"+elemento.getElementoIndice()+",\"nombre\"}";
        String textoMedia = "{"+elemento.getElementoIndice()+",\""+entryTipo+"\"}";

        repository.updateEspecificasColumnasJsonBGenericoByQueriesTextAndId2(id, textoNombre, elemento.getNombre(), textoMedia, entryMedia);
    }

    @Override
    public void actualizarMediaSubElemento2(SubElementoMediaDto subEle, int id) {
        String entryTipo;
        String entryMedia;
        if(subEle.getTipoMedia() == AUDIO.get()){
            entryTipo = "mediaAudio";
            entryMedia = subEle.getMediaAudio();
        }else{//TipoMedia.VIDEO
            entryTipo = "mediaVideo";
            entryMedia = subEle.getMediaVideo();
        }

        String textoNombre = "{"+subEle.getElementoIndice()+",\"subElementos\""+","+subEle.getSubElementoIndice()+",\"nombre\"}";
        String textoMedia = "{"+subEle.getElementoIndice()+",\"subElementos\""+","+subEle.getSubElementoIndice()+",\""+entryTipo+"\"}";
        String textoTipo = "{"+subEle.getElementoIndice()+",\"subElementos\""+","+subEle.getSubElementoIndice()+",\"tipo\"}";
        repository.updateEspecificasColumnasJsonBGenericoByQueriesTextAndId(id, textoNombre, subEle.getNombre(), textoMedia, entryMedia, textoTipo, String.valueOf(subEle.getTipo()));
    }

    @Override
    public void actualizarSubElementos(int id, int elementoIndice, String subEles) {
        String texto = "{"+elementoIndice+",\"subElementos\"}";
        repository.updateSubElementos(id, texto, subEles);
    }

    @Override
    public void actualizarDiaRaizDesdePlantilla(int id, double calorias, double distancia, int minutos, String elementos) {
        repository.updateDiaRootFromTemplate(id, calorias, distancia, minutos, elementos);
    }

    @Override
    public String actualizarElementosEstilosFull(Elemento elemento) throws JsonProcessingException {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            int semanaId = ((int[]) sessionValor.get())[elemento.getNumeroSemana()];
            int diaId = repository.findIdBySemanaId(semanaId).get(elemento.getDiaIndice());
            String texto = "{"+elemento.getElementoIndice()+",\"estilos\""+"}";
            repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndIdInt(diaId, new ObjectMapper().writeValueAsString(elemento.getEstilos()), texto);
            return Enums.ResponseCode.ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public void actualizarSemanaCompletaDesdeOtra(int semIdDesde, int semIdPara) {
        repository.updateSemanaFromAnother(semIdDesde, semIdPara);
    }
}
