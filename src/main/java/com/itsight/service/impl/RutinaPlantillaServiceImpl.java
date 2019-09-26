package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.*;
import com.itsight.domain.dto.DiaPlantillaDTO;
import com.itsight.domain.dto.RutinaPlantillaDTO;
import com.itsight.domain.dto.SemanaPlantillaDTO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.RutinaPlantillaRepository;
import com.itsight.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.itsight.util.Enums.Msg.NOMBRE_CATEGORIA_PLANTILLA_REPETIDO;
import static com.itsight.util.Enums.Msg.NOMBRE_RUTINA_PLANTILLA_REPETIDO;
import static com.itsight.util.Enums.ResponseCode.*;

@Service
@Transactional
public class RutinaPlantillaServiceImpl extends BaseServiceImpl<RutinaPlantillaRepository> implements RutinaPlantillaService {

    CategoriaPlantillaService categoriaPlantillaService;
    RutinaService rutinaService;
    SemanaService semanaService;
    DiaService diaService;
    RedFitnessService redFitnessService;

    private EntityManager entityManager;

    @Autowired
    public RutinaPlantillaServiceImpl(RutinaPlantillaRepository repository, CategoriaPlantillaService categoriaPlantillaService,
                                      RutinaService rutinaService, EntityManager entityManager, SemanaService semanaService, DiaService diaService, RedFitnessService redFitnessService) {
        super(repository);
        this.categoriaPlantillaService = categoriaPlantillaService;
        this.rutinaService = rutinaService;
        this.entityManager = entityManager;
        this.semanaService = semanaService;
        this.diaService = diaService;
        this.redFitnessService = redFitnessService;
    }


    @Override
    public RutinaPlantilla update(RutinaPlantilla rutina) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(rutina);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    public RutinaPlantilla save(RutinaPlantilla entity) {
        return repository.save(entity);
    }

    @Override
    public RutinaPlantilla findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public RutinaPlantilla findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> findAll() {
        return null;
    }

    @Override
    public List<RutinaPlantilla> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<RutinaPlantilla> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    /*
        @Override
        public List<RutinaPlantilla> listarPorFiltroPT(Integer trainerId) {
            return repository.findByTrainerIdOrderByIdDesc(trainerId);
        }
    */
    @Override
    public String registrar(RutinaPlantilla entity, String wildcard) {
        return null;
    }

