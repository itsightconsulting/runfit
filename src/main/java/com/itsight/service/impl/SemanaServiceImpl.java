package com.itsight.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.advice.CustomValidationException;
import com.itsight.domain.Dia;
import com.itsight.domain.Rutina;
import com.itsight.domain.Semana;
import com.itsight.domain.pojo.MetricaVelPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.RedFitnessRepository;
import com.itsight.repository.RutinaRepository;
import com.itsight.repository.SemanaRepository;
import com.itsight.service.RuConsolidadoService;
import com.itsight.service.SemanaService;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import com.itsight.util.Validador;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.awt.print.Pageable;
import java.io.IOException;
import java.util.*;

import static com.itsight.util.Enums.ResponseCode.*;

@Service
@Transactional
public class SemanaServiceImpl extends BaseServiceImpl<SemanaRepository> implements SemanaService {

    private RutinaRepository rutinaRepository;

    private RedFitnessRepository redFitnessRepository;

    private RuConsolidadoService ruConsolidadoService;

    private HttpSession session;

    public SemanaServiceImpl(SemanaRepository repository, RutinaRepository rutinaRepository, RedFitnessRepository redFitnessRepository, RuConsolidadoService ruConsolidadoService, HttpSession session) {
        super(repository);
        this.rutinaRepository = rutinaRepository;
        this.redFitnessRepository = redFitnessRepository;
        this.ruConsolidadoService = ruConsolidadoService;
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
    public Semana findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Semana findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

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
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) { }

    @Override
    public Semana findOneWithDaysById(Integer id) {
        return repository.findOneWithDays(id);
    }

    @Override
    public List<Semana> findByRutinaIdOrderByIdDesc(Integer idrutina) {
        return repository.findByRutinaIdOrderByIdDesc(idrutina);
    }

    @Override
    public String actualizarObjetivos(int numSem, String objetivos) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("semanaIds"));
        if(sessionValor.isPresent()) {
            Integer id = ((Integer[]) sessionValor.get())[numSem];
            repository.updateObjetivoById(id, objetivos);
            return ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public String agregarSemana(Semana semana, List<Dia> dias, Integer rutinaId, int totalSemanas, Date fechaFin) {
        semana.setRutina(rutinaId);
        semana.setLstDia(dias);
        repository.saveAndFlush(semana);
        Rutina qRutina = rutinaRepository.findById(rutinaId).orElse(null);
        qRutina.setTotalSemanas(totalSemanas);
        qRutina.setFechaFin(fechaFin);
        Integer[] qSemanaIds = Utilitarios.agregarElementoArray(qRutina.getSemanaIds(), semana.getId());
        qRutina.setSemanaIds(qSemanaIds);
        qRutina.setDias(qRutina.getDias()+7);
        rutinaRepository.saveAndFlush(qRutina);
        session.setAttribute("semanaIds", qSemanaIds);
        //Actualizar última fecha de planificación
        Integer redFitnessId = rutinaRepository.findRedFitnessIdById(rutinaId);
        redFitnessRepository.updateUltimaFechaPlanificacionById(redFitnessId, fechaFin);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizarFullMetricasVelocidad(String mVz) throws IOException {
        if(Validador.isValidJSON(mVz)){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                MetricaVelPOJO[] pojos = objectMapper.readValue(mVz, MetricaVelPOJO[].class);
                if(pojos.length == 8){
                    int indi = 0;
                    for(int i=0; i<2;i++){
                        MetricaVelPOJO metValid = pojos[new Random().nextInt(8)];
                        if(metValid.getInd().length > 0){
                            for (int k=0; k<2;k++){
                                String indValid = metValid.getInd()[new Random().nextInt(metValid.getInd().length)];
                                if(indValid != null && indValid.matches(Validador.velMetricaPattern)){
                                    indi++;
                                }
                            }
                        }
                    }

                    if(indi == 4){
                        Integer rutinaId = Integer.parseInt(session.getAttribute("edicionRutinaId").toString());
                        ruConsolidadoService.updateMatrizMejoraVelocidades(rutinaId, mVz);

                        List<MetricaVelPOJO> lstMetricaVel = Arrays.asList(pojos);
                        StringBuilder builderVels = new StringBuilder();
                        int iteBase = lstMetricaVel.get(0).getInd().length;
                        String trick = "";
                        for(int i=0; i<iteBase; i++){
                            builderVels.append(trick);
                            builderVels.append("[");
                            trick = "";
                            for (MetricaVelPOJO vel: lstMetricaVel) {
                                builderVels.append(trick);
                                trick = ",";
                                builderVels.append("{");
                                builderVels.append("\"parcial\":");
                                builderVels.append("\"" +vel.getInd()[i] + "\"");
                                builderVels.append("}");
                            }
                            trick = " ";
                            builderVels.append("]");//Importante el espacio para el posterior split
                        }
                        Integer[] ids = Arrays.copyOfRange((Integer[])session.getAttribute("semanaIds"), 0, iteBase);
                        repository.actualizarMetsVelocidadByIds(Arrays.toString(ids).substring(1, Arrays.toString(ids).length()-1), builderVels.toString());
                        return ACTUALIZACION.get();
                    }
                }
            }catch (JsonMappingException ex){
                ex.printStackTrace();
            }
        }
        return EX_VALIDATION_FAILED.get();
    }

    @Override
    public List<Semana> findByRutinaIdOrderByIdDesc(Integer rutinaId, int semanaIx) {
        return repository.findByRutinaIdOrderByIdDesc(rutinaId, PageRequest.of(1, semanaIx));
    }

    @Override
    public Semana findOneWithDaysEspById(Integer rutinaId, int semanaIx) throws CustomValidationException {
        //Debido a que el arreglo en postgresql empieza desde 1
        if (rutinaId == null) {
            throw new CustomValidationException(Enums.Msg.VALIDACION_FALLIDA.get(), SESSION_VALUE_NOT_FOUND.get());
        }
        Integer semanaId = rutinaRepository.findSemanaIdByIndex(rutinaId, semanaIx+1);
        return repository.findOneWithDays(semanaId);
    }
}
