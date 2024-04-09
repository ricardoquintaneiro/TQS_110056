package pt.ua.tqs110056.busticketbackend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import pt.ua.tqs110056.busticketbackend.model.Bus;
import pt.ua.tqs110056.busticketbackend.model.BusSeat;
import pt.ua.tqs110056.busticketbackend.model.BusSeatType;
import pt.ua.tqs110056.busticketbackend.service.BusService;

@WebMvcTest(BusController.class)
class BusControllerTest {

    @MockBean
    private BusService busService;

    @Autowired
    private MockMvc mockMvc;

    private Bus bus;

    @BeforeEach
    void setUp() throws Exception {
        List<BusSeat> seats = new ArrayList<BusSeat>();
        seats.add(new BusSeat(BusSeatType.REGULAR, "1"));
        seats.add(new BusSeat(BusSeatType.REGULAR, "2"));
        seats.add(new BusSeat(BusSeatType.PRIORITY, "3"));
        seats.add(new BusSeat(BusSeatType.PREMIUM, "4"));
        bus = new Bus("AABBCC", "Mercedes-Benz Tourismo", seats);
        bus.setId(1L);
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void whenGetBusById_thenReturnBus() throws IOException {
        Long id = bus.getId();
        Mockito.when(busService.getBusById(id)).thenReturn(Optional.of(bus));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/buses/{id}", String.valueOf(id))
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("plate", Matchers.equalTo("AABBCC"))
                    .body("model", Matchers.equalTo("Mercedes-Benz Tourismo"))
                    .body("seats", Matchers.hasSize(4))
                    .body("seats[0].type", Matchers.equalTo("REGULAR"))
                    .body("seats[0].number", Matchers.equalTo("1"));
    }

    @Test
    void whenGetBusByNonExistentId_thenReturnNotFound() throws IOException {
        Long nonExistentId = -1L;
        Mockito.when(busService.getBusById(nonExistentId)).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/buses/{nonExistentId}", String.valueOf(nonExistentId))
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void whenGetAllBuses_thenReturnAllBuses() throws IOException {
        Bus bus2 = new Bus();
        bus2.setId(2L);
        List<Bus> buses = new ArrayList<Bus>();
        buses.add(bus);
        buses.add(bus2);
        Mockito.when(busService.getAllBuses()).thenReturn(buses);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/buses")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", Matchers.hasSize(2))
                    .body("[0].id", Matchers.equalTo(1))
                    .body("[0].plate", Matchers.equalTo("AABBCC"))
                    .body("[0].model", Matchers.equalTo("Mercedes-Benz Tourismo"))
                    .body("[0].seats", Matchers.hasSize(4))
                    .body("[1].id", Matchers.equalTo(2));

    }

    @Test
    void whenGetAllBusSeats_ThenReturnAllBusSeats() throws IOException {
        Long id = bus.getId();
        Mockito.when(busService.getAllBusSeats(id)).thenReturn(Optional.of(bus.getSeats()));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/buses/{id}/seats", String.valueOf(id))
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", Matchers.hasSize(4))
                    .body("[0].type", Matchers.equalTo("REGULAR"))
                    .body("[0].number", Matchers.equalTo("1"))
                    .body("[1].type", Matchers.equalTo("REGULAR"))
                    .body("[2].type", Matchers.equalTo("PRIORITY"))
                    .body("[3].type", Matchers.equalTo("PREMIUM"));
    }

    @Test
    void whenGetBusSeatsByType_thenReturnBusSeatsOfType() throws IOException {
        Long id = bus.getId();
        Mockito.when(busService.getBusSeatsByType(id, BusSeatType.REGULAR)).thenReturn(Optional.of(
                bus.getSeats().stream().filter(seat -> seat.getType() == BusSeatType.REGULAR)
                        .collect(Collectors.toList())));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/buses/{id}/seats?type=REGULAR", String.valueOf(id))
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", Matchers.hasSize(2))
                    .body("[0].type", Matchers.equalTo("REGULAR"))
                    .body("[0].number", Matchers.equalTo("1"))
                    .body("[1].type", Matchers.equalTo("REGULAR"));
    }

    @Test
    void whenGetBusSeatsByUnknownType_thenReturnBadRequest() throws IOException {
        Long id = bus.getId();
        Mockito.when(busService.getBusSeatsByType(id, BusSeatType.REGULAR)).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/buses/{id}/seats?type=UNKNOWN", String.valueOf(id))
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void whenGetNonExistentSeats_thenReturnNotFound() throws IOException {
        Long id = bus.getId();
        Mockito.when(busService.getAllBusSeats(id)).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/buses/{id}/seats", String.valueOf(id))
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void whenGetSeatsFromNonExistingBus_thenReturnNotFound() throws IOException {
        Long nonExistentId = -1L;
        Mockito.when(busService.getAllBusSeats(nonExistentId)).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/buses/{nonExistentId}/seats", String.valueOf(nonExistentId))
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void whenReservingAvailableSeat_thenReturnSuccess() throws IOException {
        Long id = bus.getId();
        String seatNumber = "1";
        Mockito.when(busService.reserveSeat(id, seatNumber)).thenReturn(true);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .put("/api/buses/{id}/seats/{seatNumber}?reserve=true", String.valueOf(id), seatNumber)
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(Matchers.equalTo("Seat reserved successfully"));
    }

    @Test
    void whenReservingNonAvailableSeat_thenReturnUnprocessableEntity() throws IOException {
        Long id = bus.getId();
        String seatNumber = "1";
        Mockito.when(busService.reserveSeat(id, seatNumber)).thenReturn(false);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .put("/api/buses/{id}/seats/{seatNumber}?reserve=true", String.valueOf(id), seatNumber)
                .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body(Matchers.equalTo("Seat not available"));
    }

    @Test
    void whenMakingReservedSeatAvailable_thenReturnSuccess() throws IOException {
        Long id = bus.getId();
        String seatNumber = "1";
        Mockito.when(busService.makeSeatAvailable(id, seatNumber)).thenReturn(true);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .put("/api/buses/{id}/seats/{seatNumber}?reserve=false", String.valueOf(id), seatNumber)
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(Matchers.equalTo("Seat made available successfully"));
    }

    @Test
    void whenMakingAlreadyAvailableSeatAvailable_thenReturnUnprocessableEntity() throws IOException {
        Long id = bus.getId();
        String seatNumber = "1";
        Mockito.when(busService.makeSeatAvailable(id, seatNumber)).thenReturn(false);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .put("/api/buses/{id}/seats/{seatNumber}?reserve=false", String.valueOf(id), seatNumber)
                .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .body(Matchers.equalTo("Seat was already available"));
    }

    @Test
    void whenMakingAllSeatsOfAKnownBusAvailable_thenReturnSuccess() throws IOException {
        Long id = bus.getId();
        Mockito.when(busService.makeAllSeatsAvailable(id)).thenReturn(true);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .put("/api/buses/{id}/seats/all/available", String.valueOf(id))
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(Matchers.equalTo("All seats made available successfully"));
    }

    @Test
    void whenMakingAllSeatsOfAnUnknownBusAvailable_thenReturnNotFound() throws IOException {
        Long nonExistentId = -1L;
        Mockito.when(busService.makeAllSeatsAvailable(nonExistentId)).thenReturn(false);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .put("/api/buses/{nonExistentId}/seats/all/available", String.valueOf(nonExistentId))
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void whenCheckingIfSeatIsAvailable_thenReturnTrue() throws IOException {
        Long id = bus.getId();
        String seatNumber = "1";
        Mockito.when(busService.isSeatAvailable(id, seatNumber)).thenReturn(true);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/buses/{id}/seats/{seatNumber}/available", String.valueOf(id), seatNumber)
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(Matchers.equalTo("true"));
    }

}
