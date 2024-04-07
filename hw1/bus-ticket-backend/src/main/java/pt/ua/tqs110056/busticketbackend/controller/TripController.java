package pt.ua.tqs110056.busticketbackend.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.tqs110056.busticketbackend.model.Trip;
import pt.ua.tqs110056.busticketbackend.service.TripService;

@RestController
@RequestMapping("/trips")
public class TripController {

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
            return ResponseEntity.ok(tripService.findTripsByOriginAndDestinationAndDepartureDate(originId,
                    destinationId, departureDate));
        } else if (originId != null && destinationId != null) {
            return ResponseEntity.ok(tripService.findTripsByOriginAndDestination(originId, destinationId));
        } else if (departureDate != null) {
            return ResponseEntity.ok(tripService.findTripsByDepartureDate(departureDate));
        } else {
            return ResponseEntity.ok(tripService.findAllTrips());
        }
    }

}
