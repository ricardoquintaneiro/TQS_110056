package tqs.cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tqs.cars.model.Car;
import tqs.cars.data.CarRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarRepositoryTest {

    private Car ferrari;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        ferrari = new Car(1l, "Ferrari", "458 Italia");
    }

    @SuppressWarnings("null")
    @Test
    void whenFindFerrariById_thenReturnFerrari() {
        entityManager.persistAndFlush(ferrari);
        Car found = carRepository.findById(ferrari.getCarId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getCarId()).isEqualTo(ferrari.getCarId());
        assertThat(found.getMaker()).isEqualTo(ferrari.getMaker());
        assertThat(found.getModel()).isEqualTo(ferrari.getModel());
    }

    @Test
    void whenInvalidCarName_thenReturnNull() {
        Car fromDb = carRepository.findByName("Does Not Exist");
        assertThat(fromDb).isNull();
    }

    @Test
    void whenFindEmployedByExistingId_thenReturnCar() {
        Car emp = new Car("test", "test@deti.com");
        entityManager.persistAndFlush(emp);

        Car fromDb = carRepository.findById(emp.getCarId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getEmail()).isEqualTo(emp.getEmail());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Car fromDb = carRepository.findById(-111L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car alex = new Car("alex", "alex@deti.com");
        Car ron = new Car("ron", "ron@deti.com");
        Car bob = new Car("bob", "bob@deti.com");

        entityManager.persist(alex);
        entityManager.persist(bob);
        entityManager.persist(ron);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(3).extracting(Car::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
    }

}