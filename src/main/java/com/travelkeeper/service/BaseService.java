package com.travelkeeper.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.io.Serializable;

/**
 * Base service
 *
 * Created by netocris on 24/08/2018
 */
public abstract class BaseService implements Serializable {

    private static final String BASE_INDEX = "spring.data.elasticsearch.index";
    private static final String BASE_TYPE = "spring.data.elasticsearch.type";
    private static final String PAGE_SIZE = "pagination.pagesize";

    private final Environment env;

    public BaseService(final Environment env) {
        this.env = env;
    }

    protected abstract String getIndex();
    protected abstract String getType();

    protected Environment getEnv() {
        return this.env;
    }

    protected String getBaseIndex() {
        return BASE_INDEX;
    }

    protected String getBaseType() {
        return BASE_TYPE;
    }

    protected NativeSearchQuery createSearchQuery() {
        return new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withIndices(getIndex())
                .withTypes(getType())
                .withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
                .withPageable(new PageRequest(0,
                        NumberUtils.toInt(this.env.getProperty(PAGE_SIZE))))
                .build();
    }

}
