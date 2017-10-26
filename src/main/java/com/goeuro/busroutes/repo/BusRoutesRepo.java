package com.goeuro.busroutes.repo;

import com.goeuro.busroutes.exception.BusRouteLoaderException;
import com.goeuro.busroutes.exception.ResourceNotFoundException;
import com.goeuro.busroutes.model.BusRoute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class BusRoutesRepo {
    @Autowired
    private BusRoutesFileLoader routesFileLoader;

    public List<BusRoute> findAll() {
        try {
            return routesFileLoader.load();
        } catch (BusRouteLoaderException e) {
            log.error("Error while loading routes data", e);
            throw new ResourceNotFoundException("Error while loading routes data");
        }
    }
}
