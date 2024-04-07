package pt.ua.tqs110056.busticketbackend.controller;

import java.util.List;
import java.util.Optional;

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
@RequestMapping("/buses")
public class BusController {

    private final BusService busService;

    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Bus> getBusById(@PathVariable long id) {
        Optional<Bus> bus = busService.getBusById(id);
        return bus.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<Iterable<Bus>> getAllBuses() {
        Iterable<Bus> buses = busService.getAllBuses();
        return ResponseEntity.ok(buses);
    }

    @GetMapping("{id}/seats")
    public ResponseEntity<List<BusSeat>> getBusSeats(@PathVariable long id,
            @RequestParam(required = false) String type) {
        Optional<List<BusSeat>> seats;
        if (type != null) {
            try {
                BusSeatType seatType = BusSeatType.valueOf(type.toUpperCase());
                seats = busService.getBusSeatsByType(id, seatType);
                return seats.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            seats = busService.getAllBusSeats(id);
            return seats.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }
    }

    @PutMapping("{id}/seats/{seatNumber}")
    public ResponseEntity<String> updateSeatAvailability(@PathVariable long id, @PathVariable String seatNumber, @RequestParam boolean reserve) {
        boolean success = false;
        String message;

        if (reserve) {
            success = busService.reserveSeat(id, seatNumber);
            message = success ? "Seat reserved successfully" : "Seat not available";
        } else {
            success = busService.makeSeatAvailable(id, seatNumber);
            message = success ? "Seat made available successfully" : "Seat was already available";
        }

        if (success) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}/seats/all/available")
    public ResponseEntity<String> makeAllSeatsAvailable(@PathVariable long id) {
        boolean success = busService.makeAllSeatsAvailable(id);
        if (success) {
            return ResponseEntity.ok("All seats made available successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}/seats/{seatNumber}/available")
    public ResponseEntity<Boolean> isSeatAvailable(@PathVariable long id, @PathVariable String seatNumber) {
        boolean available = busService.isSeatAvailable(id, seatNumber);
        return ResponseEntity.ok(available);
    }

}
