package pt.ua.tqs110056.busticketbackend.controller;

import static org.hamcrest.Matchers.emptyString;

import java.util.ArrayList;
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
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import pt.ua.tqs110056.busticketbackend.model.City;
import pt.ua.tqs110056.busticketbackend.service.CityService;

@WebMvcTest(CityController.class)
class CityControllerTest {

    @MockBean
    private CityService cityService;

    @Autowired
    private MockMvc mockMvc;

    private City aveiro;

    @BeforeEach
    void setUp() throws Exception {
        aveiro = new City("Aveiro", "Portugal");
        aveiro.setId(1L);
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void whenGetAllCities_thenReturnJsonArray() throws Exception {
        List<City> cities = new ArrayList<>();
        City porto = new City("Porto", "Portugal");
        cities.add(aveiro);
        cities.add(porto);
        Mockito.when(cityService.getAllCities()).thenReturn(cities);

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/cities")
                .then()
                    .status(HttpStatus.OK)
                    .body("name", Matchers.hasItem(aveiro.getName()))
                    .body("country", Matchers.hasItem(aveiro.getCountry()));
    }

    @Test
    void whenGetCityById_thenReturnCity() throws Exception {
        Mockito.when(cityService.getCityById(1L)).thenReturn(Optional.of(aveiro));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/cities/1")
                .then()
                    .status(HttpStatus.OK)
                    .body("name", Matchers.equalTo(aveiro.getName()))
                    .body("country", Matchers.equalTo(aveiro.getCountry()));
    }

    @Test
    void whenGetCityByUnknownId_thenReturnNotFound() throws Exception {
        Mockito.when(cityService.getCityById(-1L)).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/cities/-1")
                .then()
                    .status(HttpStatus.NOT_FOUND)
                    .body(emptyString());
    }

    @Test
    void whenGetCityByNameAndCountry_thenReturnCity() throws Exception {
        Mockito.when(cityService.getCityByNameAndCountry(aveiro.getName(), aveiro.getCountry())).thenReturn(Optional.of(aveiro));

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/cities/search?name=Aveiro&country=Portugal")
                .then()
                    .status(HttpStatus.OK)
                    .body("name", Matchers.equalTo(aveiro.getName()))
                    .body("country", Matchers.equalTo(aveiro.getCountry()));
    }

    @Test
    void whenGetCityByUnknownNameAndCountry_thenReturnNotFound() throws Exception {
        Mockito.when(cityService.getCityByNameAndCountry("Unknown", "Unknown")).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                    .mockMvc(mockMvc)
                .when()
                    .get("/api/cities/search?name=Unknown&country=Unknown")
                .then()
                    .status(HttpStatus.NOT_FOUND)
                    .body(emptyString());
    }

}