package tqs.carsassured;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.carsassured.data.CarRepository;
import tqs.carsassured.model.Car;
import tqs.carsassured.services.CarManagerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carService;

    @BeforeEach
    public void setUp() {

        Car ferrari = new Car("Ferrari", "458 Italia");
        ferrari.setCarId(1l);

        Car bmw = new Car("BMW", "M3");
        Car lamborghini = new Car("Lamborghini", "Aventador");

        List<Car> allCars = Arrays.asList(ferrari, bmw, lamborghini);

        Mockito.when(carRepository.findByCarId(ferrari.getCarId())).thenReturn(ferrari);
        Mockito.when(carRepository.findByCarId(bmw.getCarId())).thenReturn(null);
        Mockito.when(carRepository.findByCarId(lamborghini.getCarId())).thenReturn(null);
        Mockito.when(carRepository.findAll()).thenReturn(allCars);
        Mockito.when(carRepository.findById(-99L)).thenReturn(Optional.empty());
    }


    @Test
     void whenValidId_thenCarShouldBeFound() {
        Car fromDb = carService.getCarDetails(1L).orElse(null);
        assertThat(fromDb.getMaker()).isEqualTo("Ferrari");
        assertThat(fromDb.getModel()).isEqualTo("458 Italia");

        verifyFindByIdIsCalledOnce();
    }

    @Test
     void whenInvalidId_thenCarShouldNotBeFound() {
        Car fromDb = carService.getCarDetails(-99L).orElse(null);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
     void given3Cars_whengetAll_thenReturn3Records() {
        Car ferrari = new Car("Ferrari", "458 Italia");
        Car bmw = new Car("BMW", "M3");
        Car lamborghini = new Car("Lamborghini", "Aventador");

        List<Car> allCars = carService.getAllCars();
        verifyFindAllCarsIsCalledOnce();
        assertThat(allCars).hasSize(3).extracting(Car::getMaker).contains(ferrari.getMaker(), bmw.getMaker(), lamborghini.getMaker());
        assertThat(allCars).hasSize(3).extracting(Car::getModel).contains(ferrari.getModel(), bmw.getModel(), lamborghini.getModel());
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarId(Mockito.anyLong());
    }

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }
}
