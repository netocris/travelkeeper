package com.travelkeeper.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Restaurant entity
 *
 * Created by netocris on 04/09/2018
 */
@Data
@Document(indexName = "travelkeeper", type = "restaurant", shards = 1, replicas = 0, refreshInterval = "-1")
public class Restaurant extends BaseEntity {

    @NotNull
    private String name;

    private Address address;

    private Contact contact;

    private List<Photo> photos;

}

