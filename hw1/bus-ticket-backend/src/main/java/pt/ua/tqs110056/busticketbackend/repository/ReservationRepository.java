package pt.ua.tqs110056.busticketbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs110056.busticketbackend.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByTripIdAndSeatNumber(Long tripId, String seatNumber);

    List<Reservation> findByPassengerId(Long passengerId);

    List<Reservation> findByTripId(Long tripId);

    int countByTripId(Long tripId);
}
