package com.travelkeeper.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

//import javax.persistence.*;
import java.io.Serializable;

/**
 * Simple persistent entity
 *
 * Created by netocris on 24/08/2018
 */
//@MappedSuperclass
public class SimplePersistentEntity implements Serializable {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "ID")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        final SimplePersistentEntity entity = (SimplePersistentEntity) o;

        return new EqualsBuilder()
                .append(this.id, entity.id)
                .isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(this.id)
                .toString();
    }

}
