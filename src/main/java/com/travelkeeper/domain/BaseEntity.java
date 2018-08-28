package com.travelkeeper.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Base entity
 *
 * Created by netocris on 24/08/2018
 */
@Data
public class BaseEntity implements Serializable {

    @Id
    private long id;

}
