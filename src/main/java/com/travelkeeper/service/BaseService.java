package com.travelkeeper.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.io.Serializable;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.by;

/**
 * Base service
 *
 * Created by netocris on 24/08/2018
 */
@Slf4j
abstract class BaseService implements Serializable {

    private static final String SORT_BY_RESOURCE_KEY = "spring.data.elasticsearch.sort-by";
    private static final String INDEX_RESOURCE_KEY = "spring.data.elasticsearch.index";
    private static final String BASE_TYPE_RESOURCE_KEY = "spring.data.elasticsearch.type";
    private static final String PAGE_SIZE_RESOURCE_KEY = "pagination.pagesize";

    private final Environment env;

    BaseService(final Environment env) {
        this.env = env;
    }

    /**
     *
     * @return type
     */
    abstract String getType();

    /**
     * Default sort by
     *
     * @return sort by
     */
    String getSortBy() {
        return this.env.getProperty(SORT_BY_RESOURCE_KEY);
    }

    /**
     * Page size
     * @return page size
     */
    int getPageSize() {
        return NumberUtils.toInt(this.env.getProperty(PAGE_SIZE_RESOURCE_KEY));
    }

    /**
     * Build a search query that match all records
     *
     * @param page page
     * @param pagesize page size
     * @param sortBy sort by
     *
     * @return search query
     */
    NativeSearchQuery buildSearchQuery(final int page, final int pagesize, final String sortBy) {
        return new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withIndices(getProperty(INDEX_RESOURCE_KEY))
                .withTypes(getProperty(BASE_TYPE_RESOURCE_KEY + "." + getType()))
                .withPageable(of(page, pagesize, by(asc(sortBy))))
                .build();
    }

    /**
     * Get env property identified by key
     *
     * @param key key value
     *
     * @return property
     */
    private String getProperty(final String key){
        return this.env.getProperty(key);
    }

}
