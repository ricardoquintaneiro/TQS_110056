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
    void whenFindCarByExistingId_thenReturnCar() {
        entityManager.persistAndFlush(ferrari);
        Car fromDb = carRepository.findById(ferrari.getCarId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getCarId()).isEqualTo(ferrari.getCarId());
        assertThat(fromDb.getMaker()).isEqualTo(ferrari.getMaker());
        assertThat(fromDb.getModel()).isEqualTo(ferrari.getModel());
    }

    @Test
    void whenInvalidCarId_thenReturnNull() {
        Car fromDb = carRepository.findById(2l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Car fromDb = carRepository.findById(-111L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car bmw = new Car(2l, "BMW", "M3");
        Car audi = new Car(3l, "Audi", "A4");

        entityManager.persist(ferrari);
        entityManager.persist(bmw);
        entityManager.persist(audi);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(3).extracting(Car::getMaker).containsOnly(ferrari.getMaker(), bmw.getMaker(), audi.getMaker());
    }

}