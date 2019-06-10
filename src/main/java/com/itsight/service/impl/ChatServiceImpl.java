package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.Chat;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ChatRepository;
import com.itsight.service.ChatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.itsight.util.Enums.ResponseCode.ACTUALIZACION;
import static com.itsight.util.Enums.ResponseCode.REGISTRO;

@Transactional
@Service
public class ChatServiceImpl extends BaseServiceImpl<ChatRepository> implements ChatService {

    public ChatServiceImpl(ChatRepository repository) {
        super(repository);
    }

    @Override
    public Chat save(Chat entity) {
        return repository.save(entity);
    }

    @Override
    public Chat update(Chat entity) {
        return repository.save(entity);
    }

    @Override
    public Chat findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Chat findOneWithFT(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Chat> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Chat> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<Chat> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<Chat> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<Chat> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<Chat> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<Chat> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Chat> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(Chat entity, String wildcard) throws CustomValidationException {
        repository.save(entity);
        return REGISTRO.get();
    }

    @Override
    public String actualizar(Chat entity, String wildcard) {
        repository.save(entity);
        return ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }
}
