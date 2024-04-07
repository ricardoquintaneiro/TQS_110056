package pt.ua.tqs110056.busticketbackend.service;

import java.time.LocalDate;
import java.util.List;

import pt.ua.tqs110056.busticketbackend.model.Trip;

public interface TripService {

    List<Trip> findTripsByOriginAndDestination(long originId, long destinationId);

    List<Trip> findTripsByDepartureDate(LocalDate departureDate);

    List<Trip> findTripsByOriginAndDestinationAndDepartureDate(long originId, long destinationId, LocalDate departureDate);

    List<Trip> findAllTrips();

}