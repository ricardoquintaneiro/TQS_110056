package tqs.carscontainer;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;
import tqs.carscontainer.model.Car;
import tqs.carscontainer.data.CarRepository;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CarRestControllerIT {
    
	@Container
	public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
			.withUsername("ricardo")
			.withPassword("teste")
			.withDatabaseName("testcontainers");
	
	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
	}

    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private CarRepository repository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = randomServerPort;
        repository.deleteAll();
    }

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
     void whenValidInput_thenCreateCar() {
        Car pagani = new Car("Pagani", "Zonda");
        RestAssured
            .given()
                .contentType("application/json")
                .body(pagani)
            .when()
                .post("/api/cars")
            .then()
                .statusCode(201);
    }

    @Test
     void givenCars_whenGetCars_thenStatus200()  {
        createTestCar("Mercedes-Benz", "AMG-GT Black Series");
        createTestCar("Porsche", "911 GT3 RS");

        RestAssured
            .when()
                .get("/api/cars")
            .then()
                .statusCode(200)
                .body("maker", Matchers.hasItems("Mercedes-Benz", "Porsche"))
                .body("model", Matchers.hasItems("AMG-GT Black Series", "911 GT3 RS"));

    }


    private void createTestCar(String maker, String model) {
        Car car = new Car(maker, model);
        repository.saveAndFlush(car);
    }
}
