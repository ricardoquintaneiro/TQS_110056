package pt.ua.tqs110056.busticketbackend.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.tqs110056.busticketbackend.model.Bus;
import pt.ua.tqs110056.busticketbackend.model.BusSeat;
import pt.ua.tqs110056.busticketbackend.model.BusSeatType;
import pt.ua.tqs110056.busticketbackend.repository.BusRepository;
import pt.ua.tqs110056.busticketbackend.service.impl.BusServiceImpl;

@ExtendWith(MockitoExtension.class)
class BusServiceTest {

    @Mock(lenient = true)
    private BusRepository busRepository;

    @InjectMocks
    private BusServiceImpl busService;

    private Bus bus;

    @BeforeEach
    void setUp() {
        bus = new Bus();
        bus.setId(1L);
    }

    @Test
    void whenValidId_thenBusShouldBeFound() {
        long id = bus.getId();
        Mockito.when(busRepository.findById(id)).thenReturn(Optional.of(bus));

        Optional<Bus> result = busService.getBusById(id);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(bus));
    }

    @Test
    void whenInvalidId_thenBusShouldNotBeFound() {
        long id = -1L;
        Mockito.when(busRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Bus> result = busService.getBusById(id);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    void whenGetAllBuses_thenAllBusesShouldBeFound() {
        Bus bus2 = new Bus();
        List<Bus> buses = new ArrayList<>();
        buses.add(bus);
        buses.add(bus2);
        Mockito.when(busRepository.findAll()).thenReturn(buses);

        List<Bus> result = busService.getAllBuses();

        assertThat(result, equalTo(buses));
    }

    @Test
    void whenGetAllBusSeats_thenAllBusSeatsShouldBeFound() {
        long busId = bus.getId();
        List<BusSeat> busSeats = new ArrayList<>();
        busSeats.add(new BusSeat(BusSeatType.PREMIUM, "31B"));
        busSeats.add(new BusSeat(BusSeatType.REGULAR, "14A"));
        bus.setSeats(busSeats);
        Mockito.when(busRepository.findById(busId)).thenReturn(Optional.of(bus));

        Optional<List<BusSeat>> result = busService.getAllBusSeats(busId);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(busSeats));
    }

    @Test
    void whenGetAllBusSeatsAndBusDoesNotExist_thenItShouldReturnEmptyOptional() {
        long busId = bus.getId();
        Mockito.when(busRepository.findById(busId)).thenReturn(Optional.empty());

        Optional<List<BusSeat>> result = busService.getAllBusSeats(busId);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    void whenGetBusSeatsByType_thenBusSeatsByTypeShouldBeFound() {
        long busId = bus.getId();
        List<BusSeat> busSeats = new ArrayList<>();
        BusSeat premiumSeat = new BusSeat(BusSeatType.PREMIUM, "31B");
        busSeats.add(premiumSeat);
        busSeats.add(new BusSeat(BusSeatType.REGULAR, "14A"));
        bus.setSeats(busSeats);
        Mockito.when(busRepository.findById(busId)).thenReturn(Optional.of(bus));

        Optional<List<BusSeat>> result = busService.getBusSeatsByType(busId, BusSeatType.PREMIUM);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(Arrays.asList(premiumSeat)));
    }

    @Test
    void whenGetBusSeatsByType_AndThereAreNoSeatsOfThatType_thenNoSeatsShouldBeFound() {
        long busId = bus.getId();
        List<BusSeat> busSeats = new ArrayList<>();
        busSeats.add(new BusSeat(BusSeatType.REGULAR, "14A"));
        bus.setSeats(busSeats);
        Mockito.when(busRepository.findById(busId)).thenReturn(Optional.of(bus));

        Optional<List<BusSeat>> result = busService.getBusSeatsByType(busId, BusSeatType.PREMIUM);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().size(), is(0));
    }

    @Test
    void whenGetBusSeatsByTypeAndBusDoesNotExist_thenItShouldReturnEmptyOptional() {
        long busId = bus.getId();
        Mockito.when(busRepository.findById(busId)).thenReturn(Optional.empty());

        Optional<List<BusSeat>> result = busService.getBusSeatsByType(busId, BusSeatType.PREMIUM);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    void whenReserveSeat_thenSeatShouldBeReserved() {
        long busId = bus.getId();
        String seatNumber = "31B";
        BusSeat seat = new BusSeat(BusSeatType.PREMIUM, seatNumber);
        List<BusSeat> busSeats = new ArrayList<>();
        busSeats.add(seat);
        bus.setSeats(busSeats);
        Mockito.when(busRepository.findById(busId)).thenReturn(Optional.of(bus));

        boolean result = busService.reserveSeat(busId, seatNumber);

        assertThat(result, is(true));
        assertThat(bus.getSeatAvailability(0), is(false));
    }

    @Test
    void whenReserveSeat_AndSeatDoesNotExist_thenSeatShouldNotBeReserved() {
        long busId = bus.getId();
        String seatNumber = "31B";
        BusSeat seat = new BusSeat(BusSeatType.PREMIUM, "14A");
        List<BusSeat> busSeats = new ArrayList<>();
        busSeats.add(seat);
        bus.setSeats(busSeats);
        Mockito.when(busRepository.findById(busId)).thenReturn(Optional.of(bus));

        boolean result = busService.reserveSeat(busId, seatNumber);

        assertThat(result, is(false));
    }

    @Test
    void whenMakeSeatAvailable_thenSeatShouldBeAvailable() {
        long busId = bus.getId();
        String seatNumber = "31B";
        BusSeat seat = new BusSeat(BusSeatType.PREMIUM, seatNumber);
        List<BusSeat> busSeats = new ArrayList<>();
        busSeats.add(seat);
        bus.setSeats(busSeats);
        bus.setSeatAvailability(0, false);
        Mockito.when(busRepository.findById(busId)).thenReturn(Optional.of(bus));

        boolean result = busService.makeSeatAvailable(busId, seatNumber);

        assertThat(result, is(true));
        assertThat(bus.getSeatAvailability(0), is(true));
    }

    @Test
    void whenMakeSeatAvailable_AndSeatDoesNotExist_thenSeatShouldNotBeAvailable() {
        long busId = bus.getId();
        String seatNumber = "31B";
        BusSeat seat = new BusSeat(BusSeatType.PREMIUM, "14A");
        List<BusSeat> busSeats = new ArrayList<>();
        busSeats.add(seat);
        bus.setSeats(busSeats);
        Mockito.when(busRepository.findById(busId)).thenReturn(Optional.of(bus));

        boolean result = busService.makeSeatAvailable(busId, seatNumber);

        assertThat(result, is(false));
    }

    @Test
    void whenMakeAllSeatsAvailable_thenAllSeatsShouldBeAvailable() {
        long busId = bus.getId();
        List<BusSeat> busSeats = new ArrayList<>();
        busSeats.add(new BusSeat(BusSeatType.PREMIUM, "31B"));
        busSeats.add(new BusSeat(BusSeatType.REGULAR, "14A"));
        bus.setSeats(busSeats);
        bus.setSeatAvailability(0, false);
        bus.setSeatAvailability(1, false);
        Mockito.when(busRepository.findById(busId)).thenReturn(Optional.of(bus));

        boolean result = busService.makeAllSeatsAvailable(busId);

        assertThat(result, is(true));
        assertThat(bus.getSeatAvailability(0), is(true));
        assertThat(bus.getSeatAvailability(1), is(true));
    }

    @Test
    void whenMakeAllSeatsAvailable_AndBusDoesNotExist_thenItShouldReturnFalse() {
        long busId = bus.getId();
        Mockito.when(busRepository.findById(busId)).thenReturn(Optional.empty());

        boolean result = busService.makeAllSeatsAvailable(busId);

        assertThat(result, is(false));
    }

    @Test
    void whenIsSeatAvailable_ThenItShouldReturnTheSeatAvailability() {
        long busId = bus.getId();
        String seatNumber1 = "31B";
        String seatNumber2 = "32B";
        BusSeat seat1 = new BusSeat(BusSeatType.PREMIUM, seatNumber1);
        BusSeat seat2 = new BusSeat(BusSeatType.PREMIUM, seatNumber2);
        List<BusSeat> busSeats = new ArrayList<>();
        busSeats.add(seat1);
        busSeats.add(seat2);
        bus.setSeats(busSeats);
        bus.setSeatAvailability(0, false);
        bus.setSeatAvailability(1, true);
        Mockito.when(busRepository.findById(busId)).thenReturn(Optional.of(bus));

        boolean result1 = busService.isSeatAvailable(busId, seatNumber1);
        boolean result2 = busService.isSeatAvailable(busId, seatNumber2);

        assertThat(result1, is(false));
        assertThat(result2, is(true));
    }

}
