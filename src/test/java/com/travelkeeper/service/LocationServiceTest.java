package com.travelkeeper.service;

import com.travelkeeper.domain.Location;
import com.travelkeeper.errors.ApplicationException;
import com.travelkeeper.repository.ILocationRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class LocationServiceTest {

    private ILocationService service;

    @Mock
    private Environment env;

    @Mock
    private ILocationRepository repository;

    @Before
    public void setUp() {
        this.service = new LocationService(env, repository);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAll() {

        final LocationService spy = (LocationService) spy(this.service);
        doReturn(10).when(spy).getPageSize();
        doReturn("id").when(spy).getSortBy();
        doReturn(mock(NativeSearchQuery.class))
                .when(spy).buildSearchQuery(anyInt(), anyInt(), anyString());

        final Page page = mock(Page.class);
        when(page.hasContent()).thenReturn(true);
        when(page.getNumberOfElements()).thenReturn(1);
        when(page.getContent()).thenReturn(
                singletonList(mock(Location.class)));

        when(this.repository.search((SearchQuery) any())).
                thenReturn(page);

        final Page<Location> result = spy.getAll();
        assertNotNull(result);
        assertTrue(result.hasContent());
        assertEquals(result.getNumberOfElements(), 1);
        assertEquals(result.getContent().size(), 1);

        verify(this.repository, times(1)).search((SearchQuery) any());
        verifyNoMoreInteractions(this.repository);

    }

    @Test
    public void getAllNoContents() {

        final LocationService spy = (LocationService) spy(this.service);
        doReturn(10).when(spy).getPageSize();
        doReturn("id").when(spy).getSortBy();
        doReturn(mock(NativeSearchQuery.class))
                .when(spy).buildSearchQuery(anyInt(), anyInt(), anyString());

        final Page<Location> page = mock(Page.class);
        when(page.hasContent()).thenReturn(false);

        when(this.repository.search((SearchQuery) any())).
                thenReturn(page);

        final Page<Location> result = spy.getAll();
        assertNotNull(result);
        assertFalse(result.hasContent());

        verify(this.repository, times(1)).search((SearchQuery) any());
        verifyNoMoreInteractions(this.repository);

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

        when(this.repository.search((SearchQuery) any())).
                thenReturn(page);

        final List<Long> result = spy.getAllIds();
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).longValue(), 1L);

        verify(this.repository, times(1)).search((SearchQuery) any());
        verifyNoMoreInteractions(this.repository);

    }

    @Test
    public void getAllIdsNoContents() {

        final LocationService spy = (LocationService) spy(this.service);
        doReturn(10).when(spy).getPageSize();
        doReturn("id").when(spy).getSortBy();
        doReturn(mock(NativeSearchQuery.class))
                .when(spy).buildSearchQuery(anyInt(), anyInt(), anyString());

        final Location entity = mock(Location.class);
        when(entity.getId()).thenReturn(1L);

        final Page<Location> page = mock(Page.class);
        when(page.hasContent()).thenReturn(false);

        when(this.repository.search((SearchQuery) any())).
                thenReturn(page);

        final List<Long> result = spy.getAllIds();
        assertNotNull(result);

        verify(this.repository, times(1)).search((SearchQuery) any());
        verifyNoMoreInteractions(this.repository);

    }

    @Test
    public void getById() {

        final Location entity = mock(Location.class);
        when(entity.getId()).thenReturn(1L);

        when(this.repository.findById(anyLong())).
                thenReturn(Optional.of(entity));

        final Location result = this.service.getById(1L);
        assertNotNull(result);
        assertEquals(result.getId(), 1L);

        verify(this.repository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(this.repository);

    }

    @Test
    public void getByIdError() {

        when(this.repository.findById(anyLong())).
                thenReturn(Optional.empty());

        final Location result = this.service.getById(1L);
        assertNull(result);

        verify(this.repository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(this.repository);

    }

    @Test
    public void save() {

        final Location entity = new Location();
        entity.setName("teste");

        when(this.repository.save(any())).
                thenReturn(mock(Location.class));

        this.service.save(entity);

        verify(this.repository, times(1)).save(any());
        verifyNoMoreInteractions(this.repository);

    }

    @Test
    public void delete() {

        when(this.repository.existsById(anyLong())).thenReturn(true);
        doNothing().when(this.repository).deleteById(anyLong());

        this.service.delete(1L);

        verify(this.repository, times(1)).existsById(anyLong());
        verify(this.repository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(this.repository);

    }

    @Test(expected = ApplicationException.class)
    public void deleteError() {

        when(this.repository.existsById(anyLong())).thenReturn(false);

        this.service.delete(1L);

        verify(this.repository, times(1)).existsById(anyLong());
        verifyNoMoreInteractions(this.repository);

    }

}