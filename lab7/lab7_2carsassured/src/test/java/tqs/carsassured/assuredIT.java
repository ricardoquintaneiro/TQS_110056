package tqs.carsassured;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import tqs.carsassured.boundary.CarController;
import tqs.carsassured.services.CarManagerService;

@WebMvcTest(CarController.class)
public class assuredIT {

    @MockBean
    private CarManagerService carManagerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetCars_thenRetrieveCars() {

        RestAssuredMockMvc
                .given()
                .mockMvc(mockMvc)
                .when()
                .get("/api/cars");
    }

}