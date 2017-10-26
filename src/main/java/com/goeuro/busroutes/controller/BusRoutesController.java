package com.goeuro.busroutes.controller;

import com.goeuro.busroutes.model.BusRoutesResponseDTO;
import com.goeuro.busroutes.service.BusRoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api")
public class BusRoutesController {
    @Autowired
    private BusRoutesService busRoutesService;

    @RequestMapping("/direct")
    public BusRoutesResponseDTO checkRoute(@RequestParam(name = "dep_sid") int departureSid,
                                           @RequestParam(name = "arr_sid") int arrivalSid) {
        return busRoutesService.checkRoute(departureSid, arrivalSid);
    }
}
