package com.goeuro.busroutes.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.List;

@Getter
@EqualsAndHashCode
public class BusRoute {
    private final int routeId;
    private final LinkedHashSet<Integer> routeStations;

    public BusRoute(int routeId, List<Integer> stations) {
        this.routeId = routeId;
        this.routeStations = new LinkedHashSet<>(stations);
    }
}
