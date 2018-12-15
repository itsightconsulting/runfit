package com.itsight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.Dia;
import com.itsight.domain.dto.*;
import com.itsight.domain.jsonb.Elemento;
import com.itsight.domain.jsonb.SubElemento;
import com.itsight.generic.BaseService;

import java.util.List;

public interface DiaService extends BaseService<Dia> {

    List<Integer> encontrarIdPorSemanaId(int semanaId);

    void actualizarFlagDescanso(int id, boolean flagDescanso);

    String eliminarElementoById(ElementoDel elementoDel);

    String eliminarSubElementoById(ElementoDel elementoDel);

    String insertarNuevoElemento(Elemento elemento) throws JsonProcessingException;

    String actualizarNombreElementoByListaIndexAndId(ElementoUpd elemento);

    void insertarSubElementoById(int id, int elementoIndice, int subElementoIndice, String elemento);

    void actualizarNombreSubElementoByElementoIndexAndSubElementoIndexAndId(int id, int elementoIndice, int subElementoIndice, String nombre);

    String insertarNuevoElementoPosEspecifica(ElementoEspecifico elemento) throws JsonProcessingException;

    String actualizarTiempoElementoByListaIndexAndId(ElementoUpd elemento, int minutosDia);

    String actualizarDiaAndElementoById(ElementoUpd elemento, double distanciaDia);

    String actualizarDiaAndElemento2ById(ElementoUpd elemento, double distanciaDia, int minutosDia);

    String actualizarNotaElementoByListaIndexAndId(ElementoUpd elemento);

    String insertarNuevoSubElementoPosEspecifica(SubElementoEspecifico subElementoEspecifico) throws JsonProcessingException;

    String eliminarMediaElemento(ElementoMediaDto elemento);

    String actualizarMediaElemento(ElementoMediaDto elemento);

    void actualizarDiaFromPlantilla(int id, double calorias, double distanciaTot, int minutosTot, String elementos);

    void actualizarNotaSubElementoByElementoIndexAndSubElementoIndexAndId(int id, int elementoIndice, int subElementoIndice, String nota);

    String actualizarElementoByListaIndexAndId(ElementoDto elemento) throws JsonProcessingException;

    String agregarMediaElemento(ElementoDto elemento);

    String actualizarMediaSubElemento(SubElementoMediaDto subEle);

    String eliminarMediaSubElemento(SubElementoMediaDto subEle);

    void actualizarSubElementos(int id, int elementoIndice, String subEles);

    void actualizarDiaRaizDesdePlantilla(int id, double calorias,double distancia, int minutos, String writeValueAsString);

    String actualizarElementosEstilosFull(EleEstiloUpd elemento) throws JsonProcessingException;

    String actualizarDiaAndSubElementoById(SubElemento subElemento) throws JsonProcessingException;

    void actualizarSemanaCompletaDesdeOtra(int semIdDesde, int semIdPara);
}
