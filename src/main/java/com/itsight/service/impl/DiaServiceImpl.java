package com.itsight.service.impl;

import com.itsight.domain.Dia;
import com.itsight.domain.dto.ElementoDto;
import com.itsight.domain.dto.ElementoMediaDto;
import com.itsight.domain.dto.SubElementoDto;
import com.itsight.domain.dto.SubElementoMediaDto;
import com.itsight.domain.jsonb.Elemento;
import com.itsight.domain.jsonb.SubElemento;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.DiaRepository;
import com.itsight.service.DiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.itsight.util.Enums.TipoMedia.AUDIO;

@Service
@Transactional
public class DiaServiceImpl extends BaseServiceImpl<DiaRepository> implements DiaService {

    @Autowired
    public DiaServiceImpl(DiaRepository repository) {
        super(repository);
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
    public void eliminarElementoById(int id, int listaIndice, int minutos, double distancia, double calorias) {
        repository.deleteElementoById(id, listaIndice, minutos, distancia, calorias);
    }

    @Override
    public void insertarNuevoElemento(int diaId, String elemento) {
        repository.saveElemento(diaId, elemento);
    }

    @Override
    public void actualizarNombreElementoByListaIndexAndId(String nombre, int elementoIndice, int diaId) {
        String texto = "{"+elementoIndice+",\"nombre\"}";
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(diaId, nombre, texto);
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
    public void insertarNuevoElementoPosEspecifica(int id, int posElementoReferencial, boolean insertarDespues, String elemento) {
        String texto = "{"+posElementoReferencial+"}";
        repository.saveElementoPosEspecificaGeneric(id, texto, insertarDespues, elemento);
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
    public void insertarNuevoSubElementoPosEspecifica(int id, int elementoIndice, int posSubElementoReferencial, boolean insertarDespues, String subElemento) {
        String texto = "{"+elementoIndice+",\"subElementos\""+","+posSubElementoReferencial+"}";
        repository.saveElementoPosEspecificaGeneric(id, texto, insertarDespues, subElemento);

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
    public void eliminarSubElementoById(int id, int eleIndice, int subEleIndice, double distancia, double calorias) {
        String texto = "{"+eleIndice+",subElementos,"+subEleIndice+"}";
        repository.deleteSubElementoById(id, texto, distancia, calorias);
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
    public void actualizarElementoByListaIndexAndId(String elemento, int elementoIndice, int diaId) {
        String texto = "{"+elementoIndice+"}";
        repository.updateAllJsonBGenericoByQueryTextAndId(diaId, elemento, texto);
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
    public void actualizarElementosEstilosFull(String estilos, int elementoIndice, int id) {
        String texto = "{"+elementoIndice+",\"estilos\""+"}";
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndIdInt(id, estilos, texto);
    }

    @Override
    public void actualizarSemanaCompletaDesdeOtra(int semIdDesde, int semIdPara) {
        repository.updateSemanaFromAnother(semIdDesde, semIdPara);
    }
}
