package pt.ua.tqs110056.busticketbackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ua.tqs110056.busticketbackend.model.Reservation;
import pt.ua.tqs110056.busticketbackend.model.ReservationStatus;
import pt.ua.tqs110056.busticketbackend.repository.ReservationRepository;
import pt.ua.tqs110056.busticketbackend.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public void deleteReservationById(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public boolean cancelReservation(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            reservation.get().setStatus(ReservationStatus.CANCELLED);
            return true;
        }
        return false;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getReservationsByPassengerId(Long passengerId) {
        return reservationRepository.findByPassengerId(passengerId);
    }

    @Override
    public List<Reservation> getReservationsByTripId(Long tripId) {
        return reservationRepository.findByTripId(tripId);
    }
    
    @Override
    public boolean confirmReservation(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            reservation.get().setStatus(ReservationStatus.CONFIRMED);
            return true;
        }
        return false;
    }
}
