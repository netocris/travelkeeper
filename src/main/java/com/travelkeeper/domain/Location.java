package com.travelkeeper.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "locations", type = "location")
@Data
public class Location extends BaseEntity {

    private String name;

}

