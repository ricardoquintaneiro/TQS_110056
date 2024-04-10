package pt.ua.tqs110056.busticketbackend.integration;

import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.restassured.RestAssured;
import pt.ua.tqs110056.busticketbackend.model.Bus;
import pt.ua.tqs110056.busticketbackend.model.BusSeat;
import pt.ua.tqs110056.busticketbackend.model.BusSeatType;
import pt.ua.tqs110056.busticketbackend.model.City;
import pt.ua.tqs110056.busticketbackend.model.CreditCard;
import pt.ua.tqs110056.busticketbackend.model.CreditCardType;
import pt.ua.tqs110056.busticketbackend.model.Passenger;
import pt.ua.tqs110056.busticketbackend.model.Reservation;
import pt.ua.tqs110056.busticketbackend.model.Trip;
import pt.ua.tqs110056.busticketbackend.repository.BusRepository;
import pt.ua.tqs110056.busticketbackend.repository.BusSeatRepository;
import pt.ua.tqs110056.busticketbackend.repository.ReservationRepository;
import pt.ua.tqs110056.busticketbackend.repository.TripRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
public class ReservationRestControllerIT {

    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql:8.1")
            .withUsername("ricardo")
            .withPassword("homework1")
            .withDatabaseName("bustickettestcontainers");
    
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TripRepository tripRepository;

    private Passenger passenger;
    private CreditCard creditCard;
    private Trip trip;
    private City origin;
    private City destination;
    private Bus bus;
    private BusSeat busSeat1;
    private BusSeat busSeat2;
    private List<BusSeat> busSeats;
    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        RestAssured.port = randomServerPort;
        reservationRepository.deleteAll();
        tripRepository.deleteAll();
        createReservation();
    }

    @AfterEach
    public void resetDb() {
        reservationRepository.deleteAll();
        tripRepository.deleteAll();
    }

    @Test
    void whenCreateReservation_thenReturnStatus201() throws JsonProcessingException {
        reservationRepository.deleteAll();
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        String reservationJson = mapper.writeValueAsString(reservation);

        RestAssured
            .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationJson)
            .when()
                .post("/api/reservations")
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("status", equalTo(reservation.getStatus().toString()))
                .body("seat.number", equalTo(reservation.getSeat().getNumber()))
                .body("passenger.name", equalTo(reservation.getPassenger().getName()))
                .body("creditCard.type", equalTo(reservation.getCreditCard().getType().toString()));
    }

    @Test
    void whenCreateInvalidReservation_thenReturnStatus400() {
        Reservation invalidReservation = new Reservation();
        reservationRepository.saveAndFlush(invalidReservation);

        RestAssured
            .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(invalidReservation)
            .when()
                .post("/api/reservations")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void whenGetReservationById_thenReturnStatus200() {
        RestAssured
            .given()
                .pathParam("id", reservation.getId().intValue())
            .when()
                .get("/api/reservations/{id}")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(reservation.getId().intValue()));
    }

    @Test
    void whenGetReservationByUnknownId_thenReturnStatus404() {
        RestAssured
            .given()
                .pathParam("id", reservation.getId().intValue() + 1)
            .when()
                .get("/api/reservations/{id}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void whenDeleteReservationById_thenNoContent() {
        RestAssured
            .given()
                .pathParam("id", reservation.getId().intValue())
            .when()
                .delete("/api/reservations/{id}")
            .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        
        Optional<Reservation> reservationOptional = reservationRepository.findById(this.reservation.getId());
        assert(reservationOptional.isEmpty());
    }
    
    private void createReservation() {
        passenger = new Passenger("Geny Catamo", "geny@example.com", "+351123456789");
        creditCard = new CreditCard(CreditCardType.AMERICAN_EXPRESS, "378282246310005", "123", LocalDate.now().plusYears(1));
        origin = new City("Aveiro", "Portugal");
        destination = new City("Porto", "Portugal");
        busSeat1 = new BusSeat(BusSeatType.PREMIUM, "123");
        busSeat2 = new BusSeat(BusSeatType.PRIORITY, "124");
        busSeats = List.of(busSeat1, busSeat2);
        bus = new Bus("ABCDEF", "One Model", busSeats);
        trip = new Trip(origin, destination, bus, LocalDateTime.now().plusDays(2), Duration.ofMinutes(40), BigDecimal.valueOf(5.2));
        tripRepository.saveAndFlush(trip);
        reservation = new Reservation(passenger, trip, busSeat2, creditCard);
        reservationRepository.saveAndFlush(reservation);
    }
}