package tqs.carscontainer.boundary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.carscontainer.model.Car;
import tqs.carscontainer.services.CarManagerService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class CarController {

   private final CarManagerService carManagerService;

   public CarController(CarManagerService injectedCarManagerService) {
      this.carManagerService = injectedCarManagerService;
   }

   @PostMapping("/cars")
   public ResponseEntity<Car> createCar(@RequestBody Car oneCar) {
      HttpStatus status = HttpStatus.CREATED;
      Car saved = carManagerService.save(oneCar);
      return new ResponseEntity<>(saved, status);
   }

   @GetMapping(path = "/cars", produces = "application/json")
   public List<Car> getAllCars() {
      return carManagerService.getAllCars();
   }

   @GetMapping("/cars/{id}")
   public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId)
         throws ResourceNotFoundException {
      Car car = carManagerService.getCarDetails(carId)
            .orElseThrow(() -> new ResourceNotFoundException("Car not found for id: " + carId));
      return ResponseEntity.ok().body(car);
   }

}
