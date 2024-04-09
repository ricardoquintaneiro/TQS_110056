package pt.ua.tqs110056.busticketbackend.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import pt.ua.tqs110056.busticketbackend.model.BusSeat;
import pt.ua.tqs110056.busticketbackend.model.BusSeatType;
import pt.ua.tqs110056.busticketbackend.model.Passenger;
import pt.ua.tqs110056.busticketbackend.model.Reservation;
import pt.ua.tqs110056.busticketbackend.model.Trip;

@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void findById_ShouldReturnReservation_WhenIdExists() {
        Reservation reservation = new Reservation();
        entityManager.persistAndFlush(reservation);

        Optional<Reservation> found = reservationRepository.findById(reservation.getId());
        assertThat(found.isPresent(), is(true));
        assertThat(found.get(), equalTo(reservation));
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenIdDoesNotExist() {
        Optional<Reservation> found = reservationRepository.findById(Long.valueOf(-1L));

        assertThat(found, is(Optional.empty()));
    }

    @Test
    void findByPassengerId_ShouldReturnListOfReservations_WhenPassengerIdExists() {
        Passenger passenger = new Passenger();
        Reservation reservation1 = new Reservation();
        reservation1.setPassenger(passenger);
        Reservation reservation2 = new Reservation();
        reservation2.setPassenger(passenger);
        Reservation reservation3 = new Reservation();
        entityManager.persistAndFlush(passenger);
        entityManager.persistAndFlush(reservation1);
        entityManager.persistAndFlush(reservation2);
        entityManager.persistAndFlush(reservation3);

        List<Reservation> found = reservationRepository.findByPassengerId(passenger.getId());

        assertThat(found, equalTo(List.of(reservation1, reservation2)));
    }

    @Test
    void findByPassengerId_ShouldReturnEmptyList_WhenPassengerIdDoesNotExist() {
        List<Reservation> found = reservationRepository.findByPassengerId(Long.valueOf(-1L));

        assertThat(found.isEmpty(), is(true));
    }

    @Test
    void findByTripIdAndSeatNumber_ShouldReturnReservation_WhenTripIdAndSeatNumberExist() {
        Reservation reservation = new Reservation();
        Trip trip = new Trip();
        BusSeat busSeat = new BusSeat(BusSeatType.REGULAR, "1A");
        reservation.setTrip(trip);
        reservation.setSeat(busSeat);
        entityManager.persistAndFlush(busSeat);
        entityManager.persistAndFlush(trip);
        entityManager.persistAndFlush(reservation);

        Optional<Reservation> found = reservationRepository.findByTripIdAndSeatNumber(trip.getId(), busSeat.getNumber());

        assertThat(found.isPresent(), is(true));
        assertThat(found.get(), equalTo(reservation));
    }

    @Test
    void findByTripIdAndSeatNumber_ShouldReturnEmptyOptional_WhenTripIdAndSeatNumberDoNotExist() {
        Optional<Reservation> found = reservationRepository.findByTripIdAndSeatNumber(Long.valueOf(-1L), "1A");

        assertThat(found, is(Optional.empty()));
    }

    @Test
    void findByTripId_ShouldReturnReservation_WhenTripIdExists() {
        Reservation reservation = new Reservation();
        Trip trip = new Trip();
        reservation.setTrip(trip);
        entityManager.persistAndFlush(trip);
        entityManager.persistAndFlush(reservation);

        List<Reservation> found = reservationRepository.findByTripId(trip.getId());

        assertThat(found, equalTo(List.of(reservation)));
    }

    @Test
    void findByTripId_ShouldReturnEmptyList_WhenTripIdDoesNotExist() {
        List<Reservation> found = reservationRepository.findByTripId(Long.valueOf(-1L));

        assertThat(found.isEmpty(), is(true));
    }

    @Test
    void countByTripId_ShouldReturnNumberOfReservations_WhenTripIdExists() {
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        Trip trip = new Trip();
        reservation1.setTrip(trip);
        reservation2.setTrip(trip);
        entityManager.persistAndFlush(trip);
        entityManager.persistAndFlush(reservation1);
        entityManager.persistAndFlush(reservation2);

        long count = reservationRepository.countByTripId(trip.getId());

        assertThat(count, equalTo(2L));
    }
}