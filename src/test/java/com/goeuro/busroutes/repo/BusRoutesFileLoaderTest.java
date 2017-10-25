package com.goeuro.busroutes.repo;

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
import static org.assertj.core.api.Assertions.assertThatIOException;

@RunWith(SpringJUnit4ClassRunner.class)
public class BusRoutesFileLoaderTest {
    private BusRoutesFileLoader busRoutesFileLoader;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        busRoutesFileLoader = new BusRoutesFileLoader();
    }

    @Test
    public void testLoadSucceeds() throws Exception {
        ReflectionTestUtils.setField(busRoutesFileLoader, "filePath", "src/test/resources/data/data_small");

        List<BusRoute> busRoutes = busRoutesFileLoader.load();
        assertThat(busRoutes).isEqualTo(routes());
    }

    @Test
    public void testLoadThrowsIOExceptionWhenFileDoesNotExist() throws Exception {
        ReflectionTestUtils.setField(busRoutesFileLoader, "filePath", "src/test/resources/data/null");

        assertThatIOException().isThrownBy(() -> busRoutesFileLoader.load());
    }

    private List<BusRoute> routes() {
        return Arrays.asList(
                new BusRoute("1", new String[]{"153", "150", "148"}),
                new BusRoute("2", new String[]{"5", "142", "106"})
        );
    }

}
