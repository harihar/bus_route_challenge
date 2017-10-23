package com.goeuro.busroutes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BusRoutesResponseDTO {
    @JsonProperty("dep_sid")
    private String departureStation;
    @JsonProperty("arr_sid")
    private String arrivalStation;
    @JsonProperty("direct_bus_route")
    private boolean directBusRoute;
}
