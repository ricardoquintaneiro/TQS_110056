package pt.ua.tqs110056.busticketbackend.integration;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;
import pt.ua.tqs110056.busticketbackend.model.City;
import pt.ua.tqs110056.busticketbackend.repository.CityRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CityRestControllerIT {
    
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
    private CityRepository repository;

    private City city;

    @BeforeEach
    public void setUp() {
        RestAssured.port = randomServerPort;
        repository.deleteAll();
        city = new City("Aveiro", "Portugal");
        repository.saveAndFlush(city);
    }

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    void whenGetAllCities_thenReturnJsonArray() {
        RestAssured
            .when()
                .get("/api/cities")
            .then()
                .statusCode(200)
                .body("name", Matchers.hasItem(city.getName()))
                .body("country", Matchers.hasItem(city.getCountry()));
    }

    @Test
    void whenGetCityById_thenReturnCity() {
        RestAssured
            .given()
                .pathParam("id", city.getId().intValue())
            .when()
                .get("/api/cities/{id}")
            .then()
                .statusCode(200)
                .body("name", equalTo("Aveiro"))
                .body("country", equalTo("Portugal"));
    }

    @Test
    void whenGetCityByUnknownId_thenReturnNotFound() {
        RestAssured
            .given()
                .pathParam("id", -1)
            .when()
                .get("/api/cities/{id}")
            .then()
                .statusCode(404);
    }

    @Test
    void whenGetCityByNameAndCountry_thenReturnCity() {
        RestAssured
            .given()
                .queryParam("name", "Aveiro")
                .queryParam("country", "Portugal")
            .when()
                .get("/api/cities/search")
            .then()
                .statusCode(200)
                .body("name", equalTo("Aveiro"))
                .body("country", equalTo("Portugal"));
    }

    @Test
    void whenGetCityByUnknownNameAndCountry_thenReturnNotFound() {
        RestAssured
        .given()
            .queryParam("name", "Unknown")
            .queryParam("country", "Unknown")
        .when()
            .get("/api/cities/search")
        .then()
            .statusCode(404);
    }
}
