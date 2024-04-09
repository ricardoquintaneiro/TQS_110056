package pt.ua.tqs110056.busticketbackend.controller;

import static org.hamcrest.Matchers.emptyString;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import pt.ua.tqs110056.busticketbackend.model.Trip;
import pt.ua.tqs110056.busticketbackend.service.TripService;

@WebMvcTest(TripController.class)
class TripControllerTest {

    @MockBean
    private TripService tripService;

    @Autowired
    private MockMvc mockMvc;

    private Trip trip;

    @BeforeEach
    void setUp() throws Exception {
        trip = new Trip();
        trip.setId(1L);
        RestAssuredMockMvc.mockMvc(mockMvc);
    }
    
    @Test
    void whenFindTripByOriginIdAndDestinationId_thenReturnStatus200() throws Exception {
        Mockito.when(tripService.findTripsByOriginAndDestination(1L, 2L)).thenReturn(List.of(trip));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/trips?originId=1&destinationId=2")
                .then()
                    .status(HttpStatus.OK)
                    .body("[0].id", Matchers.equalTo(trip.getId().intValue()));
    }

    @Test
    void whenFindWithNoParameters_thenReturnAllTrips() throws Exception {
        Mockito.when(tripService.findAllTrips()).thenReturn(List.of(trip));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/trips")
                .then()
                    .status(HttpStatus.OK)
                    .body("[0].id", Matchers.equalTo(trip.getId().intValue()));
    }

    @Test
    void whenFindByOnlyOriginId_thenReturnStatus400() throws Exception {
        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/trips?originId=1")
                .then()
                    .status(HttpStatus.BAD_REQUEST)
                    .body(emptyString());
    }
}