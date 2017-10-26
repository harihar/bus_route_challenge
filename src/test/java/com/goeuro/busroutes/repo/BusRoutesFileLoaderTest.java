package com.goeuro.busroutes.repo;

import com.goeuro.busroutes.exception.BusRouteLoaderException;
import com.goeuro.busroutes.model.BusRoute;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringJUnit4ClassRunner.class)
public class BusRoutesFileLoaderTest {
    private BusRoutesFileLoader busRoutesFileLoader;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        busRoutesFileLoader = new BusRoutesFileLoader();
        ReflectionTestUtils.setField(busRoutesFileLoader, "maxRoutes", 10);
        ReflectionTestUtils.setField(busRoutesFileLoader, "maxStationsInRoute", 10);
    }

    @Test
    public void testLoadSucceeds() throws Exception {
        ReflectionTestUtils.setField(busRoutesFileLoader, "filePath", "src/test/resources/data/data_small");

        List<BusRoute> busRoutes = busRoutesFileLoader.load();
        assertThat(busRoutes).isEqualTo(routes());
    }

    @Test
    public void testLoadThrowsExceptionWhenFileDoesNotExist() throws Exception {
        ReflectionTestUtils.setField(busRoutesFileLoader, "filePath", "src/test/resources/data/null");

        assertThatExceptionOfType(BusRouteLoaderException.class)
                .isThrownBy(() -> busRoutesFileLoader.load())
                .withMessage("Error while reading bus routes file. src/test/resources/data/null (No such file or directory)");
    }

    @Test
    public void testLoadThrowsExceptionWhenNoRouteExceedsMax() throws Exception {
        ReflectionTestUtils.setField(busRoutesFileLoader, "filePath", "src/test/resources/data/more_than_max_routes");

        assertThatExceptionOfType(BusRouteLoaderException.class)
                .isThrownBy(() -> busRoutesFileLoader.load())
                .withMessage("Error while reading bus routes file. No of routes exceeds max routes. Found - 12. Max allowed - 10");
    }

    @Test
    public void testLoadThrowsExceptionWhenAnEmptyLineIsPresent() throws Exception {
        ReflectionTestUtils.setField(busRoutesFileLoader, "filePath", "src/test/resources/data/with_empty_line");

        assertThatExceptionOfType(BusRouteLoaderException.class)
                .isThrownBy(() -> busRoutesFileLoader.load())
                .withMessage("Error while reading bus routes file. Found unexpected empty line");
    }

    @Test
    public void testLoadThrowsExceptionForLessThanThreeValuesInALine() throws Exception {
        ReflectionTestUtils.setField(busRoutesFileLoader, "filePath", "src/test/resources/data/with_less_than_3_stations");

        assertThatExceptionOfType(BusRouteLoaderException.class)
                .isThrownBy(() -> busRoutesFileLoader.load())
                .withMessage("Error while reading bus routes file. Expected at least three integers in line - 2 2");
    }

    @Test
    public void testLoadThrowsExceptionForMoreThanMaxStationsInARoute() throws Exception {
        ReflectionTestUtils.setField(busRoutesFileLoader, "filePath", "src/test/resources/data/with_more_than_max_stations");

        assertThatExceptionOfType(BusRouteLoaderException.class)
                .isThrownBy(() -> busRoutesFileLoader.load())
                .withMessage("Error while reading bus routes file. No of stations in route exceeds maximum allowed stations. Found - 12, Max - 10");
    }

    @Test
    public void testLoadThrowsExceptionForDuplicateStationIdsInARoute() throws Exception {
        ReflectionTestUtils.setField(busRoutesFileLoader, "filePath", "src/test/resources/data/with_duplicate_stations");

        assertThatExceptionOfType(BusRouteLoaderException.class)
                .isThrownBy(() -> busRoutesFileLoader.load())
                .withMessage("Error while reading bus routes file. Line contains duplicate station ids. 1 34 56 67 34 3");
    }

    @Test
    public void testLoadThrowsExceptionForDuplicateRoueIds() throws Exception {
        ReflectionTestUtils.setField(busRoutesFileLoader, "filePath", "src/test/resources/data/with_duplicate_roue_ids");

        assertThatExceptionOfType(BusRouteLoaderException.class)
                .isThrownBy(() -> busRoutesFileLoader.load())
                .withMessage("Error while reading bus routes file. Duplicate route id 1");
    }

    private List<BusRoute> routes() {
        return Arrays.asList(
                new BusRoute(1, Arrays.asList(153, 150, 148)),
                new BusRoute(2, Arrays.asList(5, 142, 106))
        );
    }

}
