package com.itsight.service.impl;

import com.itsight.domain.Rutina;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.RutinaRepository;
import com.itsight.service.RutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RutinaServiceImpl extends BaseServiceImpl<RutinaRepository> implements RutinaService {

    @Autowired
    public RutinaServiceImpl(RutinaRepository repository) {
        super(repository);
    }

    @Override
    public Rutina update(Rutina rutina) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(rutina);
    }

    @Override
    public void delete(int rutinaId) {
        // TODO Auto-generated method stub
        repository.delete(new Integer(rutinaId));
    }

    @Override
    public Rutina save(Rutina entity) {
        return repository.save(entity);
    }

    @Override
    public Rutina findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public Rutina findOneWithFT(int id) {
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Rutina> findAll() {
        return null;
    }

    @Override
    public List<Rutina> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Rutina> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Rutina> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Rutina> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Rutina> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Rutina> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Rutina> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public List<Rutina> listarPorFiltroPT(int trainerId) {
        return repository.findByUsuarioIdOrderByIdDesc(trainerId);
    }

    @Override
    public String registrar(Rutina entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(Rutina entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {

    }

    @Override
    public Rutina findLastByRedFitnessId(int id) {
        List<Rutina> lstRutina = repository.findFirstByRedFitnessIdOrderByIdDesc(id, new PageRequest(0,1));
        if(lstRutina.isEmpty()){
            return new Rutina();
        }else{
            return lstRutina.get(0);
        }
    }

    @Override
    public Rutina findOneWithOneWeekById(int id) {
        return repository.findOneWithOneWeekById(id);
    }

    @Override
    public void updateSemanaIds(int id, int[] semanaIds) {
        repository.updateSemanaIds(id, semanaIds);
    }

    @Override
    public void updateTotalSemanas(int id, int totalSemanas) {
        repository.updateTotalSemanas(id, totalSemanas);
    }

    @Override
    public int obtenerRedFitnessIdById(int rutinaId) {
        return repository.findRedFitnessIdById(rutinaId);
    }

    @Override
    public List<Rutina> getAllRutinasByUser(int id) {
        return repository.findByUsuarioIdOrderByIdDesc(id);
    }

    @Override
    public void updateAvance(int id, int indexsemana, String strdias, String avance) {
        String texto = "{\"avanceSemanas\","+(indexsemana-1)+"}";
        String textodia = "{\"actualizarAvance\"}";
        int valor = Integer.parseInt(avance);
        repository.updateAvanceSemanaIndex(id, String.valueOf(valor),texto, textodia, strdias);
    }

    @Override
    public void updateResetDiasFlagEnvio(List<Integer> id) {
        repository.updateResetDiasFlagEnvio(id, false);
    }

    @Override
    public void updateDiasFlagEnvio(int indexsemana, int indexdia) {
        repository.updateDiasFlagEnvio(indexsemana,indexdia, true);
    }
}
