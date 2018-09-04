package com.travelkeeper.repository;

import com.travelkeeper.domain.Dish;
import org.springframework.stereotype.Repository;

/**
 * Dish repository service
 *
 * Created by netocris on 04/09/2018
 */
@Repository
public interface IDishRepository extends IBaseRepository<Dish, Long> {
}
