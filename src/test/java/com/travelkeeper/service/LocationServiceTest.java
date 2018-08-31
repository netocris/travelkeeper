package com.travelkeeper.service;

import com.travelkeeper.domain.Location;
import com.travelkeeper.repository.ILocationRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceTest {

    private ILocationService service;

    @Mock
    private Environment env;

    @Mock
    private ILocationRepository repository;

    @Before
    public void setUp() throws Exception {
        this.service = new LocationService(env, repository);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAll() {

        final LocationService spy = (LocationService) spy(this.service);
        doReturn(10).when(spy).getPageSize();
        doReturn("id").when(spy).getSortBy();
        doReturn(mock(NativeSearchQuery.class))
                .when(spy).buildSearchQuery(anyInt(), anyInt(), anyString());

        final Page<Location> page = mock(Page.class);
        when(page.hasContent()).thenReturn(true);
        when(page.getNumberOfElements()).thenReturn(1);
        when(page.getContent()).thenReturn(
                singletonList(mock(Location.class)));

        when(this.repository.search((SearchQuery) anyObject())).
                thenReturn(page);

        final Page<Location> result = spy.getAll();
        assertNotNull(result);
        assertEquals(result.hasContent(), true);
        assertEquals(result.getNumberOfElements(), 1);
        assertEquals(result.getContent().size(), 1);

        verify(this.repository, times(1)).search((SearchQuery) anyObject());
        verifyNoMoreInteractions(this.repository);

    }

    @Test
    public void getAllNoContents() {



    }

    @Test
    public void getAllIds() {

        final LocationService spy = (LocationService) spy(this.service);
        doReturn(10).when(spy).getPageSize();
        doReturn("id").when(spy).getSortBy();
        doReturn(mock(NativeSearchQuery.class))
                .when(spy).buildSearchQuery(anyInt(), anyInt(), anyString());

        final Location entity = mock(Location.class);
        when(entity.getId()).thenReturn(1L);

        final Page<Location> page = mock(Page.class);
        when(page.hasContent()).thenReturn(true);
        when(page.getNumberOfElements()).thenReturn(1);
        when(page.getContent()).thenReturn(
                singletonList(entity));

        when(this.repository.search((SearchQuery) anyObject())).
                thenReturn(page);

        final List<Long> result = spy.getAllIds();
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).longValue(), 1L);

        verify(this.repository, times(1)).search((SearchQuery) anyObject());
        verifyNoMoreInteractions(this.repository);

    }

    @Test
    public void getAllIdsNoContents() {



    }

    @Test
    public void getById() {
    }

    @Test
    public void save() {
    }

    @Test
    public void delete() {
    }
}