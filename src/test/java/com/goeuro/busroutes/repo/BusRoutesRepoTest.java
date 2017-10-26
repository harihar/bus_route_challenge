package com.goeuro.busroutes.repo;

import com.goeuro.busroutes.exception.BusRouteLoaderException;
import com.goeuro.busroutes.exception.ResourceNotFoundException;
import com.goeuro.busroutes.model.BusRoute;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

public class BusRoutesRepoTest {

    @InjectMocks
    private BusRoutesRepo busRoutesRepo;

    @Mock
    private BusRoutesFileLoader busRoutesFileLoader;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() throws Exception {
        when(busRoutesFileLoader.load()).thenReturn(routes());
        List<BusRoute> all = busRoutesRepo.findAll();
        assertThat(routes()).isEqualTo(all);
    }

    @Test
    public void testAllThrowsExceptionOnFileLoaderError() throws Exception {
        when(busRoutesFileLoader.load()).thenThrow(new BusRouteLoaderException("File not found"));
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> busRoutesRepo.findAll())
                .withMessage("Error while loading routes data");
    }

    private List<BusRoute> routes() {
        return Arrays.asList(
                new BusRoute(1, Arrays.asList(152, 138, 150, 148, 106, 16, 20)),
                new BusRoute(2, Arrays.asList(5, 142, 116, 11)),
                new BusRoute(3, Arrays.asList(153, 114, 5, 138))
        );
    }
}
