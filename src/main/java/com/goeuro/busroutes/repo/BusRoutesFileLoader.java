package com.goeuro.busroutes.repo;

import com.goeuro.busroutes.model.BusRoute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class BusRoutesFileLoader {
    private static final String VALUE_SEPARATOR = " ";

    @Value("${filePath}")
    private String filePath;

    @Cacheable(value = "bus_routes")
    public List<BusRoute> load() throws IOException {
        List<BusRoute> routes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the 1st line. If required validate that this no and no of route lines matches.
            int noOfRoutes = Integer.parseInt(br.readLine().trim());
            log.info("Reading {} routes from file {}", noOfRoutes, filePath);

            while (noOfRoutes >= 1) {
                String line = br.readLine();
                String[] values = line.split(VALUE_SEPARATOR);
                BusRoute route = new BusRoute(values[0], Arrays.copyOfRange(values, 1, values.length));
                routes.add(route);

                noOfRoutes--;
            }
        }
        return routes;
    }
}
