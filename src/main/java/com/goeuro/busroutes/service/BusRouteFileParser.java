package com.goeuro.busroutes.service;

import com.goeuro.busroutes.model.BusRoute;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class BusRouteFileParser {
    private static final String VALUE_SEPARATOR = " ";

    public List<BusRoute> parse(String filePath) throws IOException {
        //TODO Logger
        List<BusRoute> routes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the 1st line. If required validate that this no and no of route lines matches.
            int noOfRoutes = Integer.parseInt(br.readLine().trim());

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