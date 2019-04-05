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
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    public DiaPlantilla save(DiaPlantilla entity) {
        return repository.save(entity);
    }

    @Override
    public DiaPlantilla findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public DiaPlantilla findOneWithFT(Integer id) {
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
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public List<Integer> encontrarIdPorSemanaId(Integer semanaId) {
        return repository.findIdBySemanaId(semanaId);
    }

    @Override
    public void actualizarNombreListaByListaIndexAndId(String nombre, int listaIndice, Integer id) {
        String texto = "{"+listaIndice+",\"nombre\"}";
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(id, nombre, texto);
    }

    @Override
    public void insertarNuevaListaById(Integer id, String lista) {
        repository.saveListaDia(id, lista);
    }

    @Override
    public void insertarNuevoElementoById(Integer id, int listaIndice, int elementoIndice, String elemento) {
        String texto = "{"+listaIndice+",\"elementos\""+","+elementoIndice+"}";
        repository.saveElemento(id, texto, elemento);
    }

    @Override
    public void insertarNuevosElementosById(Integer id, int listaIndice, String elemento) {
        String texto = "{"+listaIndice+",\"elementos\"}";
        repository.saveElementos(id, listaIndice, texto, elemento);
    }

    @Override
    public void actualizarFlagDescanso(Integer id, boolean flagDescanso) {
        repository.updateFlagDescanso(id, flagDescanso);
    }

    @Override
    public void actualizarNombreElementoByListaIndexAndElementoIndexAndId(Integer id, int listaIndice, int elementoIndice, String nombre) {
        String texto = "{"+listaIndice+",\"elementos\""+","+elementoIndice+",\"nombre\"}";
        repository.updateEspecificaColumnaJsonBGenericoByQueryTextAndId(id, nombre, texto);
    }

    @Override
    public void eliminarListaById(Integer id, int listaIndice) {
        repository.deleteListaById(id, listaIndice);
    }

    @Override
    public void eliminarElementoById(Integer id, int listaIndice, int elementoIndice) {
        String texto = "{"+listaIndice+",elementos,"+elementoIndice+"}";
        System.out.println("> Query :"+texto);
        repository.eliminarElementoById(id, texto);
    }
}
