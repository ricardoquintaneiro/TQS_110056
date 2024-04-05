package tqs.carscontainer.services;

import org.springframework.stereotype.Service;

import tqs.carscontainer.data.CarRepository;
import tqs.carscontainer.model.Car;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {

    final
    CarRepository carRepository;

    public CarManagerService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    public Car save(Car oneCar) {
        return carRepository.save(oneCar);
    }

    public List<Car> getAllCars() {

        return carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long carId) {
        return Optional.ofNullable(carRepository.findByCarId(carId));
    }
}
