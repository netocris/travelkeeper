package com.travelkeeper.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Address entity
 *
 * Created by netocris on 04/09/2018
 */
@Data
public class Address extends BaseEntity {

    @NotNull
    private String street;

    @NotNull
    private String number;

}

