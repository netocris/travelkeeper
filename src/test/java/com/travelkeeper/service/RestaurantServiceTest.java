package com.travelkeeper.service;

import com.travelkeeper.domain.Restaurant;
import com.travelkeeper.repository.IRestaurantRepository;
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

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class RestaurantServiceTest {

    private IRestaurantService service;

    @Mock
    private Environment env;

    @Mock
    private IRestaurantRepository repository;

    @Before
    public void setUp() {
        this.service = new RestaurantService(env, repository);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAll() {

        final RestaurantService spy = (RestaurantService) spy(this.service);
        doReturn(10).when(spy).getPageSize();
        doReturn("id").when(spy).getSortBy();
        doReturn(mock(NativeSearchQuery.class))
                .when(spy).buildSearchQuery(anyInt(), anyInt(), anyString());

        final Page page = mock(Page.class);
        when(page.hasContent()).thenReturn(true);
        when(page.getNumberOfElements()).thenReturn(1);
        when(page.getContent()).thenReturn(
                singletonList(mock(Restaurant.class)));

        when(this.repository.search((SearchQuery) any())).
                thenReturn(page);

        final Page result = spy.getAll();
        assertNotNull(result);
        assertTrue(result.hasContent());
        assertEquals(result.getNumberOfElements(), 1);
        assertEquals(result.getContent().size(), 1);

        verify(this.repository, times(1)).search((SearchQuery) any());
        verifyNoMoreInteractions(this.repository);

    }

    @Test
    public void getAllEmpty() {

        final RestaurantService spy = (RestaurantService) spy(this.service);
        doReturn(10).when(spy).getPageSize();
        doReturn("id").when(spy).getSortBy();
        doReturn(mock(NativeSearchQuery.class))
                .when(spy).buildSearchQuery(anyInt(), anyInt(), anyString());

        final Page page = mock(Page.class);
        when(page.hasContent()).thenReturn(false);

        when(this.repository.search((SearchQuery) any())).
                thenReturn(page);

        final Page result = spy.getAll();
        assertNotNull(result);
        assertFalse(result.hasContent());

        verify(this.repository, times(1)).search((SearchQuery) any());
        verifyNoMoreInteractions(this.repository);

    }

    @Test
    public void getAllIds() {
    }

    @Test
    public void getAllIdsEmpty() {



    }

    @Test
    public void getById() {
    }

    @Test
    public void getByIdError() {



    }

    @Test
    public void save() {
    }

    @Test
    public void delete() {
    }

    @Test(expected = ApplicationException.class)
    public void deleteError() {

        when(this.repository.existsById(anyLong())).thenReturn(false);

        this.service.delete(1L);

        verify(this.repository, times(1)).existsById(anyLong());
        verifyNoMoreInteractions(this.repository);

    }
}