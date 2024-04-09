package pt.ua.tqs110056.busticketbackend.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.tqs110056.busticketbackend.model.Passenger;
import pt.ua.tqs110056.busticketbackend.model.Reservation;
import pt.ua.tqs110056.busticketbackend.model.ReservationStatus;
import pt.ua.tqs110056.busticketbackend.model.Trip;
import pt.ua.tqs110056.busticketbackend.repository.ReservationRepository;
import pt.ua.tqs110056.busticketbackend.service.impl.ReservationServiceImpl;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
        reservation.setId(1L);
    }

    @Test
    void whenCreateReservation_ThenItShouldReturnSavedReservation() {
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation savedReservation = reservationService.createReservation(reservation);

        assertThat(savedReservation, equalTo(reservation));
    }

    @Test
    void whenGetReservationById_ThenItShouldReturnReservation() {
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        Optional<Reservation> foundReservation = reservationService.getReservationById(1L);

        assertThat(foundReservation.get(), equalTo(reservation));
    }

    @Test
    void whenGetReservationByIdAndNoReservationExists_ThenItShouldReturnEmptyOptional() {
        Mockito.when(reservationRepository.findById(-1L)).thenReturn(Optional.empty());

        Optional<Reservation> foundReservation = reservationService.getReservationById(-1L);

        assertThat(foundReservation.isEmpty(), is(true));
    }

    @Test
    void whenDeleteReservationById_ThenItShouldDeleteReservation() {
        reservationService.deleteReservationById(1L);

        Mockito.verify(reservationRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void whenCancelReservation_ThenItShouldReturnTrue() {
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        boolean cancelled = reservationService.cancelReservation(1L);

        assertThat(cancelled, is(true));
    }

    @Test
    void whenCancelReservationAndNoReservationExists_ThenItShouldReturnFalse() {
        Mockito.when(reservationRepository.findById(-1L)).thenReturn(Optional.empty());

        boolean cancelled = reservationService.cancelReservation(-1L);

        assertThat(cancelled, is(false));
    }

    @Test
    void whenGetAllReservations_ThenItShouldReturnAllReservations() {
        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        Mockito.when(reservationRepository.findAll()).thenReturn(List.of(reservation, reservation2));

        List<Reservation> foundReservations = reservationService.getAllReservations();

        assertThat(foundReservations.size(), is(2));
        assertThat(foundReservations.get(0), equalTo(reservation));
        assertThat(foundReservations.get(1), equalTo(reservation2));
    }

    @Test
    void whenGetReservationsByPassengerId_ThenItShouldReturnReservations() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setPassenger(passenger);
        reservation.setPassenger(passenger);
        Mockito.when(reservationRepository.findByPassengerId(1L)).thenReturn(List.of(reservation, reservation2));

        List<Reservation> foundReservations = reservationService.getReservationsByPassengerId(1L);

        assertThat(foundReservations, equalTo(List.of(reservation, reservation2)));
    }

    @Test
    void whenGetReservationsByNonExistingPassengerId_ThenItShouldReturnEmptyList() {
        Mockito.when(reservationRepository.findByPassengerId(-1L)).thenReturn(List.of());

        List<Reservation> foundReservations = reservationService.getReservationsByPassengerId(-1L);

        assertThat(foundReservations.isEmpty(), is(true));
    }

    @Test
    void whenGetReservationsByTripId_ThenItShouldReturnReservations() {
        Trip trip = new Trip();
        trip.setId(1L);
        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setTrip(trip);
        reservation.setTrip(trip);
        Mockito.when(reservationRepository.findByTripId(1L)).thenReturn(List.of(reservation, reservation2));

        List<Reservation> foundReservations = reservationService.getReservationsByTripId(1L);

        assertThat(foundReservations, equalTo(List.of(reservation, reservation2)));
    }

    @Test
    void whenGetReservationsByNonExistingTripId_ThenItShouldReturnEmptyList() {
        Mockito.when(reservationRepository.findByTripId(-1L)).thenReturn(List.of());

        List<Reservation> foundReservations = reservationService.getReservationsByTripId(-1L);

        assertThat(foundReservations.isEmpty(), is(true));
    }

    @Test
    void whenConfirmReservation_ThenItShouldReturnTrue() {
        reservation.setStatus(ReservationStatus.PENDING);
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        boolean confirmed = reservationService.confirmReservation(1L);

        assertThat(confirmed, is(true));
        assertThat(reservation.getStatus(), equalTo(ReservationStatus.CONFIRMED));
    }

    @Test
    void whenConfirmReservationAndNoReservationExists_ThenItShouldReturnFalse() {
        reservation.setStatus(ReservationStatus.PENDING);
        Mockito.when(reservationRepository.findById(-1L)).thenReturn(Optional.empty());

        boolean confirmed = reservationService.confirmReservation(-1L);

        assertThat(confirmed, is(false));
        assertThat(reservation.getStatus(), equalTo(ReservationStatus.PENDING));
    }
}