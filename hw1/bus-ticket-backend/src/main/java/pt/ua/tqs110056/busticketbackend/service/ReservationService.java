package pt.ua.tqs110056.busticketbackend.service;

import java.util.List;
import java.util.Optional;

import pt.ua.tqs110056.busticketbackend.model.Reservation;

public interface ReservationService {
    
    Reservation createReservation(Reservation reservation);

    Optional<Reservation> getReservationById(Long id);

    void deleteReservationById(Long id);

    boolean cancelReservation(Long id);

    List<Reservation> getAllReservations();

    List<Reservation> getReservationsByPassengerId(Long passengerId);
    
    List<Reservation> getReservationsByTripId(Long tripId);

}
