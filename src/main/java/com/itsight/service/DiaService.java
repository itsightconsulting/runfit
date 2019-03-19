package com.itsight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.domain.Dia;
import com.itsight.domain.dto.*;
import com.itsight.domain.jsonb.Elemento;
import com.itsight.domain.jsonb.SubElemento;
import com.itsight.generic.BaseService;

import java.util.List;

public interface DiaService extends BaseService<Dia, Long> {

    List<Long> encontrarIdPorSemanaId(Long semanaId);

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

    String eliminarMediaElemento(ElementoMediaDTO elemento);

    String actualizarMediaElemento(ElementoMediaDTO elemento);

    String actualizarDiaFromPlantilla(DiaDTO dia) throws JsonProcessingException;

    String actualizarNotaSubElementoByElementoIndexAndSubElementoIndexAndId(ElementoUpd subElemento);

    String actualizarElementoByListaIndexAndId(ElementoDTO elemento) throws JsonProcessingException;

    String agregarMediaElemento(ElementoDTO elemento);

    String actualizarMediaSubElemento(SubElementoMediaDTO subEle);

    String eliminarMediaSubElemento(SubElementoMediaDTO subEle);

    String actualizarSubElementos(ElementoDTO elemento) throws JsonProcessingException ;

    String actualizarDiaRaizDesdePlantilla(DiaDTO dia) throws JsonProcessingException;

    String actualizarElementosEstilosFull(EleEstiloUpd elemento) throws JsonProcessingException;

    String actualizarDiaAndSubElementoById(SubElemento subElemento) throws JsonProcessingException;

    String actualizarSemanaCompletaDesdeOtra(int semIxDesde, int semIxPara);
}
