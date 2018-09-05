package com.travelkeeper.repository;

import com.travelkeeper.domain.Restaurant;
import org.springframework.stereotype.Repository;

/**
 * Restaurant repository service
 *
 * Created by netocris on 04/09/2018
 */
@Repository
public interface IRestaurantRepository extends IBaseRepository<Restaurant, Long> {
}
