package com.travelkeeper.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.NotNull;

/**
 * Dish entity
 *
 * Created by netocris on 04/09/2018
 */
@Data
@Document(indexName = "travelkeeper", type = "dish", shards = 1, replicas = 0, refreshInterval = "-1")
public class Dish extends BaseEntity {

    @NotNull
    private String name;

}

