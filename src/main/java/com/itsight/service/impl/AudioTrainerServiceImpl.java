package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.AudioTrainer;
import com.itsight.domain.pojo.AwsStresPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.AudioTrainerRepository;
import com.itsight.service.AudioTrainerService;
import com.itsight.util.Enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.itsight.util.Enums.Msg.*;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;

@Service
@Transactional
public class AudioTrainerServiceImpl extends BaseServiceImpl<AudioTrainerRepository> implements AudioTrainerService {

    public static final Logger LOGGER = LogManager.getLogger(AudioTrainerServiceImpl.class);

    public AudioTrainerServiceImpl(AudioTrainerRepository repository) {
        super(repository);
    }

    @Override
    public AudioTrainer save(AudioTrainer entity) {
        return repository.save(entity);
    }

    @Override
    public AudioTrainer update(AudioTrainer entity) {
        return repository.save(entity);
    }

    @Override
    public AudioTrainer findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public AudioTrainer findOneWithFT(Integer id) {
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
    public List<AudioTrainer> findAll() {
        return repository.findAll();
    }

    @Override
    public List<AudioTrainer> findByNombre(String nombre) {
        return null;
    }

    @Override
    public List<AudioTrainer> findByNombreContainingIgnoreCase(String nombre) {
        return null;
    }

    @Override
    public List<AudioTrainer> findByDescripcionContainingIgnoreCase(String descripcion) {
        return null;
    }

    @Override
    public List<AudioTrainer> findByFlagActivo(boolean flagActivo) {
        return null;
    }

    @Override
    public List<AudioTrainer> findByFlagEliminado(boolean flagEliminado) {
        return null;
    }

    @Override
    public List<AudioTrainer> findByIdsIn(List<Integer> ids) {
        return null;
    }

    @Override
    public List<AudioTrainer> listarPorFiltro(String comodin, String estado, String fk) {
        return null;
    }

    @Override
    public String registrar(AudioTrainer entity, String wildcard) throws CustomValidationException {
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(AudioTrainer entity, String wildcard) {
        repository.save(entity);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {

    }

    @Override
    public String subirFile(MultipartFile file, Integer id, String uuid, String extension) throws CustomValidationException {
        boolean success = uploadImageToAws3(file, new AwsStresPOJO(aws3accessKey, aws3secretKey, aws3region, aws3RoutineBucket, "audio/"+id+"/", uuid, extension), LOGGER);
        if(success){
            return SUCCESS_SUBIDA_IMG.get();
        }
        throw new CustomValidationException(FAIL_SUBIDA_IMG_PERFIL.get(), EX_VALIDATION_FAILED.get());
    }
}
