package com.goeuro.busroutes.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashSet;

@Getter
@EqualsAndHashCode
public class BusRoute {
    private final String routeId;
    private final LinkedHashSet<String> routeStations;

    public BusRoute(String routeId, String[] stations) {
        this.routeId = routeId;
        this.routeStations = new LinkedHashSet<>(Arrays.asList(stations));
    }
}
