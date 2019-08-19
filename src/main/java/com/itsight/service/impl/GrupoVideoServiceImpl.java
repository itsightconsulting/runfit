package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.GrupoVideo;
import com.itsight.domain.dto.RefUpload;
import com.itsight.domain.pojo.AwsStresPOJO;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.GrupoVideoRepository;
import com.itsight.service.GrupoVideoService;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.itsight.util.Enums.Msg.*;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;

@Service
@Transactional
public class GrupoVideoServiceImpl extends BaseServiceImpl<GrupoVideoRepository> implements GrupoVideoService {

    public static final Logger LOGGER = LogManager.getLogger(TrainerServiceImpl.class);

    public GrupoVideoServiceImpl(GrupoVideoRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<GrupoVideo> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public GrupoVideo save(GrupoVideo tipoVideo) {
        // TODO Auto-generated method stub
        return repository.save(tipoVideo);
    }

    @Override
    public GrupoVideo update(GrupoVideo tipoVideo) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(tipoVideo);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    public GrupoVideo findOne(Integer id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<GrupoVideo> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivo(flagActivo);
    }

    @Override
    public List<GrupoVideo> findByNombre(String nombres) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombres);
    }

    @Override
    public GrupoVideo findOneWithFT(Integer id) {
        // TODO Auto-generated method stub
        return repository.getById(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<GrupoVideo> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<GrupoVideo> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<GrupoVideo> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<GrupoVideo> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<GrupoVideo> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        List<GrupoVideo> lstGrupoVideo;
        if (comodin.equals("0") && estado.equals("-1")) {
            lstGrupoVideo = repository.findAll();
        } else {
            if (comodin.equals("0")) lstGrupoVideo = repository.findAllByFlagActivo(Boolean.valueOf(estado.equals("1") ? "true" : "false"));
            else {
                lstGrupoVideo = !estado.equals("-1") ? repository.findAllByNombreContainingIgnoreCaseAndFlagActivo(comodin, Boolean.valueOf(estado)) : repository.findAllByNombreContainingIgnoreCase(comodin);
            }
        }
        return lstGrupoVideo;
    }

    @Override
    public String registrar(GrupoVideo entity, String wildcard) {
        // TODO Auto-generated method stub
        entity.setForest(2);//Padre-artificio|Valor final
        entity.setUuid(UUID.randomUUID());
        return Utilitarios.customResponse(Enums.ResponseCode.REGISTRO.get(), String.valueOf(repository.save(entity).getId()));
    }

    @Override
    public RefUpload registrarConSubida(GrupoVideo entity) {
        entity.setForest(2);//Padre-artificio|Valor final
        //Completamos el objeto para la subida
        RefUpload fileUpload = new RefUpload();
        fileUpload.setExtFile(entity.getExtImg());
        entity.setUuid(fileUpload.getUuid());
        entity.setExtImg(fileUpload.getExtFile());
        entity.setRutaWeb(fileUpload.getUuid()+fileUpload.getExtFile());
        //Guardamos
        GrupoVideo g = repository.save(entity);
        fileUpload.setId(g.getId());
        return fileUpload;
    }

    @Override
    public String actualizar(GrupoVideo entity, String wildcard) throws CustomValidationException {
        // TODO Auto-generated method stub
        entity.setForest(2);//Padre-artificio|Valor final
        GrupoVideo qGrupoVideo = repository.getById(entity.getId());
        //Revisando si esta cambiando el flagActivo y bloqueandolo en caso tenga records dependientes
        if(qGrupoVideo.isFlagActivo()!=entity.isFlagActivo()){
            if(!entity.isFlagActivo()){
                boolean hasChildren = checkHaveChildrenById(entity.getId());
                if(hasChildren){
                    throw new CustomValidationException(FLAG_BLOQUEADO_TIENE_DEPS.get(), EX_VALIDATION_FAILED.get());
                }
            }
        }
        entity.setRutaWeb(qGrupoVideo.getRutaWeb());
        entity.setUuid(qGrupoVideo.getUuid());
        repository.saveAndFlush(entity);
        return Utilitarios.jsonResponse(String.valueOf(entity.getId()), qGrupoVideo.getUuid().toString());
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

    @Override
    public List<GrupoVideo> encontrarGrupoConSusDepedencias() {
        return repository.findDistinctByOrderById();
    }

    @Override
    public String subirFile(MultipartFile file, Integer id, String uuid, String extension) throws CustomValidationException {
        boolean success = uploadImageToAws3(file, new AwsStresPOJO(aws3accessKey, aws3secretKey, aws3region, aws3RoutineBucket, "grupo-video/"+id+"/", uuid, extension), LOGGER);
        if(success){
            return SUCCESS_SUBIDA_IMG.get();
        }
        throw new CustomValidationException(FAIL_SUBIDA_IMG_GENERICA.get(), EX_VALIDATION_FAILED.get());
    }

    @Override
    public boolean checkHaveChildrenById(int id) {
        return repository.checkHaveChildrenById(id);
    }
}
