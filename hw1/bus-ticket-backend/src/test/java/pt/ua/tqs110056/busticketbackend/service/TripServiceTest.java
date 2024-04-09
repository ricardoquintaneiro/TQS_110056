package pt.ua.tqs110056.busticketbackend.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.tqs110056.busticketbackend.model.City;
import pt.ua.tqs110056.busticketbackend.model.Trip;
import pt.ua.tqs110056.busticketbackend.repository.TripRepository;
import pt.ua.tqs110056.busticketbackend.service.impl.TripServiceImpl;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripServiceImpl tripService;

    private Trip trip;
    private Trip trip2;

    @BeforeEach
    void setUp() {
        trip = new Trip();
        trip.setId(1L);
        trip2 = new Trip();
        trip2.setId(2L);
    }

    @Test
    void whenFindTripsByOriginAndDestination_ThenItShouldReturnTrips() {
        City origin = new City("Aveiro", "Portugal");
        origin.setId(1L);
        City destination = new City("Coimbra", "Portugal");
        origin.setId(2L);
        trip.setOrigin(origin);
        trip.setDestination(destination);
        trip2.setOrigin(origin);
        trip2.setDestination(destination);
        List<Trip> trips = List.of(trip, trip2);
        Mockito.when(tripRepository.findByOriginIdAndDestinationId(1L, 2L)).thenReturn(trips);

        List<Trip> foundTrips = tripService.findTripsByOriginAndDestination(1L, 2L);

        assertThat(foundTrips, equalTo(trips));
    }

    @Test
    void whenFindTripsByOriginAndDestinationAndNoTripsExists_ThenItShouldReturnEmptyList() {
        List<Trip> trips = List.of();
        Mockito.when(tripRepository.findByOriginIdAndDestinationId(1L, 2L)).thenReturn(trips);

        List<Trip> foundTrips = tripService.findTripsByOriginAndDestination(1L, 2L);

        assertThat(foundTrips.isEmpty(), is(true));
    }

    @Test
    void whenFindTripsByDepartureDate_ThenItShouldReturnTrips() {
        LocalDate departureDate = LocalDate.now();
        trip.setDepartureTime(departureDate.atStartOfDay());
        trip2.setDepartureTime(departureDate.atTime(LocalTime.MAX));
        List<Trip> trips = List.of(trip, trip2);
        Mockito.when(tripRepository.findByDepartureTimeBetween(departureDate.atStartOfDay(),
                departureDate.atTime(LocalTime.MAX))).thenReturn(trips);

        List<Trip> foundTrips = tripService.findTripsByDepartureDate(departureDate);

        assertThat(foundTrips, equalTo(trips));
    }

    @Test
    void whenFindTripsByDepartureDateAndNoTripsExists_ThenItShouldReturnEmptyList() {
        LocalDate departureDate = LocalDate.now();
        List<Trip> trips = List.of();
        Mockito.when(tripRepository.findByDepartureTimeBetween(departureDate.atStartOfDay(),
                departureDate.atTime(LocalTime.MAX))).thenReturn(trips);

        List<Trip> foundTrips = tripService.findTripsByDepartureDate(departureDate);

        assertThat(foundTrips.isEmpty(), is(true));
    }

    @Test
    void whenFindTripsByOriginAndDestinationAndDepartureDate_ThenItShouldReturnTrips() {
        City origin = new City("Aveiro", "Portugal");
        origin.setId(1L);
        City destination = new City("Coimbra", "Portugal");
        origin.setId(2L);
        LocalDate departureDate = LocalDate.now();
        trip.setOrigin(origin);
        trip.setDestination(destination);
        trip.setDepartureTime(departureDate.atStartOfDay());
        trip2.setOrigin(origin);
        trip2.setDestination(destination);
        trip2.setDepartureTime(departureDate.atTime(LocalTime.MAX));
        List<Trip> trips = List.of(trip, trip2);
        Mockito.when(tripRepository.findByOriginIdAndDestinationIdAndDepartureTimeBetween(1L, 2L,
                departureDate.atStartOfDay(), departureDate.atTime(LocalTime.MAX))).thenReturn(trips);

        List<Trip> foundTrips = tripService.findTripsByOriginAndDestinationAndDepartureDate(1L, 2L, departureDate);

        assertThat(foundTrips, equalTo(trips));
    }

    @Test
    void whenfindAllTrips_ThenItShouldReturnAllTrips() {
        List<Trip> trips = List.of(trip, trip2);
        Mockito.when(tripRepository.findAll()).thenReturn(trips);

        List<Trip> foundTrips = tripService.findAllTrips();

        assertThat(foundTrips, equalTo(trips));
    }

}