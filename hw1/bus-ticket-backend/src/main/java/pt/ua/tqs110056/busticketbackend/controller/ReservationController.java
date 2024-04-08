package pt.ua.tqs110056.busticketbackend.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            logger.info("Creating reservation for passenger with id {}", reservation.getPassenger().getId());
            return ResponseEntity.status(201).body(reservationService.createReservation(reservation));
        } catch (NullPointerException e) {
            logger.warn("Invalid reservation at createReservation");
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error at createReservation", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        if (!reservation.isPresent()) {
            logger.warn("No reservation found with id {}", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("Reservation found with id {}", id);
        return ResponseEntity.ok(reservation.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable Long id) {
        logger.info("Deleting reservation with id {}", id);
        reservationService.deleteReservationById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateReservationStatus(@PathVariable Long id,
            @RequestParam("action") String action) {
        boolean success = false;
        String message = "";

        if ("cancel".equalsIgnoreCase(action)) {
            logger.info("Cancelling reservation with id {}", id);
            success = reservationService.cancelReservation(id);
            message = success ? "Reservation cancelled successfully" : "Reservation not found";
        } else if ("confirm".equalsIgnoreCase(action)) {
            logger.info("Confirming reservation with id {}", id);
            success = reservationService.confirmReservation(id);
            message = success ? "Reservation confirmed successfully" : "Reservation not found";
        } else {
            logger.warn("Invalid action at updateReservationStatus");
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
        logger.info("Fetching all reservations");
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<Iterable<Reservation>> getReservationsByPassengerId(@PathVariable Long passengerId) {
        logger.info("Fetching reservations for passenger with id {}", passengerId);
        return ResponseEntity.ok(reservationService.getReservationsByPassengerId(passengerId));
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<Iterable<Reservation>> getReservationsByTripId(@PathVariable Long tripId) {
        logger.info("Fetching reservations for trip with id {}", tripId);
        return ResponseEntity.ok(reservationService.getReservationsByTripId(tripId));
    }

}
