package com.itsight.service.impl;

import com.itsight.domain.Objetivo;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ObjetivoRepository;
import com.itsight.service.ObjetivoService;
import com.itsight.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ObjetivoServiceImpl extends BaseServiceImpl<ObjetivoRepository> implements ObjetivoService {

    @Autowired
    public ObjetivoServiceImpl(ObjetivoRepository repository) {
        super(repository);
    }

    @Override
    public List<Objetivo> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public Objetivo save(Objetivo objetivo) {
        // TODO Auto-generated method stub
        return repository.save(objetivo);
    }

    @Override
    public Objetivo update(Objetivo objetivo) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(objetivo);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.delete(id);
    }

    @Override
    public Objetivo findOne(Integer id) {
        // TODO Auto-generated method stub
        return repository.findOne(id);
    }

    @Override
    public List<Objetivo> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivo(flagActivo);
    }

    @Override
    public List<Objetivo> findByNombre(String nombres) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombres);
    }

    @Override
    public Objetivo findOneWithFT(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Objetivo> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Objetivo> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Objetivo> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Objetivo> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Objetivo> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        List<Objetivo> lstObjetivo;
        if (comodin.equals("0") && estado.equals("-1")) {
            lstObjetivo = repository.findAll();
        } else {
            if (comodin.equals("0")) {
                lstObjetivo = repository.findAllByFlagActivo(Boolean.valueOf(estado));
            } else {

                lstObjetivo = repository.findAllByNombreContainingIgnoreCase(comodin);

                if (estado.equals("-1")) {
                } else {
                    lstObjetivo = lstObjetivo.stream()
                            .filter(x -> Boolean.valueOf(estado).equals(x.isFlagActivo()))
                            .collect(Collectors.toList());
                }
            }
        }

        return lstObjetivo;
    }

    @Override
    public String registrar(Objetivo entity, String wildcard) {
        // TODO Auto-generated method stub
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(Objetivo entity, String wildcard) {
        // TODO Auto-generated method stub
        repository.saveAndFlush(entity);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }
}
