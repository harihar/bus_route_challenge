package com.goeuro.busroutes.service;

import com.goeuro.busroutes.model.BusRoute;
import com.goeuro.busroutes.model.BusRoutesResponseDTO;
import com.goeuro.busroutes.repo.BusRoutesRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
@Slf4j
public class BusRoutesService {

    @Autowired
    private BusRoutesRepo busRoutesRepo;

    public BusRoutesResponseDTO checkRoute(String departureSid, String arrivalSid) {
        log.info("Received request for route check, departureSid - {}, arrivalSid - {}", departureSid, arrivalSid);
        Predicate<BusRoute> doesRouteIncludeDepartureAndArrivalStations =
                busRoute -> busRoute.getRouteStations().contains(departureSid)
                        && busRoute.getRouteStations().contains(arrivalSid);

        List<BusRoute> allRoutes = busRoutesRepo.findAll();
        log.info("Retrieved all routes");

        boolean routeExists = allRoutes
                .parallelStream()
                .anyMatch(doesRouteIncludeDepartureAndArrivalStations);

        BusRoutesResponseDTO responseDTO = new BusRoutesResponseDTO(departureSid, arrivalSid, routeExists);
        log.info("Returning response as - {}", responseDTO);

        return responseDTO;
    }
}
