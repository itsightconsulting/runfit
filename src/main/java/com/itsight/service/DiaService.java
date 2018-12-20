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

    String actualizarFlagDescanso(int numSem, int diaIndice, boolean flagDescanso);

    String eliminarElementoById(ElementoDel elementoDel);

    String eliminarSubElementoById(ElementoDel elementoDel);

    String insertarNuevoElemento(Elemento elemento) throws JsonProcessingException;

    String actualizarNombreElementoByListaIndexAndId(ElementoUpd elemento);

    String insertarSubElementoById(SubElemento subElemento) throws JsonProcessingException;

    String actualizarNombreSubElementoByElementoIndexAndSubElementoIndexAndId(ElementoUpd subElemento);

    String insertarNuevoElementoPosEspecifica(ElementoEspecifico elemento) throws JsonProcessingException;

    String actualizarTiempoElementoByListaIndexAndId(ElementoUpd elemento, int minutosDia);

    String actualizarDiaAndElementoById(ElementoUpd elemento, double distanciaDia);

    String actualizarDiaAndElemento2ById(ElementoUpd elemento, double distanciaDia, int minutosDia);

    String actualizarNotaElementoByListaIndexAndId(ElementoUpd elemento);

    String insertarNuevoSubElementoPosEspecifica(SubElementoEspecifico subElementoEspecifico) throws JsonProcessingException;

    String eliminarMediaElemento(ElementoMediaDto elemento);

    String actualizarMediaElemento(ElementoMediaDto elemento);

    String actualizarDiaFromPlantilla(DiaDto dia) throws JsonProcessingException;

    String actualizarNotaSubElementoByElementoIndexAndSubElementoIndexAndId(ElementoUpd subElemento);

    String actualizarElementoByListaIndexAndId(ElementoDto elemento) throws JsonProcessingException;

    String agregarMediaElemento(ElementoDto elemento);

    String actualizarMediaSubElemento(SubElementoMediaDto subEle);

    String eliminarMediaSubElemento(SubElementoMediaDto subEle);

    String actualizarSubElementos(ElementoDto elemento) throws JsonProcessingException ;

    String actualizarDiaRaizDesdePlantilla(DiaDto dia) throws JsonProcessingException;

    String actualizarElementosEstilosFull(EleEstiloUpd elemento) throws JsonProcessingException;

    String actualizarDiaAndSubElementoById(SubElemento subElemento) throws JsonProcessingException;

    String actualizarSemanaCompletaDesdeOtra(int semIxDesde, int semIxPara);
}
