package com.goeuro.busroutes.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashSet;

@Getter
public class BusRoute {
    private final String routeId;
    private final LinkedHashSet<String> routeStations;

    public BusRoute(String routeId, String[] stations) {
        this.routeId = routeId;
        this.routeStations = new LinkedHashSet<>(Arrays.asList(stations));
    }
}
