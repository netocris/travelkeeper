package com.travelkeeper.service;

import com.travelkeeper.domain.Dish;
import com.travelkeeper.repository.IDishRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

/**
 * Dish service implementation
 *
 * Created by netocris on 04/09/2018
 */
@Service
@Slf4j
public class DishService extends BaseService implements IDishService {

    private static final String TYPE = "dish";

    private IDishRepository repository;

    @Autowired
    DishService(final Environment env, final IDishRepository repository) {
        super(env);
        this.repository = repository;
    }

    @Override
    protected String getType() {
        log.debug("get [Dish] type");
        return TYPE;
    }

    @Override
    public Page<Dish> getAll() {

        log.debug("get all dishes");

        final Page<Dish> page = this.repository.search(
                buildSearchQuery(0, getPageSize(), getSortBy()));

        if(page.hasContent()){
            return page;
        }

        return Page.empty();

    }

    @Override
    public List<Long> getAllIds() {

        log.debug("get all dish ids");

        final Page<Dish> page = this.repository.search(
                buildSearchQuery(0, getPageSize(), getSortBy()));

        if(page.hasContent()){
            return page/**/
                    .getContent()
                    .stream()
                    .map(Dish::getId)
                    .collect(Collectors.toList());
        }

        return emptyList();

    }

    @Override
    public Dish getById(Long id) {
        log.debug("get dish with id " + id);
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public void save(Dish entity) {
        log.debug("save dish");
        this.repository.save(entity);
    }

    @Override
    public void delete(Long id) {

        log.debug("deleting dish with id " + id);

        if(!this.repository.existsById(id)){
            throw new ApplicationException("Dish with id " + id + " does not exists");
        }

        this.repository.deleteById(id);

    }

}
