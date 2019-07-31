package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.TipoCanalVenta;
import com.itsight.domain.pojo.AwsStresPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.TipoCanalVentaRepository;
import com.itsight.service.TipoCanalVentaService;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class TipoCanalVentaServiceImpl extends BaseServiceImpl<TipoCanalVentaRepository> implements TipoCanalVentaService {

    public TipoCanalVentaServiceImpl(TipoCanalVentaRepository repository) {
        super(repository);
    }

    @Override
    public TipoCanalVenta save(TipoCanalVenta entity) {
        return repository.save(entity);
    }

    @Override
    public TipoCanalVenta update(TipoCanalVenta entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public TipoCanalVenta findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public TipoCanalVenta findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<TipoCanalVenta> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<TipoCanalVenta> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<TipoCanalVenta> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<TipoCanalVenta> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<TipoCanalVenta> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<TipoCanalVenta> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<TipoCanalVenta> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<TipoCanalVenta> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(TipoCanalVenta entity, String wildcard) throws CustomValidationException {
        return null;
    }

    @Override
    public String actualizar(TipoCanalVenta entity, String wildcard) {
        return null;
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public String subirFile(MultipartFile file, Integer id, String uuid, String extension) throws CustomValidationException {
        return null;
    }

    @Override
    public String subirFiles(MultipartFile[] files, Integer id, String uuid, String extension) throws CustomValidationException {
        return null;
    }

    @Override
    public boolean uploadImageToAws3(MultipartFile file, AwsStresPOJO credentials, Logger logger) {
        return false;
    }

    @Override
    public boolean uploadMultipleToAws3(MultipartFile[] files, AwsStresPOJO credentials, Logger logger) {
        return false;
    }
}


