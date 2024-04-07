package pt.ua.tqs110056.busticketbackend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.tqs110056.busticketbackend.model.Reservation;
import pt.ua.tqs110056.busticketbackend.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        if (reservation == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(reservationService.createReservation(reservation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        if (!reservation.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation.get());
    }

    @DeleteMapping("/{id}")
    public void deleteReservationById(@PathVariable Long id) {
        reservationService.deleteReservationById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateReservationStatus(@PathVariable Long id,
            @RequestParam("action") String action) {
        boolean success = false;
        String message = "";

        if ("cancel".equalsIgnoreCase(action)) {
            success = reservationService.cancelReservation(id);
            message = success ? "Reservation cancelled successfully" : "Reservation not found";
        } else if ("confirm".equalsIgnoreCase(action)) {
            success = reservationService.confirmReservation(id);
            message = success ? "Reservation confirmed successfully" : "Reservation not found";
        } else {
            message = "Invalid action";
            return ResponseEntity.badRequest().body(message);
        }

        if (success) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<Iterable<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<Iterable<Reservation>> getReservationsByPassengerId(@PathVariable Long passengerId) {
        return ResponseEntity.ok(reservationService.getReservationsByPassengerId(passengerId));
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<Iterable<Reservation>> getReservationsByTripId(@PathVariable Long tripId) {
        return ResponseEntity.ok(reservationService.getReservationsByTripId(tripId));
    }

}
