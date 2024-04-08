package pt.ua.tqs110056.busticketbackend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs110056.busticketbackend.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByOriginIdAndDestinationId(Long originId, Long destinationId);

    List<Trip> findByDepartureTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Trip> findByOriginIdAndDestinationIdAndDepartureTimeBetween(Long originId, Long destinationId, LocalDateTime start, LocalDateTime end);

}
