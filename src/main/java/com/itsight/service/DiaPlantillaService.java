package com.itsight.service;

import com.itsight.domain.DiaPlantilla;
import com.itsight.generic.BaseService;

import java.util.List;

public interface DiaPlantillaService extends BaseService<DiaPlantilla, Integer> {

    List<Integer> encontrarIdPorSemanaId(Integer semanaId);

    List<DiaPlantilla> obtenerTodosConJerarquia(Integer rutinaPlantillaId);

    void actualizarNombreListaByListaIndexAndId(String nombre, int listaIndice, Integer id);

    void insertarNuevaListaById(Integer id, String lista);

    void insertarNuevoElementoById(Integer id, int listaIndice, int elementoIndice, String elemento);

    void insertarNuevosElementosById(Integer id, int listaIndice, String elemento);

    void actualizarFlagDescanso(Integer id, boolean flagDescanso);

    void actualizarNombreElementoByListaIndexAndElementoIndexAndId(Integer id, int listaIndice, int elementoIndice, String nombre);

    void eliminarListaById(Integer id, int listaIndice);

    void eliminarElementoById(Integer id, int listaIndice, int elementoIndice);
}
