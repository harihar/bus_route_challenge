package com.goeuro.busroutes.repo;

import com.goeuro.busroutes.exception.BusRouteLoaderException;
import com.goeuro.busroutes.model.BusRoute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BusRoutesFileLoader {
    private static final String VALUE_SEPARATOR = " ";

    @Value("${filePath}")
    private String filePath;
    @Value("${routes.max}")
    private int maxRoutes;
    @Value("${routes.stations.max}")
    private int maxStationsInRoute;

    @Cacheable(value = "bus_routes")
    public List<BusRoute> load() throws BusRouteLoaderException {
        List<BusRoute> routes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int noOfRoutes = Integer.valueOf(br.readLine().trim());
            validateNoOfRoutes(noOfRoutes);
            log.info("Reading {} routes from file {}", noOfRoutes, filePath);

            while (noOfRoutes >= 1) {
                String line = br.readLine();
                validateLine(line);

                String[] values = line.split(VALUE_SEPARATOR);
                BusRoute route = new BusRoute(Integer.valueOf(values[0]), convertToInt(values));
                routes.add(route);

                noOfRoutes--;
            }
            validateUniqueRouteIds(routes);
        } catch (Exception e) {
            log.error("Error while reading routes file - {}", filePath, e);
            throw new BusRouteLoaderException("Error while reading bus routes file. " + e.getMessage());
        }
        return routes;
    }

    private void validateNoOfRoutes(int noOfRoutes) throws BusRouteLoaderException {
        if (noOfRoutes > maxRoutes) {
            throw new BusRouteLoaderException(
                    String.format("No of routes exceeds max routes. Found - %d. Max allowed - %d", noOfRoutes, maxRoutes)
            );
        }
    }

    private void validateUniqueRouteIds(List<BusRoute> routes) throws BusRouteLoaderException {
        Set<Integer> routeIds = new HashSet<>();
        for (BusRoute route : routes) {
            if (routeIds.contains(route.getRouteId())) {
                throw new BusRouteLoaderException("Duplicate route id " + route.getRouteId());
            }
            routeIds.add(route.getRouteId());
        }
    }

    private List<Integer> convertToInt(String[] values) {
        return Arrays.stream(values)
                .skip(1)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    private void validateLine(String line) throws BusRouteLoaderException {
        if (StringUtils.isEmpty(line)) {
            throw new BusRouteLoaderException("Found unexpected empty line");
        }
        String[] values = line.split(VALUE_SEPARATOR);
        if (values.length < 3) {
            throw new BusRouteLoaderException("Expected at least three integers in line - " + line);
        }
        int noOfStationsInRoute = values.length - 1;
        if (noOfStationsInRoute > maxStationsInRoute + 1) {
            throw new BusRouteLoaderException(
                    String.format("No of stations in route exceeds maximum allowed stations. Found - %d, Max - %d", noOfStationsInRoute, maxStationsInRoute)
            );
        }
        if (Arrays.stream(values).skip(1).distinct().count() < values.length - 1) {
            throw new BusRouteLoaderException("Line contains duplicate station ids. " + line);
        }
    }
}
