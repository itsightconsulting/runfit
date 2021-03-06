package com.itsight.service.impl;

import com.itsight.domain.Dia;
import com.itsight.domain.RuConsolidado;
import com.itsight.domain.Rutina;
import com.itsight.domain.Semana;
import com.itsight.domain.dto.RuTpGeneralDTO;
import com.itsight.domain.dto.RutinaDTO;
import com.itsight.domain.dto.SemanaPlantillaDTO;
import com.itsight.domain.jsonb.RutinaControl;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ConfiguracionClienteRepository;
import com.itsight.repository.RutinaRepository;
import com.itsight.service.*;
import com.itsight.util.Enums;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.itsight.util.Enums.ResponseCode.REGISTRO;
import static com.itsight.util.Enums.ResponseCode.SESSION_VALUE_NOT_FOUND;
import static com.itsight.util.Enums.TipoRutina.GENERAL;

@Service
@Transactional
public class RutinaServiceImpl extends BaseServiceImpl<RutinaRepository> implements RutinaService {

    private RuConsolidadoService ruConsolidadoService;

    private RedFitnessService redFitnessService;

    private TipoRutinaService tipoRutinaService;

    private ConfiguracionClienteRepository configuracionClienteRepository;

    @Autowired(required = false)
    private HttpSession session;

    @Autowired
    public RutinaServiceImpl(RutinaRepository repository, RuConsolidadoService ruConsolidadoService, RedFitnessService redFitnessService,
                             TipoRutinaService tipoRutinaService, ConfiguracionClienteRepository configuracionClienteRepository) {
        super(repository);
        this.ruConsolidadoService = ruConsolidadoService;
        this.redFitnessService = redFitnessService;
        this.tipoRutinaService = tipoRutinaService;
        this.configuracionClienteRepository = configuracionClienteRepository;
    }

    @Override
    public Rutina update(Rutina rutina) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(rutina);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    public Rutina save(Rutina entity) {
        return repository.save(entity);
    }

