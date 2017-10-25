package com.goeuro.busroutes.repo;

import com.goeuro.busroutes.Exception.ResourceNotFoundException;
import com.goeuro.busroutes.model.BusRoute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
@Slf4j
public class BusRoutesRepo {
    @Autowired
    private BusRoutesFileLoader routesFileLoader;

    public List<BusRoute> findAll() {
        try {
            return routesFileLoader.load();
        } catch (IOException e) {
            log.error("Error while loading routes sample", e);
            throw new ResourceNotFoundException("Error while loading routes sample");
        }
    }
}
