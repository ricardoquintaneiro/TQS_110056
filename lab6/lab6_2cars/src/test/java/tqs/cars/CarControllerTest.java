package tqs.cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tqs.cars.boundary.CarController;
import tqs.cars.model.Car;
import tqs.cars.services.CarManagerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
class CarControllerTest {

    private Car ferrari;

    @Autowired
    private MockMvc mvc; // entry point to the web framework

    // inject required beans as "mockeable" objects
    // note that @AutoWire would result in NoSuchBeanDefinitionException
    @MockBean
    private CarManagerService carManagerService;

    @BeforeEach
    public void setUp() throws Exception {
        ferrari = new Car(1L, "Ferrari", "458 Italia");
    }

    @SuppressWarnings("null")
    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        when(carManagerService.save(Mockito.any())).thenReturn(ferrari);

        mvc.perform(post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(ferrari)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("Ferrari")))
                .andExpect(jsonPath("$.model", is("458 Italia")));

        verify(carManagerService, times(1)).save(Mockito.any());
    }

    @SuppressWarnings("null")
    @Test
    void givenCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car porsche = new Car("Porsche", "911 Turbo");
        Car bmw = new Car(7l, "BMW", "M3");
        Car car = new Car();
        List<Car> allCars = Arrays.asList(ferrari, porsche, bmw, car);

        when(carManagerService.getAllCars()).thenReturn(allCars);

        mvc.perform(get("/api/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].carId", is(ferrari.getCarId().intValue())))
                .andExpect(jsonPath("$[0].maker", is(ferrari.getMaker())))
                .andExpect(jsonPath("$[0].model", is(ferrari.getModel())))
                .andExpect(jsonPath("$[1].maker", is(porsche.getMaker())))
                .andExpect(jsonPath("$[1].model", is(porsche.getModel())))
                .andExpect(jsonPath("$[2].carId", is(bmw.getCarId().intValue())))
                .andExpect(jsonPath("$[2].maker", is(bmw.getMaker())))
                .andExpect(jsonPath("$[2].model", is(bmw.getModel())))
                .andExpect(jsonPath("$[3].carId", nullValue()));

        verify(carManagerService, times(1)).getAllCars();
    }

    @SuppressWarnings("null")
    @Test
    void whenGetCarById_thenReturnCar() throws Exception {
        when(carManagerService.getCarDetails(1L)).thenReturn(Optional.of(ferrari));

        mvc.perform(get("/api/cars/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is(ferrari.getMaker())))
                .andExpect(jsonPath("$.model", is(ferrari.getModel())));

        verify(carManagerService, times(1)).getCarDetails(1L);
    }
}