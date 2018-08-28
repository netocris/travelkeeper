package com.travelkeeper.service;

import com.travelkeeper.domain.Location;
import com.travelkeeper.errors.ApplicationException;
import com.travelkeeper.repository.ILocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

/**
 * Location service implementation
 *
 * Created by netocris on 27/08/2018
 */
@Service
@Slf4j
public class LocationService extends BaseService implements ILocationService {

    private static final String TYPE = "location";

    private ILocationRepository repository;

    @Autowired
    public LocationService(final Environment env, final ILocationRepository repository) {
        super(env);
        this.repository = repository;
    }

    @Override
    protected String getType() {
        log.debug("get [Location] type");
        return getEnv().getProperty(getBaseTypeResourceKey()+ "." + TYPE);
    }

    @Override
    public Page<Location> getAll() {

        log.debug("get all locations");

        final Page<Location> page = this.repository.search(
                buildSearchQuery(0, getPageSize(), getSortBy()));

        if(page.hasContent()){
            return page;
        }

        return Page.empty();

    }

    @Override
    public List<Long> getAllIds() {

        log.debug("get all location ids");

        final Page<Location> page = this.repository.search(
                buildSearchQuery(0, getPageSize(), getSortBy()));

        if(page.hasContent()){
            return page/**/
                    .getContent()
                    .stream()
                    .map(Location::getId)
                    .collect(Collectors.toList());
        }

        return emptyList();

    }

    @Override
    public Location getById(Long id) {
        log.debug("get location with id " + id);
        return this.repository.findById(id).get();
    }

    @Override
    public void save(Location entity) {
        log.debug("save location");
        this.repository.save(entity);
    }

    @Override
    public void delete(Long id) {

        log.debug("deleting location with id " + id);

        if(this.repository.existsById(id)){
            this.repository.deleteById(id);
        }

        throw new ApplicationException("Location with id " + id + " does not exists");

    }

}
