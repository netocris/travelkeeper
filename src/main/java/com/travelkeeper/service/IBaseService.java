package com.travelkeeper.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * Base service
 *
 * @param <T>
 * @param <ID>
 *
 * Created by netocris on 24/08/2018
 */
public interface IBaseService<T, ID extends Serializable> extends Serializable {

    /**
     *
     * @return
     */
    Page<T> getAll();

    /**
     *
     * @return
     */
    List<Long> getAllIds();

    /**
     *
     * @param id
     * @return
     */
    T getById(final ID id);

    /**
     *
     * @param entity
     */
    void save(final T entity);

    /**
     *
     * @param id
     */
    void delete(final ID id);

}
