package pt.ua.tqs110056.busticketbackend.integration;

import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.hamcrest.Matchers;
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

import io.restassured.RestAssured;
import pt.ua.tqs110056.busticketbackend.model.Bus;
import pt.ua.tqs110056.busticketbackend.model.BusSeat;
import pt.ua.tqs110056.busticketbackend.model.BusSeatType;
import pt.ua.tqs110056.busticketbackend.repository.BusRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
public class BusRestControllerIT {
    
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
    private BusRepository repository;

    private Bus bus;
    private BusSeat seat1;
    private BusSeat seat2;
    private BusSeat seat3;
    private List<BusSeat> seats;

    @BeforeEach
    public void setUp() {
        RestAssured.port = randomServerPort;
        repository.deleteAll();
        createBus();
    }

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    void whenGetBusById_thenAPIReturnsBus() {
        RestAssured
            .when()
                .get("/api/buses/{id}", bus.getId().intValue())
            .then()
                .statusCode(200)
                .body("id", equalTo(bus.getId().intValue()))
                .body("plate", equalTo(bus.getPlate()))
                .body("model", equalTo(bus.getModel()));
    }

    @Test
    void whenGetAllBuses_thenAPIReturnsAllBuses() {
        seat3 = new BusSeat(BusSeatType.REGULAR, "1B");
        Bus bus2 = new Bus("ACBD13", "Modelo 2", List.of(seat3));
        repository.saveAndFlush(bus2);

        RestAssured
            .when()
                .get("/api/buses")
            .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(2));
    }

    @Test
    void whenGetBusSeatsByType_thenReturnBusSeats() {
        RestAssured
            .given()
                .pathParam("id", bus.getId().intValue())
                .queryParam("type", BusSeatType.REGULAR.toString())
            .when()
                .get("/api/buses/{id}/seats")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", Matchers.equalTo(1));
    }

    @Test
    void whenUpdateSeatAvailability_thenReturnSuccess() {
        RestAssured
            .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", bus.getId().intValue())
                .pathParam("seatNumber", "1A")
                .queryParam("reserve", true)
            .when()
                .put("/api/buses/{id}/seats/{seatNumber}")
            .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    void whenMakeAllSeatsAvailable_thenReturnSuccess() {
        RestAssured
            .given()
                .pathParam("id", bus.getId().intValue())
            .when()
                .put("/api/buses/{id}/seats/all/available")
            .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void whenIsSeatAvailable_thenReturnAvailability() {
        RestAssured
            .given()
                .pathParam("id", bus.getId().intValue())
                .pathParam("seatNumber", "1A")
            .when()
                .get("/api/buses/{id}/seats/{seatNumber}/available")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo("true")); // Adjust the expectation based on the expected response body
    }

    private void createBus() {
        seat1 = new BusSeat(BusSeatType.REGULAR, "1A");
        seat2 = new BusSeat(BusSeatType.PREMIUM, "2A");
        seats = List.of(seat1, seat2);
        bus = new Bus("ACBD12", "Modelo 1", seats);
        repository.saveAndFlush(bus);
    }
}
