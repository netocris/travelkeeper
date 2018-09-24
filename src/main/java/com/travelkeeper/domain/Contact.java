package com.travelkeeper.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Contact entity
 *
 * Created by netocris on 24/09/2018
 */
@Data
public class Contact extends BaseEntity {

    @NotNull
    private String telephone;

    @NotNull
    private String email;

}

