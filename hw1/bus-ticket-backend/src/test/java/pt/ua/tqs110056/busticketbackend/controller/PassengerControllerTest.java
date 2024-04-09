package pt.ua.tqs110056.busticketbackend.controller;

import static org.hamcrest.Matchers.emptyString;

import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import pt.ua.tqs110056.busticketbackend.model.Passenger;
import pt.ua.tqs110056.busticketbackend.service.PassengerService;

@WebMvcTest(PassengerController.class)
class PassengerControllerTest {

    @MockBean
    private PassengerService passengerService;

    @Autowired
    private MockMvc mockMvc;

    private Passenger passenger;

    @BeforeEach
    void setUp() throws Exception {
        passenger = new Passenger("André André", "aa@example.com", "+351123456789");
        passenger.setId(1L);
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void whenGetPassengerById_thenReturnStatus200() throws Exception {
        Mockito.when(passengerService.getPassengerById(1L)).thenReturn(Optional.of(passenger));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/passengers/1")
                .then()
                    .status(HttpStatus.OK)
                    .body("name", Matchers.equalTo(passenger.getName()))
                    .body("email", Matchers.equalTo(passenger.getEmail()))
                    .body("phoneNumber", Matchers.equalTo(passenger.getPhoneNumber()));
    }

    @Test
    void whenGetPassengerByUnknownId_thenReturnStatus404() throws Exception {
        Mockito.when(passengerService.getPassengerById(10L)).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/passengers/10")
                .then()
                    .status(HttpStatus.NOT_FOUND)
                    .body(emptyString());
    }

    @Test
    void whenSavePassenger_thenReturnStatus201() throws Exception {
        Mockito.when(passengerService.savePassenger(passenger)).thenReturn(passenger);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(passenger)
                .when()
                    .post("/api/passengers")
                .then()
                    .status(HttpStatus.CREATED)
                    .body("name", Matchers.equalTo(passenger.getName()))
                    .body("email", Matchers.equalTo(passenger.getEmail()))
                    .body("phoneNumber", Matchers.equalTo(passenger.getPhoneNumber()));
    }

    @Test
    void whenSaveInvalidPassenger_thenReturnStatus400() throws Exception {
        Passenger invalidPassenger = new Passenger();
        Mockito.when(passengerService.savePassenger(invalidPassenger)).thenReturn(null);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(invalidPassenger)
                .when()
                    .post("/api/passengers")
                .then()
                    .status(HttpStatus.BAD_REQUEST)
                    .body(emptyString());
    }

    @Test
    void whenDeletePassengerById_thenReturnStatus204() throws Exception {
        Mockito.doNothing().when(passengerService).deletePassengerById(1L);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .delete("/api/passengers/1")
                .then()
                    .status(HttpStatus.NO_CONTENT)
                    .body(emptyString());
    }
    
}