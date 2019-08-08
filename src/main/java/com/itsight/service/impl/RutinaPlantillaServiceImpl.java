package com.itsight.service.impl;

import com.itsight.domain.CategoriaPlantilla;
import com.itsight.domain.Rutina;
import com.itsight.domain.RutinaPlantilla;
import com.itsight.domain.dto.RutinaPlantillaDTO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.RutinaPlantillaRepository;
import com.itsight.service.CategoriaPlantillaService;
import com.itsight.service.RutinaPlantillaService;
import com.itsight.service.RutinaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.ParseException;
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
    private EntityManager entityManager;

    @Autowired
    public RutinaPlantillaServiceImpl(RutinaPlantillaRepository repository, CategoriaPlantillaService categoriaPlantillaService, RutinaService rutinaService, EntityManager entityManager) {
        super(repository);
        this.categoriaPlantillaService = categoriaPlantillaService;
        this.rutinaService = rutinaService;
        this.entityManager = entityManager;
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


        RutinaPlantilla rutinaPlantilla = entityManager.createQuery(
                "select rp " +
                        "from RutinaPlantilla rp " +
                        "join fetch rp.lstSemana s " +
                        "join fetch s.lstDiaPlantilla " +
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
}
