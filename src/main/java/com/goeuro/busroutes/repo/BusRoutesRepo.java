package com.goeuro.busroutes.repo;

import com.goeuro.busroutes.model.BusRoute;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Getter
@Setter
public class BusRoutesRepo {
    private List<BusRoute> routes;
}
