package com.goeuro.busroutes.service;

import com.goeuro.busroutes.model.BusRoute;
import com.goeuro.busroutes.model.BusRoutesResponseDTO;
import com.goeuro.busroutes.repo.BusRoutesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class BusRoutesService {

    @Autowired
    private BusRoutesRepo busRoutesRepo;

    public BusRoutesResponseDTO checkRoute(String departureSid, String arrivalSid) {
        Predicate<BusRoute> routeIncludesDepartureAndArrivalStations =
                busRoute -> busRoute.getRouteStations().contains(departureSid)
                        && busRoute.getRouteStations().contains(arrivalSid);

        //TODO Logger
        boolean routeExists = busRoutesRepo.getRoutes().parallelStream()
                .anyMatch(routeIncludesDepartureAndArrivalStations);

        return new BusRoutesResponseDTO(departureSid, arrivalSid, routeExists);
    }
}
