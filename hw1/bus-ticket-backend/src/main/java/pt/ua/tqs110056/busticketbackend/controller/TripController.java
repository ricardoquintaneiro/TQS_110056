package pt.ua.tqs110056.busticketbackend.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.tqs110056.busticketbackend.model.Trip;
import pt.ua.tqs110056.busticketbackend.service.TripService;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private static final Logger logger = LoggerFactory.getLogger(TripController.class);

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping()
    public ResponseEntity<Iterable<Trip>> findTrips(@RequestParam(required = false) Long originId,
            @RequestParam(required = false) Long destinationId,
            @RequestParam(required = false) LocalDate departureDate) {
        if (originId != null && destinationId != null && departureDate != null) {
            logger.info("Fetching trips with originId {}, destinationId {} and departureDate {}", originId, destinationId,
                    departureDate);
            return ResponseEntity.ok(tripService.findTripsByOriginAndDestinationAndDepartureDate(originId,
                    destinationId, departureDate));
        } else if (originId != null && destinationId != null) {
            logger.info("Fetching trips with originId {} and destinationId {}", originId, destinationId);
            return ResponseEntity.ok(tripService.findTripsByOriginAndDestination(originId, destinationId));
        } else if (departureDate != null) {
            logger.info("Fetching trips with departureDate {}", departureDate);
            return ResponseEntity.ok(tripService.findTripsByDepartureDate(departureDate));
        } else if (originId == null && destinationId == null && departureDate == null) {
            logger.info("Fetching all trips");
            return ResponseEntity.ok(tripService.findAllTrips());
        } else {
            logger.warn("Invalid parameters at findTrips");
            return ResponseEntity.badRequest().build();
        }
    }

}
