package com.goeuro.busroutes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class BusRoutesResponseDTO {
    @JsonProperty("dep_sid")
    private int departureStation;
    @JsonProperty("arr_sid")
    private int arrivalStation;
    @JsonProperty("direct_bus_route")
    private boolean directBusRoute;
}
