package com.itsight.service.impl;

import com.itsight.domain.Paquete;
import com.itsight.repository.PaqueteRepository;
import com.itsight.service.PaqueteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaqueteServiceImpl implements PaqueteService {

    private PaqueteRepository paqueteRepository;

    @Autowired
    public PaqueteServiceImpl(PaqueteRepository paqueteRepository) {
        this.paqueteRepository = paqueteRepository;
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<Paquete> listAll() {
        // TODO Auto-generated method stub
        return paqueteRepository.findAll();
    }

    @Override
    public List<Paquete> findAllByFlagActivo(Boolean flagActivo) {
        // TODO Auto-generated method stub
        return paqueteRepository.findAllByFlagActivo(flagActivo);
    }

    @Override
    public Paquete getPaqueteById(int paqueteId) {
        // TODO Auto-generated method stub
        return paqueteRepository.findOne(new Integer(paqueteId));
    }

    @Override
    public Paquete findRouteNamesById(int paqueteId) {
        // TODO Auto-generated method stub
        return paqueteRepository.findRouteNamesById(paqueteId);
    }

    @Override
    public Paquete add(Paquete paquete) {
        // TODO Auto-generated method stub
        return paqueteRepository.save(paquete);
    }

    @Override
    public Paquete update(Paquete paquete) {
        // TODO Auto-generated method stub
        return paqueteRepository.saveAndFlush(paquete);
    }

    @Override
    public void delete(int paqueteId) {
        // TODO Auto-generated method stub
        paqueteRepository.delete(new Integer(paqueteId));
    }

    @Override
    public List<Paquete> findAllByNombreContainingOrDescripcionContaining(String nombre, String descripcion) {
        // TODO Auto-generated method stub
        return paqueteRepository.findAllByNombreContainingOrDescripcionContaining(nombre, descripcion);
    }


}
