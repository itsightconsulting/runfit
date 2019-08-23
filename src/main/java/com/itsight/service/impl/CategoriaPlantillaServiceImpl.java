package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.CategoriaPlantilla;
import com.itsight.domain.Trainer;
import com.itsight.domain.dto.CategoriaPlantillaDTO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.CategoriaPlantillaRepository;
import com.itsight.service.CategoriaPlantillaService;
import com.itsight.service.TrainerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.itsight.util.Enums.ResponseCode.*;

@Service
@Transactional
public class CategoriaPlantillaServiceImpl extends BaseServiceImpl<CategoriaPlantillaRepository> implements CategoriaPlantillaService {

    private HttpSession session;
    private TrainerService trainerService;


    public CategoriaPlantillaServiceImpl(CategoriaPlantillaRepository repository, HttpSession session, TrainerService trainerService) {
        super(repository);
        this.session = session;
        this.trainerService = trainerService;
    }

    @Override
    public CategoriaPlantilla save(CategoriaPlantilla categoriaPlantilla) {
        return repository.save(categoriaPlantilla);
    }

    @Override
    public String agregarCategoriaPlantilla(CategoriaPlantilla categoriaPlantilla) {

        Integer trainerId = (Integer) session.getAttribute("id");
        Trainer trainer = new Trainer();
        trainer = trainerService.findOne(trainerId);
        categoriaPlantilla.setTrainer(trainer);
        repository.save(categoriaPlantilla);
        return REGISTRO.get();
    }
    @Override
    public String actualizarCategoriaPlantilla(CategoriaPlantilla categoriaPlantilla) {

        CategoriaPlantilla currentCP = repository.findById(categoriaPlantilla.getId()).orElse(null);

        currentCP.setNombre(categoriaPlantilla.getNombre());
        currentCP.setTipo(categoriaPlantilla.getTipo());
        repository.saveAndFlush(currentCP);

        return ACTUALIZACION.get();
    }

    @Override
    public String actualizarFlagFavorito(CategoriaPlantilla categoriaPlantilla) {

        CategoriaPlantilla currentCP = repository.findById(categoriaPlantilla.getId()).orElse(null);
        currentCP.setFavorito(!currentCP.getFavorito());

        repository.saveAndFlush(currentCP);
        return ACTUALIZACION.get();
    }

    @Override
    public List<CategoriaPlantillaDTO> obtenerCategoriasbyTrainerId() {

        Integer trainerId = (Integer) session.getAttribute("id");
        List<CategoriaPlantillaDTO> lstCatPlantilla;

         lstCatPlantilla = repository.findCategoriasByTrainerId(trainerId);

        return lstCatPlantilla;
    }

    @Override
    public List<CategoriaPlantillaDTO> obtenerCategoriasbyTrainerIdAndTipo(int tipoRutina) {

        Integer trainerId = (Integer) session.getAttribute("id");
        List<CategoriaPlantillaDTO> lstCatPlantilla;

        lstCatPlantilla = repository.findCategoriasByTrainerIdAndTipo(trainerId, tipoRutina);

        return lstCatPlantilla;

        }

    @Override
    public CategoriaPlantilla update(CategoriaPlantilla entity) {
        return null;
    }

    @Override
    public CategoriaPlantilla findOne(Integer id) {

        return repository.findById(id).orElse(null);
    }

    @Override
    public CategoriaPlantilla findOneWithFT(Integer id) {
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
    public List<CategoriaPlantilla> findAll() {
        return null;
    }

    @Override
    public List<CategoriaPlantilla> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<CategoriaPlantilla> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<CategoriaPlantilla> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<CategoriaPlantilla> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<CategoriaPlantilla> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<CategoriaPlantilla> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<CategoriaPlantilla> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(CategoriaPlantilla entity, String wildcard) throws CustomValidationException {
        return null;
    }

    @Override
    public String actualizar(CategoriaPlantilla entity, String wildcard) throws CustomValidationException {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public void eliminarCategoriaPlantilla(Integer catId) {

        repository.deleteById(catId);
    }
}
