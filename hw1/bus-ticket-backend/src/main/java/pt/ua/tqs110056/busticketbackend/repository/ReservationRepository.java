package pt.ua.tqs110056.busticketbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs110056.busticketbackend.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findById(long id);

    Reservation findByTripAndSeatNumber(long tripId, int seatNumber);

    List<Reservation> findByPassengerId(long passengerId);

    List<Reservation> findByTripId(long tripId);

    int countByTripId(long tripId);
}
