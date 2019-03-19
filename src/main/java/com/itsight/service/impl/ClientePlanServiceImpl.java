package com.itsight.service.impl;

import com.itsight.domain.ClientePlan;
import com.itsight.repository.ClientePlanRepository;
import com.itsight.service.ClientePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientePlanServiceImpl implements ClientePlanService {

    private ClientePlanRepository clientePlanRepository;

    @Autowired
    public ClientePlanServiceImpl(ClientePlanRepository clientePlanRepository) {
        this.clientePlanRepository = clientePlanRepository;
    }
 
    @Override
    public List<ClientePlan> listAll() {
        // TODO Auto-generated method stub
        return clientePlanRepository.findAll();
    }

    @Override
    public ClientePlan add(ClientePlan clientePlan) {
        // TODO Auto-generated method stub
        return clientePlanRepository.save(clientePlan);
    }

    @Override
    public ClientePlan update(ClientePlan clientePlan) {
        // TODO Auto-generated method stub
        return clientePlanRepository.saveAndFlush(clientePlan);
    }

    @Override
    public void delete(Integer clientePlanId) {
        // TODO Auto-generated method stub
        clientePlanRepository.delete(clientePlanId);
    }

    @Override
    public ClientePlan findOneById(Integer id) {
        // TODO Auto-generated method stub
        return clientePlanRepository.findOne(id);
    }


}
