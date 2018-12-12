package com.itsight.service.impl;

import com.itsight.domain.Dia;
import com.itsight.domain.Rutina;
import com.itsight.domain.Semana;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.RedFitnessRepository;
import com.itsight.repository.RutinaRepository;
import com.itsight.repository.SemanaRepository;
import com.itsight.service.SemanaService;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SemanaServiceImpl extends BaseServiceImpl<SemanaRepository> implements SemanaService {

    private RutinaRepository rutinaRepository;

    private RedFitnessRepository redFitnessRepository;

    private HttpSession session;

    public SemanaServiceImpl(SemanaRepository repository, RutinaRepository rutinaRepository, RedFitnessRepository redFitnessRepository, HttpSession session) {
        super(repository);
        this.rutinaRepository = rutinaRepository;
        this.redFitnessRepository = redFitnessRepository;
        this.session = session;
    }

    @Override
    public Semana save(Semana entity) {
        return repository.save(entity);
    }

    @Override
    public Semana update(Semana entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public Semana findOne(int id) {
        return repository.findOne(new Integer(id));
    }

    @Override
    public Semana findOneWithFT(int id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Semana> findAll() {
        return null;
    }

    @Override
    public List<Semana> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Semana> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Semana> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Semana> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Semana> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Semana> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Semana> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Semana entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(Semana entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) { }

    @Override
    public Semana findOneWithDaysById(int id) {
        return repository.findOneWithDays(id);
    }

    @Override
    public List<Semana> findByRutinaIdOrderByIdDesc(int idrutina) {
        return repository.findByRutinaIdOrderByIdDesc(idrutina);
    }

    @Override
    public void actualizarObjetivos(int id, String objetivos) {
        repository.updateObjetivoById(id, objetivos);
    }

    @Override
    public void actualizarMetsVelocidadesMultiple(String ids, String metsVel) {
        repository.actualizarMetsVelocidadByIds(ids, metsVel);
    }

    @Override
    public String agregarSemana(Semana semana, List<Dia> dias, int rutinaId, int totalSemanas, Date fechaFin) {
        semana.setRutina(rutinaId);
        semana.setLstDia(dias);
        repository.saveAndFlush(semana);
        Rutina qRutina = rutinaRepository.findOne(rutinaId);
        qRutina.setTotalSemanas(totalSemanas);
        qRutina.setFechaFin(fechaFin);
        int[] qSemanaIds = Utilitarios.agregarElementoArray(qRutina.getSemanaIds(), semana.getId());
        qRutina.setSemanaIds(qSemanaIds);
        qRutina.setDias(qRutina.getDias()+7);
        rutinaRepository.saveAndFlush(qRutina);
        session.setAttribute("semanaIds", qSemanaIds);
        //Actualizar última fecha de planificación
        int redFitnessId = rutinaRepository.findRedFitnessIdById(rutinaId);
        redFitnessRepository.updateUltimaFechaPlanificacionById(redFitnessId, fechaFin);
        return Enums.ResponseCode.REGISTRO.get();
    }
}
