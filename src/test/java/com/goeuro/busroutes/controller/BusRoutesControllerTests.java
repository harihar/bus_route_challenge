package com.goeuro.busroutes.controller;

import com.goeuro.busroutes.Exception.Error;
import com.goeuro.busroutes.model.BusRoutesResponseDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        properties = {"filePath=src/test/resources/data/sample"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class BusRoutesControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testDirectlyConnectedRoutes() {
        String apiUrl = String.format("http://localhost:%s/api/direct?dep_sid=138&arr_sid=16", port);
        ResponseEntity<BusRoutesResponseDTO> response = restTemplate.getForEntity(apiUrl, BusRoutesResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new BusRoutesResponseDTO("138", "16", true));
    }

    @Test
    public void testNotDirectlyConnectedRoutes() {
        String apiUrl = String.format("http://localhost:%s/api/direct?dep_sid=138&arr_sid=916", port);
        ResponseEntity<BusRoutesResponseDTO> response = restTemplate.getForEntity(apiUrl, BusRoutesResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new BusRoutesResponseDTO("138", "916", false));
    }

    @Test
    public void testMissingInputParamArrivalStationId() {
        String apiUrl = String.format("http://localhost:%s/api/direct?dep_sid=138", port);
        ResponseEntity<Error> response = restTemplate.getForEntity(apiUrl, Error.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo(new Error(400, "Missing required parameter arr_sid"));
    }

    @Test
    public void testMissingInputParamDepartureStationId() {
        String apiUrl = String.format("http://localhost:%s/api/direct?arr_sid=138", port);
        ResponseEntity<Error> response = restTemplate.getForEntity(apiUrl, Error.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo(new Error(400, "Missing required parameter dep_sid"));
    }

}
