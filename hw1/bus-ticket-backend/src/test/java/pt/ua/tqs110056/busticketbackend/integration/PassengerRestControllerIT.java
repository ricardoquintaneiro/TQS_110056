package pt.ua.tqs110056.busticketbackend.integration;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;
import pt.ua.tqs110056.busticketbackend.model.Passenger;
import pt.ua.tqs110056.busticketbackend.repository.PassengerRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class PassengerRestControllerIT {

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
    private PassengerRepository repository;

    private Passenger passenger;

    @BeforeEach
    public void setUp() {
        RestAssured.port = randomServerPort;
        repository.deleteAll();
        passenger = new Passenger("André André", "aa@example.com", "+351123456789");
        repository.saveAndFlush(passenger);
    }

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    void whenGetPassengerById_thenReturnStatus200() {
        RestAssured
            .given()
                .pathParam("id", passenger.getId().intValue())
            .when()
                .get("/api/passengers/{id}")
            .then()
                .statusCode(200)
                .body("name", equalTo(passenger.getName()))
                .body("email", equalTo(passenger.getEmail()))
                .body("phoneNumber", equalTo(passenger.getPhoneNumber()));
    }

    @Test
    void whenGetPassengerByUnknownId_thenReturnStatus404() {
        RestAssured
            .given()
                .pathParam("id", passenger.getId().intValue() + 1)
            .when()
                .get("/api/passengers/{id}")
            .then()
                .statusCode(404);
    }

    @Test
    void whenSavePassenger_thenReturnStatus201() {
        RestAssured
            .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(passenger)
            .when()
                .post("/api/passengers")
            .then()
                .statusCode(201);
    }

    @Test
    void whenSaveInvalidPassenger_thenReturnStatus400() {
        RestAssured
            .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new Passenger())
            .when()
                .post("/api/passengers")
            .then()
                .statusCode(400);
    }

    @Test
    void whenDeletePassengerById_thenReturnStatus204() {
        RestAssured
            .given()
                .pathParam("id", passenger.getId().intValue())
            .when()
                .delete("/api/passengers/{id}")
            .then()
                .statusCode(204);
    }
}