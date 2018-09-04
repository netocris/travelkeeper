package com.travelkeeper.repository;

import com.travelkeeper.domain.Location;
import org.springframework.stereotype.Repository;

/**
 * Location repository service
 *
 * Created by netocris on 27/08/2018
 */
@Repository
public interface ILocationRepository extends IBaseRepository<Location, Long> {
}
