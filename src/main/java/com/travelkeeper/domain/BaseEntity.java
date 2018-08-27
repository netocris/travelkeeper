package com.travelkeeper.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Base entity
 *
 * Created by netocris on 24/08/2018
 */
@Data
public class BaseEntity implements Serializable {

    @Id
    @NotNull
    private Long id;

    @Version
    private Long version;

}
