package pt.ua.tqs110056.busticketbackend.integration;

import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;
import pt.ua.tqs110056.busticketbackend.model.Bus;
import pt.ua.tqs110056.busticketbackend.model.BusSeat;
import pt.ua.tqs110056.busticketbackend.model.BusSeatType;
import pt.ua.tqs110056.busticketbackend.model.City;
import pt.ua.tqs110056.busticketbackend.model.Trip;
import pt.ua.tqs110056.busticketbackend.repository.TripRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
public class TripRestControllerIT {

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
    private TripRepository tripRepository;

    private Trip trip;
    private City origin;
    private City destination;
    private Bus bus;
    private BusSeat busSeat1;
    private BusSeat busSeat2;
    private List<BusSeat> busSeats;

    @BeforeEach
    public void setUp() {
        RestAssured.port = randomServerPort;
        tripRepository.deleteAll();
        createTrip();
    }

    @AfterEach
    public void resetDb() {
        tripRepository.deleteAll();
    }

    @Test
    void whenFindTripByOriginIdAndDestinationId_thenReturnStatus200() {
        RestAssured
            .given()
                    .queryParam("originId", trip.getOrigin().getId().intValue())
                    .queryParam("destinationId", trip.getDestination().getId().intValue())
            .when()
                    .get("/api/trips")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("[0].id", equalTo(trip.getId().intValue()));
    }

    @Test
    void whenFindWithNoParameters_thenReturnAllTrips() {
        RestAssured
            .when()
                    .get("/api/trips")
            .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("[0].id", equalTo(trip.getId().intValue()));
    }

    @Test
    void whenFindByOnlyOriginId_thenReturnStatus400() {
        RestAssured
            .given()
                    .queryParam("originId", trip.getOrigin().getId().intValue())
            .when()
                    .get("/api/trips")
            .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private void createTrip() {
        origin = new City("Aveiro", "Portugal");
        destination = new City("Porto", "Portugal");
        busSeat1 = new BusSeat(BusSeatType.PREMIUM, "123");
        busSeat2 = new BusSeat(BusSeatType.PRIORITY, "124");
        busSeats = List.of(busSeat1, busSeat2);
        bus = new Bus("ABCDEF", "One Model", busSeats);
        trip = new Trip(origin, destination, bus, LocalDateTime.now().plusDays(2), Duration.ofMinutes(40), BigDecimal.valueOf(5.2));
        tripRepository.saveAndFlush(trip);
    }
}