package pt.ua.tqs110056.busticketbackend.controller;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
import pt.ua.tqs110056.busticketbackend.JsonUtils;
import pt.ua.tqs110056.busticketbackend.model.Bus;
import pt.ua.tqs110056.busticketbackend.model.BusSeat;
import pt.ua.tqs110056.busticketbackend.model.BusSeatType;
import pt.ua.tqs110056.busticketbackend.model.City;
import pt.ua.tqs110056.busticketbackend.model.CreditCard;
import pt.ua.tqs110056.busticketbackend.model.CreditCardType;
import pt.ua.tqs110056.busticketbackend.model.Passenger;
import pt.ua.tqs110056.busticketbackend.model.Reservation;
import pt.ua.tqs110056.busticketbackend.model.ReservationStatus;
import pt.ua.tqs110056.busticketbackend.model.Trip;
import pt.ua.tqs110056.busticketbackend.service.ReservationService;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private MockMvc mockMvc;

    private Reservation reservation;

    @BeforeEach
    void setUp() throws Exception {
        reservation = new Reservation();
        reservation.setId(1L);
        Passenger passenger = new Passenger("Geny Catamo", "geny@example.com", "+351123456789");
        CreditCard creditCard = new CreditCard(CreditCardType.AMERICAN_EXPRESS, "378282246310005", "123", LocalDate.now().plusYears(1));
        City origin = new City("Aveiro", "Portugal");
        City destination = new City("Porto", "Portugal");
        BusSeat busSeat1 = new BusSeat(BusSeatType.PREMIUM, "123");
        BusSeat busSeat2 = new BusSeat(BusSeatType.PRIORITY, "124");
        List<BusSeat> busSeats = List.of(busSeat1, busSeat2);
        Bus bus = new Bus("ABCDEF", "One Model", busSeats);
        Trip trip = new Trip(origin, destination, bus, LocalDateTime.now().plusDays(2), Duration.ofMinutes(40), BigDecimal.valueOf(5.2));
        reservation.setPassenger(passenger);
        reservation.setCreditCard(creditCard);
        reservation.setTrip(trip);
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setSeat(busSeat2);

        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void whenCreateReservation_thenReturnStatus201() throws Exception {
        Mockito.when(reservationService.createReservation(Mockito.any())).thenReturn(reservation);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(JsonUtils.toJson(reservation))
                .when()
                    .post("/reservations")
                .then()
                    .status(HttpStatus.CREATED)
                    .body("id", Matchers.equalTo(reservation.getId().intValue()))
                    .body("status", Matchers.equalTo(reservation.getStatus().toString()))
                    .body("reservationTime", Matchers.equalTo(reservation.getReservationTime().toString()))
                    .body("seat.number", Matchers.equalTo(reservation.getSeat().getNumber()))
                    .body("passenger.name", Matchers.equalTo(reservation.getPassenger().getName()))
                    .body("creditCard.type", Matchers.equalTo(reservation.getCreditCard().getType().toString()));
    }

    @Test
    void whenCreateInvalidReservation_thenReturnStatus400() throws Exception {
        Reservation invalidReservation = new Reservation();
        Mockito.when(reservationService.createReservation(Mockito.any())).thenReturn(null);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(JsonUtils.toJson(invalidReservation))
                .when()
                    .post("/reservations")
                .then()
                    .status(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenGetReservationById_thenReturnStatus200() throws Exception {
        Mockito.when(reservationService.getReservationById(1L)).thenReturn(Optional.of(reservation));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/reservations/1")
                .then()
                    .status(HttpStatus.OK)
                    .body("id", Matchers.equalTo(reservation.getId().intValue()));
    }

    @Test
    void whenGetReservationByUnknownId_thenReturnStatus404() throws Exception {
        Mockito.when(reservationService.getReservationById(10L)).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/reservations/10")
                .then()
                    .status(HttpStatus.NOT_FOUND);
    }

    @Test
    void whendeleteReservationById_thenNoContent() throws Exception {
        Mockito.doNothing().when(reservationService).deleteReservationById(1L);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .delete("/reservations/1")
                .then()
                    .status(HttpStatus.NO_CONTENT);
    }

    @Test
    void whenCancelReservation_thenReturnStatus200() throws Exception {
        Mockito.when(reservationService.cancelReservation(1L)).thenReturn(true);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .put("/reservations/1?action=cancel")
                .then()
                    .status(HttpStatus.OK)
                    .body(Matchers.equalTo("Reservation cancelled successfully"));
    }

    @Test
    void whenConfirmReservation_thenReturnStatus200() throws Exception {
        Mockito.when(reservationService.confirmReservation(1L)).thenReturn(true);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .put("/reservations/1?action=confirm")
                .then()
                    .status(HttpStatus.OK)
                    .body(Matchers.equalTo("Reservation confirmed successfully"));
    }

    @Test
    void whenInvalidAction_thenReturnStatus400() throws Exception {
        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .put("/reservations/1?action=invalid")
                .then()
                    .status(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenGetAllReservations_thenReturnAllReservations() throws Exception {
        Mockito.when(reservationService.getAllReservations()).thenReturn(List.of(reservation));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/reservations")
                .then()
                    .status(HttpStatus.OK)
                    .body("[0].id", Matchers.equalTo(reservation.getId().intValue()));
    }

    @Test
    void whenGetReservationsByPassengerId_thenReturnReservations() throws Exception {
        Mockito.when(reservationService.getReservationsByPassengerId(1L)).thenReturn(List.of(reservation));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/reservations/passenger/1")
                .then()
                    .status(HttpStatus.OK)
                    .body("[0].id", Matchers.equalTo(reservation.getId().intValue()));
    }

    @Test
    void whenGetReservationsByTripId_thenReturnReservations() throws Exception {
        Mockito.when(reservationService.getReservationsByTripId(1L)).thenReturn(List.of(reservation));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/reservations/trip/1")
                .then()
                    .status(HttpStatus.OK)
                    .body("[0].id", Matchers.equalTo(reservation.getId().intValue()));
    }
}