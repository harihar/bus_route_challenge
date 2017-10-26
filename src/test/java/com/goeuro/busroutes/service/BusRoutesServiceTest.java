package com.goeuro.busroutes.service;

import com.goeuro.busroutes.model.BusRoute;
import com.goeuro.busroutes.model.BusRoutesResponseDTO;
import com.goeuro.busroutes.repo.BusRoutesRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BusRoutesServiceTest {

    @InjectMocks
    private BusRoutesService busRoutesService;

    @Mock
    private BusRoutesRepo busRoutesRepo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidDirectRoute() {
        when(busRoutesRepo.findAll()).thenReturn(routes());
        BusRoutesResponseDTO expected = new BusRoutesResponseDTO(138, 16, true);
        BusRoutesResponseDTO busRoutesResponseDTO = busRoutesService.checkRoute(138, 16);
        assertThat(busRoutesResponseDTO).isEqualTo(expected);
    }

    @Test
    public void testValidDirectRouteInReverseDirection() {
        when(busRoutesRepo.findAll()).thenReturn(routes());
        BusRoutesResponseDTO expected = new BusRoutesResponseDTO(16, 138, true);
        BusRoutesResponseDTO busRoutesResponseDTO = busRoutesService.checkRoute(16, 138);
        assertThat(busRoutesResponseDTO).isEqualTo(expected);
    }

    @Test
    public void testInValidDirectRoute() {
        when(busRoutesRepo.findAll()).thenReturn(routes());
        BusRoutesResponseDTO expected = new BusRoutesResponseDTO(138, 916, false);
        BusRoutesResponseDTO busRoutesResponseDTO = busRoutesService.checkRoute(138, 916);
        assertThat(busRoutesResponseDTO).isEqualTo(expected);
    }

    private List<BusRoute> routes() {
        return Arrays.asList(
                new BusRoute(1, Arrays.asList(152, 138, 150, 148, 106, 16, 20)),
                new BusRoute(2, Arrays.asList(5, 142, 116, 11)),
                new BusRoute(3, Arrays.asList(153, 114, 5, 138))
        );
    }
}
