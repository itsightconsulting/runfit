package com.itsight.service.impl;

import com.itsight.advice.CustomValidationException;
import com.itsight.domain.CategoriaVideo;
import com.itsight.domain.SubCategoriaVideo;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.SubCategoriaVideoRepository;
import com.itsight.service.SubCategoriaVideoService;
import com.itsight.util.Enums;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.itsight.util.Enums.Msg.FLAG_BLOQUEADO_TIENE_DEPS;
import static com.itsight.util.Enums.ResponseCode.EX_VALIDATION_FAILED;

@Service
@Transactional
public class SubCategoriaVideoServiceImpl extends BaseServiceImpl<SubCategoriaVideoRepository> implements SubCategoriaVideoService {

    public SubCategoriaVideoServiceImpl(SubCategoriaVideoRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<SubCategoriaVideo> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public List<SubCategoriaVideo> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivoOrderById(flagActivo);
    }

    @Override
    public SubCategoriaVideo findOne(Integer id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    public SubCategoriaVideo save(SubCategoriaVideo subTipoVideo) {
        // TODO Auto-generated method stub
        return repository.save(subTipoVideo);
    }

    @Override
    public SubCategoriaVideo update(SubCategoriaVideo subTipoVideo) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(subTipoVideo);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.deleteById(new Integer(id));
    }

    @Override
    public SubCategoriaVideo findOneWithFT(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaVideo> findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaVideo> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<SubCategoriaVideo> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaVideo> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaVideo> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SubCategoriaVideo> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        List<SubCategoriaVideo> lstSubCategoriaVideo;
        String categoria = fk.equals("0") ? null : fk;
        comodin = comodin.equals("0") ? null : comodin;
        estado = estado.equals("-1") ? null : estado;

        if (comodin == null && estado == null && categoria == null)
            lstSubCategoriaVideo = repository.findAllByOrderById();
        else if (estado == null && categoria == null)
            lstSubCategoriaVideo = repository.findAllByNombreContainingIgnoreCase(comodin);
        else if (comodin == null && categoria == null)
            lstSubCategoriaVideo = repository.findAllByFlagActivoOrderById(Boolean.parseBoolean(estado));
        else if (comodin == null && estado == null)
            lstSubCategoriaVideo = repository.findAllByCategoriaVideoId(Integer.parseInt(categoria));
        else if (comodin == null)
            lstSubCategoriaVideo = repository.findAllByCategoriaVideoIdAndFlagActivoOrderById(Integer.parseInt(categoria), Boolean.valueOf(estado));
        else if (categoria == null)
            lstSubCategoriaVideo = repository.findAllByNombreContainingIgnoreCaseAndFlagActivoOrderById(comodin, Boolean.valueOf(estado));
        else if (estado == null)
            lstSubCategoriaVideo = repository.findAllByCategoriaVideoIdAndNombreContainingIgnoreCaseOrderById(Integer.parseInt(categoria), comodin);
        else
            lstSubCategoriaVideo = repository.findAllByCategoriaVideoIdAndNombreContainingIgnoreCaseAndFlagActivoOrderById(Integer.parseInt(categoria), comodin, Boolean.valueOf(estado));
        return lstSubCategoriaVideo;
    }

    @Override
    public String registrar(SubCategoriaVideo entity, String wildcard) {
        // TODO Auto-generated method stub
        repository.save(entity);
        return Enums.ResponseCode.REGISTRO.get();
    }

    @Override
    public String actualizar(SubCategoriaVideo entity, String wildcard) throws CustomValidationException {
        SubCategoriaVideo qSubCategoriaVideo = repository.getOne(entity.getId());
        //Revisando si esta cambiando el flagActivo y bloqueandolo en caso tenga records dependientes
        if(qSubCategoriaVideo.isFlagActivo()!=entity.isFlagActivo()){
            if(!entity.isFlagActivo()){
                boolean hasChildren = checkHaveChildrenById(entity.getId());
                if(hasChildren){
                    throw new CustomValidationException(FLAG_BLOQUEADO_TIENE_DEPS.get(), EX_VALIDATION_FAILED.get());
                }
            }
        }
        repository.saveAndFlush(entity);
        return Enums.ResponseCode.ACTUALIZACION.get();
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

    @Override
    public List<SubCategoriaVideo> listarPorCategoria(Integer categoriaVideoId) {
        return repository.findByCategoriaId(categoriaVideoId);
    }

    @Override
    public boolean checkHaveChildrenById(Integer id) {
        return repository.checkHaveChildrenById(id);
    }

}