    @Override
    public Rutina findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Rutina findOneWithFT(Integer id) {
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
    public String registrar(Rutina entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(Rutina entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public Rutina findLastByRedFitnessId(Integer redFitId) {
        List<Rutina> lstRutina = repository.findFirstByRedFitnessIdOrderByIdDesc(redFitId, PageRequest.of(0, 1));
        if (lstRutina.isEmpty()) {
            return new Rutina();
        } else {
            return lstRutina.get(0);
        }
    }

    @Override
    public Rutina findByRedFitnessOrderByIdDesc(Integer redFitId) {
        List<Rutina> lstRutina = repository.findFirstByRedFitnessIdOrderByIdDesc(redFitId, PageRequest.of(0, 1));
        return lstRutina.get(0);
    }

    @Override
    public Rutina findByIndexRedFitness(Integer redFitId, Integer page) {
        List<Rutina> lstRutina = repository.findFirstByRedFitnessIdOrderByIdDesc(redFitId, PageRequest.of(page, 1));
        return lstRutina.get(0);
    }

    @Override
    public List<Rutina> findAllByRedFitnessOrderByIdDesc(Integer redFitId) {
        List<Rutina> lstRutina = repository.findAllByRedFitnessIdOrderByIdDesc(redFitId);

        return lstRutina;
    }


    @Override
    public Rutina findOneWithOneWeekById(Integer id) {
        return repository.findOneWithOneWeekById(id);
    }

    @Override
    public void updateSemanaIds(Integer id, int[] semanaIds) {
        repository.updateSemanaIds(id, semanaIds);
    }

    @Override
    public void updateTotalSemanas(Integer id, int totalSemanas) {
        repository.updateTotalSemanas(id, totalSemanas);
    }

    public Integer obtenerRedFitnessIdById(Integer id) {
        return repository.findRedFitnessIdById(id);
    }

    @Override
    public List<Rutina> getAllRutinasByUser(Integer id) {
        return repository.findByClienteIdOrderByIdDesc(id);
    }

    @Override
    public void updateAvance(Integer id, int indexsemana, String strdias, String avance) {
        String texto = "{\"avanceSemanas\"," + (indexsemana - 1) + "}";
        String textodia = "{\"actualizarAvance\"}";
        int valor = Integer.parseInt(avance);
        repository.updateAvanceSemanaIndex(id, String.valueOf(valor), texto, textodia, strdias);
    }

    @Override
    public void updateResetDiasFlagEnvio(int anio, int mes) {
        repository.updateResetDiasFlagEnvio(anio, mes, false);
    }

    @Override
    public void updateDiasFlagEnvio(int indexsemana, int indexdia) {
        repository.updateDiasFlagEnvio(indexsemana, indexdia, true);
    }

    @Override
    public String registrarByCascada(RutinaDTO rutinaDto, Integer redFitId, Integer runneId) {
        Rutina nueRutina = new Rutina();
        //Pasando del Dto al objeto
        RutinaControl rc = new RutinaControl();
        BeanUtils.copyProperties(rutinaDto.getControl(), rc);
        BeanUtils.copyProperties(rutinaDto, nueRutina);
        nueRutina.setCliente(runneId);

        //seteando nombre por default con la fecha de creacion
        nueRutina.setNombre("Rutina con fecha de inicio " + new SimpleDateFormat("dd-MM-yyyy").format(rutinaDto.getFechaInicio()));

        nueRutina.setRedFitness(redFitId);
        nueRutina.setControl(rc);
        //Instanciando lista de semanas
        List<Semana> semanas = rutinaDto.getSemanas().stream().map(x -> obtenerSemanasRutina(x, nueRutina)).collect(Collectors.toList());
        //Agregando las semanas a la instancia de rutina que hará que se inserten mediante cascade strategy
        nueRutina.setLstSemana(semanas);
        nueRutina.setFlagActivo(false);
        // nueRutina.setTipoRutina(Enums.TipoRutina.RUNNING.get());

        nueRutina.setTipoRutina(rutinaDto.getTipoRutina());
        ruConsolidadoService.save(new RuConsolidado(rutinaDto.getGeneral(), rutinaDto.getStats(), rutinaDto.getMejoras(), rutinaDto.getMatrizMejoraVelocidades(), rutinaDto.getMatrizMejoraCadencia(), rutinaDto.getMatrizMejoraTcs(), rutinaDto.getMatrizMejoraLonPaso(), rutinaDto.getDtGrafico(), nueRutina));
        //AFTER REGISTRO LOS IDS YA SE PUEDEN RECUPERAR
        int[] arrSemIds = new int[nueRutina.getLstSemana().size()];
        for (int i = 0; i < arrSemIds.length; i++) {
            arrSemIds[i] = nueRutina.getLstSemana().get(i).getId();
        }
        repository.updateSemanaIds(nueRutina.getId(), arrSemIds);
        redFitnessService.updatePlanStatusAndUltimoDiaPlanificacionAndContadorRutinas(redFitId, 2, rutinaDto.getFechaInicio(), rutinaDto.getFechaFin(), 1);
        configuracionClienteRepository.updateNotificacionRutinasTotById(runneId, 1);
        //updateNotificacionRutinasTotById
        return REGISTRO.get();
    }

    @Override
    public String registrarGenByCascada(RuTpGeneralDTO rutina, Integer redFitId, Integer runneId) {
        Rutina nueRutina = new Rutina();
        //Pasando del Dto al objeto
        RutinaControl rc = new RutinaControl();
        BeanUtils.copyProperties(rutina.getControl(), rc);
        BeanUtils.copyProperties(rutina, nueRutina);
        nueRutina.setCliente(runneId);
        nueRutina.setRedFitness(redFitId);
        nueRutina.setControl(rc);
        nueRutina.setNombre("Rutina con fecha de inicio " + new SimpleDateFormat("dd-MM-yyyy").format(nueRutina.getFechaInicio()));
        //Instanciando lista de semanas
        List<Semana> semanas = rutina.getSemanas().stream().map(x -> obtenerSemanasRutina(x, nueRutina)).collect(Collectors.toList());
        //Agregando las semanas a la instancia de rutina que hará que se inserten mediante cascade strategy
        nueRutina.setLstSemana(semanas);
        nueRutina.setFlagActivo(false);

        nueRutina.setTipoRutina(GENERAL.get());
        repository.save(nueRutina);
        //AFTER REGISTRO LOS IDS YA SE PUEDEN RECUPERAR
        int[] arrSemIds = new int[nueRutina.getLstSemana().size()];
        for (int i = 0; i < arrSemIds.length; i++) {
            arrSemIds[i] = nueRutina.getLstSemana().get(i).getId();
        }
        repository.updateSemanaIds(nueRutina.getId(), arrSemIds);
        redFitnessService.updatePlanStatusAndUltimoDiaPlanificacionAndContadorRutinas(redFitId, 2, rutina.getFechaInicio(), rutina.getFechaFin(), 1);
        String hshRedFitId = Parseador.getEncodeHash32Id("rf-rutina", redFitId);
        String hshRunnerId = Parseador.getEncodeHash16Id("rf-rutina", runneId);
        return Utilitarios.jsonResponse(hshRedFitId, hshRunnerId);
    }

    private Semana obtenerSemanasRutina(SemanaPlantillaDTO semana, Rutina objR) {
        Semana nueSem = new Semana();
        BeanUtils.copyProperties(semana, nueSem);
        //Pasando el objeto de rutina a la semana para al momento de su registro, el ID que se genere(De la RutPlan)
        //se referencie en el registro de la semana
        nueSem.setRutina(objR);
        //Insertando dias de la semana a su respectiva lista de dias
        List<Dia> dias = new ArrayList<>();
        semana.getDias().forEach(dia -> {
            Dia objDia = new Dia();
            BeanUtils.copyProperties(dia, objDia);
            //Pasando el objeto de semana al dia para al momento de su registro, el ID que se genere(De la SemPlan)
            //se referencie en el registro del día
            objDia.setSemana(nueSem);
            dias.add(objDia);
        });
        //Agregando la lista de dias a la semana
        nueSem.setLstDia(dias);
        nueSem.setObjetivos("0,0,0,0,0,0,0");
        return nueSem;
    }

    @Override
    public String actualizarFlagActivo(boolean flagActivo) {
        Optional<Object> sessionValor = Optional.ofNullable(session.getAttribute("edicionRutinaId"));
        if (sessionValor.isPresent()) {
            Integer id = (Integer) sessionValor.get();
            repository.updateFlagActivo(id, flagActivo);
            return Enums.ResponseCode.ACTUALIZACION.get();
        }
        return SESSION_VALUE_NOT_FOUND.get();
    }

    @Override
    public void updateFechaCliAccesoById(Integer id, Date fechaMax) {
        repository.updateFechaCliAccesoById(id, fechaMax);
    }

    @Override
    public Integer getMaxRutinaIdByClienteId(Integer clienteId) {
        return repository.getMaxRutinaIdByClienteId(clienteId);
    }

    @Override
    public List<String> findRutinaIdsByClienteId(int id) {
        return repository.findRutinaIdsByClienteId(id);
    }

}
