package com.itsight.service.impl;

import com.itsight.domain.Producto;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.ProductoPresentacionRepository;
import com.itsight.repository.ProductoRepository;
import com.itsight.service.ProductoService;
import com.itsight.util.Enums;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoServiceImpl extends BaseServiceImpl<ProductoRepository> implements ProductoService {

    @Autowired
    private ProductoPresentacionRepository productoPresentacionRepository;

    public ProductoServiceImpl(ProductoRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Producto save(Producto entity) {
        // TODO Auto-generated method stub
        return repository.save(entity);
    }

    @Override
    public Producto update(Producto entity) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(entity);
    }

    @Override
    public Producto findOne(int id) {
        // TODO Auto-generated method stub
        return repository.findOne(new Integer(id));
    }

    @Override
    public Producto findOneWithFT(int id) {
        // TODO Auto-generated method stub
        return repository.findOneWithFT(id);
    }

    @Override
    public Producto findProductoById(int id) {
        // TODO Auto-generated method stub
        return repository.findProductoById(id);
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        repository.delete(new Integer(id));
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Producto> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public List<Producto> findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return repository.findAllByNombre(nombre);
    }

    @Override
    public List<Producto> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Producto> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Producto> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivo(flagActivo);
    }

    @Override
    public List<Producto> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagEliminado(flagEliminado);
    }

    @Override
    public List<Producto> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Producto> listarPorFiltro(String comodin, String estado, String fk) {
        // TODO Auto-generated method stub
        List<Producto> lstProducto = new ArrayList<Producto>();
        if (comodin.equals("0") && estado.equals("-1") && fk.equals("0")) {
            lstProducto = repository.findAll();
        } else {
            if (comodin.equals("0") && fk.equals("0")) {
                lstProducto = repository.findAllByFlagActivo(Boolean.valueOf(estado));
            } else {
                comodin = comodin.equals("0") ? "" : comodin;//Necesario para que la url de la request no viaje // y viaje /0/(otro parametro)

                lstProducto = repository.findAllByNombreContainingIgnoreCase(comodin);

                if (!estado.equals("-1")) {
                    lstProducto = lstProducto.stream()
                            .filter(x -> Boolean.valueOf(estado).equals(x.isFlagActivo()))
                            .collect(Collectors.toList());
                }

                if (!fk.equals("0")) {
                    lstProducto = lstProducto.stream()
                            .filter(x -> fk.equals(String.valueOf(x.getCategoria().getId())))
                            .collect(Collectors.toList());
                }
            }
        }

        return lstProducto;
    }

    @Override
    public String registrar(Producto producto, String categoriaId) {
        // TODO Auto-generated method stub
        producto.setCategoria(Integer.parseInt(categoriaId));
        producto.setCodigo("PRO-" + UUID.randomUUID());
        producto.addProductoPresentacion(producto.getProductoPresentacion());
        return Utilitarios.customResponse(Enums.ResponseCode.REGISTRO.get(), String.valueOf(repository.save(producto).getId()));
    }

    @Override
    public String actualizar(Producto producto, String categoriaId) {
        // TODO Auto-generated method stub
        Producto qProducto = repository.findOne(producto.getId());
        qProducto.setCategoria(Integer.parseInt(categoriaId));
        return Utilitarios.customResponse(Enums.ResponseCode.ACTUALIZACION.get(), String.valueOf(repository.saveAndFlush(qProducto).getId()));
    }

    @Override
    public void actualizarFlagActivoById(int id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }
}