    @Override
    public String actualizar(RutinaPlantilla entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public String agregarRutinaPrediseñada(RutinaPlantillaDTO rutinaPlantillaDTO) throws CustomValidationException {

        String rutinaNombre = rutinaPlantillaDTO.getNombre();
        Integer categoriaId = rutinaPlantillaDTO.getCategoriaPlantilla();
        if (!repository.findNombreRutinaPlantExiste(rutinaNombre, categoriaId)) {

            RutinaPlantilla objRp = new RutinaPlantilla();
            //Pasando del Dto al objeto
            CategoriaPlantilla cP = new CategoriaPlantilla();
            cP = categoriaPlantillaService.findOne(categoriaId);
            BeanUtils.copyProperties(rutinaPlantillaDTO, objRp);

            Rutina objR = new Rutina();
            objRp.setCategoriaPlantilla(cP);

            objRp.setForest(3);

            RutinaPlantilla rutinaPlantilla = repository.save(objRp);

            List<SemanaPlantilla> semanas = new ArrayList<>();
            for (int i = 0; i < rutinaPlantilla.getTotalSemanas(); i++) {

                SemanaPlantilla semanaPlantilla = new SemanaPlantilla();
                //BeanUtils.copyProperties(semana, semanaPlantilla);
                //  semanas.add(semanaPlantilla);
                //Pasando el objeto de rutina a la semana para al momento de su registro, el ID que se genere(De la RutPlan)
                //se referencie en el registro de la semana
                semanaPlantilla.setFechaInicio(new Date());
                semanaPlantilla.setFechaFin(new Date());
                semanaPlantilla.setHoras(2);
                semanaPlantilla.setRutinaPlantilla(rutinaPlantilla);
                semanas.add(semanaPlantilla);
                //Insertando dias de la semana a su respectiva lista de dias
                List<DiaPlantilla> dias = new ArrayList<>();
                for (int j = 0; j < 7; j++) {
                    DiaPlantilla objDia = new DiaPlantilla();
                    objDia.setCalorias(2);
                    dias.add(objDia);
                    //Pasando el objeto de semana al dia para al momento de su registro, el ID que se genere(De la SemPlan)
                    //se referencie en el registro del día
                    objDia.setSemanaPlantilla(semanaPlantilla);
                }
                //Agregando la lista de dias a la semana
                semanaPlantilla.setLstDiaPlantilla(dias);

            }

            //Agregando las semanas a la instancia de rutina que hará que se inserten mediante cascade strategy
            // objRp.setLstSemana(semanas);
            rutinaPlantilla.setLstSemana(semanas);
            rutinaPlantilla.setFlagActivo(true);
            repository.saveAndFlush(rutinaPlantilla);
            //Guardando en session el id de la nueva rutina plantilla y los ids de las semanas generadas
    /*    session.setAttribute("rpId", objRp.getId());
        session.setAttribute("rpSemanaIds", semanas.stream().map(semana-> semana.getId()).toArray(Integer[]::new));
*/
            return REGISTRO.get();
        }
        else{
            throw new CustomValidationException(NOMBRE_RUTINA_PLANTILLA_REPETIDO.get(), EX_VALIDATION_FAILED.get());

        }
    }


    @Override
    public String actualizarRutinaPrediseñada(RutinaPlantillaDTO rutinaPlantillaDTO) {

        //Pasando del Dto al objeto
        RutinaPlantilla rutinaPlantilla = repository.findById(rutinaPlantillaDTO.getId()).orElse(null);
        rutinaPlantilla.setNombre(rutinaPlantillaDTO.getNombre());
        repository.saveAndFlush(rutinaPlantilla);

        return ACTUALIZACION.get();
    }

    @Override
    public List<RutinaPlantillaDTO> listarRutinasPredByCatId(Integer categoriaId) {

        return repository.findByCategoriaId(categoriaId);
    }

    @Override
        public void agregarRutinadesdePlantilla(RutinaPlantilla rutinaPlantilla,String fechaInicio, String fechaFin, Integer redFitID, Integer cliId, Integer tipoRutina) {

        RutinaPlantilla rPtemp = new RutinaPlantilla();

        BeanUtils.copyProperties(rutinaPlantilla, rPtemp);

    //    rPtemp.setId(99);

        Rutina rutina = new Rutina(rutinaPlantilla);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateInicio = null, dateFin = null;

        try {
            dateInicio = format.parse(fechaInicio);
            dateFin = format.parse(fechaFin);

        } catch (ParseException e) {
           e.printStackTrace();
        }

        rutina.setFechaInicio(dateInicio);
        rutina.setFechaFin(dateFin);
        rutina.setRedFitness(redFitID);
        rutina.setCliente(cliId);
        rutina.setTipoRutina(tipoRutina);

        Rutina nuevaRutina = rutinaService.save(rutina);

        List<SemanaPlantilla> sP = rutinaPlantilla.getLstSemana();
        List<Integer> semIdsList = new ArrayList<>();

        int indexSemana = 0;

        for(SemanaPlantilla semanaPlantilla: sP){

            Calendar calInicio = Calendar.getInstance();
            calInicio.setTime(dateInicio);
            calInicio.add(Calendar.DATE, 7* indexSemana);
            Date dateInicioSem = calInicio.getTime();

            Calendar calFin = Calendar.getInstance();
            calFin.setTime(dateInicioSem);
            calFin.add(Calendar.DATE, 6);
            Date dateFinSem   = calFin.getTime();

            semanaPlantilla.setFechaInicio(dateInicioSem);
            semanaPlantilla.setFechaFin(dateFinSem);

            Semana semana= new Semana(semanaPlantilla);
            semana.setRutina(nuevaRutina);

            List<DiaPlantilla> diaPlantillas = new ArrayList<>();

            diaPlantillas.addAll(semanaPlantilla.getLstDiaPlantilla());

            Semana sem = semanaService.save(semana);

            semIdsList.add(sem.getId());

                    int indexDia = 0;
                    for(DiaPlantilla diaPlantilla : diaPlantillas){

                        Calendar calDia = Calendar.getInstance();
                        calInicio.setTime(dateInicio);
                        calInicio.add(Calendar.DATE, indexDia + (indexSemana * 7));

                        Date dateDia= calInicio.getTime();

                        diaPlantilla.setFecha(dateDia);
                        Dia dia = new Dia(diaPlantilla);
                        dia.setSemana(sem);

                        diaService.save(dia);

                        indexDia++;
                    }

            indexSemana++;
            diaPlantillas.clear();
        }


            int[] arrSemIds = new int[semIdsList.size()];
              for(int i=0; i<arrSemIds.length;i++){
                 arrSemIds[i] = semIdsList.get(i);
                 }


        rutinaService.updateSemanaIds(rutina.getId() , arrSemIds);
        redFitnessService.updatePlanStatusAndUltimoDiaPlanificacionAndContadorRutinas(redFitID, 2, rutina.getFechaInicio(), rutina.getFechaFin(), 1);

    }


    @Override
    public void eliminarRutinaPlantilla(Integer id) {
        repository.deleteById(id);
    }
}
