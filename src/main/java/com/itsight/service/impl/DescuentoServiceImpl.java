package com.itsight.service.impl;

import com.itsight.domain.Descuento;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.DescuentoRepository;
import com.itsight.service.DescuentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.itsight.util.Enums.ResponseCode.ACTUALIZACION;
import static com.itsight.util.Enums.ResponseCode.REGISTRO;
import static com.itsight.util.Utilitarios.customResponse;

@Service
@Transactional
public class DescuentoServiceImpl extends BaseServiceImpl<DescuentoRepository> implements DescuentoService {

    public DescuentoServiceImpl(DescuentoRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Descuento save(Descuento entity) {
        // TODO Auto-generated method stub
        return repository.save(entity);
    }

    @Override
    public Descuento update(Descuento entity) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(entity);
    }

    @Override
    public Descuento findOne(Integer id) {
        // TODO Auto-generated method stub
        return repository.findOne(id);
    }

    @Override
    public Descuento findOneWithFT(Integer id) {
        // TODO Auto-generated method stub
        return repository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.delete(id);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Descuento> listarPorProductoId(Integer productoId) {
        // TODO Auto-generated method stub
        return repository.findByProductoPresentacionId(productoId);
    }

    @Override
    public String registrar(Descuento entity, String wildcard) {
        // TODO Auto-generated method stub
        repository.save(entity);
        return customResponse(REGISTRO.get(), String.valueOf(entity.getId()));
    }

    @Override
    public String actualizar(Descuento entity, String wildcard) {
        // TODO Auto-generated method stub
        String[] fkIds = wildcard.split(",");
        Descuento qDescuento = repository.findOne(entity.getId());
        qDescuento.setTipoDescuento(Integer.parseInt(fkIds[0]));
        qDescuento.setProductoPresentacion(Integer.parseInt(fkIds[1]));
        qDescuento.setPrecioInicial(entity.getPrecioInicial());
        qDescuento.setDscto(entity.getDscto());
        qDescuento.setPrecioFinal(entity.getPrecioFinal());
        qDescuento.setFechaInicio(entity.getFechaInicio());
        qDescuento.setFechaFin(entity.getFechaFin());
        repository.saveAndFlush(qDescuento);
        return customResponse(ACTUALIZACION.get(), String.valueOf(qDescuento.getId()));
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

}
