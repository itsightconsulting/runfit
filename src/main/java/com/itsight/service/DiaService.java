package com.itsight.service;

import com.itsight.domain.Dia;
import com.itsight.domain.dto.ElementoDto;
import com.itsight.domain.dto.ElementoMediaDto;
import com.itsight.domain.dto.SubElementoDto;
import com.itsight.domain.dto.SubElementoMediaDto;
import com.itsight.domain.jsonb.Elemento;
import com.itsight.domain.jsonb.SubElemento;
import com.itsight.generic.BaseService;

import java.util.List;

public interface DiaService extends BaseService<Dia> {

    List<Integer> encontrarIdPorSemanaId(int semanaId);

    void actualizarNombreListaByListaIndexAndId(String nombre, int listaIndice, int id);

    void insertarNuevoElementoById(int id, int listaIndice, int elementoIndice, String elemento);

    void insertarNuevosElementosById(int id, int listaIndice, String elemento);

    void actualizarFlagDescanso(int id, boolean flagDescanso);

    void actualizarNombreElementoByListaIndexAndElementoIndexAndId(int id, int listaIndice, int elementoIndice, String nombre);

    void eliminarElementoById(int id, int listaIndice, int minutos, int distancia);

    void eliminarSubElementoById(int id, int eleIndice, int subEleIndice);

    void insertarNuevoElemento(int id, String elemento);

    void actualizarNombreElementoByListaIndexAndId(String nombre, int elementoIndicice, int diaId);

    void insertarSubElementoById(int id, int elementoIndice, int subElementoIndice, String elemento);

    void actualizarNombreSubElementoByElementoIndexAndSubElementoIndexAndId(int id, int elementoIndice, int subElementoIndice, String nombre);

    void insertarNuevoElementoPosEspecifica(int id, int posElementoReferencial, boolean insertarDespues, String elemento);

    void actualizarTiempoElementoByListaIndexAndId(int tiempo, int elementoIndice, int id, int minutosTotales);

    void actualizarDiaAndElementoById(double distancia, int elementoIndice, int id, double distanciaTotal, double calorias);

    void actualizarNotaElementoByListaIndexAndId(String nota, int elementoIndice, int id);

    void insertarNuevoSubElementoPosEspecifica(int id, int elementoIndice, int posSubElementoReferencial, boolean insertarDespues, String subElemento);

    void actualizarAudioElementoByListaIndexAndId(String mediaAudio, int elementoIndice, int id);

    void actualizarVideoElementoByListaIndexAndId(String mediaVideo, int elementoIndice, int id);

    void actualizarMediaSubElemento(SubElementoMediaDto subElemento, int id);

    void actualizarMediaElemento(ElementoMediaDto elemento, int id);

    void actualizarDiaFromPlantilla(int id,int distanciaTot, int minutosTot, String elementos);

    void actualizarNotaSubElementoByElementoIndexAndSubElementoIndexAndId(int id, int elementoIndice, int subElementoIndice, String nota);

    void actualizarElementoByListaIndexAndId(String elemento, int elementoIndice, int id);

    void actualizarMediaElemento2(ElementoDto elemento, int id);

    void actualizarMediaElemento3(ElementoMediaDto elemento, int diaId);//Que en el controller sirve para la "/v2"

    void actualizarMediaSubElemento2(SubElementoMediaDto subEle, int diaId);

    void actualizarSubElementos(int id, int elementoIndice, String subEles);

    void actualizarDiaRaizDesdePlantilla(int diaId, int distancia, int minutos, String writeValueAsString);

    void actualizarElementosEstilosFull(String estilos, int elementoIndice, int diaId);
}
