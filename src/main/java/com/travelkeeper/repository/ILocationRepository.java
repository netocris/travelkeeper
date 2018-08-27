package com.travelkeeper.repository;

import com.travelkeeper.domain.Location;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Base repository service
 *
 * Created by netocris on 27/08/2018
 */
@Repository
public interface ILocationRepository extends IBaseRepository<Location, Long> {
}
