package com.itsight.generic;

import java.util.List;

public interface BaseService<T> {

    T save(T entity);

    T update(T entity);

    T findOne(int id);

    T findOneWithFT(int id);

    void delete(int id);

    List<Integer> findIdsByFlagActivo(boolean flagActivo);

    List<T> findAll();

    List<T> findByNombre(String nombre);

    List<T> findByNombreContainingIgnoreCase(String nombre);

    List<T> findByDescripcionContainingIgnoreCase(String descripcion);

    List<T> findByFlagActivo(boolean flagActivo);

    List<T> findByFlagEliminado(boolean flagEliminado);

    List<T> findByIdsIn(List<Integer> ids);

    List<T> listarPorFiltro(String comodin, String estado, String fk);

    String registrar(T entity, String wildcard);

    String actualizar(T entity, String wildcard);

    void actualizarFlagActivoById(int id, boolean flagActivo);

}
