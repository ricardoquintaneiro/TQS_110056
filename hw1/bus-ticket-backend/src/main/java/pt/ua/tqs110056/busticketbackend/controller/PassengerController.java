package pt.ua.tqs110056.busticketbackend.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.tqs110056.busticketbackend.model.Passenger;
import pt.ua.tqs110056.busticketbackend.service.PassengerService;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private static final Logger logger = LoggerFactory.getLogger(PassengerController.class);

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        logger.info("Fetching passenger with id {}", id);
        Optional<Passenger> passenger = passengerService.getPassengerById(id);
        if (passenger.isPresent()) {
            logger.info("Passenger found with id {}", id);
        } else {
            logger.warn("No passenger found with id {}", id);
        }
        return passenger.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Passenger> savePassenger(@RequestBody Passenger passenger) {
        logger.info("Saving passenger with name {}", passenger.getName().replaceAll("[\n\r]", "_"));
        Passenger savedPassenger = passengerService.savePassenger(passenger);
        logger.info("Passenger saved with id {}", savedPassenger.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPassenger);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassengerById(@PathVariable Long id) {
        logger.info("Deleting passenger with id {}", id);
        passengerService.deletePassengerById(id);
        return ResponseEntity.noContent().build();
    }
    
}
