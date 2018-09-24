package com.travelkeeper.domain;

import lombok.Data;

/**
 * Photo entity
 *
 * Created by netocris on 04/09/2018
 */
@Data
public class Photo extends BaseEntity {

    private String name;

    private String contentType;

    private byte[] content;

}
