package pt.ua.tqs110056.busticketbackend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs110056.busticketbackend.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    Trip findById(long id);
    
    List<Trip> findByOriginIdAndDestinationId(long originId, long destinationId);

    List<Trip> findByDepartureTime(LocalDate departureTime);

    List<Trip> findByOriginIdAndDestinationIdAndDepartureTime(long originId, long destinationId, LocalDate departureTime);

}
