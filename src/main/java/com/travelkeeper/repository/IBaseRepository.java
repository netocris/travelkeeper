package com.travelkeeper.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Base repository service
 *
 * @param <T>
 * @param <ID>
 *
 * Created by netocris on 24/08/2018
 */
@NoRepositoryBean
public interface IBaseRepository<T, ID extends Serializable> extends ElasticsearchRepository<T, ID> {
}
