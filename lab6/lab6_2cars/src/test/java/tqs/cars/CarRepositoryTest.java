package tqs.cars;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.cars.data.CarRepository;
import tqs.cars.model.Car;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @SuppressWarnings("null")
    @Test
    void whenFindCarByExistingId_thenReturnCar() {
        Car ferrari = new Car("Ferrari", "458 Italia");
        entityManager.persistAndFlush(ferrari);
        Car fromDb = carRepository.findById(ferrari.getCarId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getCarId()).isEqualTo(ferrari.getCarId());
        assertThat(fromDb.getMaker()).isEqualTo(ferrari.getMaker());
        assertThat(fromDb.getModel()).isEqualTo(ferrari.getModel());
    }

    @Test
    void whenFindByInvalidCarId_thenReturnNull() {
        Car fromDb = carRepository.findById(-1L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car bmw = new Car("BMW", "M3");
        Car audi = new Car("Audi", "A4");

        entityManager.persist(bmw);
        entityManager.persist(audi);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(2).extracting(Car::getMaker).containsOnly(bmw.getMaker(), audi.getMaker());
    }

}