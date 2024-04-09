package pt.ua.tqs110056.busticketbackend.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import pt.ua.tqs110056.busticketbackend.model.City;
import pt.ua.tqs110056.busticketbackend.model.Trip;


@DataJpaTest
class TripRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TripRepository tripRepository;

    @Test
    void findById_ShouldReturnTrip_WhenIdExists() {
        Trip trip = new Trip();
        entityManager.persistAndFlush(trip);

        Optional<Trip> found = tripRepository.findById(trip.getId());

        assertThat(found.isPresent(), is(true));
        assertThat(found.get(), equalTo(trip));
    }
    
    @Test
    void findById_ShouldReturnEmptyOptional_WhenIdDoesNotExist() {
        Optional<Trip> found = tripRepository.findById(Long.valueOf(-1L));

        assertThat(found, is(Optional.empty()));
    }

    @Test
    void findByOriginAndDestination_ShouldReturnListOfTrips_WhenOriginAndDestinationExist() {
        City aveiro = new City("Aveiro", "Portugal");
        City porto = new City("Porto", "Portugal");
        Trip trip1 = new Trip();
        trip1.setOrigin(aveiro);
        trip1.setDestination(porto);
        Trip trip2 = new Trip();
        trip2.setOrigin(aveiro);
        trip2.setDestination(porto);
        Trip trip3 = new Trip();
        trip3.setOrigin(porto);
        trip3.setDestination(aveiro);
        entityManager.persistAndFlush(aveiro);
        entityManager.persistAndFlush(porto);
        entityManager.persistAndFlush(trip1);
        entityManager.persistAndFlush(trip2);
        entityManager.persistAndFlush(trip3);

        List<Trip> found = tripRepository.findByOriginIdAndDestinationId(aveiro.getId(), porto.getId());

        assertThat(found.size(), is(2));
    }

    @Test
    void findByOriginAndDestination_ShouldReturnEmptyList_WhenOriginAndDestinationDoNotExist() {
        List<Trip> found = tripRepository.findByOriginIdAndDestinationId(Long.valueOf(-1L), Long.valueOf(-1L));

        assertThat(found.isEmpty(), is(true));
    }

    @Test
    void findByDepartureTimeBetween_ShouldReturnListOfTrips_WhenDepartureIntervalExists() {
        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime departureStartOfDay = departureTime.toLocalDate().atStartOfDay();
        LocalDateTime departureEndOfDay = departureTime.toLocalDate().atTime(LocalTime.MAX);
        Trip trip1 = new Trip();
        trip1.setDepartureTime(departureTime);
        Trip trip2 = new Trip();
        trip2.setDepartureTime(departureTime);
        Trip trip3 = new Trip();
        entityManager.persistAndFlush(trip1);
        entityManager.persistAndFlush(trip2);
        entityManager.persistAndFlush(trip3);

        List<Trip> found = tripRepository.findByDepartureTimeBetween(departureStartOfDay, departureEndOfDay);

        assertThat(found.size(), is(2));
    }

    @Test
    void findByDepartureTimeBetween_ShouldReturnEmptyList_WhenDepartureIntervalDoesNotExist() {
        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime departureStartOfDay = departureTime.toLocalDate().atStartOfDay();
        LocalDateTime departureEndOfDay = departureTime.toLocalDate().atTime(LocalTime.MAX);
        List<Trip> found = tripRepository.findByDepartureTimeBetween(departureStartOfDay, departureEndOfDay);

        assertThat(found.isEmpty(), is(true));
    }

    @Test
    void findByOriginIdAndDestinationIdAndDepartureTimeBetween_ShouldReturnListOfTrips_WhenOriginAndDestinationAndDepartureIntervalExist() {
        City aveiro = new City("Aveiro", "Portugal");
        City porto = new City("Porto", "Portugal");
        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime departureStartOfDay = departureTime.toLocalDate().atStartOfDay();
        LocalDateTime departureEndOfDay = departureTime.toLocalDate().atTime(LocalTime.MAX);
        Trip trip1 = new Trip();
        trip1.setOrigin(aveiro);
        trip1.setDestination(porto);
        trip1.setDepartureTime(departureTime);
        Trip trip2 = new Trip();
        trip2.setOrigin(aveiro);
        trip2.setDestination(porto);
        trip2.setDepartureTime(departureTime);
        Trip trip3 = new Trip();
        trip3.setOrigin(porto);
        trip3.setDestination(aveiro);
        trip3.setDepartureTime(departureTime);
        entityManager.persistAndFlush(aveiro);
        entityManager.persistAndFlush(porto);
        entityManager.persistAndFlush(trip1);
        entityManager.persistAndFlush(trip2);
        entityManager.persistAndFlush(trip3);

        List<Trip> found = tripRepository.findByOriginIdAndDestinationIdAndDepartureTimeBetween(aveiro.getId(), porto.getId(), departureStartOfDay, departureEndOfDay);

        assertThat(found.size(), is(2));
    }

    @Test
    void findByOriginIdAndDestinationIdAndDepartureTimeBetween_ShouldReturnEmptyList_WhenOriginAndDestinationAndDepartureIntervalDoNotExist() {
        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime departureStartOfDay = departureTime.toLocalDate().atStartOfDay();
        LocalDateTime departureEndOfDay = departureTime.toLocalDate().atTime(LocalTime.MAX);
        List<Trip> found = tripRepository.findByOriginIdAndDestinationIdAndDepartureTimeBetween(Long.valueOf(-1L), Long.valueOf(-1L), departureStartOfDay, departureEndOfDay);

        assertThat(found.isEmpty(), is(true));
    }
}