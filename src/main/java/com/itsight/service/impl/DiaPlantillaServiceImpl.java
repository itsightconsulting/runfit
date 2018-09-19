package com.itsight.service.impl;

import com.itsight.domain.DiaPlantilla;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.DiaPlantillaRepository;
import com.itsight.service.DiaPlantillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DiaPlantillaServiceImpl extends BaseServiceImpl<DiaPlantillaRepository> implements DiaPlantillaService {

    @Autowired
    public DiaPlantillaServiceImpl(DiaPlantillaRepository repository) {
        super(repository);
    }

    @Override
    public DiaPlantilla update(DiaPlantilla rutina) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(rutina);
    }

    @Override
    public void delete(int rutinaId) {
        // TODO Auto-generated method stub
        repository.delete(new Integer(rutinaId));
    }

    @Override
    public DiaPlantilla save(DiaPlantilla entity) {
        return repository.save(entity);
    }

    @Override
    public DiaPlantilla findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public DiaPlantilla findOneWithFT(int id) {
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<DiaPlantilla> findAll() {
        return null;
    }

    @Override
    public List<DiaPlantilla> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<DiaPlantilla> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<DiaPlantilla> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<DiaPlantilla> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<DiaPlantilla> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<DiaPlantilla> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<DiaPlantilla> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(DiaPlantilla entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(DiaPlantilla entity, String wildcard) {
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
    public void actualizarNombreListaByListaIndexAndId(String nombre, int listaIndice, int diaPlantillaId) {
        String texto = "{"+listaIndice+",\"nombre\"}";
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(diaPlantillaId, nombre, texto);
    }

    @Override
    public void insertarNuevaListaById(int diaPlantillaId, String lista) {
        repository.saveListaDia(diaPlantillaId, lista);
    }

    @Override
    public void insertarNuevoElementoById(int diaPlantillaId, int listaIndice, int elementoIndice, String elemento) {
        String texto = "{"+listaIndice+",\"elementos\""+","+elementoIndice+"}";
        repository.saveElemento(diaPlantillaId, texto, elemento);
    }

    @Override
    public void insertarNuevosElementosById(int diaPlantillaId, int listaIndice, String elemento) {
        String texto = "{"+listaIndice+",\"elementos\"}";
        repository.saveElementos(diaPlantillaId, listaIndice, texto, elemento);
    }

    @Override
    public void actualizarFlagDescanso(int id, boolean flagDescanso) {
        repository.updateFlagDescanso(id, flagDescanso);
    }

    @Override
    public void actualizarNombreElementoByListaIndexAndElementoIndexAndId(int diaPlantillaId, int listaIndice, int elementoIndice, String nombre) {
        String texto = "{"+listaIndice+",\"elementos\""+","+elementoIndice+",\"nombre\"}";
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(diaPlantillaId, nombre, texto);
    }

    @Override
    public void eliminarListaById(int id, int listaIndice) {
        repository.deleteListaById(id, listaIndice);
    }

    @Override
    public void eliminarElementoById(int id, int listaIndice, int elementoIndice) {
        String texto = "{"+listaIndice+",elementos,"+elementoIndice+"}";
        System.out.println("> Query :"+texto);
        repository.eliminarElementoById(id, texto);
    }
}
