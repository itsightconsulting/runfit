package com.itsight.service;

import com.itsight.domain.DiaPlantilla;
import com.itsight.generic.BaseService;

import java.util.List;

public interface DiaPlantillaService extends BaseService<DiaPlantilla, Long> {

    List<Integer> encontrarIdPorSemanaId(Long semanaId);

    void actualizarNombreListaByListaIndexAndId(String nombre, int listaIndice, Long id);

    void insertarNuevaListaById(Long id, String lista);

    void insertarNuevoElementoById(Long id, int listaIndice, int elementoIndice, String elemento);

    void insertarNuevosElementosById(Long id, int listaIndice, String elemento);

    void actualizarFlagDescanso(Long id, boolean flagDescanso);

    void actualizarNombreElementoByListaIndexAndElementoIndexAndId(Long id, int listaIndice, int elementoIndice, String nombre);

    void eliminarListaById(Long id, int listaIndice);

    void eliminarElementoById(Long id, int listaIndice, int elementoIndice);
}
