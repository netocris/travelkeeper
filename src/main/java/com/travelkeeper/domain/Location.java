package com.travelkeeper.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.NotNull;

/**
 * Location entity
 *
 * Created by netocris on 24/08/2018
 */
@Data
@Document(indexName = "travelkeeper", type = "location", shards = 1, replicas = 0, refreshInterval = "-1")
public class Location extends BaseEntity {

    @NotNull
    private String name;

}

