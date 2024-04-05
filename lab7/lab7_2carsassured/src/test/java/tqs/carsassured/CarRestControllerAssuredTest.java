package tqs.carsassured;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import tqs.carsassured.boundary.CarController;
import tqs.carsassured.model.Car;
import tqs.carsassured.services.CarManagerService;

@WebMvcTest(CarController.class)
public class CarRestControllerAssuredTest {

    @MockBean
    private CarManagerService carManagerService;

    @Autowired
    private MockMvc mockMvc;

    private Car ferrari;

    @BeforeEach
    void setUp() throws Exception {
        ferrari = new Car(1L, "Ferrari", "458 Italia");
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void whenPostCar_thenCreateCar() throws IOException {
        when(carManagerService.save(Mockito.any())).thenReturn(ferrari);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(JsonUtils.toJson(ferrari))
                .when()
                    .post("/api/cars")
                .then()
                    .body("maker", Matchers.equalTo(ferrari.getMaker()))
                    .body("model", Matchers.equalTo(ferrari.getModel()));
    }

    @Test
    void givenCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car porsche = new Car("Porsche", "911 Turbo");
        Car bmw = new Car(7l, "BMW", "M3");
        Car car = new Car();
        List<Car> allCars = Arrays.asList(ferrari, porsche, bmw, car);

        when(carManagerService.getAllCars()).thenReturn(allCars);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .get("/api/cars")
                .then()
                    .body("$", Matchers.hasSize(4))
                    .body("[0].maker", Matchers.equalTo(ferrari.getMaker()))
                    .body("[0].model", Matchers.equalTo(ferrari.getModel()))
                    .body("[1].maker", Matchers.equalTo(porsche.getMaker()))
                    .body("[1].model", Matchers.equalTo(porsche.getModel()))
                    .body("[2].maker", Matchers.equalTo(bmw.getMaker()))
                    .body("[2].model", Matchers.equalTo(bmw.getModel()))
                    .body("[3].maker", Matchers.equalTo(car.getMaker()))
                    .body("[3].model", Matchers.equalTo(car.getModel()));
    }

    @Test
    void whenGetCarById_thenReturnCar() throws Exception {
        when(carManagerService.getCarDetails(1L)).thenReturn(Optional.of(ferrari));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .get("/api/cars/1")
                .then()
                    .body("maker", Matchers.equalTo(ferrari.getMaker()))
                    .body("model", Matchers.equalTo(ferrari.getModel()));
    }

}