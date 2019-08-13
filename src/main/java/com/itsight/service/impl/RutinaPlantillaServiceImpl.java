package com.itsight.service.impl;

import com.itsight.domain.*;
import com.itsight.domain.dto.RutinaPlantillaDTO;
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

import static com.itsight.util.Enums.ResponseCode.ACTUALIZACION;
import static com.itsight.util.Enums.ResponseCode.REGISTRO;

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
    public String agregarRutinaPrediseñada(RutinaPlantillaDTO rutinaPlantillaDTO) {

        RutinaPlantilla objRp = new RutinaPlantilla();
        //Pasando del Dto al objeto
        Integer categoriaId = rutinaPlantillaDTO.getCategoriaPlantilla();
        CategoriaPlantilla cP = new CategoriaPlantilla();
        cP = categoriaPlantillaService.findOne(categoriaId);
        BeanUtils.copyProperties(rutinaPlantillaDTO, objRp);

        Rutina objR = new Rutina();

     /*   BeanUtils.copyProperties(objRp, objR);

        rutinaService.save(objR);
     */

/*

        RutinaPlantilla rutinaPlantilla = entityManager.createQuery(
                "select rp " +
                        "from RutinaPlantilla rp " +
                        "join fetch rp.lstSemana s " +
                        "where rp.id = :id", RutinaPlantilla.class)
                .setParameter(
                        "id",
                        7
                )
                .getSingleResult();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse("2019-08-30");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Rutina rutinaPlantillaClone = new Rutina(rutinaPlantilla);
        rutinaPlantillaClone.setFechaInicio(new Date());
        rutinaPlantillaClone.setFechaFin(date);
        entityManager.persist(rutinaPlantillaClone);

*/

        objRp.setCategoriaPlantilla(cP);

        repository.save(objRp);

        return REGISTRO.get();
    }

    @Override
    public String actualizarRutinaPrediseñada(RutinaPlantillaDTO rutinaPlantillaDTO) {

        RutinaPlantilla objRp = new RutinaPlantilla();
        //Pasando del Dto al objeto

        Integer categoriaId = rutinaPlantillaDTO.getCategoriaPlantilla();
        CategoriaPlantilla cP = new CategoriaPlantilla();
        cP = categoriaPlantillaService.findOne(categoriaId);

        BeanUtils.copyProperties(rutinaPlantillaDTO, objRp);

        objRp.setCategoriaPlantilla(cP);
      //  objRp.setLstSemana(null);

        repository.saveAndFlush(objRp);

        return ACTUALIZACION.get();
    }

    @Override
    public List<RutinaPlantillaDTO> listarRutinasPredByCatId(Integer categoriaId) {

        return repository.findByCategoriaId(categoriaId);
    }

    @Override
        public void agregarRutinadesdePlantilla(RutinaPlantilla rutinaPlantilla,String fechaInicio, String fechaFin, Integer redFitID, Integer cliId, Integer tipoRutina) {

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

      List<DiaPlantilla> diaPlantillas = new ArrayList<>();

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

              diaPlantillas.addAll(semanaPlantilla.getLstDiaPlantilla());

              Semana sem = new Semana();
              sem = semanaService.save(semana);

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
          };

        int[] arrSemIds = new int[semIdsList.size()];
        for(int i=0; i<arrSemIds.length;i++){
            arrSemIds[i] = semIdsList.get(i);
        }
        rutinaService.updateSemanaIds(rutina.getId() , arrSemIds);
        redFitnessService.updatePlanStatusAndUltimoDiaPlanificacionAndContadorRutinas(redFitID, 2, rutina.getFechaInicio(), rutina.getFechaFin(), 1);
    }
}
