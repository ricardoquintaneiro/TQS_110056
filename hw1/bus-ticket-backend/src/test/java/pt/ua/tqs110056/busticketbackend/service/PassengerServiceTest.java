package pt.ua.tqs110056.busticketbackend.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.tqs110056.busticketbackend.model.Passenger;
import pt.ua.tqs110056.busticketbackend.repository.PassengerRepository;
import pt.ua.tqs110056.busticketbackend.service.impl.PassengerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceTest {

    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerServiceImpl passengerService;

    private Passenger passenger;

    @BeforeEach
    public void setUp() {
        passenger = new Passenger("Santos Silva", "santossilva@example.com", "+351123456789");
        passenger.setId(1L);
    }

    @Test
    public void whenGetPassengerById_ThenItShouldReturnPassenger() {
        Mockito.when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));

        Optional<Passenger> foundPassenger = passengerService.getPassengerById(1L);

        assertThat(foundPassenger.get(), equalTo(passenger));
    }

    @Test
    public void whenGetPassengerByIdAndNoPassengerExists_ThenItShouldReturnEmptyOptional() {
        Mockito.when(passengerRepository.findById(-1L)).thenReturn(Optional.empty());

        Optional<Passenger> foundPassenger = passengerService.getPassengerById(-1L);

        assertThat(foundPassenger.isEmpty(), is(true));
    }

    @Test
    public void whenSavePassenger_ThenItShouldReturnSavedPassenger() {
        Mockito.when(passengerRepository.save(passenger)).thenReturn(passenger);

        Passenger savedPassenger = passengerService.savePassenger(passenger);

        assertThat(savedPassenger, equalTo(passenger));
    }

    @Test
    public void whenDeletePassengerById_ThenItShouldDeletePassenger() {
        passengerService.deletePassengerById(1L);

        Mockito.verify(passengerRepository, Mockito.times(1)).deleteById(1L);
    }

}