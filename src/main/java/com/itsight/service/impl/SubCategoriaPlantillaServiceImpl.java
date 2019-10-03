package com.itsight.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsight.advice.CustomValidationException;
import com.itsight.domain.CategoriaPlantilla;
import com.itsight.domain.RutinaPlantilla;
import com.itsight.domain.SubCategoriaPlantilla;
import com.itsight.domain.Trainer;
import com.itsight.domain.dto.CategoriaPlantillaDTO;
import com.itsight.domain.dto.SubCategoriaPlantillaDTO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.SubCategoriaPlantillaRepository;
import com.itsight.service.CategoriaPlantillaService;
import com.itsight.service.CategoriaService;
import com.itsight.service.SubCategoriaPlantillaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.itsight.util.Enums.Msg.NOMBRE_SUB_CATEGORIA_PLANTILLA_REPETIDA;
import static com.itsight.util.Enums.ResponseCode.*;

@Service
@Transactional
public class SubCategoriaPlantillaServiceImpl extends BaseServiceImpl<SubCategoriaPlantillaRepository> implements SubCategoriaPlantillaService {

    CategoriaPlantillaService categoriaPlantillaService;

    public SubCategoriaPlantillaServiceImpl(SubCategoriaPlantillaRepository repository, CategoriaPlantillaService categoriaPlantillaService) {
        super(repository);
        this.categoriaPlantillaService = categoriaPlantillaService;
    }

    @Override
    public String agregarSubCategoriaPlantilla(SubCategoriaPlantilla subCategoriaPlantilla, Integer catPlantillaId) throws CustomValidationException {

        String nombreSubCat= subCategoriaPlantilla.getNombre();

        if(!repository.findNombreSubCategPlantExiste(nombreSubCat, catPlantillaId)) {
            CategoriaPlantilla catPlantilla = new CategoriaPlantilla();
            catPlantilla = categoriaPlantillaService.findOne(catPlantillaId);
            subCategoriaPlantilla.setCategoriaPlantilla(catPlantilla);
            repository.save(subCategoriaPlantilla);
            return REGISTRO.get();
        }else{
            throw new CustomValidationException(NOMBRE_SUB_CATEGORIA_PLANTILLA_REPETIDA.get(), EX_VALIDATION_FAILED.get());
        }

    }

    @Override
    public String actualizarSubCategoriaPlantilla(SubCategoriaPlantilla subCategoriaPlantilla) throws JsonProcessingException {

        SubCategoriaPlantilla subCP = repository.findById(subCategoriaPlantilla.getId()).orElse(null);
        subCP.setNombre(subCategoriaPlantilla.getNombre());
        repository.saveAndFlush(subCP);

        return ACTUALIZACION.get();
    }

    @Override
    public List<SubCategoriaPlantillaDTO> obtenerSubCategoriasbyCategoriaId(Integer categoriaId) {

        List<SubCategoriaPlantillaDTO> lstSubCatPlantilla;
        lstSubCatPlantilla = repository.findSubCategoriasByCategoriaId(categoriaId);

        return lstSubCatPlantilla;
    }

    @Override
    public String actualizarFlagFavorito(SubCategoriaPlantilla subCategoriaPlantilla) throws JsonProcessingException {

        SubCategoriaPlantilla subCP = repository.findById(subCategoriaPlantilla.getId()).orElse(null);
        subCP.setFavorito(!subCP.getFavorito());
        repository.saveAndFlush(subCP);
        return ACTUALIZACION.get();
    }

    @Override
    public void eliminarSubCategoriaPlantilla(Integer subCatId) {

        repository.deleteById(subCatId);
    }

    @Override
    public SubCategoriaPlantilla save(SubCategoriaPlantilla entity) {
        return null;
    }

    @Override
    public SubCategoriaPlantilla update(SubCategoriaPlantilla entity) {
        return null;
    }

    @Override
    public SubCategoriaPlantilla findOne(Integer id) {
        return null;
    }

    @Override
    public SubCategoriaPlantilla findOneWithFT(Integer id) {
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
    public List<SubCategoriaPlantilla> findAll() {
        return null;
    }

    @Override
    public List<SubCategoriaPlantilla> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<SubCategoriaPlantilla> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<SubCategoriaPlantilla> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<SubCategoriaPlantilla> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<SubCategoriaPlantilla> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<SubCategoriaPlantilla> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<SubCategoriaPlantilla> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(SubCategoriaPlantilla entity, String wildcard) throws CustomValidationException {
        return null;
    }

    @Override
    public String actualizar(SubCategoriaPlantilla entity, String wildcard) throws CustomValidationException {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
