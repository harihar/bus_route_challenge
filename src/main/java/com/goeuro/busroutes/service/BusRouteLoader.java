package com.goeuro.busroutes.service;

import com.goeuro.busroutes.model.BusRoute;
import com.goeuro.busroutes.repo.BusRoutesRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BusRouteLoader implements CommandLineRunner {
    @Autowired
    private BusRoutesRepo busRoutesRepo;
    @Autowired
    private BusRouteFileParser busRouteFileParser;

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0) {
            log.error("File path argument is not provided");
            throw new IllegalArgumentException("File path command line argument is not provided");
        }
        //TODO Logger
        List<BusRoute> routes = busRouteFileParser.parse(args[0]);
        busRoutesRepo.setRoutes(routes);
    }
}
