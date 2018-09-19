package com.itsight.service;

import com.itsight.domain.DiaPlantilla;
import com.itsight.generic.BaseService;

import java.util.List;

public interface DiaPlantillaService extends BaseService<DiaPlantilla> {

    List<Integer> encontrarIdPorSemanaId(int semanaId);

    void actualizarNombreListaByListaIndexAndId(String nombre, int listaIndice, int id);

    void insertarNuevaListaById(int id, String lista);

    void insertarNuevoElementoById(int id, int listaIndice, int elementoIndice, String elemento);

    void insertarNuevosElementosById(int id, int listaIndice, String elemento);

    void actualizarFlagDescanso(int id, boolean flagDescanso);

    void actualizarNombreElementoByListaIndexAndElementoIndexAndId(int id, int listaIndice, int elementoIndice, String nombre);

    void eliminarListaById(int id, int listaIndice);

    void eliminarElementoById(int id, int listaIndice, int elementoIndice);
}
