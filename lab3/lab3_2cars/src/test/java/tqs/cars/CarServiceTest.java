package tqs.cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.cars.data.Car;
import tqs.cars.data.CarRepository;
import tqs.cars.service.CarServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    // mocking the responses of the repository (i.e., no database will be used)
    // lenient is required because we load more expectations in the setup
    // than those used in some tests. As an alternative, the expectations
    // could move into each test method and be trimmed (no need for lenient then)
    @Mock( lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    public void setUp() {

        //these expectations provide an alternative to the use of the repository
        Car john = new Car("john", "john@deti.com");
        john.setId(111L);

        Car bob = new Car("bob", "bob@deti.com");
        Car alex = new Car("alex", "alex@deti.com");

        List<Car> allCars = Arrays.asList(john, bob, alex);

        Mockito.when(carRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(carRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(carRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(carRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(carRepository.findAll()).thenReturn(allCars);
        Mockito.when(carRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
     void whenSearchValidName_thenCarShouldBeFound() {
        String name = "alex";
        Car found = carService.getCarByName(name);

        assertThat(found.getName()).isEqualTo(name);
    }

    @Test
     void whenSearchInvalidName_thenCarShouldNotBeFound() {
        Car fromDb = carService.getCarByName("wrong_name");
        assertThat(fromDb).isNull();

        verifyFindByNameIsCalledOnce("wrong_name");
    }

    @Test
     void whenValidName_thenCarShouldExist() {
        boolean doesCarExist = carService.exists("john");
        assertThat(doesCarExist).isTrue();

        verifyFindByNameIsCalledOnce("john");
    }

    @Test
     void whenNonExistingName_thenCarShouldNotExist() {
        boolean doesCarExist = carService.exists("some_name");
        assertThat(doesCarExist).isFalse();
        verifyFindByNameIsCalledOnce("some_name");
    }

    @Test
     void whenValidId_thenCarShouldBeFound() {
        Car fromDb = carService.getCarById(111L);
        assertThat(fromDb.getName()).isEqualTo("john");

        verifyFindByIdIsCalledOnce();
    }

    @Test
     void whenInValidId_thenCarShouldNotBeFound() {
        Car fromDb = carService.getCarById(-99L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
     void given3Cars_whengetAll_thenReturn3Records() {
        Car alex = new Car("alex", "alex@deti.ua.pt");
        Car john = new Car("john", "john@deti.ua.pt");
        Car bob = new Car("bob", "bob@deti.ua.pt");

        List<Car> allCars = carService.getAllCars();
        verifyFindAllCarsIsCalledOnce();
        assertThat(allCars).hasSize(3).extracting(Car::getName).contains(alex.getName(), john.getName(), bob.getName());
    }

    private void verifyFindByNameIsCalledOnce(String name) {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByName(name);
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }
}
