package pt.ua.tqs110056.busticketbackend.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ua.tqs110056.busticketbackend.model.Trip;
import pt.ua.tqs110056.busticketbackend.repository.TripRepository;
import pt.ua.tqs110056.busticketbackend.service.TripService;

@Service
public class TripServiceImpl implements TripService {
    
    private final TripRepository tripRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public List<Trip> findTripsByOriginAndDestination(long originId, long destinationId) {
        return tripRepository.findByOriginIdAndDestinationId(originId, destinationId);
    }

    @Override
    public List<Trip> findTripsByDepartureDate(LocalDate departureDate) {
        return tripRepository.findByDepartureDate(departureDate);
    }

    @Override
    public List<Trip> findTripsByOriginAndDestinationAndDepartureDate(long originId, long destinationId,
            LocalDate departureDate) {
        return tripRepository.findByOriginIdAndDestinationIdAndDepartureDate(originId, destinationId, departureDate);
    }

    @Override
    public List<Trip> findAllTrips() {
        return tripRepository.findAll();
    }

}
