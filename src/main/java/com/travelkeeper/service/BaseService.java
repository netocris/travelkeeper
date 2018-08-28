package com.travelkeeper.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.io.Serializable;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Order.*;
import static org.springframework.data.domain.Sort.by;

/**
 * Base service
 *
 * Created by netocris on 24/08/2018
 */
@Slf4j
public abstract class BaseService implements Serializable {

    private static final String SORT_BY_RESOURCE_KEY = "spring.data.elasticsearch.sort-by";
    private static final String INDEX_RESOURCE_KEY = "spring.data.elasticsearch.index";
    private static final String BASE_TYPE_RESOURCE_KEY = "spring.data.elasticsearch.type";
    private static final String PAGE_SIZE_RESOURCE_KEY = "pagination.pagesize";

    private final Environment env;

    public BaseService(final Environment env) {
        this.env = env;
    }

    /**
     *
     * @return
     */
    protected abstract String getType();

    /**
     *
     * @return
     */
    protected Environment getEnv() {
        return this.env;
    }

    /**
     * Base type resource key
     *
     * @return
     */
    protected String getBaseTypeResourceKey() {
        return BASE_TYPE_RESOURCE_KEY;
    }

    /**
     * Default sort by
     *
     * @return
     */
    protected String getSortBy() {
        return this.env.getProperty(SORT_BY_RESOURCE_KEY);
    }

    /**
     * Page size
     * @return
     */
    protected int getPageSize() {
        return NumberUtils.toInt(this.env.getProperty(PAGE_SIZE_RESOURCE_KEY));
    }

    /**
     * Build a search query that match all records
     *
     * @param page
     * @param pagesize
     * @param sortBy
     *
     * @return
     */
    protected NativeSearchQuery buildSearchQuery(final int page, final int pagesize, final String sortBy) {
        return new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withIndices(getIndex())
                .withTypes(getType())
                .withPageable(of(page, pagesize, by(asc(sortBy))))
                .build();
    }

    /**
     *
     * @return
     */
    private String getIndex(){
        return this.env.getProperty(INDEX_RESOURCE_KEY);
    }

}
