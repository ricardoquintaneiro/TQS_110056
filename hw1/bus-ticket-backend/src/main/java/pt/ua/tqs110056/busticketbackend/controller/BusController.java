package pt.ua.tqs110056.busticketbackend.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.tqs110056.busticketbackend.model.Bus;
import pt.ua.tqs110056.busticketbackend.model.BusSeat;
import pt.ua.tqs110056.busticketbackend.model.BusSeatType;
import pt.ua.tqs110056.busticketbackend.service.BusService;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    private static final Logger logger = LoggerFactory.getLogger(BusController.class);

    private final BusService busService;

    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Bus> getBusById(@PathVariable long id) {
        logger.info("Fetching bus with id {}", id);
        Optional<Bus> bus = busService.getBusById(id);
        if (bus.isPresent()) {
            logger.info("Bus found with id {}", id);
        } else {
            logger.warn("No bus found with id {}", id);
        }
        return bus.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<Iterable<Bus>> getAllBuses() {
        logger.info("Fetching all buses");
        Iterable<Bus> buses = busService.getAllBuses();
        return ResponseEntity.ok(buses);
    }

    @GetMapping("{id}/seats")
    public ResponseEntity<List<BusSeat>> getBusSeats(@PathVariable long id,
            @RequestParam(required = false) String type) {
        Optional<List<BusSeat>> seats;
        if (type != null) {
            logger.info("Fetching seats of type {} for bus with id {}", type, id);
            try {
                BusSeatType seatType = BusSeatType.valueOf(type.toUpperCase());
                seats = busService.getBusSeatsByType(id, seatType);
                if (seats.isPresent()) {
                    logger.info("Seats found for bus with id {}", id);
                } else {
                    logger.warn("No seats found for bus with id {}", id);
                }
                return seats.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
            } catch (IllegalArgumentException e) {
                logger.error("Invalid seat type provided: {}", type);
                return ResponseEntity.badRequest().build();
            }
        } else {
            logger.info("Fetching all seats for bus with id {}", id);
            seats = busService.getAllBusSeats(id);
            if (seats.isPresent()) {
                logger.info("Seats found for bus with id {}", id);
            } else {
                logger.warn("No seats found for bus with id {}", id);
            }
            return seats.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }
    }

    @PutMapping("{id}/seats/{seatNumber}")
    public ResponseEntity<String> updateSeatAvailability(@PathVariable long id, @PathVariable String seatNumber,
            @RequestParam boolean reserve) {
        boolean success = false;
        String message;

        if (reserve) {
            logger.info("Reserving seat {} in bus with id {}", seatNumber, id);
            success = busService.reserveSeat(id, seatNumber);
            message = success ? "Seat reserved successfully" : "Seat not available";
            if (success) {
                logger.info("Seat {} in bus with id {} was reserved successfully", seatNumber, id);
            } else {
                logger.warn("Seat {} in bus with id {} was not available", seatNumber, id);
            }
        } else {
            logger.info("Making seat {} available in bus with id {}", seatNumber, id);
            success = busService.makeSeatAvailable(id, seatNumber);
            message = success ? "Seat made available successfully" : "Seat was already available";
            if (success) {
                logger.info("Seat {} in bus with id {} was made available successfully", seatNumber, id);
            } else {
                logger.warn("Seat {} in bus with id {} was already available", seatNumber, id);
            }
        }

        if (success) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.unprocessableEntity().body(message);
        }
    }

    @PutMapping("{id}/seats/all/available")
    public ResponseEntity<String> makeAllSeatsAvailable(@PathVariable long id) {
        logger.info("Making all seats available for bus with id {}", id);
        boolean success = busService.makeAllSeatsAvailable(id);
        if (success) {
            logger.info("All seats made available successfully for bus with id {}", id);
            return ResponseEntity.ok("All seats made available successfully");
        } else {
            logger.warn("Failed to make seats available for bus with id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}/seats/{seatNumber}/available")
    public ResponseEntity<Boolean> isSeatAvailable(@PathVariable long id, @PathVariable String seatNumber) {
        logger.info("Checking if seat {} is available in bus with id {}", seatNumber, id);
        boolean available = busService.isSeatAvailable(id, seatNumber);
        if (available) {
            logger.info("Seat {} is available in bus with id {}", seatNumber, id);
        } else {
            logger.warn("Seat {} is not available in bus with id {}", seatNumber, id);
        }
        return ResponseEntity.ok(available);
    }

}
